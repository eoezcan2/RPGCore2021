package at.emreeocn.rpgcore.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LostAndFound {

	private static HashMap<Player, ArrayList<Item>> lostandfound = new HashMap<Player, ArrayList<Item>>();
	
	public static HashMap<Player, ArrayList<Item>> getList() { return lostandfound; }
	
	public static void add(Player player, Item item) {
		if(!lostandfound.containsKey(player)) {
			register(player);
			lostandfound.get(player).add(item);
			
		} else {
			lostandfound.get(player).add(item);
		}
	}
	
	public static void remove(Player player, Item item) {
		if(!lostandfound.containsKey(player)) {
			register(player);
			return;
			
		} else {
			if(lostandfound.get(player).contains(item)) {
				lostandfound.get(player).remove(item);
			}
		}
	}
	
	public static void register(Player player) {
		lostandfound.put(player, new ArrayList<Item>());
	}
	
	public static Inventory getGui(Player player) {
		if(!lostandfound.containsKey(player)) {
			register(player);
		}
		
		Inventory inv = Bukkit.createInventory(null, 36, "§fFund-Inventar-" + player.getDisplayName());
		
		for(Item item : lostandfound.get(player)) {
			inv.addItem(item.getItemStack());
		}
		
		return inv;
	}
	
	public static ItemStack getItem() {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		
		List<String> lores = new ArrayList<String>();
		lores.add("§2§oDeine verlorenen Items befinden sich hier §r 24h");
		
		meta.setDisplayName("§6Fund-Büro");
		meta.setLore(lores);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
