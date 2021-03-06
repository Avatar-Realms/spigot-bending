package net.bendercraft.spigot.bending;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import net.bendercraft.spigot.bending.controller.LanguageParameter;

// TODO : Load all messages. Note that there will be custom messages in the Plugin Data folder and
// original english messages in the .jar file
public class Messages {

	private static final String FILENAME = "messages.properties";

	@LanguageParameter("command.error.not_console")
	public static String NOT_CONSOLE_COMMAND = "This command cannot be executed by the console.";

	@LanguageParameter("command.error.no_permission")
	public static String NO_PERMISSION = "You do not have the permission to execute that command.";

	@LanguageParameter("command.error.invalid_ability")
	public static String INVALID_ABILITY = "This is an invalid ability.";

	@LanguageParameter("command.error.not_element")
	public static String NOT_HAVE_ELEMENT = "You do not have the proper element : ";

	@LanguageParameter("command.ability_bound")
	public static String ABILITY_BOUND = "{0} bound to slot {1}.";

	@LanguageParameter("command.error.console_specify_player")
	public static String CONSOLE_SPECIFY_PLAYER = "You must specify a player if you are a console.";

	@LanguageParameter("command.error.invalid_player")
	public static String INVALID_PLAYER = "Invalid player (may not be connected on server).";

	@LanguageParameter("command.error.invalid_element")
	public static String INVALID_ELEMENT = "Invalid element";

	@LanguageParameter("command.error.choose_other")
	public static String ERROR_CHOOSE_OTHER = "You do not have the permission to change another one's bending element";

	@LanguageParameter("command.error.change_element")
	public static String ERROR_CHANGE_ELEMENT = "You do not have the permission to change your bending element";

	@LanguageParameter("command.other_change_you")
	public static String OTHER_CHANGE_YOU = "Your bending bending element has been set to {0}.";

	@LanguageParameter("command.you_change_other")
	public static String YOU_CHANGE_OTHER = "You've set the bending element of {0} to {1}";

	@LanguageParameter("command.element_set")
	public static String SET_ELEMENT = "You've set your bending element to : {0}.";

	@LanguageParameter("command.you_remove_other")
	public static String YOU_REMOVE_OTHER = "You've removed the bending of {0}.";

	@LanguageParameter("command.other_remove_you")
	public static String OTHER_REMOVE_YOU = "Your bending has been removed.";

	@LanguageParameter("command.you_no_exist")
	public static String YOU_NO_EXIST = "You do not exist.";

	@LanguageParameter("bending.toggle_on")
	public static String TOGGLE_ON = "You activated your bending.";

	@LanguageParameter("bending.toggle_off")
	public static String TOGGLE_OFF = "You deactivated your bending.";

	@LanguageParameter("bending.toggle_spe_on")
	public static String TOGGLE_SPE_ON = "You activated your bending specialization.";

	@LanguageParameter("bending.toggle_spe_off")
	public static String TOGGLE_SPE_OFF = "You deactivated your bending specialization.";

	@LanguageParameter("command.no_bending")
	public static String NO_BENDING = "No bending";

	@LanguageParameter("command.who.is_bending")
	public static String WHO_IS_BENDING = "{0} is a {1} bender. (Path : {2})";

	@LanguageParameter("command.who_is_master")
	public static String WHO_IS_MASTER = "{0} is a master.";

	@LanguageParameter("command.not_element_able")
	public static String NOT_ELEMENT_ABLE = "The target is not able to use the {0} bending.";

	@LanguageParameter("command.already_element")
	public static String ALREADY_ELEMENT = "The target already has this bending.";

	@LanguageParameter("command.you_were_added")
	public static String YOU_WERE_ADDED = "You now can use {0}.";

	@LanguageParameter("command.you_added")
	public static String YOU_ADDED = "You added {0} to {1}.";

	@LanguageParameter("command.reloaded")
	public static String RELOADED = "Reload done";

	@LanguageParameter("command.nothing_bound")
	public static String NOTHING_BOUND = "Nothing bound";

	@LanguageParameter("command.invalid_affinity")
	public static String INVALID_AFFINITY = "This affinity does not exist.";

	@LanguageParameter("command.invalid_affinity_element")
	public static String INVALID_AFFINITY_ELEMENT = "Target does not have the required element for this affinity.";

	@LanguageParameter("command.affinity_added")
	public static String AFFINITY_ADDED = "You were added a new affinity : {0}.";

	@LanguageParameter("command.you_added_affinity")
	public static String YOU_ADDED_AFFINITY = "You added {0} to {1}.";

	@LanguageParameter("command.affinity_set")
	public static String AFFINITY_SET = "You were set the affinity : {0}.";

