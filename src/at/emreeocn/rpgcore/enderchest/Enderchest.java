package at.emreeocn.rpgcore.enderchest;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.InventorySQL;

public class Enderchest {

	public static Inventory getInventory(Player player) {
		ResultSet rs = null;
		Inventory res = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT * FROM enderchest WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			try {
				res = InventorySQL.fromBase64(rs.getString("INVENTORY"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return res;
	}
	
	public static boolean isInSystem(Player player) {
		ResultSet rs = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM enderchest WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			if(rs.getInt(1) != 0) {
				return true;
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public static void saveInventory(Player player, Inventory inventory) {
		try {
			Main.getMain().prepareStatement("UPDATE enderchest SET "
					+ "NAME = '" + player.getDisplayName() + "', "
					+ "INVENTORY" + " = '" + InventorySQL.toBase64(inventory) + "' WHERE UUID = '" + player.getUniqueId().toString() + "';").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void insert(Player player) {
		Inventory inv = Bukkit.createInventory(null, 9 * 3, "§5Enderchest-" + player.getDisplayName());
		
		try {
			Main.getMain().prepareStatement("INSERT INTO enderchest(NAME, UUID, INVENTORY) VALUES("
					+ "'" + player.getDisplayName().toString() + "', "
					+ "'" + player.getUniqueId().toString() + "', "
					+ "'" + InventorySQL.toBase64(inv) + "');"
					+ "").execute();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
}
