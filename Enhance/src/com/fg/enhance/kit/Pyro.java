package com.fg.enhance.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.*;
import com.fg.enhance.main.Enhance;

public class Pyro extends Kit {

	/*
	 * Kit with ability
	 */
	public Pyro() {
		name = "Pyro";
		icon = Enhance.renameItem(new ItemStack(Material.FIREBALL), name);
		spawnItems.add(Enhance.renameItem(new ItemStack(Material.WOOD_SWORD), ChatColor.DARK_AQUA+"Old Sword"));
		lore.add(ChatColor.WHITE+"Fire? What fire?");
		lore.add("");
		lore.add(ChatColor.WHITE+"Pyro is a pyromaniac from the moon.");
		lore.add(ChatColor.WHITE+"Somehow he doesn't know what fire is.");
		Burn b = new Burn();
		b.u = player;
		abilities.add(b);
	}

}
