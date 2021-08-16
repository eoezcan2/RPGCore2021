package at.emreeocn.rpgcore.group;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GroupListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(GroupManager.isInGroup(e.getPlayer()))
			GroupManager.getGroup(e.getPlayer()).leave(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerHit(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
			Player player = (Player) e.getDamager();
			Player target = (Player) e.getEntity();
			
			if(GroupManager.isInGroup(player) && GroupManager.isInGroup(target)) {
				if(GroupManager.sameGroup(player, target))
					// Group members cannot damage eachother
					e.setCancelled(true);
			}
		}
	}
	
}
