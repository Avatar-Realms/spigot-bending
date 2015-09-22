package net.avatar.realms.spigot.bending.commands.subcommands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.avatar.realms.spigot.bending.Messages;
import net.avatar.realms.spigot.bending.abilities.BendingPlayer;
import net.avatar.realms.spigot.bending.commands.BendingCommand;

public class ClearExecution extends BendingCommand {

	public ClearExecution() {
		super();
		this.command = "clear";
		this.aliases.add("cl");
	}

	@Override
	public boolean execute(CommandSender sender, List<String> args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + Messages.NOT_CONSOLE_COMMAND);
			return true;
		}

		if (!sender.hasPermission("bending.command.clear")) {
			sender.sendMessage(ChatColor.RED + Messages.NO_PERMISSION);
			return true;
		}

		Player player = (Player) sender;

		BendingPlayer bender = BendingPlayer.getBendingPlayer(player);

		if (args.isEmpty()) {
			bender.clearAbilities();
			sender.sendMessage(Messages.CLEARED);
		}
		else {
			try {
				int slot = Integer.parseInt(args.get(0));
				if (slot < 1 || slot > 9) {
					sender.sendMessage(ChatColor.RED + Messages.INVALID_SLOT);
					return true;
				}
				bender.removeAbility(--slot);
				String msg = Messages.SLOT_CLEARED;
				msg = msg.replaceAll("\\{0\\}", "" + slot);
				sender.sendMessage(msg);
			}
			catch (NumberFormatException ex) {
				sender.sendMessage(ChatColor.RED + Messages.INVALID_SLOT);
			}

		}

		return true;
	}

	@Override
	public void printUsage(CommandSender sender) {
		if (sender.hasPermission("bending.command.clear")) {
			sender.sendMessage("/bending clear [slot#]");
		}
	}
}
