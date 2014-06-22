package net.avatarrealms.minecraft.bending.abilities.water;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.avatarrealms.minecraft.bending.model.Abilities;
import net.avatarrealms.minecraft.bending.model.BendingPlayer;
import net.avatarrealms.minecraft.bending.model.BendingType;
import net.avatarrealms.minecraft.bending.model.TempBlock;
import net.avatarrealms.minecraft.bending.utils.BlockTools;
import net.avatarrealms.minecraft.bending.utils.EntityTools;
import net.avatarrealms.minecraft.bending.utils.PluginTools;
import net.avatarrealms.minecraft.bending.utils.Tools;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TorrentBurst {
	private static Map<Integer, TorrentBurst> instances = new HashMap<Integer, TorrentBurst>();

	private static int ID = Integer.MIN_VALUE;
	private static double defaultmaxradius = 15;
	private static double dr = 0.5;
	private static double defaultfactor = 1.5;
	private static long interval = Torrent.interval;

	private static final byte full = 0x0;
	// private static final Vector reference = new Vector(1, 0, 0);

	private Player player;
	private int id;
	private long time;
	private Location origin;
	private double radius = dr;
	private double maxradius = defaultmaxradius;
	private double factor = defaultfactor;
	private Map<Integer, Map<Integer, Double>> heights = new HashMap<Integer, Map<Integer, Double>>();
	private List<TempBlock> blocks = new LinkedList<TempBlock>();
	private List<Entity> affectedentities = new LinkedList<Entity>();

	public TorrentBurst(Player player) {
		this(player, player.getEyeLocation(), dr);
	}

	public TorrentBurst(Player player, Location location) {
		this(player, location, dr);
	}

	public TorrentBurst(Player player, double radius) {
		this(player, player.getEyeLocation(), radius);
	}

	public TorrentBurst(Player player, Location location, double radius) {
		this.player = player;
		World world = player.getWorld();
		origin = location.clone();
		time = System.currentTimeMillis();
		id = ID++;
		factor = PluginTools.waterbendingNightAugment(factor, world);
		maxradius = PluginTools.waterbendingNightAugment(maxradius, world);
		this.radius = radius;
		if (ID >= Integer.MAX_VALUE) {
			ID = Integer.MIN_VALUE;
		}
		initializeHeightsMap();
		instances.put(id, this);
	}

	private void initializeHeightsMap() {
		for (int i = -1; i <= 1; i++) {
			Map<Integer, Double> angles = new HashMap<Integer, Double>();
			double dtheta = Math.toDegrees(1 / (maxradius + 2));
			int j = 0;
			for (double theta = 0; theta < 360; theta += dtheta) {
				angles.put(j, theta);
				j++;
			}
			heights.put(i, angles);
		}
	}

	private boolean progress() {
		if (player.isDead() || !player.isOnline()) {
			return false;
		}

		if (!EntityTools.canBend(player, Abilities.Torrent)) {
			return false;
		}

		if (System.currentTimeMillis() > time + interval) {
			if (radius < maxradius) {
				radius += dr;
			} else {
				returnWater();
				return false;
			}

			boolean result = formBurst();
			time = System.currentTimeMillis();
			return result;
		}
		return true;
	}

	private boolean formBurst() {
		for (TempBlock tempBlock : blocks) {
			tempBlock.revertBlock();
		}

		blocks.clear();

		affectedentities.clear();

		List<Entity> indexlist = new LinkedList<Entity>();
		indexlist.addAll(EntityTools.getEntitiesAroundPoint(origin, radius + 2));

		List<Block> torrentblocks = new LinkedList<Block>();

		if (indexlist.contains(player))
			indexlist.remove(player);

		List<Integer> toRemoveHeights = new LinkedList<Integer>();
		for (Entry<Integer, Map<Integer, Double>> entry : heights.entrySet()) {
			int id = entry.getKey();
			List<Integer> toRemoveAngles = new LinkedList<Integer>();
			Map<Integer, Double> angles = entry.getValue();
			for (Entry<Integer, Double> entryAngle : angles.entrySet()) {
				int index = entryAngle.getKey();
				double angle = entryAngle.getValue();
				double theta = Math.toRadians(angle);
				double dx = Math.cos(theta) * radius;
				double dy = id;
				double dz = Math.sin(theta) * radius;
				Location location = origin.clone().add(dx, dy, dz);
				Block block = location.getBlock();
				if (torrentblocks.contains(block))
					continue;
				if (BlockTools.isTransparentToEarthbending(player,
						Abilities.Torrent, block)) {
					TempBlock tempBlock = new TempBlock(block, Material.WATER,
							full);
					blocks.add(tempBlock);
					torrentblocks.add(block);
				} else {
					toRemoveAngles.add(index);
					continue;
				}
				for (Entity entity : indexlist) {
					if (!affectedentities.contains(entity)) {
						if (entity.getLocation().distance(location) <= 2) {
							affectedentities.add(entity);
							affect(entity);
						}
					}
				}
			}
			for(int angle : toRemoveAngles) {
				angles.remove(angle);
			}
			
			if (angles.isEmpty())
				toRemoveHeights.add(id);
		}
		
		for(int id : toRemoveHeights) {
			heights.remove(id);
		}
		
		if (heights.isEmpty())
			return false;
		
		return true;
	}

	private void affect(Entity entity) {
		Vector direction = Tools.getDirection(origin, entity.getLocation());
		direction.setY(0);
		direction.normalize();
		entity.setVelocity(entity.getVelocity().clone()
				.add(direction.multiply(factor)));
		if (((entity instanceof Player) ||(entity instanceof Monster)) && (entity.getEntityId() != player.getEntityId())){
			BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);
			if (bPlayer != null) {
				bPlayer.earnXP(BendingType.Water);
			}
		}
	}
	
	private void clear() {
		for (TempBlock block : blocks) {
			block.revertBlock();
		}
	}

	private void remove() {
		this.clear();
		instances.remove(id);
	}

	private void returnWater() {
		Location location = new Location(origin.getWorld(), origin.getX()
				+ radius, origin.getY(), origin.getZ());
		if (!location.getWorld().equals(player.getWorld()))
			return;
		if (location.distance(player.getLocation()) > maxradius + 5)
			return;
		new WaterReturn(player, location.getBlock());
	}

	public static void progressAll() {
		List<TorrentBurst> toRemove = new LinkedList<TorrentBurst>();
		for (TorrentBurst burst : instances.values()) {
			boolean keep = burst.progress();
			if(!keep) {
				toRemove.add(burst);
			}
		}
		for (TorrentBurst burst : toRemove) {
			burst.remove();
		}
	}

	public static void removeAll() {
		for (TorrentBurst burst : instances.values())
			burst.clear();
		
		instances.clear();
	}

}