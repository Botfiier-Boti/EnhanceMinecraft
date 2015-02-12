package com.fg.enhance.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.fg.enhance.PlayerMeta;
import com.fg.enhance.abilities.Ability;
import com.fg.enhance.abilities.Ability.AbilityType;
import com.fg.enhance.clans.Clan;
import com.fg.enhance.events.KitChangeEvent;
import com.fg.enhance.kit.Absorber;
import com.fg.enhance.kit.Kit;
import com.fg.enhance.kit.None;
import com.fg.enhance.main.Enhance;

public class PlayerListener implements Listener {
	
	/*
	 * Fires the player's ability function of kits with the defend entity type
	 */
	@EventHandler
	public void onDamageEntity(EntityDamageEvent e) {
		if (e.getEntity()== null)
			return;
		if (e.isCancelled() == true)
			return;
		if (e.getEntity() instanceof Player) {
			Player p = (Player) e.getEntity();
			PlayerMeta pm = PlayerMeta.getMeta(p);
			if (pm.kit != null) {
				for (Ability a : pm.kit.abilities) {
					if (a != null) {
						if (a.type == AbilityType.DEFEND) {
							a.useDefendAbility(p, e);
						}
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the damage type
	 */
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() == null)
			return;
		if (e.getEntity() == null) 
			return;
		if (e.isCancelled() == true)
			return;
		if (e.getDamager() instanceof Player) {
			Player p = (Player)e.getDamager();
			PlayerMeta pm = PlayerMeta.getMeta(p);
			if (e.getEntity() instanceof Player) {
				PlayerMeta m = PlayerMeta.getMeta((Player)e.getEntity());
				if (m.clan != null)
					if (pm.clan.name.contains(m.clan.name)) {
						e.setCancelled(true);
						return;
				}
			}
			if (pm.kit != null) {
				if (pm.kit instanceof Absorber) {
					e.setDamage(e.getFinalDamage()*.5);
				}
				for (Ability a : pm.kit.abilities) {
					if (a != null) {
						if (a.type == AbilityType.DAMAGE) {
							a.useAbility(p);
							if (e.getEntity() instanceof LivingEntity) {
								a.useAbilityOther(p, (LivingEntity)e.getEntity());
							}
						}
					}
				}
			}
		}
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			PlayerMeta pm = PlayerMeta.getMeta(p);
			if (pm.kit instanceof Absorber) {
				for (ItemStack i : p.getInventory().getArmorContents()) {
					if (i != null) {
						i.setDurability((short) (i.getDurability()+4));
						p.updateInventory();
					}
				}
			}
		}
		if (e.getDamager() instanceof Projectile) {
			Projectile pro = (Projectile)e.getDamager();
			if (pro.getShooter() != null) {
				if (pro.getShooter() instanceof Player) {
					Player p = (Player)pro.getShooter();
					PlayerMeta pm = PlayerMeta.getMeta(p);
					if (e.getEntity() instanceof Player) {
						PlayerMeta m = PlayerMeta.getMeta((Player)e.getEntity());
						if (pm.clan.name.contains(m.clan.name)) {
							e.setCancelled(true);
							return;
						}
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the Interact type
	 */
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (pm.kit != null) {
			for (Ability a : pm.kit.abilities) {
				if (a != null) {
					if (a.type == AbilityType.INTERACT) {
						a.useAbility(p);
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the interact entity type
	 */
	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (pm.kit != null) {
			if (e.getRightClicked() instanceof LivingEntity) {
				for (Ability a : pm.kit.abilities) {
					if (a != null) {
						if (a.type == AbilityType.INTERACT_ENTITY) {
							a.useAbilityOther(p, (LivingEntity)e.getRightClicked());
						}
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the interact player type
	 */
	@EventHandler
	public void onInteractPlayer(PlayerInteractEntityEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (e.getRightClicked() instanceof Player) {
			if (pm.kit != null) {
				for (Ability a : pm.kit.abilities) {
					if (a != null) {
						if (a.type == AbilityType.INTERACT_PLAYER) {
							a.useAbilityOther(p, (Player)e.getRightClicked());
						}
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the fishing type
	 */
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (e.getState() == State.CAUGHT_FISH) {
			if (pm.kit != null) {
				for (Ability a : pm.kit.abilities) {
					if (a != null) {
						if (a.type == AbilityType.FISH) {
							a.useAbility(p);
						}
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the fly type
	 */
	@EventHandler
	public void onFly(PlayerToggleFlightEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (pm.kit != null) {
			for (Ability a : pm.kit.abilities) {
				if (a != null) {
					if (a.type == AbilityType.FLY) {
						a.useAbility(p);
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the block break type
	 */
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (pm.kit != null) {
			for (Ability a : pm.kit.abilities) {
				if (a != null) {
					if (a.type == AbilityType.BLOCK_BREAK) {
						a.useAbility(p);
					}
				}
			}
		}
	}
	
	/*
	 * Fires the player's ability function of kits with the block place type
	 */
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if (e.getPlayer() == null)
			return;
		if (e.isCancelled() == true)
			return;
		Player p = e.getPlayer();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (pm.kit != null) {
			for (Ability a : pm.kit.abilities) {
				if (a != null) {
					if (a.type == AbilityType.BLOCK_PLACE) {
						a.useAbility(p);
					}
				}
			}
		}
	}

	/*
	 * Fires the player's ability function of kits with the kill type
	 */
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity() ==  null)
			return;
		if (e.getEntity().getKiller() ==  null)
			return;
		Player p = e.getEntity().getKiller();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (pm.kit != null) {
			for (Ability a : pm.kit.abilities) {
				if (a != null) {
					if (a.type == AbilityType.KILL) {
						a.useAbility(p);
					}
				}
			}
		}
	}
	
	//Auto respawn
	@EventHandler
	public void onDeath(final PlayerDeathEvent e) {
		Bukkit.getScheduler().runTaskLater(Enhance.plugin, new Runnable(){

			@Override
			public void run() {
				e.getEntity().spigot().respawn();
			}
			
		}, 100);
	}
	
	//Gives the items that the player's kit contains
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		if (e.getPlayer() ==  null)
			return;
		PlayerMeta pm = PlayerMeta.getMeta(e.getPlayer());
		pm.kit.give(e.getPlayer());
	}

	//Tracks the kit gui
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (e.getInventory() == null)
			return;
		if (e.getClickedInventory() ==  null)
			return;
		if (e.getWhoClicked() == null)
			return;
		Inventory inv = e.getInventory();
		Player p = (Player) e.getWhoClicked();
		PlayerMeta pm = PlayerMeta.getMeta(p);
		if (inv.getTitle().contains(ChatColor.BLACK+"Clan Invites")) {
			for (Clan c : pm.invites) {
				if (c == null)
					return;
				if (e.getCurrentItem().getItemMeta().getDisplayName().contains(ChatColor.GREEN+c.name)) {
					if (c.players.size() >= Enhance.maxClanMembers) {
						p.sendMessage("That clan is full.");
						e.setCancelled(true);
						return;
					}
					c.players.add(p.getUniqueId());
					pm.invites.remove(c);
					p.sendMessage("You have joined the clan "+c.name);
					pm.clan = c;
					pm.showInvites();
					e.setCancelled(true);
					return;
				}
			}
			e.setCancelled(true);
		}
		if (inv.getTitle().contains("Class Selector")) {
			if (e.getCurrentItem().getType() == Material.AIR)
				return;
			for (Class<? extends Kit> type : Enhance.plugin.kits) {
				if (type != null) {
					try {
						Kit k = type.newInstance();
						if (e.getCurrentItem().getItemMeta().getDisplayName().contains(k.name)) {
							k.player = p.getUniqueId();
							pm.kit = k;
							KitChangeEvent kitchange = new KitChangeEvent(k, p);
							Bukkit.getServer().getPluginManager().callEvent(kitchange);
							if (!(k instanceof None)) {
								p.sendMessage(ChatColor.DARK_AQUA + "You will receive kit items next the time you respawn.");
								p.sendMessage(ChatColor.DARK_AQUA + "Selected " + k.name);
							} else {
								p.closeInventory();
								p.sendMessage(ChatColor.DARK_AQUA + "Kit Removed");
							}
							e.setCancelled(true);
						} else {
							e.setCancelled(true);
						}
					} catch (InstantiationException e1) {
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}
