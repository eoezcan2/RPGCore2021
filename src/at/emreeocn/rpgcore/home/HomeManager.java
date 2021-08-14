package at.emreeocn.rpgcore.home;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.main.Main;

public class HomeManager {
	
	private static int defaultLimit = 5;

	public static int getAmount(Player player) {
		ResultSet rs = null;
		int amount = 0;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM homes WHERE UUID = '" + player.getUniqueId().toString() + "';"
					+ "").executeQuery();
			
			rs.next();
			
			amount = rs.getInt(1);
			
		} catch(SQLException ex) {
			amount = 0;
		}
		
		return amount; // IF NULL -> NO RESULTS
	}
	
	public static String getList(Player player) {
		ResultSet rs = null;
		String s = "";
		
		try {
			rs = Main.getMain().prepareStatement("SELECT HOME FROM homes WHERE UUID = '" + player.getUniqueId().toString() + "'"
					+ ";"
					+ "").executeQuery();
			
			while(rs.next()) {
				s += "§f§l- §r§6" + rs.getString("HOME") + "\n";
			}
			
		} catch(SQLException ex) {
			s = "";
		}
		
		return s;
	}
	
	public static ArrayList<String> getHomes(Player player) {
		ArrayList<String> a = new ArrayList<String>();
		ResultSet rs = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT HOME FROM homes WHERE UUID = '" + player.getUniqueId().toString() + "'"
					+ ";"
					+ "").executeQuery();
			
			while(rs.next()) {
				a.add(rs.getString("HOME"));
			}
			
		} catch(SQLException ex) {
			a = null;
		}
		
		return a;
	}
	
	public static Location get(Player player, String home) {
		ResultSet rs = null;
		Location loc = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT * FROM homes WHERE UUID = '" + player.getUniqueId().toString() + "' "
					+ "AND HOME = '" + home + "';"
					+ "").executeQuery();
			rs.next();
			
			loc = new Location(Bukkit.getWorld(rs.getString("WORLD")), rs.getDouble("X"), rs.getDouble("Y"), rs.getDouble("Z")
					, (float) rs.getDouble("YAW"), (float) rs.getDouble("PITCH"));
			
		} catch(SQLException ex) {
			loc = null;
		}

		return loc; // IF NULL -> NO RESULTS
	}
	
	public static boolean exists(Player player, String home) {
		if(get(player, home) != null) return true;
		else return false;
	}
	
	public static void add(Player player, String home, Location loc) {
		try {
			Main.getMain().prepareStatement("INSERT INTO homes(NAME, UUID, HOME, WORLD, X, Y, Z, YAW, PITCH) VALUES('"
					+ player.getDisplayName() + "', '"
					+ player.getUniqueId().toString() + "', '"
					+ home + "', '"
					+ loc.getWorld().getName().toString() + "', "
					+ loc.getX() + ", "
					+ loc.getY() + ", "
					+ loc.getZ() + ", "
					+ loc.getYaw() + ", "
					+ loc.getPitch() + ");").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static boolean remove(Player player, String home) {
		try {
			Main.getMain().prepareStatement("DELETE FROM homes WHERE UUID = '" + player.getUniqueId().toString() + "' AND HOME = '" + home + "';").executeUpdate();
			
		} catch(SQLException ex) {
			return false;
		}
		
		return true;
	}
	
	public static int getLimit(Player player) {
		int limit = 0;
		if(player.hasPermission("mephistogames.rank.premium")) limit = 10; // PREMIUM
		if(player.hasPermission("mephistogames.rank.owner") || 
				player.hasPermission("mephistogames.rank.admin")) limit = Integer.MAX_VALUE; // ADMIN
		
		else limit = defaultLimit; // DEFAULT
		
		return limit;
	}
	
}
