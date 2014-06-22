package net.avatarrealms.minecraft.bending.abilities.earth;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import net.avatarrealms.minecraft.bending.controller.ConfigManager;
import net.avatarrealms.minecraft.bending.model.Abilities;
import net.avatarrealms.minecraft.bending.model.BendingPlayer;
import net.avatarrealms.minecraft.bending.model.BendingType;
import net.avatarrealms.minecraft.bending.model.TempBlock;
import net.avatarrealms.minecraft.bending.model.TempPotionEffect;
import net.avatarrealms.minecraft.bending.utils.BlockTools;
import net.avatarrealms.minecraft.bending.utils.EntityTools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EarthArmor {

	private static long duration = ConfigManager.earthArmorDuration;
	private static int strength = ConfigManager.earthArmorStrength;
	private static long cooldown = ConfigManager.earthArmorCooldown;
	private static int range = 7;

	private Player player;
	private Block headblock, legsblock;
	private Location headblocklocation, legsblocklocation;
	private Material headtype, legstype;
	private byte headdata, legsdata;
	private long time, starttime;
	private boolean formed = false;
	private boolean complete = false;
	public ItemStack[] oldarmor;

	private static long interval = 2000;
	public static ConcurrentHashMap<Player, EarthArmor> instances = new ConcurrentHashMap<Player, EarthArmor>();

	public EarthArmor(Player player) {
		if (instances.containsKey(player)) {
			return;
		}

		if (BendingPlayer.getBendingPlayer(player).isOnCooldown(
				Abilities.EarthArmor))
			return;

		this.player = player;
		
		headblock = EntityTools.getTargetBlock(player, range, BlockTools.getTransparentEarthbending());
		if (BlockTools.getEarthbendableBlocksLength(player, headblock, new Vector(0,
				-1, 0), 2) >= 2) {
			legsblock = headblock.getRelative(BlockFace.DOWN);
			headtype = headblock.getType();
			legstype = legsblock.getType();
			headdata = headblock.getData();
			legsdata = legsblock.getData();
			headblocklocation = headblock.getLocation();
			legsblocklocation = legsblock.getLocation();
			Block oldheadblock, oldlegsblock;
			oldheadblock = headblock;
			oldlegsblock = legsblock;
			if (!moveBlocks())
				return;
			if (ConfigManager.reverseearthbending) {
				BlockTools.addTempAirBlock(oldheadblock);
				BlockTools.addTempAirBlock(oldlegsblock);
			} else {
				BlockTools.removeBlock(oldheadblock);
				BlockTools.removeBlock(oldlegsblock);
			}
			instances.put(player, this);
		}
	}
	
	public static void EarthShield(Player player) {
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

		if (bPlayer.isOnCooldown(Abilities.EarthGrab))
			return;

		Entity closestentity = player;

		if (closestentity != null) {
			// Tools.verbose("grabbing");
			ArrayList<Block> blocks = new ArrayList<Block>();
			Location location = closestentity.getLocation();
			Location loc1 = location.clone();
			Location loc2 = location.clone();
			Location testloc, testloc2;
			double factor = 3;
			double factor2 = 4;
			int height1 = 3;
			int height2 = 2;
			for (double angle = 0; angle <= 360; angle += 20) {
				testloc = loc1.clone().add(
						factor * Math.cos(Math.toRadians(angle)), 1,
						factor * Math.sin(Math.toRadians(angle)));
				testloc2 = loc2.clone().add(
						factor2 * Math.cos(Math.toRadians(angle)), 1,
						factor2 * Math.sin(Math.toRadians(angle)));
				for (int y = 0; y < EarthColumn.standardheight - height1; y++) {
					testloc = testloc.clone().add(0, -1, 0);
					if (BlockTools.isEarthbendable(player, testloc.getBlock())) {
						if (!blocks.contains(testloc.getBlock())) {
							new EarthColumn(player, testloc, height1 + y - 1);
						}
						blocks.add(testloc.getBlock());
						break;
					}
				}
				for (int y = 0; y < EarthColumn.standardheight - height2; y++) {
					testloc2 = testloc2.clone().add(0, -1, 0);
					if (BlockTools.isEarthbendable(player, testloc2.getBlock())) {
						if (!blocks.contains(testloc2.getBlock())) {
							new EarthColumn(player, testloc2, height2 + y - 1);
						}
						blocks.add(testloc2.getBlock());
						break;
					}
				}
			}

			if (!blocks.isEmpty())
				bPlayer.cooldown(Abilities.EarthGrab);
		}
	}

	private boolean moveBlocks() {
		if (player.getWorld() != headblock.getWorld()) {
			cancel();
			return false;
		}

		Location headlocation = player.getEyeLocation();
		Location legslocation = player.getLocation();
		Vector headdirection = headlocation.toVector()
				.subtract(headblocklocation.toVector()).normalize()
				.multiply(.5);
		Vector legsdirection = legslocation.toVector()
				.subtract(legsblocklocation.toVector()).normalize()
				.multiply(.5);

		Block newheadblock = headblock;
		Block newlegsblock = legsblock;

		if (!headlocation.getBlock().equals(headblock)) {
			headblocklocation = headblocklocation.clone().add(headdirection);
			newheadblock = headblocklocation.getBlock();
		}
		if (!legslocation.getBlock().equals(legsblock)) {
			legsblocklocation = legsblocklocation.clone().add(legsdirection);
			newlegsblock = legsblocklocation.getBlock();
		}

		if (BlockTools.isTransparentToEarthbending(player, newheadblock)
				&& !newheadblock.isLiquid()) {
			BlockTools.breakBlock(newheadblock);
		} else if (!BlockTools.isEarthbendable(player, newheadblock)
				&& !newheadblock.isLiquid()
				&& newheadblock.getType() != Material.AIR) {
			cancel();
			return false;
		}

		if (BlockTools.isTransparentToEarthbending(player, newlegsblock)
				&& !newlegsblock.isLiquid()) {
			BlockTools.breakBlock(newlegsblock);
		} else if (!BlockTools.isEarthbendable(player, newlegsblock)
				&& !newlegsblock.isLiquid()
				&& newlegsblock.getType() != Material.AIR) {
			cancel();
			return false;
		}

		// if ((!Tools.isEarthbendable(player, newlegsblock)
		// && !newlegsblock.isLiquid() && newlegsblock.getType() !=
		// Material.AIR)
		// || (!Tools.isEarthbendable(player, newheadblock)
		// && !newheadblock.isLiquid() && newheadblock.getType() !=
		// Material.AIR)) {
		// cancel();
		// return false;
		// }

		if (headblock.getLocation().distance(player.getEyeLocation()) > range
				|| legsblock.getLocation().distance(player.getLocation()) > range) {
			cancel();
			return false;
		}

		if (!newheadblock.equals(headblock)) {
			new TempBlock(newheadblock, headtype, headdata);
			if (TempBlock.isTempBlock(headblock))
				TempBlock.revertBlock(headblock, Material.AIR);
		}

		if (!newlegsblock.equals(legsblock)) {
			new TempBlock(newlegsblock, legstype, legsdata);
			if (TempBlock.isTempBlock(legsblock))
				TempBlock.revertBlock(legsblock, Material.AIR);
		}

		headblock = newheadblock;
		legsblock = newlegsblock;

		return true;
	}

	private void cancel() {
		if (ConfigManager.reverseearthbending) {
			if (TempBlock.isTempBlock(headblock))
				TempBlock.revertBlock(headblock, Material.AIR);
			if (TempBlock.isTempBlock(legsblock))
				TempBlock.revertBlock(legsblock, Material.AIR);
		} else {
			headblock.breakNaturally();
			legsblock.breakNaturally();
		}
		if (instances.containsKey(player))
			instances.remove(player);
	}

	private boolean inPosition() {
		if (headblock.equals(player.getEyeLocation().getBlock())
				&& legsblock.equals(player.getLocation().getBlock())) {
			return true;
		}
		return false;
	}

	private void formArmor() {
		if (TempBlock.isTempBlock(headblock))
			TempBlock.revertBlock(headblock, Material.AIR);
		if (TempBlock.isTempBlock(legsblock))
			TempBlock.revertBlock(legsblock, Material.AIR);

		oldarmor = player.getInventory().getArmorContents();
		ItemStack armors[] = { new ItemStack(Material.LEATHER_BOOTS, 1),
				new ItemStack(Material.LEATHER_LEGGINGS, 1),
				new ItemStack(Material.LEATHER_CHESTPLATE, 1),
				new ItemStack(Material.LEATHER_HELMET, 1) };
		player.getInventory().setArmorContents(armors);
		PotionEffect resistance = new PotionEffect(
				PotionEffectType.DAMAGE_RESISTANCE, (int) duration / 50,
				strength - 1);
		new TempPotionEffect(player, resistance);
		// player.addPotionEffect(new PotionEffect(
		// PotionEffectType.DAMAGE_RESISTANCE, (int) duration / 50,
		// strength - 1));
		formed = true;
		starttime = System.currentTimeMillis();
		BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
		bPlayer.receiveXP(BendingType.Earth,2);
	}

	public static void moveArmor(Player player) {
		if (!instances.containsKey(player))
			return;
		EarthArmor eartharmor = instances.get(player);

		if (player.isDead() || !player.isOnline()) {
			eartharmor.cancel();
			eartharmor.removeEffect();
			return;
		}

		if (eartharmor.formed) {
			if (System.currentTimeMillis() > eartharmor.starttime + duration
					&& !eartharmor.complete) {
				eartharmor.complete = true;
				eartharmor.removeEffect();
				return;
			}
			if (System.currentTimeMillis() > eartharmor.starttime + cooldown) {
				instances.remove(player);
				return;
			}
		} else if (System.currentTimeMillis() > eartharmor.time + interval) {
			if (!eartharmor.moveBlocks())
				return;

			if (eartharmor.inPosition()) {
				eartharmor.formArmor();
			}
		}

	}

	private void removeEffect() {
		player.getInventory().setArmorContents(oldarmor);
		// player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		// instances.remove(player);
	}

	public static void removeEffect(Player player) {
		if (!instances.containsKey(player))
			return;
		instances.get(player).removeEffect();
	}

	public static void removeAll() {
		for (Player player : instances.keySet()) {
			EarthArmor eartharmor = instances.get(player);
			eartharmor.cancel();
			eartharmor.removeEffect();
		}
	}

	public static String getDescription() {
		return "This ability encases the earthbender in temporary armor. To use, click on a block that is earthbendable. If there is another block under"
				+ " it that is earthbendable, the block will fly to you and grant you temporary armor and damage reduction. This ability has a long cooldown.";
	}

	public static boolean canRemoveArmor(Player player) {
		if (instances.containsKey(player)) {
			EarthArmor eartharmor = instances.get(player);
			if (System.currentTimeMillis() < eartharmor.starttime + duration)
				return false;
		}
		return true;
	}
}