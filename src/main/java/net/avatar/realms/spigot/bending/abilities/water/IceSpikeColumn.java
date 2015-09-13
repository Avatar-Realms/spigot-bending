package net.avatar.realms.spigot.bending.abilities.water;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.avatar.realms.spigot.bending.abilities.Abilities;
import net.avatar.realms.spigot.bending.abilities.BendingPlayer;
import net.avatar.realms.spigot.bending.abilities.TempPotionEffect;
import net.avatar.realms.spigot.bending.abilities.base.IAbility;
import net.avatar.realms.spigot.bending.controller.ConfigurationParameter;
import net.avatar.realms.spigot.bending.utils.EntityTools;
import net.avatar.realms.spigot.bending.utils.ProtectionManager;
import net.avatar.realms.spigot.bending.utils.Tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class IceSpikeColumn {
	private static Map<Integer, IceSpikeColumn> instances = new HashMap<Integer, IceSpikeColumn>();
	private Map<Player, Long> removeTimers = new HashMap<Player, Long>();
	private static Map<Player, Long> cooldowns = new HashMap<Player, Long>();
	private static long removeTimer = 500;
	private static Map<Block, Integer> baseblocks = new HashMap<Block, Integer>();

	private static int ID = Integer.MIN_VALUE;

	@ConfigurationParameter("Range")
	private static double RANGE = 20;
	
	@ConfigurationParameter("Cooldown")
	private static long COOLDOWN = 2000;
	
	@ConfigurationParameter("Damage")
	private static int DAMAGE = 4;
	
	@ConfigurationParameter("Throw-Mult")
	private static double THROW_MULT = 0.7;
	
	private static double speed = 25;
	private static final Vector direction = new Vector(0, 1, 0);

	private static long interval = (long) (1000. / speed);

	private Location origin;
	private Location location;
	private Block block;
	private int progress = 0;
	private int damage = DAMAGE;
	int id;
	private long time;
	int height = 2;
	private Vector thrown = new Vector(0, THROW_MULT, 0);
	private Map<Block, Block> affectedblocks = new HashMap<Block, Block>();
	private List<LivingEntity> damaged = new ArrayList<LivingEntity>();
	private Player player;

	public IceSpikeColumn(Player player, IAbility parent) {
		this.player = player;
		if (cooldowns.containsKey(player))
			if (cooldowns.get(player) + COOLDOWN >= System.currentTimeMillis())
				return;
		try {
			double lowestdistance = RANGE + 1;
			Entity closestentity = null;
			for (LivingEntity entity : EntityTools.getLivingEntitiesAroundPoint(
					player.getLocation(), RANGE)) {
				if(ProtectionManager.isEntityProtectedByCitizens(entity)) {
					continue;
				}
				if (Tools.getDistanceFromLine(player.getLocation()
						.getDirection(), player.getLocation(), entity
						.getLocation()) <= 2
						&& (entity.getEntityId() != player.getEntityId())) {
					double distance = player.getLocation().distance(
							entity.getLocation());
					if (distance < lowestdistance) {
						closestentity = entity;
						lowestdistance = distance;
					}
				}
			}
			if (closestentity != null) {
				Block temptestingblock = closestentity.getLocation().getBlock()
						.getRelative(BlockFace.DOWN, 1);
				// if (temptestingblock.getType() == Material.ICE){
				this.block = temptestingblock;
				// }
			} else {
				this.block = EntityTools.getTargetBlock(player, RANGE);
			}
			origin = block.getLocation();
			location = origin.clone();

		} catch (IllegalStateException e) {
			return;
		}

		loadAffectedBlocks();

		if (height != 0) {
			if (canInstantiate()) {
				id = ID;
				instances.put(id, this);
				if (ID >= Integer.MAX_VALUE) {
					ID = Integer.MIN_VALUE;
				}
				ID++;
				time = System.currentTimeMillis() - interval;
				cooldowns.put(player, System.currentTimeMillis());
			}
		}
	}

	public IceSpikeColumn(Player player, Location origin, int damage,
			Vector throwing, long aoecooldown, SpikeField spikeField) {
		this.player = player;
		this.origin = origin;
		location = origin.clone();
		block = location.getBlock();
		this.damage = damage;
		this.thrown = throwing;

		loadAffectedBlocks();

		if (block.getType() == Material.ICE) {
			if (canInstantiate()) {
				id = ID;
				instances.put(id, this);
				if (ID >= Integer.MAX_VALUE) {
					ID = Integer.MIN_VALUE;
				}
				ID++;
				time = System.currentTimeMillis() - interval;
			}
		}
	}

	private void loadAffectedBlocks() {
		affectedblocks.clear();
		Block thisblock;
		for (int i = 1; i <= height; i++) {
			thisblock = block.getWorld().getBlockAt(
					location.clone().add(direction.clone().multiply(i)));
			affectedblocks.put(thisblock, thisblock);
		}
	}

	private boolean blockInAffectedBlocks(Block block) {
		return affectedblocks.containsKey(block);
	}

	private static boolean blockInAllAffectedBlocks(Block block) {
		for (IceSpikeColumn spike : instances.values()) {
			if (spike.blockInAffectedBlocks(block))
				return true;
		}
		return false;
	}

	private boolean canInstantiate() {
		if (block.getType() != Material.ICE)
			return false;
		for (Block block : affectedblocks.keySet()) {
			if (blockInAllAffectedBlocks(block)
					|| block.getType() != Material.AIR
					|| (block.getX() == player.getEyeLocation().getBlock()
							.getX() && block.getZ() == player.getEyeLocation()
							.getBlock().getZ())) {
				return false;
			}
		}
		return true;
	}

	public boolean progress() {
		if (System.currentTimeMillis() - time >= interval) {
			time = System.currentTimeMillis();
			if (progress < height) {
				moveEarth();
				removeTimers.put(player, System.currentTimeMillis());
			} else {
				if (removeTimers.get(player) + removeTimer <= System
						.currentTimeMillis()) {
					baseblocks.put(
							location.clone()
									.add(direction.clone().multiply(
											-1 * (height))).getBlock(),
							(height - 1));
					if (!revertblocks()) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean moveEarth() {
		progress++;
		Block affectedblock = location.clone().add(direction).getBlock();
		location = location.add(direction);
		if (ProtectionManager.isRegionProtectedFromBending(player, Abilities.IceSpike,
				location))
			return false;
		for (LivingEntity en : EntityTools.getLivingEntitiesAroundPoint(location, 1.4)) {
			if (en != player && !damaged.contains(((LivingEntity) en))) {
				LivingEntity le = (LivingEntity) en;
				affect(le);
			}
		}
		affectedblock.setType(Material.ICE);
		loadAffectedBlocks();

		if (location.distance(origin) >= height) {
			return false;
		}

		return true;
	}

	private void affect(LivingEntity entity) {
		if(ProtectionManager.isEntityProtectedByCitizens(entity)) {
			return;
		}
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		entity.setVelocity(thrown);
		entity.damage(damage);
		damaged.add(entity);
		long slowCooldown = IceSpike.slowCooldown;
		int mod = 2;
		if (entity instanceof Player) {
			if (bPlayer.canBeSlowed()) {
				PotionEffect effect = new PotionEffect(PotionEffectType.SLOW,
						70, mod);
				new TempPotionEffect(entity, effect);
				bPlayer.slow(slowCooldown);
			}
		} else {
			PotionEffect effect = new PotionEffect(PotionEffectType.SLOW, 70,
					mod);
			new TempPotionEffect(entity, effect);
		}

	}

	private static boolean blockIsBase(Block block) {
		if (baseblocks.containsKey(block)) {
			return true;
		}
		return false;
	}

	public boolean revertblocks() {
		Vector direction = new Vector(0, -1, 0);
		location.getBlock().setType(Material.AIR);// .clone().add(direction).getBlock().setType(Material.AIR);
		location.add(direction);
		if (blockIsBase(location.getBlock()))
			return false;
		return true;
	}

}
