package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.task.Task;

public class TaskCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender.hasPermission("mephistogames.command.task")) {
			if(args.length == 1) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					sender.sendMessage(Config.getPrefix() + "§6Tasks:");
					sender.sendMessage(Task.getTasksString(target));
					return true;
					
				} else {
					sender.sendMessage(Config.getPrefix() + "§cSpieler konnte nicht gefunden werden");
					return true;
				}
				
			} else if(args.length == 2) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					
					int id = 0;
					try {
						id = Integer.parseInt(args[1]);
						
					} catch(NumberFormatException ex) {
						sender.sendMessage(Config.getPrefix() + "§cDie eingegebene Zahl ist ungültig!");
						return true;
					}
					
					if(Task.getTaskById(id) != null) {
						sender.sendMessage(Task.getTaskString(target, Task.getTaskById(id)));
						return true;
						
					} else {
						sender.sendMessage(Config.getPrefix() + "§cDie eingegebene ID ist ungültig!");
						return true;
					}
					
				} else {
					sender.sendMessage(Config.getPrefix() + "§cSpieler konnte nicht gefunden werden");
					return true;
				}
				
			} else if(args.length == 3) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					
					int id = 0;
					try {
						id = Integer.parseInt(args[1]);
						
					} catch(NumberFormatException ex) {
						sender.sendMessage(Config.getPrefix() + "§cDie eingegebene Zahl ist ungültig!");
						return true;
					}
					
					if(Task.getTaskById(id) != null) {
						Task task = Task.getTaskById(id);
						if(args[2].equalsIgnoreCase("true")) {
							Task.setTask(target, task);
							sender.sendMessage("§a" + task.getTitle() + " §6[" + task.getId() + "] §a set to §6" + args[2]);
							return true;
							
						} else if(args[2].equalsIgnoreCase("false")) {
							Task.removeTask(target, task);
							sender.sendMessage("§a" + task.getTitle() + " §6[" + task.getId() + "] §a set to §6" + args[2]);
							return true;
							
						} else {
							return false;
						}
						
					} else {
						sender.sendMessage(Config.getPrefix() + "§cDie eingegebene ID ist ungültig!");
						return true;
					}
					
				} else {
					sender.sendMessage(Config.getPrefix() + "§cSpieler konnte nicht gefunden werden");
					return true;
				}
			}
		}
		
		return false;
	}

}
