package com.fg.enhance.abilities;

import org.bukkit.entity.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class HealingTome extends Ability {

	public HealingTome() {
		type = AbilityType.INTERACT_PLAYER;
		name = "HealingTome";
		cooldown = 1;
	}

	@Override
	public void useAbility(Player p) {}

	@Override
	public void useAbilityOther(Player p, LivingEntity e) {
		if (e instanceof Damageable) {
			Damageable d = (Damageable)e;
			if (currentCooldown <= 0) {
				if (p.getHealth() != 0) {
					if (d.getHealth() <= d.getMaxHealth()-4) {
						p.setHealth(p.getHealth()-4);
						d.setHealth(d.getHealth()+4);
						p.sendMessage("Healed!");
						currentCooldown = cooldown;
						return;
					} else if (d.getHealth() < d.getMaxHealth()) {
						p.setHealth(p.getHealth()-(d.getMaxHealth()-d.getHealth()));
						d.setHealth(d.getHealth()+(d.getMaxHealth()-d.getHealth()));
						p.sendMessage("Healed!");
						currentCooldown = cooldown;
						return;
					} else {
						p.sendMessage("Player is already at full health.");
						return;
					}
				}
			} else {
				p.sendMessage("You have "+currentCooldown+" seconds until you can do this again.");
			}
		}
	}

	@Override
	public void useDefendAbility(Player p, EntityDamageEvent e) {}
}
