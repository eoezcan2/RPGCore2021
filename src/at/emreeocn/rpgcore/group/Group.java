package at.emreeocn.rpgcore.group;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Group {
	
	private static String msgPrefix = "§d[§5Group§d] ";
	
	private Player leader;
	private ArrayList<Player> members;
	
	public Group(Player leader) {
		this.setLeader(leader);
		this.members = new ArrayList<Player>();
		GroupManager.addGroup(this);
	}
	
	public void setGroupLeader(Player player) {
		setLeader(player);
		
		// MESSAGE
		sendMessage("§6" + player.getDisplayName() + " §7ist jetzt Gruppenleiter");
	}
	
	public void invite(Player player) {
		// ADD TO HASHMAP
		
		// SEND MESSAGE
		sendMessage("§6" + player.getDisplayName() + " §7 wurde eingeladen");
	}
	
	public void join(Player player, boolean invitation) {
		this.members.add(player);
		// MESSAGE
		if(invitation) {
			sendMessage("§6" + player.getDisplayName() + " §7hat die Einladung angenommen eingeladen");
		} else {
			sendMessage("§6" + player.getDisplayName() + " §7ist der Gruppe beigetreten");
		}
	}
	
	public void leave(Player player) {
		boolean isLeader = GroupManager.isGroupLeader(player);
		this.members.remove(player);
	
		// MESSAGE
		sendMessage("§6" + player.getDisplayName() + " §7hat die Gruppe verlassen");
		
		if(isLeader)
			if(this.members.size() != 0)
				setGroupLeader(this.members.get(0));
			else
				dissolve();
	}
	
	public void dissolve() {
		// MESSAGE
		sendMessage("Die Gruppe wurde aufgelöst");
		
		GroupManager.removeGroup(this);
	}
	
	public void sendMessage(Player player, String msg) {
		for(Player p : members) {
			p.sendMessage(msgPrefix + "§6" + player.getDisplayName() + " >> §7" + msg);
		}
	}
	
	public void sendMessage(String msg) {
		for(Player p : members) {
			p.sendMessage(msgPrefix + "§7" + msg);
		}
	}

	public Player getLeader() {
		return leader;
	}

	public void setLeader(Player leader) {
		this.leader = leader;
	}

	public ArrayList<Player> getMembers() {
		return members;
	}

	public void setMembers(ArrayList<Player> members) {
		this.members = members;
	}
	
}
