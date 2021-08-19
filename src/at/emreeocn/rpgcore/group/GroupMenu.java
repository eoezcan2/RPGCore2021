package at.emreeocn.rpgcore.group;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.rpg.RPGMenu;
import at.emreeocn.rpgcore.util.GUI;

public class GroupMenu extends GUI {
	private static String inventoryTitle = "§8Gruppe-Menü";
	
	private Player player;
	private Group group;
	
	public GroupMenu(Player player) {
		super(getInventoryTitle(), 9 * 6);
		this.player = player;
		this.group = GroupManager.getGroup(player);
		
		setItems();
	}
	
	public void setItems() {
		addMemberItems();
		setItem(45, getLeaveItem());
		setItem(getInventory().getSize() - 1, getReturnItem());
		setItem(getInventory().getSize() - 5, getHelpItem());
		
		fillBackground();
	}
	
	private void addMemberItems() {
		for(Player p : group.getMembers()) {
			getInventory().addItem(getMemberItem(p, (p == group.getLeader())));
		}
	}
	
	private static ItemStack getMemberItem(Player player, boolean leader) {
		ItemStack item = RPGMenu.getHead(player);
		ItemMeta meta = item.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add("§7Rolle: " + (leader ? "§2Gruppenleiter" : "§aMitglied"));
		
		meta.setDisplayName("§f" + player.getDisplayName());
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	private static ItemStack getHelpItem() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add("§7/group");
		lore.add("§7- create");
		lore.add("§7- invite <Spieler>");
		lore.add("§7- list");
		lore.add("§7- promote/leader <Spieler>");
		lore.add("§7- msg <Message>");
		lore.add("§7- leave");
		lore.add("§7- accept/decline");
		
		meta.setDisplayName("§fHilfe");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack getLeaveItem() {
		ItemStack item = new ItemStack(Material.BARRIER);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cGruppe verlassen");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack getReturnItem() {
		ItemStack item = new ItemStack(Material.ARROW);
		
		ArrayList<String> lore = new ArrayList<>();
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cZurück");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		return item;
	}

	public Player getPlayer() {
		return player;
	}
	
	public static String getInventoryTitle() { return inventoryTitle; }
	
}
