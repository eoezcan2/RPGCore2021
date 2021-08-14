package at.emreeocn.rpgcore.util;

import org.bukkit.Bukkit;

import at.emreeocn.rpgcore.main.Main;

public class Delay implements Runnable {

	private int seconds;
	
	public Delay(int seconds) {
		this.seconds = seconds;
		
		Bukkit.getScheduler().runTaskLater(Main.getMain(), this, seconds * 20);
	}
	
	@Override
	public void run() {
		
	}

	public int getSeconds() {
		return seconds;
	}

}
