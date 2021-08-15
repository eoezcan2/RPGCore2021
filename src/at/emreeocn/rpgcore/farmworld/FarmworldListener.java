package at.emreeocn.rpgcore.farmworld;

import java.util.Collection;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.Modify;
import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.reward.Reward;

public class FarmworldListener implements Listener {

	@EventHandler
	public void onEntityHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			if(Modify.canModify((Player) e.getDamager())) return;
		}
		
		if(Farmworld.contains(e.getEntity().getWorld())) {
			if(Farmworld.isProtected(e.getEntity().getLocation(), Farmworld.getType(e.getEntity().getLocation()))) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(Farmworld.isProtectedAnyworld(e.getClickedBlock().getLocation())) {
				Material m = e.getPlayer().getInventory().getItemInMainHand().getType();
				if(m == Material.WATER_BUCKET || m == Material.LAVA_BUCKET || m == Material.BUCKET) e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		if(Farmworld.contains(e.getBlock().getWorld())) {
			if(Farmworld.isProtected(e.getBlock().getLocation(), Farmworld.getType(e.getBlock().getLocation()))) e.setCancelled(true);
			if(e.getBlock().getWorld() == Farmworld.END.getSpawn().getWorld()) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(Modify.canModify(e.getPlayer())) return;
		
		/* Spawn Protection */
		if(Farmworld.contains(e.getBlock().getWorld())) {
			if(Farmworld.isProtected(e.getBlock().getLocation(), Farmworld.getType(e.getBlock().getLocation()))) {
				e.setCancelled(true);
				return;
			}
			
			if(Farmworld.isOre(e.getBlock())) {
				int rnd = new Random().nextInt(Reward.getMaxMobCoins());
				Money.addMoney(e.getPlayer().getUniqueId(), e.getPlayer().getDisplayName(), rnd);
			}
		}
		
		/* Nether */
		if(e.getBlock().getLocation().getWorld() == Config.getNether()) {
			if(e.getBlock().getType() == Material.SPAWNER) e.setCancelled(true);
		}
		
		/* End */
		if(e.getBlock().getLocation().getWorld() == Config.getEnd().getWorld()) {
			Material m = e.getBlock().getType();
			Collection<ItemStack> drops = e.getBlock().getDrops();
			e.getBlock().setType(Material.BEDROCK);
			e.setCancelled(true);
			
			if(e.getPlayer().getGameMode() != GameMode.CREATIVE) {
				for(ItemStack item : drops) {
					e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), item);
				}
			}
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getMain(), new Runnable() {
				
				@Override
				public void run() {
					e.getBlock().setType(m);
				}
			}, 20 * 3);
		}
	}
	
	@EventHandler
	public void onCreeperExplosion(EntityExplodeEvent e) {
		if(Farmworld.contains(e.getEntity().getWorld())) {
			if(Farmworld.isProtected(e.getEntity().getLocation(), Farmworld.getType(e.getEntity().getLocation()))) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onEndermanPickup(EntityChangeBlockEvent e) {
		if(Farmworld.contains(e.getEntity().getWorld())) {
			if(Farmworld.isProtected(e.getEntity().getLocation(), Farmworld.getType(e.getEntity().getLocation()))) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onProjectile(ProjectileHitEvent e) {
		if(e.getEntity().getShooter() instanceof Player) {
			if(Farmworld.isProtectedAnyworld(((Player)e.getEntity().getShooter()).getLocation())) e.getEntity().remove();
		}
	}
	
}
