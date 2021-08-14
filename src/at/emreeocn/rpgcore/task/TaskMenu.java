package at.emreeocn.rpgcore.task;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.rpg.RPGMenu;

public class TaskMenu {

	private Player player;
	private Inventory inventory;
	private ItemStack backItem;
	
	public TaskMenu(Player player) {
		this.player = player;
		
		createBackItem();
	}
	
	public Inventory getGui() {
		inventory = Bukkit.createInventory(null, 9 * 6, "§8Aufgaben-Menu");
		
		inventory.setItem(53, backItem);
		
		for(Task task : Task.values()) {
			if(Task.didTask(player, task)) {
				ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
				ItemMeta meta = item.getItemMeta();
				
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("");
				lore.add("§2" + task.getDescription());
				lore.add("");
				lore.addAll(Task.rewardLores(task));
				
				meta.setDisplayName("§a" + task.getTitle());
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				
				meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
				lore.add("");
				lore.add("§4[§cABGESCHLOSSEN§4]");
				
				if(Task.didTask(player, task)) {
				}
				
				meta.setLore(lore);
				
				item.setItemMeta(meta);
				
				inventory.addItem(item);
			}
		}
		
		for(Task task : Task.values()) {
			if(!Task.didTask(player, task)) {
				ItemStack item = new ItemStack(Material.WRITABLE_BOOK);
				ItemMeta meta = item.getItemMeta();
				
				ArrayList<String> lore = new ArrayList<String>();
				lore.add("");
				lore.add("§2" + task.getDescription());
				lore.add("");
				lore.addAll(Task.rewardLores(task));
				
				meta.setDisplayName("§a" + task.getTitle());
				meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
				meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
				
				if(Task.didTask(player, task)) {
				}
				
				meta.setLore(lore);
				
				item.setItemMeta(meta);
				
				inventory.addItem(item);
			}
		}
		
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) == null) inventory.setItem(i, RPGMenu.getNull());
		}
		
		return inventory;
	}

	private void createBackItem() {
		ItemStack item = new ItemStack(Material.ARROW);
		
		ArrayList<String> lore = new ArrayList<>();
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cZurück");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		backItem = item;
	}
	
	public ItemStack getBackItem() { return backItem; }
	
	public Player getPlayer() {
		return player;
	}
	
	public static String getInventoryTitle(Player player) { return "§8Aufgaben-Menu"; }
	
}
