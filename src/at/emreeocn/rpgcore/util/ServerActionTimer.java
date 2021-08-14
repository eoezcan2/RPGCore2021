package at.emreeocn.rpgcore.util;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import at.emreeocn.rpgcore.main.Main;

public class ServerActionTimer implements Runnable {

	private long time;
	private BukkitTask task;
	private ServerActionType type;
	
	public ServerActionTimer(ServerActionType type, long time) {
		this.setTime(time);
		this.type = type;
	}
	
	public void start() {
		task = Bukkit.getScheduler().runTaskTimer(Main.getMain(), this, 20L, 20 * time);
	}
	
	public void stop() {
		task.cancel();
	}
	
	@Override
	public void run() {
		if(time % 10 == 0) {
		    Bukkit.broadcastMessage(Config.getPrefix() + "§c§l" + type.getText() + " in §6" + time + " Sekunden");
		    
		} else if(time <= 5 && time != 0) {
		    Bukkit.broadcastMessage(Config.getPrefix() + "§c§l" + type.getText() + " in §6" + time + " Sekunden");
		}

		time--;
		
		if(time == 0) {
			Bukkit.getScheduler().cancelTask(task.getTaskId());
			
			if(type == ServerActionType.RELOAD) Bukkit.getServer().reload();
			if(type == ServerActionType.STOP) Bukkit.getServer().shutdown();
		}
	}

	public long getSeconds() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public ServerActionType getType() {
		return type;
	}

	public void setType(ServerActionType type) {
		this.type = type;
	}

}
