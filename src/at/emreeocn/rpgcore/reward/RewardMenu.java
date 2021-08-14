package at.emreeocn.rpgcore.reward;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RewardMenu {

	private Player player;
	private Inventory gui;
	private String title;
	
	public RewardMenu(Player player, String title) {
		this.player = player;
		this.title = title;
		
		createGui();
	}
	
	public void createGui() {
		Inventory inv = RewardManager.getInventory(player);
		
		Inventory temp = Bukkit.createInventory(null, 54, "§8Post");
		temp.setContents(inv.getContents());
		
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
	
	public static String getInventoryTitle(Player player) { return "§8Post"; }

	public String getTitle() {
		return title;
	}
	
}
