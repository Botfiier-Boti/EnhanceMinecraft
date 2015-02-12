package com.fg.enhance.abilities;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Burn extends Ability {

	/*
	 * Basic ability
	 */
	public Burn() {
		type = AbilityType.DAMAGE;
		name =  "Burn";
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
		if (chance <= 50) {
			e.setFireTicks(e.getFireTicks()+60);
			e.getWorld().playEffect(e.getLocation(), Effect.FLAME, 0);
			e.getWorld().playSound(e.getLocation(), Sound.FIRE_IGNITE, 1.0F, 1.0F);
		}
	}
	
	@Override
	public void useDefendAbility(Player p, EntityDamageEvent e) {}

}
