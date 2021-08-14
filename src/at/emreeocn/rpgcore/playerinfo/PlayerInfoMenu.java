package at.emreeocn.rpgcore.playerinfo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.util.ItemCreator;

public class PlayerInfoMenu {
	
	private Player player;
	private Player target;
	
	public PlayerInfoMenu(Player player, Player target) {
		this.player = player;
		this.target = target;
	}
	
	public Inventory getGui() {
		Inventory inventory = Bukkit.createInventory(null, 9 * 4, "§8Info-Menu");
		
		if(target.getInventory().getHelmet() != null) inventory.setItem(1 - 1, target.getInventory().getHelmet());
		else inventory.setItem(1 - 1, getEmpty());
		
		if(target.getInventory().getChestplate() != null) inventory.setItem(10 - 1, target.getInventory().getChestplate());
		else inventory.setItem(10 - 1, getEmpty());
		
		if(target.getInventory().getLeggings() != null) inventory.setItem(19 - 1, target.getInventory().getLeggings());
		else inventory.setItem(19 - 1, getEmpty());
		
		if(target.getInventory().getBoots() != null) inventory.setItem(28 - 1, target.getInventory().getBoots());
		else inventory.setItem(28 - 1, getEmpty());
		
		if(target.getInventory().getItemInMainHand() != null) inventory.setItem(11 - 1, target.getInventory().getItemInMainHand());
		else inventory.setItem(11 - 1, getEmpty());
		
		return inventory;
	}
	
	private static ItemStack getEmpty() {
		return ItemCreator.createGuiItem(Material.BARRIER, "§cœ–", null);
	}

	public Player getTarget() {
		return target;
	}

	public Player getPlayer() {
		return player;
	}
	
	public static String getInventoryTitle(Player player) { return "§8Info-Menu"; }
	
}
