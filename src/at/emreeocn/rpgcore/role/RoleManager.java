package at.emreeocn.rpgcore.role;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.util.ItemCreator;

public class RoleManager {

	private static String table = "roles";

	/* SQL METHODS */
	
	/* ITEMS */
	public static ItemStack getTankItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Als Tank solltest du den meisten Schaden durchstehen, da die Gegner");
		lore.add("§2auf dich fokussiert sind.");
		
		ItemStack item = ItemCreator.createGuiItem(Material.SHIELD, "§fTank", lore);
		
		return item;
	}
	
	public static ItemStack getDamagerItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Sorge für viel Schaden an die Gegner");
		
		ItemStack item = ItemCreator.createGuiItem(Material.IRON_SWORD, "§fSchaden", lore);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getHealerItem() {
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Halte deine Gruppenmitglieder am Leben, indem du sie heilst");
		
		ItemStack item = new ItemStack(Material.POTION, 1, (byte) 16421);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§fHealer");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static String getTable() { return table; }
	
}
