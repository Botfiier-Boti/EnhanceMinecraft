package com.fg.enhance.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.*;
import com.fg.enhance.main.Enhance;

public class Medic extends Kit {

	public Medic() {
		name = "Medic";
		icon = Enhance.renameItem(new ItemStack(Material.BOOK), name);
		spawnItems.add(Enhance.renameItem(new ItemStack(Material.WOOD_SWORD), ChatColor.DARK_AQUA+"Old Sword"));
		lore.add(ChatColor.WHITE+"I will heal you with stories!");
		lore.add("");
		lore.add(ChatColor.WHITE+"Medic loves reading, infact");
		lore.add(ChatColor.WHITE+"he loves it so much that it heals people!");
		lore.add(ChatColor.WHITE+"Right click a player to transfer health to them!");
		HealingTome ht = new HealingTome();
		ht.u = player;
		abilities.add(ht);
	}

}
