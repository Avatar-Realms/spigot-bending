package net.avatarrealms.minecraft.bending.listeners;

import net.avatarrealms.minecraft.bending.Bending;
import net.avatarrealms.minecraft.bending.abilities.BendingType;
import net.avatarrealms.minecraft.bending.abilities.chi.*;
import net.avatarrealms.minecraft.bending.abilities.earth.*;
import net.avatarrealms.minecraft.bending.abilities.fire.*;
import net.avatarrealms.minecraft.bending.abilities.water.*;
import net.avatarrealms.minecraft.bending.controller.ConfigManager;
import net.avatarrealms.minecraft.bending.utils.BlockTools;
import net.avatarrealms.minecraft.bending.utils.EntityTools;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.SlimeSplitEvent;

public class BendingEntityListener implements Listener {

	public Bending plugin;

	public BendingEntityListener(Bending bending) {
		this.plugin = bending;
	}	

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityCombust(EntityCombustEvent event) {
		Entity entity = event.getEntity();
		Block block = entity.getLocation().getBlock();
		if (FireStream.isIgnited(block) && entity instanceof LivingEntity) {
			// TODO parent is FireStream !
			new Enflamed(entity, FireStream.getIgnited(block), null);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamageEvent(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		if (event.getCause() == DamageCause.FIRE
				&& FireStream.isIgnited(entity.getLocation().getBlock())) {
			// TODO parent is FireStream
			new Enflamed(entity, FireStream.getIgnited(entity.getLocation()
					.getBlock()), null);
		}
		if (Enflamed.isEnflamed(entity)
				&& event.getCause() == DamageCause.FIRE_TICK) {
			event.setCancelled(true);
			Enflamed.dealFlameDamage(entity);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityDamage(EntityDamageByEntityEvent event) {

		Entity source = event.getDamager();
		Entity entity = event.getEntity();
		Fireball fireball = Fireball.getFireball(source);
		Lightning lightning = Lightning.getLightning(source);

		if (fireball != null) {
			event.setCancelled(true);
			fireball.dealDamage(entity);
			return;
		}

		if (event.getCause() == DamageCause.LIGHTNING) {
			if (Lightning.isNearbyChannel(source.getLocation())) {
				event.setCancelled(true);
				return;
			}
		}

		if (lightning != null) {
			event.setCancelled(true);
			lightning.dealDamage(entity);
			return;
		}

		if (Paralyze.isParalyzed(event.getDamager())) {
			event.setCancelled(true);
			return;
		}

		if (source instanceof Player && entity instanceof Monster
				&& event.getCause() == DamageCause.CUSTOM) {
			event.setDamage(event.getDamage() * 2.5);
		}

		if (entity instanceof Player && source instanceof Monster) {
			event.setDamage(event.getDamage() * 2 / 3);
		}

		boolean dodged = false;

		if (source instanceof Player && entity instanceof Player) {
			Player sourceplayer = (Player) source;
			Player targetplayer = (Player) entity;
			if (EntityTools
					.canBendPassive(sourceplayer, BendingType.ChiBlocker)
					&& EntityTools.isBender(sourceplayer,
							BendingType.ChiBlocker)
					&& event.getCause() == DamageCause.ENTITY_ATTACK
					&& event.getDamage() == 1
					&& sourceplayer.getLocation().distance(
							targetplayer.getLocation()) <= ConfigManager.rapidPunchDistance
					&& (!EntityTools.isWeapon(sourceplayer.getItemInHand()
							.getType()) || ConfigManager.useWeapon
							.get("ChiBlocker"))) {
				EntityTools.blockChi(targetplayer, System.currentTimeMillis());
			}
		}
		if (entity instanceof Player) {
			if ((event.getCause() == DamageCause.ENTITY_ATTACK
					|| event.getCause() == DamageCause.ENTITY_EXPLOSION || event
					.getCause() == DamageCause.PROJECTILE)
					&& EntityTools.isBender(((Player) event.getEntity()),
							BendingType.ChiBlocker)
					&& EntityTools.canBendPassive((Player) event.getEntity(),
							BendingType.ChiBlocker)) {
				double rand = Math.random();

				if (rand <= ConfigManager.dodgechance / 100.
						&& !Paralyze.isParalyzed(event.getEntity())) {
					event.getEntity()
							.getWorld()
							.playEffect(event.getEntity().getLocation(),
									Effect.SMOKE, 1);
					dodged = true;
					event.setCancelled(true);
				}
			}
		}
		if (source instanceof Player) {

			if (!dodged)
				new Paralyze((Player) event.getDamager(), event.getEntity(),
						null);
			if (EntityTools.isBender(((Player) event.getDamager()),
					BendingType.ChiBlocker)
					&& event.getCause() == DamageCause.ENTITY_ATTACK
					&& !EntityTools.isWeapon(((Player) event.getDamager())
							.getItemInHand().getType())) {
			}
		}
	}


	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onEntityExplode(EntityExplodeEvent event) {
		for (Block block : event.blockList()) {
			EarthBlast blast = EarthBlast.getBlastFromSource(block);

			if (blast != null) {
				blast.cancel();
			}
			if (FreezeMelt.isFrozen(block)) {
				FreezeMelt.thawThenRemove(block);
			}

			if (WaterWall.isWaterWallPart(block)) {
				block.setType(Material.AIR);
			}
			if (!Wave.canThaw(block)) {
				Wave.thaw(block);
			}
			if (BlockTools.movedEarth.containsKey(block)) {
				BlockTools.removeRevertIndex(block);
			}
		}
	}


	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityTarget(EntityTargetEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}
	}		

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityTargetLiving(EntityTargetLivingEntityEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}		
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}	
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityExplodeEvent(EntityExplodeEvent event) {
		Entity entity = event.getEntity();
		if (entity != null)
			if (Paralyze.isParalyzed(entity)
					|| Bloodbending.isBloodbended(entity)) {
				event.setCancelled(true);
			}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityInteractEvent(EntityInteractEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityShootBowEvent(EntityShootBowEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}	
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityTeleportEvent(EntityTeleportEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntityProjectileLaunchEvent(ProjectileLaunchEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}	
	}

	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
	public void onEntitySlimeSplitEvent(SlimeSplitEvent event) {
		Entity entity = event.getEntity();
		if (Paralyze.isParalyzed(entity) || Bloodbending.isBloodbended(entity)) {
			event.setCancelled(true);
		}	
	}
}
