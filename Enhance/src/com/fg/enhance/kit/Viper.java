package com.fg.enhance.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.*;
import com.fg.enhance.main.Enhance;

public class Viper extends Kit {

	/*
	 * Kit with ability
	 */
	public Viper() {
		name = "Viper";
		icon = Enhance.renameItem(new ItemStack(Material.POISONOUS_POTATO), name);
		spawnItems.add(Enhance.renameItem(new ItemStack(Material.WOOD_SWORD), ChatColor.DARK_AQUA+"Old Sword"));
		lore.add(ChatColor.WHITE+"*HISS*");
		lore.add("");
		lore.add(ChatColor.WHITE+"Two things we know about Viper:");
		lore.add(ChatColor.WHITE+"1. He is venomous.");
		lore.add(ChatColor.WHITE+"2. He likes potatos.");
		Poison p = new Poison();
		p.u = player;
		abilities.add(p);
	}

}
