package com.fg.enhance.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.main.Enhance;


public class None extends Kit {

	/*
	 * Basic Kit
	 */
	public None() {
		name = "None";
		icon = Enhance.renameItem(new ItemStack(Material.WOOL), name);
	}

}
