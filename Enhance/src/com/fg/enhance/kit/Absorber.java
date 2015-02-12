package com.fg.enhance.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.*;
import com.fg.enhance.main.Enhance;

public class Absorber extends Kit {

	public Absorber() {
		name = "Absorber";
		icon = Enhance.renameItem(new ItemStack(Material.CHAINMAIL_CHESTPLATE), name);
		spawnItems.add(Enhance.renameItem(new ItemStack(Material.WOOD_SWORD), ChatColor.DARK_AQUA+"Old Sword"));
		lore.add(ChatColor.WHITE+"Bulk is what it is about!");
		lore.add("");
		lore.add(ChatColor.WHITE+"The absorber is a very bulky thing.");
		lore.add(ChatColor.WHITE+"he takes 50% of the damage he receives");
		lore.add(ChatColor.WHITE+"and deals 50% of damage he sends.");
		Bulk b = new Bulk();
		b.u = player;
		abilities.add(b);
	}

}
