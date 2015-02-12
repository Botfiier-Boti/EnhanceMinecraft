package com.fg.enhance.kit;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.abilities.Ability;

public abstract class Kit {
	//Name of the kit
	public String name = "";
	//GUI icon of the kit
	public ItemStack icon = null;
	//Player who is using the kit
	public UUID player = null;
	//List of items you get on spawn
	public ArrayList<ItemStack> spawnItems;
	//List of abilities the kit has
	public ArrayList<Ability> abilities;
	//Lore of the icon
	public ArrayList<String> lore;
	
	/*
	 * Basic kit example:
	 * 
	 * public class TestKit extends Kit{
	 *    
	 *    public TestKit() {
	 *    	name = "test";
	 *    	icon = Enhance.renameItem(new ItemStack(Material.<Material>), name);
	 *    	spawnItems.add(new ItemStack(Material.<Material>));
	 *     	//Optional
	 *     	lore.add("Lore text.");
	 *     	//Recommended
	 *      <? extends Ability> ability = new <? extends Ability>();
	 *      ability.u = player;
	 *    }
	 *    
	 * }
	 */
	public Kit() {
		this.spawnItems = new ArrayList<ItemStack>();
		this.abilities = new ArrayList<Ability>();
		this.lore = new ArrayList<String>();
	}
	
	//Gives the player 'p' the kit's spawn items
	public void give(Player p) {
		for (ItemStack i : spawnItems) {
			if (i != null) {
				p.getInventory().addItem(i);
			}
		}
	}
	
}
