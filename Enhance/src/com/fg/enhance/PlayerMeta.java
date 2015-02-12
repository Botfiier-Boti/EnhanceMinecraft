package com.fg.enhance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.clans.Clan;
import com.fg.enhance.kit.Kit;
import com.fg.enhance.kit.None;
import com.fg.enhance.main.Enhance;

public class PlayerMeta {
	//Holds all player data for enhance
	public static HashMap<UUID, PlayerMeta> metaTable =  new HashMap<UUID, PlayerMeta>();
	//Player's UUID
	public UUID uuid;
	//Kit
	public Kit kit = new None();
	//isOwnerofClan
	public boolean isClanOwner = false;
	public Clan clan = null;
	public ArrayList<Clan> invites = new ArrayList<Clan>();
	
	/*
	 * Function that returns the data of the player.
	 */
	public static PlayerMeta getMeta(Player p) {
		if (!metaTable.containsKey(p.getUniqueId()))
			metaTable.put(p.getUniqueId(), new PlayerMeta());
		metaTable.get(p.getUniqueId()).uuid = p.getUniqueId();
		metaTable.get(p.getUniqueId()).kit.player = p.getUniqueId();
		return metaTable.get(p.getUniqueId());
	}
	
	public static PlayerMeta getMeta(UUID u) {
		if (!metaTable.containsKey(u))
			metaTable.put(u, new PlayerMeta());
		metaTable.get(u).uuid =u;
		metaTable.get(u).kit.player = u;
		return metaTable.get(u);
	}
	
	public void showInvites() {
		Player p = Bukkit.getPlayer(uuid);
		int size = ((invites.size() + 8) / 9) * 9;
		Inventory inv = Bukkit.createInventory(null, size, ChatColor.BLACK+"Clan Invites");
		for (Clan c : invites) {
			ItemStack i = Enhance.renameItem(new ItemStack(Material.SKULL_ITEM), ChatColor.GREEN+c.name);
			inv.addItem(i);
		}
		p.openInventory(inv);
	}
}
