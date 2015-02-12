package com.fg.enhance.clans;

import java.util.ArrayList;
import java.util.UUID;

public class Clan {
	public String name = "";
	public UUID owner = null;
	public ArrayList<UUID> players = new ArrayList<UUID>();
	
	public Clan(String name, UUID owner) {
		this.name = name;
		this.owner = owner;
	}

}
