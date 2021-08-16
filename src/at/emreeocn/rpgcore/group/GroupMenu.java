package at.emreeocn.rpgcore.group;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.rpg.RPGMenu;
import at.emreeocn.rpgcore.util.GUI;

public class GroupMenu extends GUI {
	private Player player;
	private Group group;
	
	public GroupMenu(Player player) {
		super("§8Gruppe-Menü", 9 * 6);
		this.player = player;
		this.group = GroupManager.getGroup(player);
		
		setItems();
	}
	
	public void setItems() {
		addMemberItems();
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
		lore.add((leader ? "§2Leiter" : "§aMitglied"));
		
		meta.setDisplayName("§f" + player.getDisplayName());
		
		item.setItemMeta(meta);
		
		return item;
	}

	public Player getPlayer() {
		return player;
	}
	
}
