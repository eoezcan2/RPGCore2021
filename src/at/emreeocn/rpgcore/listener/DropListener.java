package at.emreeocn.rpgcore.listener;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.events.TaskFinishedEvent;
import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.reward.Reward;
import at.emreeocn.rpgcore.task.Task;

public class DropListener implements Listener {

	@EventHandler
	public void onMobKill(EntityDeathEvent e) {
		if(e.getEntity() instanceof Creature && e.getEntity().getKiller() instanceof Player) {
			Player player = (Player) e.getEntity().getKiller();
			
			switch(e.getEntity().getType()) {
			default:
				break;
				
			case WITHER_SKELETON:
			    if(e.getEntity().getLocation().getWorld() == Farmworld.NETHER.getSpawn().getWorld()) {
			    	checkWitherSkull(player, e.getDrops());
			    }
			    
				break;
			}
            
			if(e.getEntity() instanceof Monster) {
                int rnd = new Random().nextInt(Reward.getMaxMobCoins()) + 1;
                Money.addMoney(player.getUniqueId(), player.getDisplayName(), rnd);
            }
		}
	}
	
	private static void checkWitherSkull(Player player, List<ItemStack> items) {
		if(containsMaterial(items, Material.WITHER_SKELETON_SKULL)) {
			new TaskFinishedEvent(player, Task.WITHER_SKELETON_HEAD);
		}
	}
	
	private static boolean containsMaterial(List<ItemStack> items, Material material) {
		for(ItemStack i : items) {
			if(i.getType() == material) return true;
		}
		
		return false;
	}
	
	/*private static void randomLuck(Player player, int chance, ItemStack item) {
		int rnd = new Random().nextInt(chance);
		boolean b = false;
		
		if(rnd == 1) b = true;
		
		if(b) RewardManager.addItemStackToInventory(player, item);
	}*/
	
}
