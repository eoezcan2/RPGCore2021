package at.emreeocn.rpgcore.plotsurvive;

import org.bukkit.scheduler.BukkitRunnable;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.PacketMethods;

public class PlotSurviveTimer extends BukkitRunnable {

	private PlotSurvive p;
	private long seconds;
	
	public PlotSurviveTimer(PlotSurvive p, long seconds) {
		this.p = p;
		this.seconds = seconds;
		
		this.runTaskTimer(Main.getMain(), 0, 20);
	}

	public long getSeconds() {
		return seconds;
	}

	public PlotSurvive getPlotSurvive() {
		return p;
	}

	@Override
	public void run() {
		if(seconds >= 0) {
			PacketMethods.sendActionBar(p.getPlayer(), "§fZeit: §6" + seconds + " Sekunden");
			
		} else {
			if(!p.getMobs().isEmpty()) {
				p.lost();
			}
			
			cancel();
		}
		
		seconds--;
	}
	
}
