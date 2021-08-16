package at.emreeocn.rpgcore.group;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class GroupCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 1) {
				// CREATE
				if(args[0].equalsIgnoreCase("create")) {
					if(!GroupManager.isInGroup(player)) {
						new Group(player);
					} else {
						if(GroupManager.isGroupLeader(player)) {
							player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu leitest bereits eine Gruppe");
						} else {
							GroupManager.getGroup(player).leave(player);
						}
					}
					return true;
				}
				
				// LEAVE
				if(args[0].equalsIgnoreCase("leave")) {
					if(!GroupManager.isInGroup(player)) {
						player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu bist in keiner Gruppe");
					} else {
						GroupManager.getGroup(player).leave(player);
					}
					return true;
				}
				
				// ACCEPT
				if(args[0].equalsIgnoreCase("accept")) {
					if(GroupManager.getInvites().containsKey(player)) {
						if(GroupManager.isInGroup(player))
							GroupManager.getGroup(player).leave(player);
						
						GroupManager.getInvites().get(player).join(player, true);
						GroupManager.getInvites().remove(player);
					} else {
						player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu hast keine Einladung");
					}
					return true;
				}
				
				// DECLINE
				if(args[0].equalsIgnoreCase("decline")) {
					if(GroupManager.getInvites().containsKey(player)) {
						GroupManager.getInvites().get(player).sendMessage("§6" + player.getDisplayName() + " §7hat die Einladung abgelehnt");
						GroupManager.getInvites().remove(player);
					} else {
						player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu hast keine Einladung");
					}
					return true;
				}
				
				// LIST
				if(args[0].equalsIgnoreCase("list")) {
					if(player.hasPermission("mephistogames.group.list")) {
						player.sendMessage(GroupManager.getList());
					}
					return true;
				}
			}
			
			if(args.length == 2) {
				// INVITE
				if(args[0].equalsIgnoreCase("invite")) {
					if(GroupManager.isInGroup(player)) {
						if(GroupManager.isGroupLeader(player)) {
							if(Bukkit.getPlayer(args[1]) != null) {
								Player target = Bukkit.getPlayer(args[1]);
								
								if(target != player) {
									GroupManager.getGroup(player).invite(target);
								} else {
									player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu kannst dich nicht selbst einladen");
								}
								
							} else {
								player.sendMessage(Config.getPrefix() + "§4Fehler: §cDieser Spieler ist nicht online");
							}
						}
					} else {
						player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu bist in keiner Gruppe");
					}
					return true;
				}
			}
			
			if(args.length >= 2) {
				// MESSAGE
				if(args[0].equalsIgnoreCase("msg")) {
					if(GroupManager.isInGroup(player)) {
						GroupManager.getGroup(player).sendMessage(buildString(args));
					} else {
						player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu bist in keiner Gruppe");
					}
				}
			}
			
			else {
				return false;
			}
		}
		
		return false;
	}
	
	private static String buildString(String[] args) {
		String s = "";
		for(int i = 1; i < args.length; i++) {
			s += args[i];
			if(i == (args.length - 1))
				s += " ";
		}
		return s;
	}

}
