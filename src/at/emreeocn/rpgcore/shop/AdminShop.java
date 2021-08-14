package at.emreeocn.rpgcore.shop;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.rpg.RPGMenu;

public class AdminShop {

	private Player player;
	private int invSize;
	
	public AdminShop(Player player) {
		this.player = player;
		invSize = 9 * 3;
	}
	
	public Inventory getGui() {
		Inventory inventory = Bukkit.createInventory(null, invSize, "§eAdmin-Shop");
		
		inventory.setItem(0, getBedrock());
		
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) == null) inventory.setItem(i, RPGMenu.getNull());
		}
		
		return inventory;
	}
	
	private static ItemStack getBedrock() {
		ItemStack item = createItem(Material.BEDROCK, 1, getItemPrice(Material.BEDROCK));
		
		return item;
	}

	public static ItemStack createItem(Material material, int amount, int price) {
		ItemStack item = new ItemStack(material, amount);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Preis: §6" + price + " Coins");
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(material.name());
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Material material, byte b, int amount, int price) {
		ItemStack item = new ItemStack(material, amount, b);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Preis: §6" + price + " Coins");
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName("§a§l" + material.name());
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createItem(Material material, byte b, int amount, int price, String displayName) {
		ItemStack item = new ItemStack(material, amount, b);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Preis: §6" + price + " Coins");
		
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(displayName);
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static int getItemPrice(Material material) {
		int res = 0;
		
		switch(material) {
		case BEDROCK:
			res = 500;
			break;
		default:
			res = -1;
			break;
		}
		
		return res;
	}
	
	public static ItemStack processItem(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		meta.setLore(new ArrayList<String>());
		
		String s = item.getItemMeta().getDisplayName();
		String displayName = "";
		for(int i = 0; i < s.length(); i++) {
			if(i == 0) displayName += Character.toUpperCase(s.charAt(i));
			else displayName += Character.toLowerCase(s.charAt(i));
		}
		
		meta.setDisplayName("§f" + displayName);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public static String getInventoryTitle(Player player) { return "§eAdmin-Shop"; }
	
}
