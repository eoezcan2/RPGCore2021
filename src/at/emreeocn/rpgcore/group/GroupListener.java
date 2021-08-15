package at.emreeocn.rpgcore.group;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class GroupListener implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(GroupManager.isInGroup(e.getPlayer()))
			GroupManager.getGroup(e.getPlayer()).leave(e.getPlayer());
	}
	
}
