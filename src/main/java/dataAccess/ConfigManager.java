package dataAccess;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Abilities;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	public static boolean enabled = true;
	public static boolean bendToItem = false;
	public static boolean colors = true;
	public static boolean compatibility = false;

	public static String chat = "<name>: <message>";

	public static Map<String, String> prefixes = new HashMap<String, String>();
	public static Map<String, String> color = new HashMap<String, String>();
	public static List<String> earthbendable = new ArrayList<String>();
	public static Map<String, Boolean> useWeapon = new HashMap<String, Boolean>();
	public static boolean useMySQL = false;
	public static String dbHost = "localhost";
	public static String dbUser = "root";
	public static String dbPass = "";
	public static String dbDB = "minecraft";
	public static String dbPort = "3306";

	public static int seaLevel = 62;

	public static long globalCooldown = 500;

	public static long chiblockduration = 3000;
	public static double dodgechance = 0;
	public static double punchdamage = 7;
	public static double falldamagereduction = 1;

	public static boolean reverseearthbending = true;
	public static boolean safeRevert = true;
	public static long revertchecktime = 120000;

	public static boolean useTagAPI = true;

	private static List<String> defaultearthbendable = new ArrayList<String>();
	
	public static int maxlevel = 50;
	private static Map<Abilities,Integer> levelsRequired;
	public static int ticksBeforeLoseXP = 12000;
	public static int xpLost = 2;

	public static int avatarStateLevelRequired = 51;
	// Air
	// AirBlast
	public static double airBlastSpeed = 25;
	public static double airBlastRange = 20;
	public static double airBlastRadius = 2;
	public static double airBlastPush = 3.0;
	public static int airBlastLevelRequired = 1;
	
	public static int airBurstLevelRequired = 1;

	// AirBubble
	public static int airBubbleRadius = 4;
	public static int airBubbleLevelRequired = 1;

	// AirPassive
	public static float airPassiveFactor = 0.3F;
	public static int airPassiveLevelRequired = 1;

	// AirScooter
	public static double airScooterSpeed = .675;
	public static int airScooterLevelRequired = 1;

	// AirShield
	public static double airShieldRadius = 7;
	public static int airShieldLevelRequired = 1;

	// AirSpout
	public static double airSpoutHeight = 20;
	public static int airSpoutLevelRequired = 1;

	// AirSuction
	public static double airSuctionSpeed = 25;
	public static double airSuctionRange = 20;
	public static double airSuctionRadius = 2;
	public static double airSuctionPush = 3.5;
	public static int airSuctionLevelRequired = 1;

	// AirSwipe
	public static int airdmg = 2;
	public static double airSwipeRange = 16;
	public static int airSwipeArc = 20;
	public static double airSwipeSpeed = 25;
	public static double airSwipeRadius = 2;
	public static double airSwipePush = 1;
	public static long airSwipeCooldown = 1500;
	public static int airSwipeLevelRequired = 1;

	// Tornado
	public static double tornadoRadius = 10;
	public static double tornadoHeight = 25;
	public static double tornadoRange = 25;
	public static double tornadoMobPush = 1;
	public static double tornadoPlayerPush = 1;
	public static int tornadoLevelRequired = 45;

	// ChiBlocker
	// HighJump
	public static double jumpheight = 1.5;
	public static long highJumpCooldown = 5000;
	public static int highJumpLevelRequired = 1;

	// Paralyze
	public static long paralyzeCooldown = 10000;
	public static long paralyzeDuration = 2500;
	public static int paralyzeLevelRequired = 1;

	// RapidPunch
	public static int rapidPunchDamage = 4;
	public static int rapidPunchDistance = 4;
	public static long rapidPunchCooldown = 3000;
	public static int rapidPunchPunches = 5;
	public static int rapidPunchLevelRequired = 1;

	// Earth
	// Catapult
	public static int catapultLength = 7;
	public static double catapultSpeed = 12;
	public static double catapultPush = 5;
	public static int catapultLevelRequired = 45;
	
	//ShockWave
	public static int shockwaveLevelRequired = 45;

	// Collapse
	public static int collapseRange = 20;
	public static double collapseRadius = 7;
	public static int collapseLevelRequired = 1;

	// CompactColumn
	public static double compactColumnRange = 20;
	public static double compactColumnSpeed = 8;
	public static int compactColumnLevelRequired = 1;

	// EarthArmor
	public static long eartharmorduration = 60000;
	public static int eartharmorstrength = 2;
	public static long eartharmorcooldown = 60000;
	public static int earthArmorLevelRequired = 1;

	// EarthBlast
	public static int earthdmg = 8;
	public static boolean earthBlastHitSelf = false;
	public static double earthBlastPrepareRange = 7;
	public static double earthBlastRange = 20;
	public static double earthBlastSpeed = 35;
	public static boolean earthBlastRevert = true;
	public static double EarthBlastPush = .3;
	public static int earthBlastLevelRequired = 1;

	// EarthColumn
	public static int earthColumnHeight = 6;
	public static int earthColumnLevelRequired = 1;

	// EarthGrab
	public static double earthGrabRange = 15;
	public static int earthGrabLevelRequired = 1;

	// EarthPassive
	public static long earthPassive = 3000;
	public static int earthPassiveLevelRequired = 1;

	// EarthTunnel
	public static double earthTunnelMaxRadius = 1;
	public static double earthTunnelRange = 10;
	public static double earthTunnelRadius = 0.25;
	public static long earthTunnelInterval = 30;
	public static boolean earthTunnelRevert = true;
	public static int earthTunnelLevelRequired = 51;

	// EarthWall
	public static int earthWallRange = 15;
	public static int earthWallHeight = 6;
	public static int earthWallWidth = 6;
	public static int earthWallLevelRequired = 1;

	// Tremorsense
	public static long tremorsenseCooldown = 1000;
	public static int tremorsenseMaxDepth = 10;
	public static int tremorsenseRadius = 5;
	public static byte tremorsenseLightThreshold = 7;
	public static int tremorsenseLevelRequired = 1;

	// Fire
	// ArcOfFire
	public static int arcOfFireArc = 20;
	public static int arcOfFireRange = 9;
	public static int arcOfFireLevelRequired = 1;

	// Extinguish
	public static double extinguishRange = 20;
	public static double extinguishRadius = 7;
	public static int extinguishLevelRequired = 1;

	// Fireball
	public static long fireballCooldown = 300000;
	public static double fireballSpeed = 0.3;
	public static int fireballLevelRequired = 1;

	// FireBlast
	public static double fireBlastSpeed = 15;
	public static double fireBlastRange = 25;
	public static double fireBlastRadius = 2;
	public static double fireBlastPush = .3;
	public static int fireBlastDamage = 6;
	public static long fireBlastCooldown = 1500;
	public static boolean fireBlastDissipate = false;
	public static int fireBlastLevelRequired = 1;
	
	//FireBurst
	public static int fireBurstLevelRequired = 1;
	
	//FireShield
	public static int fireShieldLevelRequired = 1;

	// FireJet
	public static double fireJetSpeed = 0.7;
	public static long fireJetDuration = 1500;
	public static long fireJetCooldown = 6000;
	public static int fireJetLevelRequired = 45;

	// FireStream
	public static double fireStreamSpeed = 15;
	public static long dissipateAfter = 400;
	public static int fireStreamLevelRequired = 1;

	// HeatMelt
	public static int heatMeltRange = 15;
	public static int heatMeltRadius = 5;
	public static int heatMeltLevelRequired = 1;

	// Illumination
	public static int illuminationRange = 5;
	public static int illuminationLevelRequired = 1;

	// Lightning
	public static long lightningwarmup = 3500;
	public static int lightningrange = 50;
	public static double lightningmisschance = 5;
	public static int lightningLevelRequired = 45;

	// RingOfFire
	public static int ringOfFireRange = 7;
	public static int ringOfFireLevelRequired = 1;

	// WallOfFire
	public static int wallOfFireRange = 4;
	public static int wallOfFireHeight = 4;
	public static int wallOfFireWidth = 4;
	public static long wallOfFireDuration = 5000;
	public static int wallOfFireDamage = 2;
	public static long wallOfFireInterval = 500;
	public static long wallOfFireCooldown = 7500;
	public static int wallOfFireLevelRequired = 1;

	// Day
	public static double dayFactor = 1.0;

	// Water
	// Bloodbending
	public static double bloodbendingThrowFactor = 2;
	public static int bloodbendingRange = 10;
	public static int bloodbendingLevelRequired = 51;

	// FastSwimming
	public static double fastSwimmingFactor = 0.7;
	public static int fastSwimmingLevelRequired = 1;

	// FreezeMelt
	public static int freezeMeltRange = 20;
	public static int freezeMeltRadius = 5;
	public static int freezeMeltLevelRequired = 1;

	// HealingWaters
	public static double healingWatersRadius = 5;
	public static long healingWatersInterval = 750;
	public static int healingWatersLevelRequired = 1;

	// IceSpike
	public static long icespikecooldown = 2000;
	public static int icespikedamage = 2;
	public static int icespikerange = 20;
	public static double icespikethrowingmult = 0.7;
	public static int icespikeLevelRequired = 1;

	// Plantbending
	public static long plantbendingRegrowTime = 180000;
	public static int plantbendingLevelRequired = 1;

	// SpikeField
	public static long icespikeareacooldown = 3000;
	public static int icespikeareadamage = 2;
	public static int icespikearearadius = 6;
	public static double icespikeareathrowingmult = 1;
	
	// WaterBubble
	public static double waterBubbleRadius = airBubbleRadius;
	public static int waterBubbleLevelRequired = 1;

	// WaterManipulation
	public static int waterdmg = 7;
	public static double waterManipulationRange = 25;
	public static double waterManipulationSpeed = 35;
	public static double WaterManipulationPush = .3;
	public static int watermanipulationLevelRequired = 1;

	// WaterSpout
	public static int waterSpoutHeight = 16;
	public static int waterSpoutLevelRequired = 1;

	// WaterWall
	public static double waterWallRange = 5;
	public static double waterWallRadius = 2;
	public static int waterWallLevelRequired = 1;

	// Wave
	public static double waveRadius = 3;
	public static double waveHorizontalPush = 1;
	public static double waveVerticalPush = 0.2;
	public static int waveLevelRequired = 1;
	
	//Torrent
	public static int torrentLevelRequired = 45;
	
	//OctopusForm
	
	public static int octopusFormLevelRequired = 45;

	// Night
	public static double nightFactor = 1.5;

	public void load(File file) {
		FileConfiguration config = new YamlConfiguration();
		try {
			if (file.exists())
				config.load(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			e.printStackTrace();
		}
		levelsRequired = new HashMap<Abilities, Integer>();

		// Chat:
		enabled = config.getBoolean("Chat.Enabled", enabled);
		colors = config.getBoolean("Chat.Colors", colors);
		compatibility = config.getBoolean("Chat.Compatibility", compatibility);

		chat = config.getString("Chat.Format", chat);

		// Prefix
		prefixes.put("Air", config.getString("Chat.Prefix.Air", "[Airbender] "));
		prefixes.put("Avatar",
				config.getString("Chat.Prefix.Avatar", "[Avatar] "));
		prefixes.put("Fire",
				config.getString("Chat.Prefix.Fire", "[Firebender ]"));
		prefixes.put("Water",
				config.getString("Chat.Prefix.Water", "[Waterbender] "));
		prefixes.put("Earth",
				config.getString("Chat.Prefix.Earth", "[Earthbender] "));
		prefixes.put("ChiBlocker",
				config.getString("Chat.Prefix.ChiBlocker", "[Chiblocker] "));

		// Color
		color.put("Avatar",
				config.getString("Chat.Color.Avatar", "DARK_PURPLE"));
		color.put("Air", config.getString("Chat.Color.Air", "GRAY"));
		color.put("Fire", config.getString("Chat.Color.Fire", "RED"));
		color.put("Water", config.getString("Chat.Color.Water", "AQUA"));
		color.put("Earth", config.getString("Chat.Color.Earth", "GREEN"));
		color.put("ChiBlocker",
				config.getString("Chat.Color.ChiBlocker", "GOLD"));

		// Bending
		// Damage
		airdmg = config.getInt("Bending.Damage.AirSwipe", airdmg);
		earthdmg = config.getInt("Bending.Damage.EarthBlast", earthdmg);
		waterdmg = config.getInt("Bending.Damage.WaterManipulation", waterdmg);

		// Option
		earthbendable = defaultearthbendable;
		maxlevel = config.getInt("Bending.Option.max-level");
		ticksBeforeLoseXP = 20*config.getInt("Bending.Option.lose-experience.seconds");
		xpLost = config.getInt("Bending.Option.lose-experience.amount");
		if (config.contains("Bending.Option.EarthBendable"))
			earthbendable = config
					.getStringList("Bending.Option.EarthBendable");
		

		// EarthBendable
		useWeapon
				.put("Air", config.getBoolean(
						"Bending.Option.Bend-With-Weapon.Air", false));
		useWeapon.put("Earth", config.getBoolean(
				"Bending.Option.Bend-With-Weapon.Earth", false));
		useWeapon.put("Fire", config.getBoolean(
				"Bending.Option.Bend-With-Weapon.Fire", false));
		useWeapon.put("Water", config.getBoolean(
				"Bending.Option.Bend-With-Weapon.Water", false));
		useWeapon.put("ChiBlocker", config.getBoolean(
				"Bending.Option.Bend-With-Weapon.ChiBlocker", false));

		bendToItem = config.getBoolean("Bending.Option.Bend-To-Item",
				bendToItem);
		useTagAPI = config.getBoolean("Bending.Option.Use-TagAPI", useTagAPI);
		reverseearthbending = config.getBoolean(
				"Bending.Option.Reverse-Earthbending", reverseearthbending);
		revertchecktime = config.getLong(
				"Bending.Option.Reverse-Earthbending-Check-Time",
				revertchecktime);
		safeRevert = config
				.getBoolean("Bending.Option.Safe-Revert", safeRevert);
		dissipateAfter = config.getLong(
				"Bending.Option.Firebending-Dissipate-Time", dissipateAfter);
		seaLevel = config.getInt("Bending.Option.Sea-Level", seaLevel);

		// Properties
		avatarStateLevelRequired = config.getInt(
				"Properties.AvatarState.level-required", avatarStateLevelRequired);
		levelsRequired.put(Abilities.AvatarState,avatarStateLevelRequired);
		// ChiBlocker
		chiblockduration = config.getLong(
				"Properties.ChiBlocker.ChiBlock-Duration", chiblockduration);
		dodgechance = config.getDouble("Properties.ChiBlocker.Dodge-Chance",
				dodgechance);
		punchdamage = config.getDouble("Properties.ChiBlocker.Punch-Damage",
				punchdamage);
		falldamagereduction = config.getDouble(
				"Properties.ChiBlocker.Fall-Damage-Reduction",
				falldamagereduction);

		globalCooldown = config.getLong("Properties.GlobalCooldown",
				globalCooldown);

		// HighJump
		jumpheight = config.getDouble("Properties.ChiBlocker.HighJump.Height",
				jumpheight);
		highJumpCooldown = config.getLong(
				"Properties.ChiBlocker.HighJump.Cooldown", highJumpCooldown);
		highJumpLevelRequired = config.getInt(
				"Properties.ChiBlocker.HighJump.level-required", highJumpLevelRequired);
		levelsRequired.put(Abilities.HighJump,highJumpLevelRequired);

		// Paralyze
		paralyzeCooldown = config.getLong(
				"Properties.ChiBlocker.Paralyze.Cooldown", paralyzeCooldown);
		paralyzeDuration = config.getLong(
				"Properties.ChiBlocker.Paralyze.Duration", paralyzeDuration);
		paralyzeLevelRequired = config.getInt(
				"Properties.ChiBlocker.Paralyze.level-required", paralyzeLevelRequired);
		levelsRequired.put(Abilities.Paralyze, paralyzeLevelRequired);

		// RapidPunch
		rapidPunchDamage = config.getInt(
				"Properties.ChiBlocker.RapidPunch.Damage", rapidPunchDamage);
		rapidPunchDistance = config
				.getInt("Properties.ChiBlocker.RapidPunch.Distance",
						rapidPunchDistance);
		rapidPunchCooldown = config
				.getLong("Properties.ChiBlocker.RapidPunch.Cooldown",
						rapidPunchCooldown);
		rapidPunchPunches = config.getInt(
				"Properties.ChiBlocker.RapidPunch.Punches", rapidPunchPunches);
		rapidPunchLevelRequired = config.getInt(
				"Properties.ChiBlocker.RapidPunch.level-required", rapidPunchLevelRequired);
		levelsRequired.put(Abilities.RapidPunch, rapidPunchLevelRequired);

		// Air
		// AirBlast
		airBlastSpeed = config.getDouble("Properties.Air.AirBlast.Speed",
				airBlastSpeed);
		airBlastRange = config.getDouble("Properties.Air.AirBlast.Range",
				airBlastRange);
		airBlastRadius = config.getDouble(
				"Properties.Air.AirBlast.Affecting-Radius", airBlastRadius);
		airBlastPush = config.getDouble("Properties.Air.AirBlast.Push-Factor",
				airBlastPush);
		airBlastLevelRequired = config.getInt(
				"Properties.Air.AirBlast.level-required", airBlastLevelRequired);
		levelsRequired.put(Abilities.AirBlast, airBlastLevelRequired);
		
		//AirBurst	
		airBlastLevelRequired = config.getInt(
				"Properties.Air.AirBurst.level-required", airBurstLevelRequired);
		levelsRequired.put(Abilities.AirBurst, airBurstLevelRequired);

		// AirBubble
		airBubbleRadius = config.getInt("Properties.Air.AirBubble.Radius",
				airBubbleRadius);
		airBubbleLevelRequired = config.getInt(
				"Properties.Air.AirBubble.level-required", airBubbleLevelRequired);
		levelsRequired.put(Abilities.AirBubble, airBubbleLevelRequired);

		// AirPassive
		airPassiveFactor = (float) config.getDouble(
				"Properties.Air.Passive.Factor", airPassiveFactor);
		airPassiveLevelRequired = config.getInt(
				"Properties.Air.AirPassive.level-required", airPassiveLevelRequired);

		// AirShield
		airShieldRadius = config.getDouble("Properties.Air.AirShield.Radius",
				airShieldRadius);
		airShieldLevelRequired = config.getInt(
				"Properties.Air.AirShield.level-required", airShieldLevelRequired);
		levelsRequired.put(Abilities.AirShield, airShieldLevelRequired);

		// AirSuction
		airSuctionSpeed = config.getDouble("Properties.Air.AirSuction.Speed",
				airSuctionSpeed);
		airSuctionRange = config.getDouble("Properties.Air.AirSuction.Range",
				airSuctionRange);
		airSuctionRadius = config.getDouble(
				"Properties.Air.AirSuction.Affecting-Radius", airSuctionRadius);
		airSuctionPush = config.getDouble(
				"Properties.Air.AirSuction.Push-Factor", airSuctionPush);
		airSuctionLevelRequired = config.getInt(
				"Properties.Air.AirSuction.level-required", airSuctionLevelRequired);
		levelsRequired.put(Abilities.AirSuction, airSuctionLevelRequired);

		// AirSwipe
		airSwipeRange = config.getDouble("Properties.Air.AirSwipe.Range",
				airSwipeRange);
		airSwipeArc = config.getInt("Properties.Air.AirSwipe.Arc", airSwipeArc);
		airSwipeSpeed = config.getDouble("Properties.Air.AirSwipe.Speed",
				airSwipeSpeed);
		airSwipeRadius = config.getDouble(
				"Properties.Air.AirSwipe.Affecting-Radius", airSwipeRadius);
		airSwipePush = config.getDouble("Properties.Air.AirSwipe.Push-Factor",
				airSwipePush);
		airSwipeCooldown = config.getLong("Properties.Air.AirSwipe.Cooldown",
				airSwipeCooldown);
		airSwipeLevelRequired = config.getInt(
				"Properties.Air.AirSwipe.level-required", airSwipeLevelRequired);
		levelsRequired.put(Abilities.AirSwipe, airSwipeLevelRequired);

		// Tornado
		tornadoRadius = config.getDouble("Properties.Air.Tornado.Radius",
				tornadoRadius);
		tornadoHeight = config.getDouble("Properties.Air.Tornado.Height",
				tornadoHeight);
		tornadoRange = config.getDouble("Properties.Air.Tornado.Range",
				tornadoRange);
		tornadoMobPush = config.getDouble(
				"Properties.Air.Tornado.Mob-Push-Factor", tornadoMobPush);
		tornadoPlayerPush = config.getDouble(
				"Properties.Air.Tornado.Player-Push-Factor", tornadoPlayerPush);
		tornadoLevelRequired = config.getInt(
				"Properties.Air.Tornado.level-required", tornadoLevelRequired);
		levelsRequired.put(Abilities.Tornado, tornadoLevelRequired);

		// Air Scooter
		airScooterSpeed = config.getDouble("Properties.Air.AirScooter.Speed",
				airScooterSpeed);
		airScooterLevelRequired = config.getInt(
				"Properties.Air.AirScooter.level-required", airScooterLevelRequired);
		levelsRequired.put(Abilities.AirScooter, airScooterLevelRequired);

		// Air Spout
		airSpoutHeight = config.getDouble("Properties.Air.AirSpout.Height",
				airSpoutHeight);
		airSpoutLevelRequired = config.getInt(
				"Properties.Air.AirSpout.level-required", airSpoutLevelRequired);
		levelsRequired.put(Abilities.AirSpout, airSpoutLevelRequired);

		// Earth
		// Catapult
		catapultLength = config.getInt("Properties.Earth.Catapult.Length",
				catapultLength);
		catapultSpeed = config.getDouble("Properties.Earth.Catapult.Speed",
				catapultSpeed);
		catapultPush = config.getDouble("Properties.Earth.Catapult.Push",
				catapultPush);
		catapultLevelRequired = config.getInt(
				"Properties.Earth.Catapult.level-required", catapultLevelRequired);
		levelsRequired.put(Abilities.Catapult, catapultLevelRequired);
		
		//ShockWave
		shockwaveLevelRequired = config.getInt("Properties.Earth.ShockWave.level-required",shockwaveLevelRequired);
		levelsRequired.put(Abilities.Shockwave, shockwaveLevelRequired);

		// CompactColumn
		compactColumnRange = config.getDouble(
				"Properties.Earth.CompactColumn.Range", compactColumnRange);
		compactColumnSpeed = config.getDouble(
				"Properties.Earth.CompactColumn.Speed", compactColumnSpeed);
		compactColumnLevelRequired = config.getInt(
				"Properties.Earth.ComptactColumn.level-required", compactColumnLevelRequired);
		levelsRequired.put(Abilities.Collapse, compactColumnLevelRequired);

		// EarthBlast
		earthBlastHitSelf = config.getBoolean(
				"Properties.Earth.EarthBlast.Hit-Self", earthBlastHitSelf);
		earthBlastPrepareRange = config.getDouble(
				"Properties.Earth.EarthBlast.Prepare-Range",
				earthBlastPrepareRange);
		earthBlastRange = config.getDouble("Properties.Earth.EarthBlast.Range",
				earthBlastRange);
		earthBlastSpeed = config.getDouble("Properties.Earth.EarthBlast.Speed",
				earthBlastSpeed);
		earthBlastRevert = config.getBoolean(
				"Properties.Earth.EarthBlast.Revert", earthBlastRevert);
		EarthBlastPush = config.getDouble("Properties.Earth.EarthBlast.Push", EarthBlastPush);
		earthBlastLevelRequired = config.getInt(
				"Properties.Earth.EarthBlast.level-required", earthBlastLevelRequired);
		levelsRequired.put(Abilities.EarthBlast, earthBlastLevelRequired);

		// EarthColumn
		earthColumnHeight = config.getInt(
				"Properties.Earth.EarthColumn.Height", earthColumnHeight);
		earthColumnLevelRequired = config.getInt(
				"Properties.Earth.EarthColumn.level-required", earthColumnLevelRequired);
		levelsRequired.put(Abilities.RaiseEarth, earthColumnLevelRequired);

		// EarthGrab
		earthGrabRange = config.getDouble("Properties.Earth.EarthGrab.Range",
				earthGrabRange);
		earthGrabLevelRequired = config.getInt(
				"Properties.Earth.EarthGrab.level-required", earthGrabLevelRequired);
		levelsRequired.put(Abilities.EarthGrab, earthGrabLevelRequired);

		// EarthPassive
		earthPassive = config.getLong(
				"Properties.Earth.EarthPassive.Wait-Before-Reverse-Changes",
				earthPassive);
		earthPassiveLevelRequired = config.getInt(
				"Properties.Earth.EarthPassive.level-required", earthPassiveLevelRequired);

		// EarthTunnel
		earthTunnelMaxRadius = config
				.getDouble("Properties.Earth.EarthTunnel.Max-Radius",
						earthTunnelMaxRadius);
		earthTunnelRange = config.getDouble(
				"Properties.Earth.EarthTunnel.Range", earthTunnelRange);
		earthTunnelRadius = config.getDouble(
				"Properties.Earth.EarthTunnel.Radius", earthTunnelRadius);
		earthTunnelInterval = config.getLong(
				"Properties.Earth.EarthTunnel.Interval", earthTunnelInterval);
		earthTunnelRevert = config.getBoolean(
				"Properties.Earth.EarthTunnel.Revert", earthTunnelRevert);
		earthTunnelLevelRequired = config.getInt(
				"Properties.Earth.EarthTunnel.level-required", earthTunnelLevelRequired);
		levelsRequired.put(Abilities.EarthTunnel, earthTunnelLevelRequired);

		// EarthWall
		earthWallRange = config.getInt("Properties.Earth.EarthWall.Range",
				earthWallRange);
		earthWallHeight = config.getInt("Properties.Earth.EarthWall.Height",
				earthWallHeight);
		earthWallWidth = config.getInt("Properties.Earth.EarthWall.Width",
				earthWallWidth);
		earthWallLevelRequired = config.getInt(
				"Properties.Earth.EarthWall.level-required", earthWallLevelRequired);

		// Collapse
		collapseRange = config.getInt("Properties.Earth.Collapse.Range",
				collapseRange);
		collapseRadius = config.getDouble("Properties.Earth.Collapse.Radius",
				collapseRadius);
		collapseLevelRequired = config.getInt(
				"Properties.Earth.Collapse.level-required", collapseLevelRequired);

		// Tremorsense
		tremorsenseCooldown = config.getLong(
				"Properties.Earth.Tremorsense.Cooldown", tremorsenseCooldown);
		tremorsenseMaxDepth = config.getInt(
				"Properties.Earth.Tremorsense.Max-Depth", tremorsenseMaxDepth);
		tremorsenseRadius = config.getInt(
				"Properties.Earth.Tremorsense.Radius", tremorsenseRadius);
		tremorsenseLightThreshold = (byte) config.getInt(
				"Properties.Earth.Tremorsense.Light-Threshold",
				tremorsenseLightThreshold);
		tremorsenseLevelRequired = config.getInt(
				"Properties.Earth.Tremorsense.level-required", tremorsenseLevelRequired);
		levelsRequired.put(Abilities.Tremorsense, tremorsenseLevelRequired);

		// EarthArmor
		eartharmorduration = config.getLong(
				"Properties.Earth.EarthArmor.Duration", eartharmorduration);
		eartharmorstrength = config.getInt(
				"Properties.Earth.EarthArmor.Strength", eartharmorstrength);
		eartharmorcooldown = config.getLong(
				"Properties.Earth.EarthArmor.Cooldown", eartharmorcooldown);
		earthArmorLevelRequired = config.getInt(
				"Properties.Earth.EarthArmor.level-required", earthArmorLevelRequired);
		levelsRequired.put(Abilities.EarthArmor, earthArmorLevelRequired);

		// Fire
		// FireBlast
		fireBlastRange = config.getDouble("Properties.Fire.FireBlast.Range",
				fireBlastRange);
		fireBlastSpeed = config.getDouble("Properties.Fire.FireBlast.Speed",
				fireBlastSpeed);
		fireBlastPush = config.getDouble("Properties.Fire.FireBlast.Push",
				fireBlastPush);
		fireBlastRadius = config.getDouble("Properties.Fire.FireBlast.Radius",
				fireBlastRadius);
		fireBlastCooldown = config.getLong(
				"Properties.Fire.FireBlast.Cooldown", fireBlastCooldown);
		fireBlastDamage = config.getInt("Properties.Fire.FireBlast.Damage",
				fireBlastDamage);
		fireBlastDissipate = config.getBoolean(
				"Properties.Fire.FireBlast.Dissipates", fireBlastDissipate);
		fireBlastLevelRequired = config.getInt(
				"Properties.Fire.FireBlast.level-required", fireBlastLevelRequired);
		levelsRequired.put(Abilities.FireBlast, fireBlastLevelRequired);
		
		//FireBurst
		fireBurstLevelRequired = config.getInt(
				"Properties.Fire.FireBurst.level-required", fireBurstLevelRequired);
		levelsRequired.put(Abilities.FireBurst, fireBurstLevelRequired);
		
		//FireShield
		fireShieldLevelRequired = config.getInt(
				"Properties.Fire.FireShield.level-required", fireShieldLevelRequired);
		levelsRequired.put(Abilities.FireShield, fireShieldLevelRequired);
		
		// ArcOfFire
		arcOfFireArc = config.getInt("Properties.Fire.ArcOfFire.Arc",
				arcOfFireArc);
		arcOfFireRange = config.getInt("Properties.Fire.ArcOfFire.Range",
				arcOfFireRange);
		arcOfFireLevelRequired = config.getInt(
				"Properties.Fire.ArcOfFire.level-required", arcOfFireLevelRequired);
		levelsRequired.put(Abilities.Blaze, arcOfFireLevelRequired);

		// RingOfFire
		ringOfFireRange = config.getInt("Properties.Fire.RingOfFire.Range",
				ringOfFireRange);
		ringOfFireLevelRequired = config.getInt(
				"Properties.Fire.RingOfFire.level-required", ringOfFireLevelRequired);

		// Extinguish
		extinguishRange = config.getDouble("Properties.Fire.Extinguish.Range",
				extinguishRange);
		extinguishRadius = config.getDouble(
				"Properties.Fire.Extinguish.Radius", extinguishRadius);
		extinguishLevelRequired = config.getInt(
				"Properties.Fire.Extinguish.level-required", extinguishLevelRequired);
		levelsRequired.put(Abilities.HeatControl, extinguishLevelRequired);

		// Fireball
		fireballCooldown = config.getLong("Properties.Fire.Fireball.Cooldown",
				fireballCooldown);
		fireballSpeed = config.getDouble("Properties.Fire.Fireball.Speed",
				fireballSpeed);
		fireballLevelRequired = config.getInt(
				"Properties.Fire.Fireball.level-required", fireballLevelRequired);

		// FireJet
		fireJetSpeed = config.getDouble("Properties.Fire.FireJet.Speed",
				fireJetSpeed);
		fireJetDuration = config.getLong("Properties.Fire.FireJet.Duration",
				fireJetDuration);
		fireJetCooldown = config.getLong("Properties.Fire.FireJet.CoolDown",
				fireJetCooldown);
		fireJetLevelRequired = config.getInt(
				"Properties.Fire.FireJet.level-required", fireJetLevelRequired);
		levelsRequired.put(Abilities.FireJet, fireJetLevelRequired);

		// FireStream
		fireStreamSpeed = config.getDouble("Properties.Fire.FireStream.Speed",
				fireStreamSpeed);
		fireStreamLevelRequired = config.getInt(
				"Properties.Fire.FireStream.level-required", fireStreamLevelRequired);

		// WallOfFire
		wallOfFireRange = config.getInt("Properties.Fire.WallOfFire.Range",
				wallOfFireRange);
		wallOfFireHeight = config.getInt("Properties.Fire.WallOfFire.Height",
				wallOfFireHeight);
		wallOfFireWidth = config.getInt("Properties.Fire.WallOfFire.Width",
				wallOfFireWidth);
		wallOfFireDuration = config.getLong(
				"Properties.Fire.WallOfFire.Duration", wallOfFireDuration);
		wallOfFireDamage = config.getInt("Properties.Fire.WallOfFire.Damage",
				wallOfFireDamage);
		wallOfFireInterval = config.getLong(
				"Properties.Fire.WallOfFire.Interval", wallOfFireInterval);
		wallOfFireCooldown = config.getLong(
				"Properties.Fire.WallOfFire.Cooldown", wallOfFireCooldown);
		wallOfFireLevelRequired = config.getInt(
				"Properties.Fire.WallOfFire.level-required", wallOfFireLevelRequired);
		levelsRequired.put(Abilities.WallOfFire, wallOfFireLevelRequired);

		// HeatMelt
		heatMeltRange = config.getInt("Properties.Fire.HeatMelt.Range",
				heatMeltRange);
		heatMeltRadius = config.getInt("Properties.Fire.HeatMelt.Radius",
				heatMeltRadius);
		heatMeltLevelRequired = config.getInt(
				"Properties.Fire.HeatMelt.level-required", heatMeltLevelRequired);

		// Illumination
		illuminationRange = config.getInt("Properties.Fire.Illumination.Range",
				illuminationRange);
		illuminationLevelRequired = config.getInt(
				"Properties.Fire.Illumination.level-required", illuminationLevelRequired);
		levelsRequired.put(Abilities.Illumination, illuminationLevelRequired);

		// Lightning
		lightningwarmup = config.getLong("Properties.Fire.Lightning.Warmup",
				lightningwarmup);
		lightningrange = config.getInt("Properties.Fire.Lightning.Range",
				lightningrange);
		lightningmisschance = config.getDouble(
				"Properties.Fire.Lightning.Miss-Chance", lightningmisschance);
		lightningLevelRequired = config.getInt(
				"Properties.Fire.Lightning.level-required", lightningLevelRequired);
		levelsRequired.put(Abilities.Lightning, lightningLevelRequired);

		// Day
		dayFactor = config.getDouble("Properties.Fire.Day-Power-Factor",
				dayFactor);

		// Water
		// Bloodbending
		bloodbendingThrowFactor = config.getDouble(
				"Properties.Water.Bloodbending.Throw-Factor",
				bloodbendingThrowFactor);
		bloodbendingRange = config.getInt(
				"Properties.Water.Bloodbending.Range", bloodbendingRange);
		bloodbendingLevelRequired = config.getInt(
				"Properties.Water.Bloodbending.level-required", bloodbendingLevelRequired);
		levelsRequired.put(Abilities.Bloodbending, bloodbendingLevelRequired);
		
		//WaterBubble
		waterBubbleLevelRequired = config.getInt(
				"Properties.Water.WaterBubble.level-required", waterBubbleLevelRequired);
		levelsRequired.put(Abilities.WaterBubble, waterBubbleLevelRequired);

		// FreezeMelt
		freezeMeltRange = config.getInt("Properties.Water.FreezeMelt.Range",
				freezeMeltRange);
		freezeMeltRadius = config.getInt("Properties.Water.FreezeMelt.Radius",
				freezeMeltRadius);
		freezeMeltLevelRequired = config.getInt(
				"Properties.Water.FreezeMelt.level-required", freezeMeltLevelRequired);
		levelsRequired.put(Abilities.PhaseChange, freezeMeltLevelRequired);

		// HealingWaters
		healingWatersRadius = config.getDouble(
				"Properties.Water.HealingWaters.Radius", healingWatersRadius);
		healingWatersInterval = config.getLong(
				"Properties.Water.HealingWaters.Interval",
				healingWatersInterval);
		healingWatersLevelRequired = config.getInt(
				"Properties.Water.HealingWaters.level-required", healingWatersLevelRequired);
		levelsRequired.put(Abilities.HealingWaters, wallOfFireLevelRequired);

		// Plantbending
		plantbendingRegrowTime = config.getLong(
				"Properties.Water.Plantbending.Regrow-Time",
				plantbendingRegrowTime);
		plantbendingLevelRequired = config.getInt(
				"Properties.Water.Plantbending.level-required", plantbendingLevelRequired);

		// WaterManipulation
		waterManipulationRange = config.getDouble(
				"Properties.Water.WaterManipulation.Range",
				waterManipulationRange);
		waterManipulationSpeed = config.getDouble(
				"Properties.Water.WaterManipulation.Speed",
				waterManipulationSpeed);
		WaterManipulationPush = config.getDouble("Properties.Water.WaterManipulation.Push",
				WaterManipulationPush);
		watermanipulationLevelRequired = config.getInt(
				"Properties.Water.WaterManipulation.level-required", watermanipulationLevelRequired);
		levelsRequired.put(Abilities.WaterManipulation, watermanipulationLevelRequired);

		// WaterSpout
		waterSpoutHeight = config.getInt("Properties.Water.WaterSpout.Height",
				waterSpoutHeight);
		waterSpoutLevelRequired = config.getInt(
				"Properties.Water.WaterSpout.level-required", waterSpoutLevelRequired);
		levelsRequired.put(Abilities.WaterSpout, waterSpoutLevelRequired);

		// WaterWall
		waterWallRange = config.getDouble("Properties.Water.WaterWall.Range",
				waterWallRange);
		waterWallRadius = config.getDouble("Properties.Water.WaterWall.Radius",
				waterWallRadius);
		waterWallLevelRequired = config.getInt(
				"Properties.Water.WaterWall.level-required", waterWallLevelRequired);
		levelsRequired.put(Abilities.Surge, waterWallLevelRequired);

		// Wave
		waveRadius = config.getDouble("Properties.Water.Wave.Radius",
				waveRadius);
		waveHorizontalPush = config.getDouble(
				"Properties.Water.Wave.Horizontal-Push-Force",
				waveHorizontalPush);
		waveVerticalPush = config.getDouble(
				"Properties.Water.Wave.Vertical-Push-Force", waveVerticalPush);
		waveLevelRequired = config.getInt(
				"Properties.Water.Wave.level-required", waveLevelRequired);

		// Fast Swimming
		fastSwimmingFactor = config.getDouble(
				"Properties.Water.FastSwimming.Factor", fastSwimmingFactor);
		fastSwimmingLevelRequired = config.getInt(
				"Properties.Water.FastSwimming.level-required", fastSwimmingLevelRequired);

		// IceSpike
		icespikecooldown = config.getLong("Properties.Water.IceSpike.Cooldown",
				icespikecooldown);
		icespikedamage = config.getInt("Properties.Water.IceSpike.Damage",
				icespikedamage);
		icespikerange = config.getInt("Properties.Water.IceSpike.Range",
				icespikerange);
		icespikethrowingmult = config.getDouble(
				"Properties.Water.IceSpike.ThrowingMult", icespikethrowingmult);
		icespikeareacooldown = config.getLong(
				"Properties.Water.IceSpike.AreaCooldown", icespikeareacooldown);
		icespikeareadamage = config.getInt(
				"Properties.Water.IceSpike.AreaDamage", icespikeareadamage);
		icespikearearadius = config.getInt(
				"Properties.Water.IceSpike.AreaRadius", icespikearearadius);
		icespikeareathrowingmult = config.getDouble(
				"Properties.Water.IceSpike.AreaThrowingMult",
				icespikeareathrowingmult);
		icespikeLevelRequired = config.getInt(
				"Properties.Water.IceSpike.level-required", icespikeLevelRequired);
		levelsRequired.put(Abilities.IceSpike, icespikeLevelRequired);
		
		//Torrent
		
		torrentLevelRequired = config.getInt("Properties.Water.Torrent.level-required",torrentLevelRequired);
		levelsRequired.put(Abilities.Torrent, torrentLevelRequired);

		//OctopusForm
		octopusFormLevelRequired = config.getInt("Properties.Water.OctopusForm.level-required",octopusFormLevelRequired);
		levelsRequired.put(Abilities.OctopusForm, octopusFormLevelRequired);
		
		// Night
		nightFactor = config.getDouble("Properties.Water.Night-Power-Factor",
				nightFactor);

		// MySQL
		useMySQL = config.getBoolean("MySQL.Use-MySQL", useMySQL);
		dbHost = config.getString("MySQL.MySQL-host", dbHost);
		dbUser = config.getString("MySQL.User", dbUser);
		dbPass = config.getString("MySQL.Password", dbPass);
		dbDB = config.getString("MySQL.Database", dbDB);
		Integer dbPortint = (Integer) config.getInt("MySQL.MySQL-portnumber",
				Integer.parseInt(dbPort));
		dbPort = dbPortint.toString();

		// set defaults
		config.set("Chat.Enabled", enabled);
		config.set("Chat.Colors", colors);
		config.set("Chat.Compatibility", compatibility);
		config.set("Chat.Format", chat);
		// Prefix
		config.set("Chat.Prefix.Air", prefixes.get("Air"));
		config.set("Chat.Prefix.Fire", prefixes.get("Fire"));
		config.set("Chat.Prefix.Avatar", prefixes.get("Avatar"));
		config.set("Chat.Prefix.Water", prefixes.get("Water"));
		config.set("Chat.Prefix.Earth", prefixes.get("Earth"));
		config.set("Chat.Prefix.ChiBlocker", prefixes.get("ChiBlocker"));
		// Color
		config.set("Chat.Color.Avatar", color.get("Avatar"));
		config.set("Chat.Color.Air", color.get("Air"));
		config.set("Chat.Color.Fire", color.get("Fire"));
		config.set("Chat.Color.Earth", color.get("Earth"));
		config.set("Chat.Color.Water", color.get("Water"));
		config.set("Chat.Color.ChiBlocker", color.get("ChiBlocker"));
		// Bending
		// Damage
		config.set("Bending.Damage.AirSwipe", airdmg);
		config.set("Bending.Damage.EarthBlast", earthdmg);
		config.set("Bending.Damage.WaterManipulation", waterdmg);
		// Option

		config.set("Bending.Option.Bend-To-Item", bendToItem);
		config.set("Bending.Option.Use-TagAPI", useTagAPI);
		config.set("Bending.Option.Reverse-Earthbending", reverseearthbending);
		config.set("Bending.Option.Reverse-Earthbending-Check-Time",
				revertchecktime);
		config.set("Bending.Option.Firebending-Dissipate-Time", dissipateAfter);
		config.set("Bending.Option.Sea-Level", seaLevel);
		config.set("Bending.Option.max-level", maxlevel);
		config.set("Bending.Option.lose-experience.seconds", ticksBeforeLoseXP/20);
		config.set("Bending.Option.lose-experience.amount", xpLost);

		// Properties
		// ChiBlocker
		config.set("Properties.ChiBlocker.ChiBlock-Duration", chiblockduration);
		config.set("Properties.ChiBlocker.Dodge-Chance", dodgechance);
		config.set("Properties.ChiBlocker.Punch-Damage", punchdamage);
		config.set("Properties.ChiBlocker.Fall-Damage-Reduction",
				falldamagereduction);
		config.set("Properties.GlobalCooldown", globalCooldown);
		// HighJump
		config.set("Properties.ChiBlocker.HighJump.Height", jumpheight);
		config.set("Properties.ChiBlocker.HighJump.Cooldown", highJumpCooldown);
		config.set("Properties.ChiBlocker.HighJump.level-required", highJumpLevelRequired);
		// Paralyze
		config.set("Properties.ChiBlocker.Paralyze.Cooldown", paralyzeCooldown);
		config.set("Properties.ChiBlocker.Paralyze.Duration", paralyzeDuration);
		config.set("Properties.ChiBlocker.Paralyze.level-required", paralyzeLevelRequired);
		// RapidPunch
		config.set("Properties.ChiBlocker.RapidPunch.Damage", rapidPunchDamage);
		config.set("Properties.ChiBlocker.RapidPunch.Distance",
				rapidPunchDistance);
		config.set("Properties.ChiBlocker.RapidPunch.Cooldown",
				rapidPunchCooldown);
		config.set("Properties.ChiBlocker.RapidPunch.Punches",
				rapidPunchPunches);
		config.set("Properties.ChiBlocker.RapidPunch.level-required", rapidPunchLevelRequired);
		// Air
		// AirBlast
		config.set("Properties.Air.AirBlast.Speed", airBlastSpeed);
		config.set("Properties.Air.AirBlast.Range", airBlastRange);
		config.set("Properties.Air.AirBlast.Affecting-Radius", airBlastRadius);
		config.set("Properties.Air.AirBlast.Push-Factor", airBlastPush);
		config.set("Properties.Air.AirBlast.level-required", airBlastLevelRequired);
		//AirBurst
		config.set("Properties.Air.AirBurst.level-required", airBurstLevelRequired);
		// AirBubble
		config.set("Properties.Air.AirBubble.Radius", airBubbleRadius);
		config.set("Properties.Air.AirBubble.level-required", airBubbleLevelRequired);
		// AirPassive
		config.set("Properties.Air.Passive.Factor", airPassiveFactor);
		config.set("Properties.Air.Passive.level-required", airPassiveLevelRequired);
		// AirShield
		config.set("Properties.Air.AirShield.Radius", airShieldRadius);
		config.set("Properties.Air.AirShield.level-required", airShieldLevelRequired);
		// AirSuction
		config.set("Properties.Air.AirSuction.Speed", airSuctionSpeed);
		config.set("Properties.Air.AirSuction.Range", airSuctionRange);
		config.set("Properties.Air.AirSuction.Affecting-Radius",
				airSuctionRadius);
		config.set("Properties.Air.AirSuction.Push-Factor", airSuctionPush);
		config.set("Properties.Air.AirSuction.level-required", airSuctionLevelRequired);
		// AirSwipe
		config.set("Properties.Air.AirSwipe.Range", airSwipeRange);
		config.set("Properties.Air.AirSwipe.Arc", airSwipeArc);
		config.set("Properties.Air.AirSwipe.Speed", airSwipeSpeed);
		config.set("Properties.Air.AirSwipe.Affecting-Radius", airSwipeRadius);
		config.set("Properties.Air.AirSwipe.Push-Factor", airSwipePush);
		config.set("Properties.Air.AirSwipe.Cooldown", airSwipeCooldown);
		config.set("Properties.Air.AirSwipe.level-required", airSwipeLevelRequired);
		// Tornado
		config.set("Properties.Air.Tornado.Radius", tornadoRadius);
		config.set("Properties.Air.Tornado.Height", tornadoHeight);
		config.set("Properties.Air.Tornado.Range", tornadoRange);
		config.set("Properties.Air.Tornado.Mob-Push-Factor", tornadoMobPush);
		config.set("Properties.Air.Tornado.Player-Push-Factor",
				tornadoPlayerPush);
		config.set("Properties.Air.Tornado.level-required", tornadoLevelRequired);
		// Air Scooter
		config.set("Properties.Air.AirScooter.Speed", airScooterSpeed);
		config.set("Properties.Air.AirScooter.level-required", airScooterLevelRequired);
		// Air Spout
		config.set("Properties.Air.AirSpout.Height", airSpoutHeight);
		config.set("Properties.Air.AirSpout.level-required", airSpoutLevelRequired);
		// Earth
		// Catapult
		config.set("Properties.Earth.Catapult.Length", catapultLength);
		config.set("Properties.Earth.Catapult.Speed", catapultSpeed);
		config.set("Properties.Earth.Catapult.Push", catapultPush);
		config.set("Properties.Earth.Catapult.level-required", catapultLevelRequired);
		// CompactColumn
		config.set("Properties.Earth.CompactColumn.Range", compactColumnRange);
		config.set("Properties.Earth.CompactColumn.Speed", compactColumnSpeed);
		config.set("Properties.Earth.CompactColumn.level-required", compactColumnLevelRequired);
		// EarthBlast
		config.set("Properties.Earth.EarthBlast.Hit-Self", earthBlastHitSelf);
		config.set("Properties.Earth.EarthBlast.Prepare-Range",
				earthBlastPrepareRange);
		config.set("Properties.Earth.EarthBlast.Range", earthBlastRange);
		config.set("Properties.Earth.EarthBlast.Speed", earthBlastSpeed);
		config.set("Properties.Earth.EarthBlast.Revert", earthBlastRevert);
		config.set("Properties.Earth.EarthBlast.Push", EarthBlastPush);
		config.set("Properties.Earth.EarthBlast.level-required", earthBlastLevelRequired);
		// EarthColumn
		config.set("Properties.Earth.EarthColumn.Height", earthColumnHeight);
		config.set("Properties.Earth.EarthColumn.level-required", earthColumnLevelRequired);
		// EarthGrab
		config.set("Properties.Earth.EarthGrab.Range", earthGrabRange);
		config.set("Properties.Earth.EarthGrab.level-required", earthGrabLevelRequired);
		// EarthPassive
		config.set("Properties.Earth.EarthPassive.Wait-Before-Reverse-Changes",
				earthPassive);
		config.set("Properties.Earth.EarthPassive.level-required", earthPassiveLevelRequired);
		// EarthTunnel
		config.set("Properties.Earth.EarthTunnel.Max-Radius",
				earthTunnelMaxRadius);
		config.set("Properties.Earth.EarthTunnel.Range", earthTunnelRange);
		config.set("Properties.Earth.EarthTunnel.Radius", earthTunnelRadius);
		config.set("Properties.Earth.EarthTunnel.Interval", earthTunnelInterval);
		config.set("Properties.Earth.EarthTunnel.Revert", earthTunnelRevert);
		config.set("Properties.Earth.EarthTunnel.level-required", earthTunnelLevelRequired);
		// EarthWall
		config.set("Properties.Earth.EarthWall.Range", earthWallRange);
		config.set("Properties.Earth.EarthWall.Height", earthWallHeight);
		config.set("Properties.Earth.EarthWall.Width", earthWallWidth);
		config.set("Properties.Earth.EarthWall.level-required", earthWallLevelRequired);
		// Collapse
		config.set("Properties.Earth.Collapse.Range", collapseRange);
		config.set("Properties.Earth.Collapse.Radius", collapseRadius);
		config.set("Properties.Earth.Collapse.level-required", collapseLevelRequired);
		// Tremorsense
		config.set("Properties.Earth.Tremorsense.Cooldown", tremorsenseCooldown);
		config.set("Properties.Earth.Tremorsense.Max-Depth",
				tremorsenseMaxDepth);
		config.set("Properties.Earth.Tremorsense.Radius", tremorsenseRadius);
		config.set("Properties.Earth.Tremorsense.Light-Threshold",
				tremorsenseLightThreshold);
		config.set("Properties.Earth.Tremorsense.level-required", tremorsenseLevelRequired);
		// EarthArmor
		config.set("Properties.Earth.EarthArmor.Duration", eartharmorduration);
		config.set("Properties.Earth.EarthArmor.Strength", eartharmorstrength);
		config.set("Properties.Earth.EarthArmor.Cooldown", eartharmorcooldown);
		config.set("Properties.Earth.EarthArmor.level-required", earthArmorLevelRequired);
		// Fire
		// FireBlast
		config.set("Properties.Fire.FireBlast.Range", fireBlastRange);
		config.set("Properties.Fire.FireBlast.Speed", fireBlastSpeed);
		config.set("Properties.Fire.FireBlast.Push", fireBlastPush);
		config.set("Properties.Fire.FireBlast.Radius", fireBlastRadius);
		config.set("Properties.Fire.FireBlast.Cooldown", fireBlastCooldown);
		config.set("Properties.Fire.FireBlast.Damage", fireBlastDamage);
		config.set("Properties.Fire.FireBlast.Dissipates", fireBlastDissipate);
		config.set("Properties.Fire.FireBlast.level-required", fireBlastLevelRequired);
		//FireBurst
		config.set("Properties.Fire.FireBurst.level-required", fireBurstLevelRequired);
		// ArcOfFire
		config.set("Properties.Fire.ArcOfFire.Arc", arcOfFireArc);
		config.set("Properties.Fire.ArcOfFire.Range", arcOfFireRange);
		config.set("Properties.Fire.ArcOfFire.level-required", arcOfFireLevelRequired);
		// RingOfFire
		config.set("Properties.Fire.RingOfFire.Range", ringOfFireRange);
		config.set("Properties.Fire.RingOfFire.level-required", ringOfFireLevelRequired);
		// Extinguish
		config.set("Properties.Fire.Extinguish.Range", extinguishRange);
		config.set("Properties.Fire.Extinguish.Radius", extinguishRadius);
		config.set("Properties.Fire.Extinguish.level-required", extinguishLevelRequired);
		// Fireball
		config.set("Properties.Fire.Fireball.Cooldown", fireballCooldown);
		config.set("Properties.Fire.Fireball.Speed", fireballSpeed);
		config.set("Properties.Fire.Fireball.level-required", fireballLevelRequired);
		// FireJet
		config.set("Properties.Fire.FireJet.Speed", fireJetSpeed);
		config.set("Properties.Fire.FireJet.Duration", fireJetDuration);
		config.set("Properties.Fire.FireJet.CoolDown", fireJetCooldown);
		config.set("Properties.Fire.FireJet.level-required", fireJetLevelRequired);
		// FireStream
		config.set("Properties.Fire.FireStream.Speed", fireStreamSpeed);
		config.set("Properties.Fire.FireStream.level-required", fireStreamLevelRequired);
		// WallOfFire
		config.set("Properties.Fire.WallOfFire.Range", wallOfFireRange);
		config.set("Properties.Fire.WallOfFire.Height", wallOfFireHeight);
		config.set("Properties.Fire.WallOfFire.Width", wallOfFireWidth);
		config.set("Properties.Fire.WallOfFire.Duration", wallOfFireDuration);
		config.set("Properties.Fire.WallOfFire.Damage", wallOfFireDamage);
		config.set("Properties.Fire.WallOfFire.Interval", wallOfFireInterval);
		config.set("Properties.Fire.WallOfFire.Cooldown", wallOfFireCooldown);
		config.set("Properties.Fire.WallOfFire.level-required", wallOfFireLevelRequired);
		// HeatMelt
		config.set("Properties.Fire.HeatMelt.Range", heatMeltRange);
		config.set("Properties.Fire.HeatMelt.Radius", heatMeltRadius);
		config.set("Properties.Fire.HeatMelt.level-required", heatMeltLevelRequired);
		// Illumination
		config.set("Properties.Fire.Illumination.Range", illuminationRange);
		config.set("Properties.Fire.Illumination.level-required", illuminationLevelRequired);
		// Lightning
		config.set("Properties.Fire.Lightning.Warmup", lightningwarmup);
		config.set("Properties.Fire.Lightning.Range", lightningrange);
		config.set("Properties.Fire.Lightning.Miss-Chance", lightningmisschance);
		config.set("Properties.Fire.Lightning.level-required", lightningLevelRequired);
		// Day
		config.set("Properties.Fire.Day-Power-Factor", dayFactor);
		// Water
		// Bloodbending
		config.set("Properties.Water.Bloodbending.Throw-Factor",
				bloodbendingThrowFactor);
		config.set("Properties.Water.Bloodbending.Range", bloodbendingRange);
		config.set("Properties.Water.Bloodbending.level-required", bloodbendingLevelRequired);
		//WaterBubble
		config.set("Properties.Water.WaterBubble.level-required", waterBubbleLevelRequired);
		// FreezeMelt
		config.set("Properties.Water.FreezeMelt.Range", freezeMeltRange);
		config.set("Properties.Water.FreezeMelt.Radius", freezeMeltRadius);
		config.set("Properties.Water.FreezeMelt.level-required", freezeMeltLevelRequired);
		// HealingWaters
		config.set("Properties.Water.HealingWaters.Radius", healingWatersRadius);
		config.set("Properties.Water.HealingWaters.Interval",
				healingWatersInterval);
		config.set("Properties.Water.HealingWaters.level-required", healingWatersLevelRequired);
		// Plantbending
		config.set("Properties.Water.Plantbending.Regrow-Time",
				plantbendingRegrowTime);
		config.set("Properties.Water.Plantbending.level-required", plantbendingLevelRequired);
		// WaterManipulation
		config.set("Properties.Water.WaterManipulation.Range",
				waterManipulationRange);
		config.set("Properties.Water.WaterManipulation.Speed",
				waterManipulationSpeed);
		config.set("Properties.Water.WaterManipulation.Push", WaterManipulationPush);
		config.set("Properties.Water.WaterManipulation.level-required", watermanipulationLevelRequired);
		// WaterSpout
		config.set("Properties.Water.WaterSpout.Height", waterSpoutHeight);
		config.set("Properties.Water.WaterSpout.level-required", waterSpoutLevelRequired);
		// WaterWall
		config.set("Properties.Water.WaterWall.Range", waterWallRange);
		config.set("Properties.Water.WaterWall.Radius", waterWallRadius);
		config.set("Properties.Water.WaterWall.level-required", waterWallLevelRequired);
		// Wave
		config.set("Properties.Water.Wave.Radius", waveRadius);
		config.set("Properties.Water.Wave.Horizontal-Push-Force",
				waveHorizontalPush);
		config.set("Properties.Water.Wave.Vertical-Push-Force",
				waveVerticalPush);
		config.set("Properties.Water.Wave.level-required", waveLevelRequired);
		// Fast Swimming
		config.set("Properties.Water.FastSwimming.Factor", fastSwimmingFactor);
		config.set("Properties.Water.FastSwimming.level-required", fastSwimmingLevelRequired);
		// IceSpike
		config.set("Properties.Water.IceSpike.Cooldown", icespikecooldown);
		config.set("Properties.Water.IceSpike.Damage", icespikedamage);
		config.set("Properties.Water.IceSpike.Range", icespikerange);
		config.set("Properties.Water.IceSpike.ThrowingMult",
				icespikethrowingmult);
		config.set("Properties.Water.IceSpike.AreaCooldown",
				icespikeareacooldown);
		config.set("Properties.Water.IceSpike.AreaDamage", icespikeareadamage);
		config.set("Properties.Water.IceSpike.AreaRadius", icespikearearadius);
		config.set("Properties.Water.IceSpike.AreaThrowingMult",
				icespikeareathrowingmult);
		config.set("Properties.Water.IceSpike.level-required", icespikeLevelRequired);		
		//Torrent
		config.set("Properties.Water.Torrent.level-required", torrentLevelRequired);
		// Night
		config.set("Properties.Water.Night-Power-Factor", nightFactor);
		// MySQL
		config.set("MySQL.Use-MySQL", useMySQL);
		config.set("MySQL.MySQL-host", dbHost);
		config.set("MySQL.User", dbUser);
		config.set("MySQL.Password", dbPass);
		config.set("MySQL.Database", dbDB);
		config.set("MySQL.MySQL-portnumber", Integer.parseInt(dbPort));

		// Option
		config.set("Bending.Option.EarthBendable", earthbendable);

		try {
			// config.setDefaults(config);
			// config.options().copyDefaults(true);
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static int getLevelRequired(Abilities ability) {	
		return levelsRequired.get(ability);	
	}
	public static String getColor(String element) {
		return color.get(element);
	}

	public static String getPrefix(String element) {
		return prefixes.get(element);
	}

	static {
		defaultearthbendable.add("STONE");

		defaultearthbendable.add("CLAY");

		defaultearthbendable.add("COAL_ORE");

		defaultearthbendable.add("DIAMOND_ORE");

		defaultearthbendable.add("DIRT");

		defaultearthbendable.add("GOLD_ORE");

		defaultearthbendable.add("EMERALD_ORE");

		defaultearthbendable.add("GRASS");

		defaultearthbendable.add("GRAVEL");

		defaultearthbendable.add("IRON_ORE");

		defaultearthbendable.add("LAPIS_ORE");

		defaultearthbendable.add("REDSTONE_ORE");

		defaultearthbendable.add("SAND");

		defaultearthbendable.add("SANDSTONE");

		defaultearthbendable.add("GLOWING_REDSTONE_ORE");

		defaultearthbendable.add("MYCEL");

	}
}
