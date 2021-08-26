package at.emreeocn.rpgcore.role;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.ItemCreator;

public class RoleManager {

	private static String table = "roles";

	/* SQL METHODS */
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
			Main.getMain().prepareStatement("INSERT INTO " + table + "(NAME, UUID, ROLE) VALUES('" + player.getDisplayName() + 
					"', '" + player.getUniqueId().toString() + "', DEFAULT);").executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	/* ITEMS */
	public static ItemStack getTankItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Du solltest den meisten Schaden durchstehen, da die Gegner");
		lore.add("§2auf dich fokussiert sind.");
		
		ItemStack item = ItemCreator.createGuiItem(Material.SHIELD, "§fTank", lore);
		
		return item;
	}
	
	public static ItemStack getDamagerItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Sorge für viel Schaden an die Gegner.");
		
		ItemStack item = ItemCreator.createGuiItem(Material.IRON_SWORD, "§fSchaden", lore);
		
		return item;
	}
	
	public static ItemStack getAdventurerItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Du hast keine Bedingungen und bist unabhängig von einer Dungeon-Gruppe.");
		
		ItemStack item = ItemCreator.createGuiItem(Material.CHEST, "§fSchaden", lore);
		
		return item;
	}
	
	public static Role getRole(Player player) {
		ResultSet rs;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM " + table + " WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			
			return Role.getRoleByName(rs.getString("ROLE"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getHealerItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Halte deine Gruppenmitglieder am Leben, indem du sie heilst.");
		
		ItemStack item = new ItemStack(Material.POTION, 1, (byte) 16421);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§fHealer");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static String getTable() { return table; }

}
