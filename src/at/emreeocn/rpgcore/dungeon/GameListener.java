package at.emreeocn.rpgcore.dungeon;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

import at.emreeocn.rpgcore.util.Config;

public class GameListener implements Listener {

	@EventHandler
	public void onMobKill(EntityDeathEvent e) {
		if(!(e.getEntity() instanceof Player) && (e.getEntity().getKiller() instanceof Player)) {
			Player player = (Player) e.getEntity().getKiller();
			if(DungeonManager.isPlaying(player) && DungeonManager.getDungeon(player).getState() == GameState.LIVE) {
				e.setDroppedExp(0);
				e.getDrops().clear();
				DungeonManager.getDungeon(player).getGame().addKill(player, e.getEntity());
				if(DungeonManager.getDungeon(player) == null) return;
//				DungeonManager.getDungeon(player).getGame().spawnEntity();
			}
		}
	}
	
	@EventHandler
	public void onPortalEnter(EntityPortalEnterEvent e) {
		if(e.getEntity() instanceof Player) {
			Player player = (Player) e.getEntity();
			if(DungeonManager.isPlaying(player)) {
				if(DungeonManager.getDungeon(player).getState() == GameState.OVER || 
						DungeonManager.getDungeon(player).getState() == GameState.RECRUITING) {
					Bukkit.getPlayer(player.getUniqueId()).teleport(Config.getSpawn());
				}
			}
		}
	}
	
	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(DungeonManager.isPlaying(player)) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(DungeonManager.isPlaying(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if(DungeonManager.isPlaying(e.getPlayer())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(DungeonManager.isPlaying(e.getPlayer())) {
			DungeonManager.getDungeon(e.getPlayer()).removePlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		if(e.getEntity().getLocation().getWorld() == Config.getDungeonSpawn(0).getWorld()) {
			if(e.getSpawnReason() != SpawnReason.CUSTOM) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		if(e.getFrom() == Config.getDungeonSpawn(0).getWorld()) {
			if(DungeonManager.isPlaying(e.getPlayer())) {
				DungeonManager.getDungeon(e.getPlayer()).removePlayer(e.getPlayer());
			}
		}
	}
	
	@EventHandler
	public void onWeather(WeatherChangeEvent e) {
		if(e.getWorld() == Config.getDungeonSpawn(0).getWorld()) e.setCancelled(true);
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		if(DungeonManager.isPlaying(e.getPlayer())) {
			DungeonManager.getDungeon(e.getPlayer()).sendMessage(
					"§4[§cDungeon§4] " + "§6" + e.getPlayer().getDisplayName() + " §7>> §f" + e.getMessage());
		}
	}
	
}
