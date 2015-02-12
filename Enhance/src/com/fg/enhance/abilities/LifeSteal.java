package com.fg.enhance.abilities;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class LifeSteal extends Ability {

	/*
	 * Basic ability
	 */
	public LifeSteal() {
		type = AbilityType.DAMAGE;
		name =  "LifeSteal";
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
		int chance = r.nextInt(100);
		if (chance <= 30) {
			if (p.getHealth() != 0) {
				if (p.getHealth() != p.getMaxHealth()) {
					p.setHealth(p.getHealth()+1);
				}
				p.getWorld().playSound(p.getLocation(), Sound.DRINK, 1.0F, 1.0F);
				p.getWorld().playEffect(p.getLocation(), Effect.HEART, 1);
			}
		}
	}

	@Override
	public void useAbilityOther(Player p, LivingEntity e) {}

	@Override
	public void useDefendAbility(Player p, EntityDamageEvent e) {}
	
}
