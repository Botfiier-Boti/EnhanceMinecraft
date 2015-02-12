package com.fg.enhance;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fg.enhance.clans.Clan;
import com.fg.enhance.main.Enhance;

public class ClanCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p =  (Player)sender;
			PlayerMeta m = PlayerMeta.getMeta(p);
			if (args.length == 0) {
				p.sendMessage("/clan create <name>");
				p.sendMessage("/clan invite <username>");
				p.sendMessage("/clan invites");
				p.sendMessage("/clan info");
				p.sendMessage("/clan leave");
				p.sendMessage("/clan disband");
				return true;
			} else if (args.length == 1) {
				if (args[0].equalsIgnoreCase("info")){
					for (Clan c : Enhance.clans) {
						if (c == null)
							continue;
						if (c.players.contains(p.getUniqueId()) || c.owner == p.getUniqueId()) {
							p.sendMessage("Name: "+c.name);
							p.sendMessage("Owner: "+Bukkit.getPlayer(c.owner).getName());
							p.sendMessage("Players:");
							for (UUID u : c.players) {
								p.sendMessage("- "+Bukkit.getOfflinePlayer(u).getName());
							}
							return true;
						} else {
							continue;
						}
					}
				}
				if (args[0].equalsIgnoreCase("invites")) {
					if (m.invites.size() == 0) {
						p.sendMessage("You have no pending invitations.");
						return false;
					}
					m.showInvites();
					return true;
				}
				if (args[0].equalsIgnoreCase("disband")) {
					for (Clan c : Enhance.clans) {
						if (c == null)
							continue;
						if (p.getUniqueId() == c.owner) {
							Enhance.clans.remove(c);
							p.sendMessage("The clan "+c.name+" has been disbanded");
							m.isClanOwner = false;
							return true;
						} else {
							continue;
						}
					}
				}
				if (args[0].equalsIgnoreCase("leave")) {
					for (Clan c : Enhance.clans) {
						if (c == null)
							continue;
						if (p.getUniqueId() != c.owner) {
							c.players.remove(p);
							p.sendMessage("You left the clan.");
							return true;
						} else {
							continue;
						}
					}
					p.sendMessage("You are not in a clan or you are an owner a clan.");
				}
			} else if (args.length == 2) {
				if (args[0].equalsIgnoreCase("invite")) {
					if (m.isClanOwner) {
						for (Clan c : Enhance.clans) {
							if (c.owner.equals(p.getUniqueId())) {
								if (Bukkit.getPlayer(args[1]) == null) {
									p.sendMessage("Player is not online.");
									return false;
								}
								Player p2 = Bukkit.getPlayer(args[1]);
								PlayerMeta pm = PlayerMeta.getMeta(p2.getUniqueId());
								pm.invites.add(c);
								p.sendMessage("Invited player!");
								return true;
							} else {
								continue;
							}
						}
					} else {
						p.sendMessage("You must own a clan to send invitations!");
						return false;
					}
				}
				if (args[0].equalsIgnoreCase("create")) {
					for (Clan c :  Enhance.clans) {
						if (c == null)
							continue;
						if (c.name == args[1]) {
							p.sendMessage("A clan with this name already exist");
							return false;
						}
					}
					Clan c =  new Clan(args[1], p.getUniqueId());
					m.clan = c;
					c.players.add(p.getUniqueId());
					Enhance.clans.add(c);
					m.isClanOwner = true;
					p.sendMessage("A clan with name "+args[1]+" has been created.");
					return true;
				}
			}
		} else {
			for (Clan c : Enhance.clans) {
				sender.sendMessage(c.name);
			}
			return true;
		}
		return false;
	}

}
