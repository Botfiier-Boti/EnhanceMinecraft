package com.fg.enhance.abilities;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Fish extends Ability {

	/*
	 * Basic ability
	 */
	public Fish() {
		type = AbilityType.FISH;
		name =  "Fish";
		cooldown = 0;
	}

	/*
	 * When fired, it will get a random integer from zero to one hundred and if the integer less than or equal to thirty
	 * the function will heal player 'p' and play some effects.
	 */
	@Override
	public void useAbility(Player p) {
		if (p == null)
			return;
		Random r = new Random();
		int extra = r.nextInt(4);
		if (extra <= 0) {
			extra = 1;
		}
		p.getWorld().dropItem(p.getLocation(), new ItemStack(Material.RAW_FISH, extra));
	}

	@Override
	public void useAbilityOther(Player p, LivingEntity e) {}
	
	@Override
	public void useDefendAbility(Player p, EntityDamageEvent e) {}

}
