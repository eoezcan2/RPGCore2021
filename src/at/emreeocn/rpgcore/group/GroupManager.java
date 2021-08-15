package at.emreeocn.rpgcore.group;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

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
	
	public static String getList() {
		String s = "";
		for(Group g : groups) {
			s += "§7- §2" + g.getLeader().getDisplayName() + "\n §7Mitglieder: §a" + g.getMembers().toString() + "\n";
		}
		return s;
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
	
}
