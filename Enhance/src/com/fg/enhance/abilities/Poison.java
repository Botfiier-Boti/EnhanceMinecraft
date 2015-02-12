package com.fg.enhance.abilities;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Poison extends Ability {

	/*
	 * Basic ability
	 */
	public Poison() {
		type = AbilityType.DAMAGE;
		name =  "Poison";
		cooldown = 0;
	}

	@Override
	public void useAbility(Player p) {}

	@Override
	public void useAbilityOther(Player p, LivingEntity e) {
		if (p == null)
			return;
		if (e ==  null)
			return;
		Random r = new Random();
		int chance = r.nextInt(100);
		if (chance <= 30) {
			e.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60, 1));
			e.getWorld().playEffect(e.getLocation(), Effect.INSTANT_SPELL, 0);
			e.getWorld().playSound(e.getLocation(), Sound.CLICK, 1.0F, 1.0F);
		}
	}

	@Override
	public void useDefendAbility(Player p, EntityDamageEvent e) {}
	
}
