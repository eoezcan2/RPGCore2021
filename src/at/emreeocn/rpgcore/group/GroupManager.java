package at.emreeocn.rpgcore.group;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GroupManager {

	private static HashMap<Player, Group> invites = new HashMap<Player, Group>();
	private static ArrayList<Group> groups = new ArrayList<Group>();
	
	public static HashMap<Player, Group> getInvites() { return invites; }
	public static ArrayList<Group> getGroups() { return groups; }
	
	public static boolean isInGroup(Player player) {
		for(Group g : groups)
			if(g.getMembers().contains(player))
				return true;
		
		return false;
	}
	
	public static boolean isGroupLeader(Player player) {
		for(Group g : groups)
			if(g.getLeader() == player)
				return true;
		
		return false;
	}
	
	public static String getAdminList() {
		String s = "";
		for(Group g : groups) {
			s += "§2" + g.getLeader().getDisplayName() + "\n§7- Mitglieder: §a" + getPlayerDisplayNameList(g.getMembers()) + "\n";
		}
		return s;
	}
	
	public static String getList(Group g) {
		return "§7Gruppenleiter: §2" + g.getLeader().getDisplayName() + "\n§7Mitglieder: §a" + getPlayerDisplayNameList(g.getMembers());
	}
	
	public static Group getGroup(Player player) {
		for(Group g : groups)
			if(g.getMembers().contains(player)) return g;
		
		return null;
	}
	
	public static void addGroup(Group group) {
		groups.add(group);
	}
	
	public static void removeGroup(Group group) {
		groups.remove(group);
	}
	
	public static void accept(Player player) {
		if(isInGroup(player)) {
			GroupManager.getGroup(player).leave(player);
		}
		invites.get(player).join(player, true);
		invites.remove(player);
	}
	
	public static void decline(Player player) {
		invites.get(player).sendMessage("§6" + player.getDisplayName() + " §7hat die Einladung abgelehnt");
		invites.remove(player);
	}
	
	public static boolean sameGroup(Player player1, Player player2) {
		for(Group g : groups) {
			if(g.getMembers().contains(player1) && g.getMembers().contains(player2))
				return true;
		}
		return false;
	}
	
	public static boolean hasInvitation(Player player) {
		if(invites.containsKey(player))
			return true;
		else
			return false;
	}
	
	public static Player getPlayerByMemberItem(ItemStack item) {
		if(item.getType() == Material.PLAYER_HEAD) {
			String name = item.getItemMeta().getDisplayName().substring(2, item.getItemMeta().getDisplayName().length());
			System.out.println(name);
			return Bukkit.getPlayer(name);
		}
		
		return null;
	}
	
	public static ArrayList<String> getPlayerDisplayNameList(ArrayList<Player> players) {
		ArrayList<String> res = new ArrayList<String>();
		for(Player p : players) {
			res.add(p.getDisplayName());
		}
		return res;
	}
	
}
