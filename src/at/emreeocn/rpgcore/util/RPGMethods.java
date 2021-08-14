package at.emreeocn.rpgcore.util;

import org.bukkit.inventory.Inventory;

public class RPGMethods {

	public static int countFreeSlots(Inventory inventory) {
		int free = 0;
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) == null) free++;
		}
		return free;
	}
	
	public static int countFullSlots(Inventory inventory) {
		int full = 0;
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) != null) full++;
		}
		return full;
	}
	
}
