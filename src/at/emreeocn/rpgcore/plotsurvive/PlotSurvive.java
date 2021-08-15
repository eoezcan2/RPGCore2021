package at.emreeocn.rpgcore.plotsurvive;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.events.TaskFinishedEvent;
import at.emreeocn.rpgcore.util.Delay;
import at.emreeocn.rpgcore.task.Task;

public class PlotSurvive {

	private static EntityType[] types = { 
			EntityType.SPIDER, 
			EntityType.CREEPER, 
			EntityType.SKELETON, 
			EntityType.ZOMBIE };
	
	private Player player;
	private int level;
	private ArrayList<Entity> mobs;
	private PlotSurviveTimer timer;
	
	public PlotSurvive(Player player) {
		this.player = player;
		this.level = PlotSurviveManager.getLevel(player);
		this.mobs = new ArrayList<Entity>();
		start();
	}
	
	public void start() {
		PlotSurviveManager.getList().add(this);
		player.sendTitle("ßc‹berlebenskampf", "ß6Level " + this.level, 15, (20 * 3), 15);
		new Delay(1);
		this.timer = new PlotSurviveTimer(this, 60);
		spawnMobs();
	}
	
	public void reset() {
		PlotSurviveManager.getList().remove(this);
		killMobs();
		new TaskFinishedEvent(player, Task.PLAY_PLOTSURVIVE);
		spawnMobs(); // Mobs spawn fix
	}
	
	public void won() {
		reset();
		player.sendMessage("ßaDu hast es geschafft");
		PlotSurviveManager.increase(player);
	}
	
	public void lost() {
		reset();
		player.sendMessage("ßcDu hast es nicht geschafft!");
	}

	public void spawnMobs() {
		for(int i = 0; i < (level * 5); i++) {
			int random = new Random().nextInt(types.length);
			
			Entity e = player.getWorld().spawnEntity(player.getLocation(), types[random]);
			
			mobs.add(e);
		}
	}
	
	public void killMob(Entity e) {
		if(mobs.contains(e)) {
			mobs.remove(e);
			if(mobs.size() == 0) {
				won();
			}
		}
	}
	
	public void killMobs() {
		for(Entity e : mobs) {
			mobs.remove(e);
			e.remove();
		}
	}
	
	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getMobs() {
		return mobs;
	}

	public PlotSurviveTimer getTimer() {
		return timer;
	}
	
}
