package com.fg.enhance.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.LifeSteal;
import com.fg.enhance.main.Enhance;

public class Leech extends Kit {

	/*
	 * Kit with ability
	 */
	public Leech() {
		name = "Leech";
		icon = Enhance.renameItem(new ItemStack(Material.BLAZE_POWDER), name);
		spawnItems.add(Enhance.renameItem(new ItemStack(Material.WOOD_SWORD), ChatColor.DARK_AQUA+"Old Sword"));
		lore.add(ChatColor.WHITE+"*Screeching noises*");
		lore.add("");
		lore.add(ChatColor.WHITE+"Not much is known about");
		lore.add(ChatColor.WHITE+"the leech. All we know");
		lore.add(ChatColor.WHITE+"is that he likes blood, and lots of it.");
		LifeSteal ls = new LifeSteal();
		ls.u = player;
		abilities.add(ls);
	}

}
