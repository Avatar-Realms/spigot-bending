package net.avatarrealms.minecraft.bending.abilities.fire;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.avatarrealms.minecraft.bending.controller.ConfigManager;
import net.avatarrealms.minecraft.bending.model.Abilities;
import net.avatarrealms.minecraft.bending.model.AvatarState;
import net.avatarrealms.minecraft.bending.model.BendingPlayer;
import net.avatarrealms.minecraft.bending.model.BendingType;
import net.avatarrealms.minecraft.bending.model.IAbility;
import net.avatarrealms.minecraft.bending.utils.EntityTools;
import net.avatarrealms.minecraft.bending.utils.PluginTools;
import net.avatarrealms.minecraft.bending.utils.Tools;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class Lightning implements IAbility {
	private static Map<Player, Lightning> instances = new HashMap<Player, Lightning>();
	private static Map<Entity, Lightning> strikes = new HashMap<Entity, Lightning>();
	
	public static int defaultdistance = ConfigManager.lightningRange;
	private static long defaultwarmup = ConfigManager.lightningWarmup;
	private static double misschance = ConfigManager.lightningMissChance;
	private static double threshold = 0.1;
	private static double blockdistance = 4;

	private int maxdamage = 6;
	private double strikeradius = 4;

	private Player player;
	private long starttime;
	private boolean charged = false;
	private LightningStrike strike = null;
	private List<Entity> hitentities = new LinkedList<Entity>();
	private IAbility parent;

	public Lightning(Player player, IAbility parent) {
		this.parent = parent;
		if (instances.containsKey(player)) {
			return;
		}
		this.player = player;
		starttime = System.currentTimeMillis();
		instances.put(player, this);
	}

	public static Lightning getLightning(Entity entity) {
		return strikes.get(entity);
	}

	private void strike() {
		Location targetlocation = getTargetLocation();
		if (AvatarState.isAvatarState(player))
			maxdamage = AvatarState.getValue(maxdamage);
		if (!Tools.isRegionProtectedFromBuild(player, Abilities.Lightning,
				targetlocation)) {
			strike = player.getWorld().strikeLightning(targetlocation);
			strikes.put(strike, this);
		}
	}

	private Location getTargetLocation() {
		int distance = (int) PluginTools.firebendingDayAugment(defaultdistance,
				player.getWorld());

		Location targetlocation;
		targetlocation = EntityTools.getTargetedLocation(player, distance);
		Entity target = EntityTools.getTargettedEntity(player, distance);
		if (target != null) {
			if (target instanceof LivingEntity
					&& player.getLocation().distance(targetlocation) > target
							.getLocation().distance(player.getLocation())) {
				targetlocation = target.getLocation();
				if (target.getVelocity().length() < threshold) {
					misschance = 0;
				}
				if (((target instanceof Player) ||(target instanceof Monster)) && (target.getEntityId() != player.getEntityId())) {
					BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
					if (bPlayer != null) {
						bPlayer.earnXP(BendingType.Fire, this);
					}
				}
			}
		} else {
			misschance = 0;
		}

		if (targetlocation.getBlock().getType() == Material.AIR)
			targetlocation.add(0, -1, 0);
		if (targetlocation.getBlock().getType() == Material.AIR)
			targetlocation.add(0, -1, 0);

		if (misschance != 0 && !AvatarState.isAvatarState(player)) {
			double A = Math.random() * Math.PI * misschance * misschance;
			double theta = Math.random() * Math.PI * 2;
			double r = Math.sqrt(A) / Math.PI;
			double x = r * Math.cos(theta);
			double z = r * Math.sin(theta);

			targetlocation = targetlocation.add(x, 0, z);
		}

		return targetlocation;
	}
	
	private void remove() {
		instances.remove(player);
	}

	private boolean progress() {
		if (player.isDead() || !player.isOnline()) {
			return false;
		}

		if (EntityTools.getBendingAbility(player) != Abilities.Lightning) {
			return false;
		}

		int distance = (int) PluginTools.firebendingDayAugment(defaultdistance,
				player.getWorld());
		long warmup = (int) ((double) defaultwarmup / ConfigManager.dayFactor);
		if (AvatarState.isAvatarState(player))
			warmup = 0;
		if (System.currentTimeMillis() > starttime + warmup)
			charged = true;

		if (charged) {
			if (player.isSneaking()) {
				player.getWorld().playEffect(
						player.getEyeLocation(),
						Effect.SMOKE,
						Tools.getIntCardinalDirection(player.getEyeLocation()
								.getDirection()), distance);
			} else {
				strike();
				return false;
			}
		} else {
			if (!player.isSneaking()) {
				return false;
			}
		}
		return true;
	}

	public void dealDamage(Entity entity) {
		if (strike == null) {
			return;
		}
		if (hitentities.contains(entity)) {
			return;
		}
		double distance = entity.getLocation().distance(strike.getLocation());
		if (distance > strikeradius)
			return;
		double damage = maxdamage - (distance / strikeradius) * .5;
		hitentities.add(entity);
		EntityTools.damageEntity(player, entity, (int) damage);
	}

	public static boolean isNearbyChannel(Location location) {
		boolean value = false;
		for (Player player : instances.keySet()) {
			if (!player.getWorld().equals(location.getWorld()))
				continue;
			if (player.getLocation().distance(location) <= blockdistance) {
				value = true;
				instances.get(player).starttime = 0;
			}
		}
		return value;
	}

	public static void progressAll() {
		List<Lightning> toRemove = new LinkedList<Lightning>();
		for (Lightning lightning : instances.values()) {
			boolean keep = lightning.progress();
			if(!keep) {
				toRemove.add(lightning);
			}
		}
		for (Lightning lightning : toRemove) {
			lightning.remove();
		}
	}
	
	public static void removeAll() {
		instances.clear();
	}

	public static String getDescription() {
		return "Hold sneak while selecting this ability to charge up a lightning strike. Once "
				+ "charged, release sneak to discharge the lightning to the targetted location.";
	}

	@Override
	public int getBaseExperience() {
		return 12;
	}

	@Override
	public IAbility getParent() {
		return parent;
	}

}
