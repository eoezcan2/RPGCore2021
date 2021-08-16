package at.emreeocn.rpgcore.scoreboard;

import java.time.LocalDate;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.farmworld.Farmworld;

public class RPGScoreboard {
	
	private Scoreboard scoreboard;
	private Player player;
	
	@SuppressWarnings("deprecation")
	public RPGScoreboard(Player player) {
		this.player = player;
		
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
		
		Objective obj = scoreboard.registerNewObjective("rpg", "dummy");
		obj.setDisplayName("§4§lmc.MephistoGames.xyz");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		obj.getScore("§8" + LocalDate.now().toString()).setScore(13);
		
		obj.getScore(" ").setScore(12);
		
		String s1 = "";
		
		if(Farmworld.getType(player.getLocation()) != null) {
			s1 = Farmworld.getType(player.getLocation()).getDisplayName();
			
		} else {
			if(player.getLocation().getWorld() == Config.getSpawn().getWorld()) s1 = "Stadt";
			else if(player.getLocation().getWorld() == Config.getCitybuild().getWorld()) s1 = "Citybuild";
			else {
				s1 = player.getWorld().getName();
			}
		}
		
		obj.getScore("§f=> §6§l" + s1).setScore(11);
		
		obj.getScore("  ").setScore(10);
		
		obj.getScore("§fCoins:§r §6§l" + Money.getMoney(player.getUniqueId())).setScore(9);
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
	
}
