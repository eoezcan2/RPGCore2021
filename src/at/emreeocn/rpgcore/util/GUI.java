package at.emreeocn.rpgcore.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.rpg.RPGMenu;

public class GUI {

	private Inventory inventory;
	private String title;
	
	public GUI(String title, int size) {
		this.inventory = Bukkit.createInventory(null, size, title);
		this.title = title;
	}
	
	public void display(Player player) {
		player.openInventory(inventory);
	}
	
	public void fillBackground() {
		ItemStack item = RPGMenu.getNull();
		for(int i = 0; i < getInventory().getSize(); i++) {
			if(getInventory().getItem(i) == null) setItem(i, item);
		}
	}
	
	public void setItem(int slot, ItemStack item) {
		inventory.setItem(slot, item);
	}

	public Inventory getInventory() {
		return inventory;
	}

	public String getTitle() {
		return title;
	}
	
}
