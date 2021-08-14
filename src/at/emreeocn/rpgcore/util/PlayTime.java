package at.emreeocn.rpgcore.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.main.Main;

public class PlayTime {

	private static HashMap<Player, Long> login = new HashMap<Player, Long>();
	
	public static long getTotal(Player player) {
		ResultSet rs = null;
		long res = 0;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM playtime WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			
			if(rs.getInt(1) != 0) {
				rs = Main.getMain().prepareStatement("SELECT * FROM playtime WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
				rs.next();
				res = rs.getLong("TOTAL");
				
			} else {
				res = 0;
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return res;
	}
	
	public static long getLoginTime(Player player) {
		return login.get(player);
	}
	
	public static long calcTime(Player player, long millis) {
		return (millis - getLoginTime(player));
	}
	
	public static void saveTime(Player player) {
		ResultSet rs = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM playtime WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			
			if(rs.getInt(1) != 0) {
				long calc = PlayTime.calcTime(player, System.currentTimeMillis());
				
				Main.getMain().prepareStatement("UPDATE playtime SET "
						+ "NAME = '" + player.getDisplayName() + "', "
						+ "TOTAL = " + calc
						+ " WHERE UUID = '" + player.getUniqueId() + "';").execute();
				
			} else {
				Main.getMain().prepareStatement("INSERT INTO playtime(NAME, UUID, TOTAL) VALUES("
						+ "'" + player.getDisplayName() + "', "
						+ "'" + player.getUniqueId().toString() + "', "
						+ "" + System.currentTimeMillis() + ")"
						+ ";").execute();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void setLoginTime(Player player, long millis) {
		login.put(player, millis);
	}
	
}
