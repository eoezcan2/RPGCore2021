package at.emreeocn.rpgcore.listener;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import at.emreeocn.rpgcore.commands.VanishCommand;
import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.Modify;
import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.collection.Collection;
import at.emreeocn.rpgcore.enderchest.Enderchest;
import at.emreeocn.rpgcore.plotsurvive.PlotSurviveManager;
import at.emreeocn.rpgcore.reward.RewardManager;
import at.emreeocn.rpgcore.rpg.RPG;
import at.emreeocn.rpgcore.scoreboard.RPGScoreboard;
import at.emreeocn.rpgcore.skill.Skill;

public class MainListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		try {
			if(Main.getMain().getSql().getCon().isClosed())
				Main.getMain().getSql().connect();
				
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	    
		for(Player vanishedPlayer : VanishCommand.vanished) {
		    e.getPlayer().hidePlayer(vanishedPlayer);
		}
		
		Player player = e.getPlayer();
		
		e.setJoinMessage("§2[§a+§2] §6" + e.getPlayer().getDisplayName());
		
		try {
			player.teleport(Config.getSpawn());
			player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cSpawn wurde noch nicht gesetzt!");
		}
		
		if(!player.getInventory().contains(RPG.getMenuItem())) player.getInventory().setItem(8, RPG.getMenuItem());
		
		if(!RPG.isInSystem(player)) RPG.insert(player);
		
		if(!Collection.isInSystem(player)) Collection.insert(player);
		
		Skill.updatePlayer(player);
		
		if(!RewardManager.isInSystem(player)) RewardManager.insert(player);
		
		if(!Enderchest.isInSystem(player)) Enderchest.insert(player);
		
		if(!Money.existsSQL(player.getUniqueId())) Money.insertSQL(player.getUniqueId(), player.getDisplayName());
		
		if(!PlotSurviveManager.isInserted(player)) PlotSurviveManager.insert(player);
		
		if(!player.hasPlayedBefore()) {
			player.sendMessage(Config.getPrefix() + "§cBitte betrete den Server neu!");
		}
		
		RPGScoreboard sb = new RPGScoreboard(player);
		sb.display();
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.setQuitMessage("§4[§c-§4] §6" + e.getPlayer().getDisplayName());
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onItemDamage(PlayerItemDamageEvent e) {
		e.getItem().setDurability((short) 0);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		e.setDroppedExp(0);
		e.getDrops().clear();
		e.setKeepLevel(true);
		e.setKeepInventory(true);
	}
	
	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Player player = e.getPlayer();
		
		Bukkit.getScheduler().runTaskLater(Main.getMain(), new Runnable() {
			
			@Override
			public void run() {
				Config.teleportToSpawn(player);
			}
		}, 1);
		
		Skill.updatePlayer(player);
	}
	
	@EventHandler
	public void onPortal(PlayerPortalEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		e.setCancelled(true);
	}
	
}
