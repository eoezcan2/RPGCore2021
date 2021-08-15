package at.emreeocn.rpgcore.plotsurvive;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class PlotSurvivalListener implements Listener {

	@EventHandler
	public void onEntityDamage(EntityDamageEvent e) {
		if(PlotSurviveManager.getPlotSurvive(e.getEntity()) != null) {
			if(e.getCause() == DamageCause.FALL) e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			if(PlotSurviveManager.isPlaying(player))
				if(PlotSurviveManager.getPlotSurvive(e.getEntity()).getPlayer() != player)
					e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityKill(EntityDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player && !(e.getEntity() instanceof Player)) {
			if(PlotSurviveManager.getPlotSurvive(e.getEntity()) != null) {
				PlotSurviveManager.getPlotSurvive(e.getEntity()).killMob(e.getEntity());
			}
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(PlotSurviveManager.isPlaying(e.getPlayer())) PlotSurviveManager.getPlotSurvive(e.getPlayer()).lost();
	}
	
}
