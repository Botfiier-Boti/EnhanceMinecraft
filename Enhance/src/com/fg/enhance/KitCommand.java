package com.fg.enhance;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fg.enhance.main.Enhance;

public class KitCommand implements CommandExecutor {
	
	/*
	 * Command /kit
	 * shows kit selector to sender.
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			Enhance.showKitSelector(p);
		} else {
			sender.sendMessage("You are not a player.");
		}
		return false;
	}

}
