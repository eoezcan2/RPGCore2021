package at.emreeocn.rpgcore.scoreboard;

import java.time.LocalDate;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.role.RoleManager;
import at.emreeocn.rpgcore.util.Config;

public class ServerScoreboard {
	
	private Scoreboard scoreboard;
	private Player player;
	
	@SuppressWarnings("deprecation")
	public ServerScoreboard(Player player) {
		this.player = player;
		
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = scoreboard.registerNewObjective("rpg", "dummy");
		obj.setDisplayName("§4§lmc.MephistoGames.xyz");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		obj.getScore("§8" + LocalDate.now().toString()).setScore(13);
		
		obj.getScore(" ").setScore(12);
		
		obj.getScore("§fOrt: §6§l" + formatLocation(player.getLocation())).setScore(11);
		
		obj.getScore("  ").setScore(10);
		
		obj.getScore("§fCoins: §6§l" + Money.getMoney(player.getUniqueId())).setScore(9);
		
		obj.getScore("   ").setScore(9);
		
		obj.getScore("§fRolle: §6§l" + RoleManager.getRole(player).getDisplayName()).setScore(8);
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	public Player getPlayer() {
		return player;
	}
	
	public void display() {
		player.setScoreboard(scoreboard);
	}
	
	public static String formatLocation(Location location) {
		String s = "";
		if(Farmworld.getType(location) != null) {
			s = Farmworld.getType(location).getDisplayName();
			
		} else {
			if(location.getWorld() == Config.getSpawn().getWorld()) s = "Stadt";
			else if(location.getWorld() == Config.getCitybuild().getWorld()) s = "Citybuild";
			else {
				s = location.getWorld().getName();
			}
		}
		return s;
	}
	
}
