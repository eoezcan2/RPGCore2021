package at.emreeocn.rpgcore.plotsurvive;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.github.intellectualsites.plotsquared.api.PlotAPI;
import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;

import at.emreeocn.rpgcore.main.Main;

public class PlotSurviveManager {

	private static ArrayList<PlotSurvive> list = new ArrayList<PlotSurvive>();
	private static String table = "plotsurvive";
	
	public static ArrayList<PlotSurvive> getList() { return list; }
	
	public static boolean isInserted(Player player) {
		ResultSet rs;
		boolean b = false;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM " + table + " WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			
			if(rs.getInt(1) != 0) {
				b = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	public static void insert(Player player) {
		try {
			Main.getMain().prepareStatement("INSERT INTO " + table + "(NAME, UUID, LEVEL) VALUES('" + player.getDisplayName() + 
					"', '" + player.getUniqueId().toString() + "', DEFAULT);").executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void increase(Player player) {
		try {
			Main.getMain().prepareStatement("UPDATE " + table + " SET NAME = '" + player.getDisplayName() + "'"
					+ ", LEVEL = " + (getLevel(player) + 1) + " WHERE UUID = '" + player.getUniqueId().toString() + "'").executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean isRunning(Player player) {
		for(PlotSurvive p : list) {
			if(p.getPlayer() == p) return true;
		}
		return false;
	}
	
	public static int getLevel(Player player) {
		ResultSet rs;
		int i = -1;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT * FROM " + table + " WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			
			i = rs.getInt("LEVEL");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return i;
	}
	
	public static void onGuiClick(Player player) {
		player.closeInventory();
		
		if(playerIsOnOwnPlot(player)) {
			new PlotSurvive(player);
			
		} else {
			player.sendMessage("§cDu musst dich für diese Funktion auf deinem Plot befinden!");
		}
	}
	
	public static boolean playerIsOnOwnPlot(Player player) {
		PlotAPI p = new PlotAPI();
		for(Plot plot : p.getPlayerPlots(PlotPlayer.get(player.getDisplayName()))) {
			for(PlotPlayer plotPlayer : plot.getPlayersInPlot()) {
				if(Bukkit.getPlayer(plotPlayer.getUUID()) == player) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static PlotSurvive getPlotSurvive(Entity entity) {
		for(PlotSurvive p : list) {
			if(p.getMobs().contains(entity)) return p;
		}
		return null;
	}
	
	public static PlotSurvive getPlotSurvive(Player player) {
		for(PlotSurvive p : list) {
			if(p.getPlayer() == player) return p;
		}
		return null;
	}
	
	public static boolean isPlaying(Player player) {
		for(PlotSurvive p : list) {
			if(p.getPlayer() == player) return true;
		}
		return false;
	}
	
}
