package com.fg.enhance.events;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.fg.enhance.PlayerMeta;
import com.fg.enhance.abilities.Ability;
import com.fg.enhance.kit.Kit;

public class KitChangeEvent extends Event {
	//Handlers of the event
	private static final HandlerList handlers = new HandlerList();
	//The kit the player changed to
	private Kit k = null;
	//The player changing kit
	private Player p = null;
	
	public KitChangeEvent(Kit k, Player p) {
		this.k = k;
		this.p = p;
	}
	public Player getPlayer() {
		return p;
	}
	public Kit getKit() {
		return k;
	}
	public ArrayList<Ability> getAbilities() {
		return k.abilities;
	}
	public PlayerMeta getPlayerMeta() {
		return PlayerMeta.getMeta(p);
	}
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	public static HandlerList getHandlerList() {
	    return handlers;
	}

}
