package com.fg.enhance.abilities;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.fg.enhance.main.Enhance;

public abstract class Ability implements Listener{
	//Ability name
	public String name = "";
	//Default cool-down
	public int cooldown = 0;
	//The cool-down the ability has
	public int currentCooldown = 0;
	//UUID of the player with the ability
	public UUID u = null;
	//The type of the ability
	public AbilityType type = null;
	//if true ability can be used
	public boolean useAb = true;
	
	/*
	 * Basic ability example:
	 * 
	 * public class TestAbility extends Ability {
	 *    
	 *    public TestAbility() {
	 *    	type = AbilityType.<Type>;
	 *    	name = "TestAbility";
	 *    	cooldown = 0;
	 *    }
	 *    
	 * }
	 */
	public Ability () {
		currentCooldown = cooldown;
		Bukkit.getScheduler().runTaskTimer(Enhance.plugin, new Runnable() {

			@Override
			public void run() {
				countDown();
			}
			
		}, 20, 20);
	}
	
	/*
	 * Resets the cool-down
	 */
	public void resetCooldown() {
		this.currentCooldown = cooldown;
	}
	
	/*
	 * Counts down the time left on the cool-down
	 */
	public void countDown() {
		if (currentCooldown > 0) {
			currentCooldown -= 1;
		}
	}
	
	/*
	 * used to call an 'Event'
	 */
	public abstract void useAbility(Player p);
	/*
	 * used to call an 'Event' on another entity
	 */
	public abstract void useAbilityOther(Player p, LivingEntity e);
	
	/*
	 * used to call for defensive abilities
	 */
	public abstract void useDefendAbility(Player p, EntityDamageEvent e);
	
	/*
	 * Enumeration used to define what event the ability will fire the use ability function in.
	 */
	public enum AbilityType {
		DAMAGE,
		DEFEND,
		INTERACT,
		INTERACT_ENTITY,
		INTERACT_PLAYER,
		FLY,
		MOVE,
		KILL,
		FISH,
		BLOCK_BREAK,
		BLOCK_PLACE;
	}
}
