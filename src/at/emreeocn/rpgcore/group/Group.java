package at.emreeocn.rpgcore.group;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Group {
	
	private static String msgPrefix = "§d[§5Group§d] ";
	
	private Player leader;
	private ArrayList<Player> members;
	
	/**
	 * Constructor with leader player as parameter
	 * @param leader player that leads the group
	 */
	public Group(Player leader) {
		this.setLeader(leader);
		this.members = new ArrayList<Player>();
		this.members.add(leader);
		GroupManager.addGroup(this);
		
		// MESSAGE
		sendMessage("§7Gruppe wurde erstellt");
	}
	
	/**
	 * Sets the leader of the group
	 * @param player
	 */
	public void setGroupLeader(Player player) {
		setLeader(player);
		
		// MESSAGE
		sendMessage("§6" + player.getDisplayName() + " §7ist jetzt Gruppenleiter");
	}
	
	/**
	 * Invites a player to the group
	 * @param player player to invite
	 */
	public void invite(Player player) {
		// ADD TO HASHMAP
		GroupManager.getInvites().put(player, this);
		
		// SEND MESSAGE
		sendMessage("§6" + player.getDisplayName() + " §7 wurde eingeladen");
		player.sendMessage(msgPrefix + "§7Du wurdest von §6" + leader.getDisplayName() + " §7eingeladen");
	}
	
	/**
	 * Adds a player to the group
	 * @param player player to add
	 * @param invitation
	 */
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
	
	/**
	 * Teleports the group to a location
	 * @param location location to teleport to
	 */
	public void teleport(Location location) {
		for(Player p : members) {
			p.teleport(location);
		}
	}
	
	/**
	 * Delete this group
	 */
	public void dissolve() {
		// MESSAGE
		sendMessage("Die Gruppe wurde aufgelöst");
		
		GroupManager.removeGroup(this);
	}
	
	/**
	 * Sends a message to the group that comes from a player
	 * @param from player the message comes from
	 * @param msg message to send
	 */
	public void sendMessage(Player from, String msg) {
		for(Player p : members) {
			p.sendMessage(msgPrefix + "§6" + from.getDisplayName() + " >> §7" + msg);
		}
	}
	
	/**
	 * Sends a message to the group
	 * @param msg
	 */
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
