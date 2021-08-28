package at.emreeocn.rpgcore.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {
	
	public static ItemStack createGuiItem(Material material, String displayName, List<String> lore) {
		ItemStack item = new ItemStack(material);
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(displayName);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	/*public static ItemStack createGuiItem(Material material, byte b, String displayName, List<String> lore) {
		ItemStack item = new ItemStack(material, 1, b);
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(displayName);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack createItem(Material material, int amount, byte b, String displayName, List<String> lore) {
		ItemStack item = new ItemStack(material, amount, b);
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(displayName);
		
		item.setItemMeta(meta);
		
		return item;
	}*/
	
	public static ItemStack createItem(Material material, int amount, String displayName, List<String> lore) {
		ItemStack item = new ItemStack(material, amount);
		
		ItemMeta meta = item.getItemMeta();
		meta.setLore(lore);
		meta.setDisplayName(displayName);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
