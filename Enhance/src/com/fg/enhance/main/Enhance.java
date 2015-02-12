package com.fg.enhance.main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



import com.fg.enhance.ClanCommand;
//import com.fg.enhance.ClanCommand;
import com.fg.enhance.KitCommand;
import com.fg.enhance.PlayerMeta;
import com.fg.enhance.abilities.Ability;
import com.fg.enhance.abilities.Ability.AbilityType;
import com.fg.enhance.clans.Clan;
import com.fg.enhance.kit.*;
import com.fg.enhance.listeners.PlayerListener;

public class Enhance extends JavaPlugin {
	//Used for easy access to the plugin
	public static Enhance plugin;
	//List of kit classes
	public ArrayList<Class<? extends Kit>> kits =  new ArrayList<Class<? extends Kit>>();
	//Config file definitions
	public File playerConfig;
	public FileConfiguration playerConfigz;
	public File clanConfig;
	public FileConfiguration clanConfigz;
	//Clans
	public static ArrayList<Clan> clans = new ArrayList<Clan>();
	//max clan members
	public static int maxClanMembers = 16;
	
	@Override
	public void onEnable() {
		playerConfig = new File(getDataFolder(), "PlayerData.yml"); 
		playerConfigz = YamlConfiguration.loadConfiguration(playerConfig);
		clanConfig = new File(getDataFolder(), "ClanData.yml"); 
		clanConfigz = YamlConfiguration.loadConfiguration(clanConfig);
		plugin = this;
		kits.add(None.class);
		kits.add(Leech.class);
		kits.add(Pyro.class);
		kits.add(Viper.class);
		kits.add(Medic.class);
		kits.add(Fisherman.class);
		kits.add(Absorber.class);
		getCommand("kit").setExecutor(new KitCommand());
		getCommand("clan").setExecutor(new ClanCommand());
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(), this);
		if (!playerConfig.exists()) {
			try {
				playerConfigz.save(playerConfig);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (!clanConfig.exists()) {
			try {
				clanConfigz.save(clanConfig);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loadMeta();
		loadClans();
		//Tracks the players movement for the move ability type
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			@Override
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					PlayerMeta pm = PlayerMeta.getMeta(p);
					if (pm.kit != null) {
						for (Ability a : pm.kit.abilities) {
							if (a != null) {
								if (a.type == AbilityType.MOVE) {
									a.useAbility(p);
								}
							}
						}
					}
				}
			}
			
		}, 10, 10);
	}
	
	@Override
	public void onDisable() {
		saveMeta();
		saveClans();
	}
	
	public void loadClans() {
		try {
			clanConfigz.load(clanConfig);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String s : clanConfigz.getKeys(true))  {
			if (s.contains(".owner") || s.contains(".players"))
				continue;
			System.out.println(s);
			Clan c = new Clan(s, UUID.fromString(clanConfigz.getString(s+".owner")));
			PlayerMeta pm2 = PlayerMeta.getMeta(UUID.fromString(clanConfigz.getString(s+".owner")));
			pm2.clan = c;
			pm2.isClanOwner = true;
			Enhance.clans.add(c);
			for (Object o : clanConfigz.getList(s+".players")) {
				if (o == null)
					return;
				if (o instanceof String) {
					UUID u = UUID.fromString((String)o);
					PlayerMeta pm = PlayerMeta.getMeta(u);
					pm.clan = c;
					c.players.add(u);
				}
			}
		}
	}
	
	public void saveClans() {
		try {
			clanConfigz.load(clanConfig);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidConfigurationException e) {
			// TODO Auto-generated catch block
		}
		for (Clan c : clans) {
			if (c == null)
				continue;
			String str_1 = c.name+".players";
			String str_2 = c.name+".owner";
			if (!clanConfigz.contains(str_1))
				clanConfigz.createSection(str_1);
			if (!clanConfigz.contains(str_2))
				clanConfigz.createSection(str_2);
			List<String> uu = new ArrayList<String>();
			for (UUID u : c.players) {
				uu.add(u.toString());
			}
			clanConfigz.set(str_1, uu);
			clanConfigz.set(str_2, c.owner.toString());
			try {
				clanConfigz.save(clanConfig);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Saves the enhance player data
	 */
	public void saveMeta() {
		for (PlayerMeta pm : PlayerMeta.metaTable.values()) {
			if (pm != null) {
				String ka = pm.uuid + ".kit";
				if (!playerConfigz.contains(ka))
					playerConfigz.createSection(ka);
				playerConfigz.set(ka, pm.kit.name);
				try {
					playerConfigz.save(playerConfig);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void loadMeta() {
		for (String s : playerConfigz.getKeys(true)) {
			if (s.contains(".kit"))
				continue;
			PlayerMeta meta = PlayerMeta.getMeta(UUID.fromString(s));
			for (Class<? extends Kit> t : kits) {
				Kit k = null;
				try {
					k = t.newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (k != null) {
					if (playerConfigz.get(s+".kit").equals(k.name)) {
						k.player = UUID.fromString(s);
						meta.kit = k;
					}
				}
			}
		}
	}
	
	/*public void loadMeta() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p != null) {
				PlayerMeta meta = PlayerMeta.getMeta(p.getPlayer());
				for (Class<? extends Kit> t : kits) {
					Kit k = null;
					try {
						k = t.newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (k != null) {
						if (playerConfigz.contains("players." + p.getUniqueId().toString() + ".kit")) {
							if (playerConfigz.get("players." + p.getUniqueId().toString() + ".kit").equals(k.name)) {
								k.player = p.getUniqueId();
								meta.kit = k;
							}
						}
					}
				}
			}
		}
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p.getPlayer() != null) {
				PlayerMeta meta = PlayerMeta.getMeta(p.getPlayer());
				for (Class<? extends Kit> t : kits) {
					Kit k = null;
					try {
						k = t.newInstance();
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (k != null) {
						if (playerConfigz.contains("players." + p.getUniqueId().toString() + ".kit")) {
							if (playerConfigz.get("players." + p.getUniqueId().toString() + ".kit").equals(k.name)) {
								k.player = p.getUniqueId();
								meta.kit = k;
							}
						}
					}
				}
			}
		}
	}*/
	
	/*
	 * returns the kit inventory and displays it to the player
	 */
	public static Inventory showKitSelector(Player p) {
		int size = ((Enhance.plugin.kits.size() + 8) / 9) * 9;
		Inventory inv = Bukkit.createInventory(null, size, ChatColor.BLACK+"Class Selector");
		for (Class<? extends Kit> clazz : Enhance.plugin.kits) {
			try {
				Kit k = clazz.newInstance();
				if (k != null) {
					ItemStack i = k.icon.clone();
					ItemMeta im = i.getItemMeta();
					im.setLore(k.lore);
					i.setItemMeta(im);
					inv.addItem(i);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		p.openInventory(inv);
		return inv;
	}
	
	//Used to rename items with ease
	public static ItemStack renameItem(ItemStack item, String name) {
		ItemStack i = item.clone();
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
}
