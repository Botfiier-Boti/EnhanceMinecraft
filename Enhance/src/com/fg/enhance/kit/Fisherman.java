package com.fg.enhance.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.*;
import com.fg.enhance.main.Enhance;

public class Fisherman extends Kit {

	/*
	 * Kit with ability
	 */
	public Fisherman() {
		name = "Fisherman";
		icon = Enhance.renameItem(new ItemStack(Material.FISHING_ROD), name);
		spawnItems.add(Enhance.renameItem(new ItemStack(Material.WOOD_SWORD), ChatColor.DARK_AQUA+"Old Sword"));
		ItemStack i = new ItemStack(Material.FISHING_ROD);
		i.setDurability((short) 46);
		i.addEnchantment(Enchantment.LURE, 1);
		spawnItems.add(i);
		lore.add(ChatColor.WHITE+"Fish are food, and friends.");
		lore.add("");
		lore.add(ChatColor.WHITE+"Fisherman is an expert fisher");
		lore.add(ChatColor.WHITE+"once he gets a fishing rod he can");
		lore.add(ChatColor.WHITE+"catch up to 4 extra fish!");
		Fish f = new Fish();
		f.u = player;
		abilities.add(f);
	}

}