	@LanguageParameter("command.you_set_affinity")
	public static String YOU_SET_AFFINITY = "You set {0} as the affinity of {1}.";

	@LanguageParameter("command.affinity_removed")
	public static String AFFINITY_REMOVED = "You were removed the affinity : {0}.";

	@LanguageParameter("command.you_removed_affinity")
	public static String YOU_REMOVED_AFFINITY = "You removed {0} from the affinities of {1}.";

	@LanguageParameter("command.affinity_cleared")
	public static String AFFINITY_CLEARED = "Your affinities have been cleared.";

	@LanguageParameter("command.you_clear_affinity")
	public static String YOU_CLEARED_AFFINITY = "You cleared the affinities of {0}.";

	@LanguageParameter("command.invalid_path")
	public static String INVALID_PATH = "Invalid path.";

	@LanguageParameter("command.invalid_path_element")
	public static String INVALID_PATH_ELEMENT = "The target does not have the required bending for this path.";

	@LanguageParameter("command.path_set")
	public static String PATH_SET = "You were set the {0} path.";

	@LanguageParameter("command.you_set_path")
	public static String YOU_SET_PATH = "You set the {0} path to {1}.";

	@LanguageParameter("command.invalid_slot")
	public static String INVALID_SLOT = "Invalid slot number.";

	@LanguageParameter("command.slot_cleared")
	public static String SLOT_CLEARED = "You cleared the slot {0}.";

	@LanguageParameter("command.cleared")
	public static String CLEARED = "You cleared your slots.";

	@LanguageParameter("command.no_bind_permission")
	public static String NO_BIND_PERMISSION = "You do not have the permission to bind that ability.";

	@LanguageParameter("command.already_element_affinity")
	public static String ALREADY_ELEMENT_AFFINITY = "The target already has an affinity for {0}.";

	@LanguageParameter("command.learning_ability")
	public static String ABILITY_LEARNED = "You now can use ";

	@LanguageParameter("command.invalid_parameter")
	public static String INVALID_PARAMETER = "Invalid parameter.";

	@LanguageParameter("command.max_decks_reached")
	public static String MAX_DECKS_REACHED = "You've already reached the max amount of decks available. You should rename one.";

	@LanguageParameter("command.deck_set")
	public static String DECK_SET = "Deck set to {0}.";

	@LanguageParameter("command.already_deck_name")
	public static String ALREADY_DECK_NAME = "You already have a deck with the name : ";

	@LanguageParameter("command.deck_renamed")
	public static String DECK_RENAMED = "Deck renamed to : ";

	@LanguageParameter("command.invalid_deck")
	public static String INVALID_DECK = "You do not have a deck with this name.";

	private static Properties lines;
	private static File languageFile;
	private static InputStream input;
	
	private Messages() {
		
	}

	public static void loadMessages() {
		lines = new Properties();

		File folder = Bending.getInstance().getDataFolder();
		if (!folder.exists()) {
			folder.mkdir();
		}

		languageFile = new File(folder, FILENAME);
		if (languageFile.exists()) {
			try {
				input = new FileInputStream(languageFile);
			} catch (Exception e) {
				input = Messages.class.getClassLoader().getResourceAsStream(FILENAME);
				Bending.getInstance().getLogger().log(Level.WARNING, "File "+languageFile.getName()+" not found or broken, loading "+FILENAME+" instead", e);
			}
		} else {
			input = Messages.class.getClassLoader().getResourceAsStream(FILENAME);
		}

		try {
			lines.load(input);
		} catch (IOException e) {
			Bending.getInstance().getLogger().log(Level.SEVERE, "Error when laoding messages", e);
		}

	}

	public static String getAbilityDescription(String ability) {
		return lines.getProperty("abilities." + ability.toLowerCase());
	}

	public static String getString(String string) {
		return lines.getProperty(string);
	}

	public static void sendMessage(Player player, String key) {
		sendMessage(player, key, ChatColor.WHITE);
	}

	public static void sendMessage(Player player, String key, ChatColor color) {
		if (player == null) {
			Bending.getInstance().getLogger().info(color + getString(key));
		} else {
			player.sendMessage(color + getString(key));
		}
	}

	public static void sendMessage(Player player, String key, Map<String, String> params) {
		sendMessage(player, key, ChatColor.WHITE, params);
	}

	public static void sendMessage(Player player, String key, ChatColor color, Map<String, String> params) {
		String msg = getString(key);
		for (String k : params.keySet()) {
			msg.replaceAll("%" + k + "%", params.get(k));
		}
		if (player == null) {
			Bending.getInstance().getLogger().info(color + msg);
		} else {
			player.sendMessage(color + msg);
		}
	}

}
