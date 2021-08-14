package at.emreeocn.rpgcore.dungeon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class Game {

//	private static EntityType[] types = { EntityType.SKELETON, EntityType.ZOMBIE, EntityType.SPIDER };
	
	private Dungeon raid;
	private HashMap<UUID, Integer> kills;
	private ArrayList<Entity> mobs;
	
	public Game(Dungeon raid) {
		this.raid = raid;
		this.kills = new HashMap<>();
		this.mobs = new ArrayList<>();
	}
	
	public void start() {
		raid.setState(GameState.LIVE);
		
		raid.sendMessage("§aDungeon wurde gestartet.");
		raid.sendMessage("§5Eliminiere alle Monster!");
		
		init();
	}
	
	public void addKill(Player player, Entity entity) {
		kills.replace(player.getUniqueId(), kills.get(player.getUniqueId()) + 1);
		
		raid.sendMessage(Config.getPrefix() + "§7" + killsSummary() + "§f/" + Config.getKillScore(this.raid.getID()));
		
		if(objectiveIsFinished()) {
			over();
		}
	}
	
	private void over() {
		raid.sendMessage("§aDer Dungeon wurde gemeistert.");
		raid.sendMessage("§aMVP des Raids: §6" + mvp().getDisplayName());
		raid.setState(GameState.OVER);
		clearMobs();
		for(UUID u : raid.getPlayers()) {
			Bukkit.getPlayer(u).teleport(Config.getSpawn());
		}
		raid.reset();
	}
	
	private void clearMobs() {
		
	}

	private void init() {
		/* KILLS-HASHMAP */
		for(UUID u : raid.getPlayers()) {
			kills.put(u, 0);
		}
	}
	
	private boolean objectiveIsFinished() {
		if(killsSummary() >= Config.getKillScore(raid.getID())) return true;
		else return false;
	}
	
	private int killsSummary() {
		int sum = 0;
		for(Entry<UUID, Integer> e : kills.entrySet()) {
			sum += e.getValue();
		}
		return sum;
	}
	
	private Player mvp() {
		Entry<UUID, Integer> mvp = null;
		for(Entry<UUID, Integer> e : kills.entrySet()) {
			if(mvp == null) mvp = e;
			else if(e.getValue() > mvp.getValue()) mvp = e;
		}
		
		return Bukkit.getPlayer(mvp.getKey());
	}
	
	/*private EntityType randomEntity() {
		int rnd = new Random().nextInt(types.length);
		return types[rnd];
	}*/

	public ArrayList<Entity> getMobs() {
		return mobs;
	}
	
}
