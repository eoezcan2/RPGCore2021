package at.emreeocn.rpgcore.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.Modify;
import at.emreeocn.rpgcore.shop.AdminShop;

public class LobbyListener implements Listener {

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getPlayer().getLocation().getWorld() == Config.getSpawn().getWorld()) {
			if(!e.getPlayer().getInventory().getItemInMainHand().getType().isEdible()
					|| !e.getPlayer().getInventory().getItemInOffHand().getType().isEdible()) {
				e.setCancelled(true);
			}
			
			
			
			/*if(e.getAction() == Action.PHYSICAL) {
				if(e.getClickedBlock().getType() == Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
					if(Task.didTask(e.getPlayer(), Task.WITHER_SKELETON_HEAD)) {
						Config.teleportToCave(e.getPlayer());
					}
				}
			}*/
		}
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getRightClicked().getType() == EntityType.VILLAGER && 
				e.getRightClicked().getLocation().getWorld() == Config.getSpawn().getWorld()) {
			Player player = e.getPlayer();
			player.closeInventory();
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
				
				@Override
				public void run() {
					AdminShop shop = new AdminShop(player);
					player.openInventory(shop.getGui());
				}
			}, 1);
		}
	}
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		if(e.getWorld() == Config.getSpawn().getWorld() || e.getWorld() == Config.getCitybuild().getWorld()) e.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			if(Modify.canModify((Player) e.getEntity())) return;
			
			Player player = (Player) e.getEntity();
			if(player.getWorld() == Config.getSpawn().getWorld()) {
				e.setCancelled(true);
				if(e.getCause() == DamageCause.VOID) {
					player.teleport(Config.getSpawn());
				}
			}
		}
	}
	
	@EventHandler
	public void onFoodChange(FoodLevelChangeEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getEntity().getLocation().getWorld() == Config.getSpawn().getWorld()) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBuild(BlockPlaceEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		try {
			if(e.getBlock().getWorld() == Config.getSpawn().getWorld()) {
				e.setCancelled(true);
			}
		} catch(IllegalArgumentException ex) {
			e.getPlayer().sendMessage(Config.getPrefix() + "§cSpawn wurde noch nicht gesetzt!");
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		try {
			if(e.getBlock().getWorld() == Config.getSpawn().getWorld()) {
				e.setCancelled(true);
			}
		} catch(IllegalArgumentException ex) {
			e.getPlayer().sendMessage(Config.getPrefix() + "§cSpawn wurde noch nicht gesetzt!");
		}
	}
	
}
