package at.emreeocn.rpgcore.enderchest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EnderchestMenu {
	
	// Premium: 9*6 Enderchest
	
	private Player player;
	private Inventory gui;
	
	public EnderchestMenu(Player player, String title) {
		this.player = player;
		
		createGui();
	}
	
	public void createGui() {
		Inventory inv = Enderchest.getInventory(player);
		Inventory temp = null;
		
		if(player.hasPermission("mephistogames.rank.premium")) {
			temp = Bukkit.createInventory(null, 9 * 6, "§8Enderchest");
			for(int i = 0; i < inv.getSize(); i++) {
				temp.setItem(i, inv.getItem(i));
			}
			
		} else {
			temp = Bukkit.createInventory(null, 9 * 3, "§5Enderchest-" + player.getDisplayName());
			for(int i = 0; i < temp.getSize(); i++) {
				temp.setItem(i, inv.getItem(i));
			}
		}
		
		gui = temp;
	}
	
	public void remove(ItemStack item) {
		gui.remove(new ItemStack(item.getType()));
	}
	
	public static Inventory copyInventory(Inventory inventory, String title) {
		Inventory inv = Bukkit.createInventory(null, inventory.getSize(), title);
		inv.setContents(inventory.getContents());
		return inv;
	}


	public Player getPlayer() {
		return player;
	}

	public Inventory getGui() {
		return gui;
	}
	
	public static String getInventoryTitle(Player player) { return "§8Enderchest"; }
	
}
