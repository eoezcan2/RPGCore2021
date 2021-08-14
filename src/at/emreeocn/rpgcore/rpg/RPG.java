package at.emreeocn.rpgcore.rpg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.main.Main;

public class RPG {
	
	public static ItemStack getMenuItem() {
		ItemStack item = new ItemStack(Material.CLOCK);
		
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§2Öffne das Menü");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6RPG-Menü");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack getOldMenuItem() {
		ItemStack item = new ItemStack(Material.BLAZE_ROD);
		
		ArrayList<String> lore = new ArrayList<>();
		lore.add("§2Öffne das Menü");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6RPG-Menü");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static boolean isInSystem(Player player) {
		ResultSet rs = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM skills WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			if(rs.getInt(1) != 0) {
				return true;
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	public static void insert(Player player) {
		try {
			Main.getMain().prepareStatement("INSERT INTO skills(NAME, UUID, HEALTH, SPEED, POINTS) VALUES("
					+ "'" + player.getDisplayName().toString() + "', "
					+ "'" + player.getUniqueId().toString() + "', "
					+ "DEFAULT, "
					+ "DEFAULT, "
					+ "DEFAULT);"
					+ "").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
}
