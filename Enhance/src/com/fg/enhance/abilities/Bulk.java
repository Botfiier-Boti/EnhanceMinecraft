package com.fg.enhance.abilities;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Bulk extends Ability {

	public Bulk() {
		type = AbilityType.DEFEND;
		name =  "Bulk";
		cooldown = 0;
	}

	@Override
	public void useAbility(Player p) {}

	@Override
	public void useAbilityOther(Player p, LivingEntity e) {}

	@Override
	public void useDefendAbility(Player p, EntityDamageEvent e) {
		if (p == null)
			return;
		if (e.isCancelled() == true)
			return;
		e.setDamage(e.getFinalDamage()*.5);
	}
	
}
