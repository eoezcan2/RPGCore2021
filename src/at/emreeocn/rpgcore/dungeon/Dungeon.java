package at.emreeocn.rpgcore.dungeon;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.task.Task;

public class Dungeon {

	private int id;
	private String title;
	private ArrayList<UUID> players;
	private Location spawn;
	private GameState state;
	private Countdown countdown;
	private Game game;
	
	public Dungeon(int id) {
		this.id = id;
		this.title = Config.getDungeonTitle(id);
		players = new ArrayList<>();
		spawn = Config.getDungeonSpawn(id);
		state = GameState.RECRUITING;
		countdown = new Countdown(this);
		game = new Game(this);
	}
	
	public void sendMessage(String msg) {
		for(UUID u : players) {
			Bukkit.getPlayer(u).sendMessage(msg);
		}
	}
	
	public void sendMessage(Player player, String msg) {
		for(UUID u : players) {
			if(Bukkit.getPlayer(u) == player) {
				player.sendMessage(msg);
			}
		}
		return;
	}
	
	public void addPlayer(Player player) {
		if(Task.didTask(player, Task.WITHER_SKELETON_HEAD)) {
			if(state != GameState.LIVE) {
				players.add(player.getUniqueId());
				player.teleport(spawn);
				
				if(players.size() >= Config.getRequiredPlayers(this.getID())) {
					countdown.begin();
				}
				
				this.sendMessage("§4[§cDungeon§4] " + "§6" + player.getDisplayName() + " §aist dem Dungeon beigetreten.");
				
			} else {
				player.sendMessage(Config.getPrefix() + "§cDer Dungeon läuft im Moment");
			}
			
		} else {
			player.sendMessage(Config.getPrefix() + "§cDu musst dich dafür im §5Endgame §cbefinden");
		}
	}
	
	public void removePlayer(Player player) {
		players.remove(player.getUniqueId());
		
		if(player != null) player.teleport(Config.getSpawn());
		
		if(players.size() <= Config.getRequiredPlayers(id) && state == GameState.COUNTDOWN) {
			reset();
		}
		
		if(players.size() == 0 && state == GameState.LIVE) {
			reset();
		}
		
		sendMessage("§6" + player.getDisplayName() + " §ahat den Dungeon verlassen!");
	}
	
	public void start() {
		game.start();
	}
	
	public int getID() { return id; }

	public ArrayList<UUID> getPlayers() {
		return players;
	}

	public GameState getState() {
		return state;
	}
	
	public void setState(GameState state) { this.state = state; }

	public Countdown getCountdown() {
		return countdown;
	}
	
	public Location getSpawn() { return spawn; }

	public void reset() {
		players.clear();
		state = GameState.RECRUITING;
		countdown = new Countdown(this);
		game = new Game(this);
	}

	public String getTitle() {
		return title;
	}
	
	public Game getGame() { return game; }
	
}
