package at.emreeocn.rpgcore.util;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Modify {

	private static ArrayList<Player> modify = new ArrayList<Player>();
	
	public static ArrayList<Player> getList() { return modify; }
	
	public static boolean canModify(Player player) {
		if(modify.contains(player)) return true;
		return false;
	}
	
	public static void setCanModify(Player player, boolean canModify) {
		if(canModify) {
			if(!modify.contains(player)) {
				modify.add(player);
			}
			
		} else {
			if(modify.contains(player)) {
				modify.remove(player);
			}
		}
	}
	
}
