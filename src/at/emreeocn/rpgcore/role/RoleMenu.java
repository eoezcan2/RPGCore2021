package at.emreeocn.rpgcore.role;

import at.emreeocn.rpgcore.util.GUI;

public class RoleMenu extends GUI {
	private static String inventoryTitle = "§8Rollen-Menü";
	
	public RoleMenu() {
		super(getInventoryTitle(), 9 * 3);
		
		setItems();
	}

	public void setItems() {
		setItem(10, RoleManager.getAdventurerItem());
		setItem(12, RoleManager.getTankItem());
		setItem(14, RoleManager.getHealerItem());
		setItem(16, RoleManager.getDamagerItem());
		fillBackground();
	}
	
	public static String getInventoryTitle() {
		return inventoryTitle;
	}

}
