package at.emreeocn.rpgcore.listener;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import at.emreeocn.rpgcore.util.Modify;
import at.emreeocn.rpgcore.util.PacketMethods;
import at.emreeocn.rpgcore.dragonraid.DragonRaidManager;
import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.playerinfo.PlayerInfoMenu;
import at.emreeocn.rpgcore.rpg.RPG;
import at.emreeocn.rpgcore.rpg.RPGMenu;
import at.emreeocn.rpgcore.scoreboard.RPGScoreboard;

public class RPGListener implements Listener {
	
	@EventHandler
	public void onNetherPortal(PlayerTeleportEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getPlayer().getInventory().getItemInHand() != null && e.getPlayer().getInventory().getItemInHand().isSimilar(RPG.getMenuItem())) {
				RPGMenu menu = new RPGMenu(e.getPlayer());
				e.getPlayer().openInventory(menu.getGui());
				e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLOCK_CHEST_OPEN, 8, 1);
			}
		}
	}
	
	@EventHandler
	public void onInteractEntity(PlayerInteractAtEntityEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getRightClicked() instanceof Player && e.getPlayer().isSneaking()) {
			Player target = (Player) e.getRightClicked();
			e.getPlayer().openInventory(new PlayerInfoMenu(e.getPlayer(), target).getGui());
		}
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			if(Modify.canModify((Player) e.getDamager())) return;
			
			if(((Player) e.getDamager()).getInventory().getItemInMainHand().isSimilar(RPG.getMenuItem())) {
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getItemDrop().getItemStack().isSimilar(RPG.getMenuItem())) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPickUp(EntityPickupItemEvent e) {
		if(e.getEntity() instanceof Player) {
			PacketMethods.sendActionBar((Player) e.getEntity(), "§f+" + e.getItem().getItemStack().getAmount() + " " + e.getItem().getName());
		}
	}
	
	@EventHandler
	public void onPlayerWorldChange(PlayerChangedWorldEvent e) {
		RPGScoreboard sb = new RPGScoreboard(e.getPlayer());
		sb.display();
	}
	
	@EventHandler
	public void onProjectile(PlayerEggThrowEvent e) {
		if(e.getEgg().getItem().isSimilar(DragonRaidManager.spawnItem())) {
			if(e.getEgg().getLocation().getWorld() == Farmworld.END.getSpawn().getWorld()) {
				if(!DragonRaidManager.dragonRaidRunning()) {
					DragonRaidManager.spawnDragon(e.getPlayer().getLocation());
					e.getPlayer().sendMessage("§aDu hast den Raid gestartet");
					Bukkit.broadcastMessage("§5Ein Drachenraid wurde gestartet. Eilt zum End!");
					
				} else {
					e.getPlayer().sendMessage("§cEs läuft bereits ein §5Drachenraid§c!");
					e.getPlayer().getInventory().addItem(DragonRaidManager.spawnItem());
				}
				
				e.getEgg().remove();
			}
		}
	}
	
}
