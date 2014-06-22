package net.avatarrealms.minecraft.bending.abilities.water;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import net.avatarrealms.minecraft.bending.controller.ConfigManager;
import net.avatarrealms.minecraft.bending.controller.Flight;
import net.avatarrealms.minecraft.bending.model.Abilities;
import net.avatarrealms.minecraft.bending.model.BendingPlayer;
import net.avatarrealms.minecraft.bending.model.TempBlock;
import net.avatarrealms.minecraft.bending.utils.BlockTools;
import net.avatarrealms.minecraft.bending.utils.EntityTools;
import net.avatarrealms.minecraft.bending.utils.PluginTools;
import net.avatarrealms.minecraft.bending.utils.Tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class WaterSpout {

	public static Map<Player, WaterSpout> instances = new HashMap<Player, WaterSpout>();
	public static List<Block> affectedblocks = new LinkedList<Block>();
	public static List<Block> newaffectedblocks = new LinkedList<Block>();
	public static List<Block> baseblocks = new LinkedList<Block>();

	private static final int defaultheight = ConfigManager.waterSpoutHeight;

	// private static final double threshold = .05;
	// private static final byte half = 0x4;
	private static final byte full = 0x0;
	private int currentCardinalPoint = 0;
	private Player player;
	private Block base;
	private TempBlock baseblock;
	

	public WaterSpout(Player player) {
		if (BendingPlayer.getBendingPlayer(player).isOnCooldown(
				Abilities.WaterSpout))
			return;

		if (instances.containsKey(player)) {
			instances.get(player).remove();
			return;
		}
		this.player = player;
		new Flight(player);
		player.setAllowFlight(true);
		instances.put(player, this);
		spout(player);
	}

	private void remove() {
		revertBaseBlock(player);
		instances.remove(player);
	}

	public static void handleSpouts(Server server) {
		// affectedblocks.clear();
		newaffectedblocks.clear();

		for (Player player : instances.keySet()) {
			if (!player.isOnline() || player.isDead()) {
				instances.get(player).remove();
			} else if (EntityTools.hasAbility(player, Abilities.WaterSpout)
					&& EntityTools.canBend(player, Abilities.WaterSpout)) {
				spout(player);
			} else {
				instances.get(player).remove();
			}
		}

		List<Block> toRemove = new LinkedList<Block>();
		for(Block block : affectedblocks) {
			if (!newaffectedblocks.contains(block)) {
				toRemove.add(block);
			}
		}
		for (Block block : toRemove) {
			remove(block);
		}
	}

	private static void remove(Block block) {
		affectedblocks.remove(block);
		TempBlock.revertBlock(block, Material.AIR);
		// block.setType(Material.AIR);
		// block.setData(half);
	}

	public static void spout(Player player) {
		WaterSpout spout = instances.get(player);
		player.setFallDistance(0);
		player.setSprinting(false);

		player.removePotionEffect(PotionEffectType.SPEED);
		Location location = player.getLocation().clone().add(0, .2, 0);
		Block block = location.clone().getBlock();
		int height = spoutableWaterHeight(location, player);

		// Tools.verbose(height + " " + WaterSpout.height + " "
		// + affectedblocks.size());
		if (height != -1) {
			location = spout.base.getLocation();
			for (int i = 1, cardinalPoint = (int)(spout.currentCardinalPoint/10); i <= height; i++, cardinalPoint++) {
				if (cardinalPoint == 8) {cardinalPoint = 0;}
				
				block = location.clone().add(0, i, 0).getBlock();
				if (!TempBlock.isTempBlock(block)) {
					new TempBlock(block, Material.WATER, full);
				}
				// block.setType(Material.WATER);
				// block.setData(full);
				if (!affectedblocks.contains(block)) {
					affectedblocks.add(block);
				}
				newaffectedblocks.add(block);
				
				switch (cardinalPoint) {
				case 0 : block = location.clone().add(0, i, -1).getBlock(); break;
				case 1 : block = location.clone().add(-1, i, -1).getBlock(); break;
				case 2 : block = location.clone().add(-1, i, 0).getBlock(); break;
				case 3 : block = location.clone().add(-1, i, 1).getBlock(); break;
				case 4 : block = location.clone().add(0, i, 1).getBlock(); break;
				case 5 : block = location.clone().add(1, i, 1).getBlock(); break;
				case 6 : block = location.clone().add(1, i, 0).getBlock(); break;
				case 7 : block = location.clone().add(1, i, -1).getBlock(); break;
				}
				spout.currentCardinalPoint ++;
				if (spout.currentCardinalPoint == 10*8) {
					spout.currentCardinalPoint = 0;
				}
				if (!TempBlock.isTempBlock(block)) {
					new TempBlock(block, Material.WATER, full);
				}
				if (!affectedblocks.contains(block)) {
					affectedblocks.add(block);
				}
				newaffectedblocks.add(block);	
			}
			if (player.getLocation().getBlockY() > block.getY()) {
				player.setFlying(false);
			} else {
				new Flight(player);
				player.setAllowFlight(true);
				player.setFlying(true);
			}
		} else {
			instances.get(player).remove();
		}
	}

	private static int spoutableWaterHeight(Location location, Player player) {
		WaterSpout spout = instances.get(player);
		int height = defaultheight;
		if (Tools.isNight(player.getWorld()))
			height = (int) PluginTools.waterbendingNightAugment((double) height,
					player.getWorld());
		int maxheight = (int) ((double) defaultheight * ConfigManager.nightFactor) + 5;
		Block blocki;
		for (int i = 0; i < maxheight; i++) {
			blocki = location.clone().add(0, -i, 0).getBlock();
			if (Tools.isRegionProtectedFromBuild(player, Abilities.WaterSpout,
					blocki.getLocation()))
				return -1;
			if (!affectedblocks.contains(blocki)) {
				if (blocki.getType() == Material.WATER
						|| blocki.getType() == Material.STATIONARY_WATER) {
					if (!TempBlock.isTempBlock(blocki)) {
						revertBaseBlock(player);
					}
					spout.base = blocki;
					if (i > height)
						return height;
					return i;
				}
				if (blocki.getType() == Material.ICE
						|| blocki.getType() == Material.SNOW
						|| blocki.getType() == Material.SNOW_BLOCK) {
					if (!TempBlock.isTempBlock(blocki)) {
						revertBaseBlock(player);
						instances.get(player).baseblock = new TempBlock(blocki,
								Material.WATER, full);
					}
					spout.base = blocki;
					if (i > height)
						return height;
					return i;
				}
				if ((blocki.getType() != Material.AIR && (!BlockTools.isPlant(blocki) 
						|| !EntityTools.canPlantbend(player)))) {
					revertBaseBlock(player);
					return -1;
				}
			}
		}
		revertBaseBlock(player);
		return -1;
	}

	public static void revertBaseBlock(Player player) {
		if (instances.containsKey(player)) {
			if (instances.get(player).baseblock != null) {
				instances.get(player).baseblock.revertBlock();
				instances.get(player).baseblock = null;
			}
		}
	}

	public static void removeAll() {
		for (Player player : instances.keySet()) {
			instances.get(player).remove();
		}
		for (Block block : affectedblocks) {
			// block.setType(Material.AIR);
			TempBlock.revertBlock(block, Material.AIR);
		}
		affectedblocks.clear();
	}

	public static ArrayList<Player> getPlayers() {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player : instances.keySet())
			players.add(player);
		return players;
	}

	public static void removeSpouts(Location loc0, double radius,
			Player sourceplayer) {
		for (Player player : instances.keySet()) {
			if (!player.equals(sourceplayer)) {
				Location loc1 = player.getLocation().getBlock().getLocation();
				loc0 = loc0.getBlock().getLocation();
				double dx = loc1.getX() - loc0.getX();
				double dy = loc1.getY() - loc0.getY();
				double dz = loc1.getZ() - loc0.getZ();

				double distance = Math.sqrt(dx * dx + dz * dz);

				if (distance <= radius && dy > 0 && dy < defaultheight)
					instances.get(player).remove();
			}
		}
	}

	public static String getDescription() {
		return "To use this ability, click while over or in water. "
				+ "You will spout water up from beneath you to experience controlled levitation. "
				+ "This ability is a toggle, so you can activate it then use other abilities and it "
				+ "will remain on. If you try to spout over an area with no water, snow or ice, "
				+ "the spout will dissipate and you will fall. Click again with this ability selected to deactivate it.";
	}
}