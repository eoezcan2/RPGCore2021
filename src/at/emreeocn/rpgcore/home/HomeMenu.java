package at.emreeocn.rpgcore.home;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;

import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.rpg.RPGMenu;

public class HomeMenu {

	private static String inventoryTitle = "§8Plots & Homes-Menü";
	private static Material plotMaterial = Material.IRON_AXE;
	
	private Player player;
	private Inventory inventory;
	
	public HomeMenu(Player player) {
		this.player = player;
		this.inventory = Bukkit.createInventory(null, 9 * 6, inventoryTitle);
		
		for(String s : HomeManager.getHomes(player)) {
			inventory.addItem(homeItem(s, HomeManager.get(player, s)));
		}
		
		inventory.setItem(inventory.getSize() - 1, backItem());
		
		for(int i = 0; i < inventory.getSize(); i++) {
			if(inventory.getItem(i) == null) inventory.setItem(i, RPGMenu.getNull());
		}
		
		int i = 18;
		for(Plot p : PlotPlayer.get(player.getDisplayName()).getPlots()) {
			inventory.setItem(i, plotItem(p));
			i++;
		}
	}
	
	private ItemStack homeItem(String name, Location loc) {
		Farmworld f = Farmworld.getType(loc);
		ItemStack item = new ItemStack(Material.STONE);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f" + name);
		
		if(f != null) {
			item.setType(f.getClickMaterial());
			lore.add("§7Welt: §f" + f.getDisplayName());
			
		} else {
			lore.add("§7Welt: §f" + loc.getWorld().getName());
		}
		
		lore.add("§7Koordinaten: §f" + (int) loc.getX() + ", " + (int) loc.getY() + ", " + (int) loc.getZ());
		
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	private ItemStack plotItem(Plot plot) {
		ItemStack item = new ItemStack(getPlotMaterial());
		
		ArrayList<String> lore = new ArrayList<String>();
		if(plot.getMembers().size() != 0) {
			lore.add("");
			lore.add("§7Mitglieder: §f");
			for(UUID uuid : plot.getMembers()) {
				lore.add("§7– §f" + Bukkit.getOfflinePlayer(uuid).getName());
			}
		}
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§f" + plot.getId().toString());
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	private ItemStack backItem() {
		ItemStack item = new ItemStack(Material.ARROW);
		
		ArrayList<String> lore = new ArrayList<>();
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cZurück");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public void display() {
		player.openInventory(inventory);
	}
	
	public static String getInventoryTitle() { return inventoryTitle; }

	public static Material getPlotMaterial() {
		return plotMaterial;
	}
	
}
