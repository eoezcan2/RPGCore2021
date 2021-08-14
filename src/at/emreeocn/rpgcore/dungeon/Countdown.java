package at.emreeocn.rpgcore.dungeon;

import org.bukkit.scheduler.BukkitRunnable;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;

public class Countdown extends BukkitRunnable {

	private Dungeon raid;
	private int seconds;
	
	public Countdown(Dungeon raid) {
		this.raid = raid;
		this.seconds = Config.getCountdownSeconds();
	}
	
	public void begin() {
		raid.setState(GameState.COUNTDOWN);
		this.runTaskTimer(Main.getMain(), 0, 20L);
	}

	@Override
	public void run() {
		if(seconds == 0) {
			cancel();
			raid.start();
		}
		
		else if(seconds % 30 == 0 || seconds <= 5) {
			if(seconds == 1) {
				raid.sendMessage("§aRaid startet in §6einer Sekunde");
				
			} else {
				raid.sendMessage("§aRaid startet in §6" + seconds + " Sekunden");
			}
		}
		
		if(raid.getPlayers().size() < Config.getRequiredPlayers(raid.getID())) {
			cancel();
			raid.setState(GameState.RECRUITING);
			raid.sendMessage("§cCountdown wird gestoppt aufgrund geringer Spieleranzahl!");
			return;
		}
		
		seconds--;
	}
	
}
