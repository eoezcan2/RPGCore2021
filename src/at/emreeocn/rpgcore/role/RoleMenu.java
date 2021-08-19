package at.emreeocn.rpgcore.role;

import at.emreeocn.rpgcore.util.GUI;

public class RoleMenu extends GUI {

	private static String inventoryTitle = "§8Rollen-Menü";
	
	public RoleMenu() {
		super(getInventoryTitle(), 9 * 3);
		
		setItems();
	}

	public static void setItems() {
		// ITEM METHODS
	}
	
	public static String getInventoryTitle() {
		return inventoryTitle;
	}

}
