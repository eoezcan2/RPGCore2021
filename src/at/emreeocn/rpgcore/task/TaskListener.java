package at.emreeocn.rpgcore.task;

import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.github.intellectualsites.plotsquared.bukkit.events.PlayerClaimPlotEvent;

import at.emreeocn.rpgcore.dragonraid.DragonRaidManager;
import at.emreeocn.rpgcore.events.TaskFinishedEvent;
import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.Modify;

public class TaskListener implements Listener {
	
	@EventHandler
	public void onEntityInteract(PlayerInteractAtEntityEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getRightClicked() instanceof Player) {
			Player target = (Player) e.getRightClicked();
			if(target.hasPermission("rpgproject.task.admin")) {
				new TaskFinishedEvent(e.getPlayer(), Task.TOUCH_ADMIN);
			}
		}
	}
	
	@EventHandler
	public void onCraft(CraftItemEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			if(e.getRecipe().getResult().getType() == Material.DIAMOND_CHESTPLATE) {
				new TaskFinishedEvent((Player) e.getWhoClicked(), Task.DIAMOND_CHESTPLATE);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(Modify.canModify(e.getPlayer())) return;

		if(e.getBlock().getLocation().getWorld() != Config.getSpawn().getWorld()) {
			if(e.getBlock().getType() == Material.OBSIDIAN && e.getBlock().getLocation().getWorld() == Farmworld.NORMAL.getSpawn().getWorld())
				new TaskFinishedEvent(e.getPlayer(), Task.BREAK_OBSIDIAN_BLOCK);
		}
	}
	
	@EventHandler
	public void onPlotClaim(PlayerClaimPlotEvent e) {
		new TaskFinishedEvent(e.getPlayer(), Task.PLOT_CLAIM);
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		if(e.getEntity() instanceof Player) {
			new TaskFinishedEvent((Player) e.getEntity(), Task.DIE);
		}
	}
	
	@EventHandler
	public void onWorldChange(PlayerChangedWorldEvent e) {
		if(e.getPlayer().getLocation().getWorld() == Config.getCave().getWorld()) {
			new TaskFinishedEvent(e.getPlayer(), Task.TELEPORT_CAVE);
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof EnderDragon) {
			if(e.getDamager() instanceof Player) {
				DragonRaidManager.getDamagers().add((Player) e.getDamager());
				
			} else if(e.getDamager() instanceof Projectile) {
				DragonRaidManager.getDamagers().add(((Player) ((Arrow) e).getShooter()));
			}
		}
	}
	
	@EventHandler
	public void onEntityKill(EntityDeathEvent e) {
		if(e.getEntity() instanceof EnderDragon) {
			DragonRaidManager.taskAllDamagers();
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		if(DragonRaidManager.getDamagers().contains(e.getPlayer())) DragonRaidManager.getDamagers().remove(e.getPlayer());
	}
	
}
