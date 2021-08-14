package at.emreeocn.rpgcore.listener;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.Modify;

public class CaveListener implements Listener {
	
	private static Material[] allowedBlocks = {
		Material.STONE,
		Material.COBBLESTONE,
		Material.OBSIDIAN,
		Material.COAL_ORE,
		Material.IRON_ORE,
		Material.REDSTONE_ORE,
		Material.REDSTONE_ORE,
		Material.LAPIS_ORE,
		Material.EMERALD_ORE,
		Material.DIAMOND_ORE
	};
	
	private static boolean isAllowed(Material material) {
		for(Material m : allowedBlocks) {
			if(m == material) return true;
		}
		return false;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if(e.getBlock().getLocation().getWorld() == Config.getCave().getWorld()) {
			if(Modify.canModify(e.getPlayer())) return;
			e.setCancelled(true);
			if(isAllowed(e.getBlock().getType())) {
				Material m = e.getBlock().getType();
				Collection<ItemStack> drops = e.getBlock().getDrops();
				e.getBlock().setType(Material.BEDROCK);
				
				if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
					for(ItemStack item : drops) {
						if(e.getPlayer().getInventory().firstEmpty() != -1) {
							e.getPlayer().getInventory().addItem(item);
							
						} else {
							e.getPlayer().sendMessage(Config.getPrefix() + "�cDein Inventar ist voll!");
						}
					}
				}
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
					
					@Override
					public void run() {
						e.getBlock().setType(m);
					}
				}, 20 * 3);
				//  60 ticks = 3s
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(e.getBlock().getLocation().getWorld() == Config.getCave().getWorld()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if(e.getEntity().getLocation().getWorld() == Config.getCave().getWorld()) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getPlayer().getLocation().getWorld() == Config.getCave().getWorld()) {
			e.setCancelled(true);
		}
	}
	
}
