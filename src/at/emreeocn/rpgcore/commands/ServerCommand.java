package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;

public class ServerCommand implements CommandExecutor {
	
	private BukkitTask task;
	
	private static boolean isRunning = false;
	private static int time = 0;
	
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		CommandSender player = sender;
		
		if(player.hasPermission("rpgproject.server.admin")) {
			if(args.length == 2 && args[0].equalsIgnoreCase("reload")) {
				int seconds = 30;
				try {
					seconds = Integer.parseInt(args[1]);
					
					if(seconds < 0) return false;
					
				} catch(NumberFormatException ex) {
					seconds = 30;
				}
				
				if(isRunning) return false;
				else player.sendMessage(Config.getPrefix() + "§aAktion wird durchgeführt!");
				
				time = seconds;
				
				isRunning = true;
				
				this.task = Bukkit.getScheduler().runTaskTimer(Main.getMain(), new Runnable() {
					
					@Override
					public void run() {
						if(time % 10 == 0) {
						    Bukkit.broadcastMessage(Config.getPrefix() + "§cServer Reload in §6" + time);
						} else if(time <= 5 && time != 0) {
						    Bukkit.broadcastMessage(Config.getPrefix() + "§cServer Reload in §6" + time);
						}

						time--;
						
						if(time == 0) {
							Bukkit.getScheduler().cancelTask(task.getTaskId());
							isRunning = false;
							Bukkit.getServer().reload();
							Bukkit.broadcastMessage(Config.getPrefix() + "§aFertig!");
						}
					}
				}, 0L, 20L);	
				
				
			} else if(args.length == 2 && args[0].equalsIgnoreCase("stop")) {
				int seconds = 30;
				try {
					seconds = Integer.parseInt(args[1]);
					
					if(seconds < 0) return false;
					
				} catch(NumberFormatException ex) {
					seconds = 30;
				}
				
				if(isRunning) return false;
				else player.sendMessage(Config.getPrefix() + "§aAktion wird durchgeführt!");
				
				time = seconds;
				
				isRunning = true;
				
				this.task = Bukkit.getScheduler().runTaskTimer(Main.getMain(), new Runnable() {
					
					@Override
					public void run() {
						if(time % 10 == 0) {
						    Bukkit.broadcastMessage(Config.getPrefix() + "§cServer stoppt in §6" + time);
						} else if(time <= 5 && time != 0) {
						    Bukkit.broadcastMessage(Config.getPrefix() + "§cServer stoppt in §6" + time);
						}

						time--;
						
						if(time == 0) {
							Bukkit.getScheduler().cancelTask(task.getTaskId());
							isRunning = false;
							Bukkit.getServer().shutdown();
						}
					}
				}, 0L, 20L);
				
			} else if(args.length == 2 && args[0].equalsIgnoreCase("wa")) {
				int seconds = 30;
				try {
					seconds = Integer.parseInt(args[1]);
					
					if(seconds < 0) return false;
					
				} catch(NumberFormatException ex) {
					seconds = 30;
				}
				
				if(isRunning) return false;
				else player.sendMessage(Config.getPrefix() + "§aAktion wird durchgeführt!");
				
				time = seconds;
				
				isRunning = true;
				
				this.task = Bukkit.getScheduler().runTaskTimer(Main.getMain(), new Runnable() {
					
					@Override
					public void run() {
						Bukkit.broadcastMessage(Config.getPrefix() + "§cWartungsarbeiten in §6" + time);
						
						time--;
						
						if(time == 0) {
							Bukkit.getScheduler().cancelTask(task.getTaskId());
							isRunning = false;
							
							for(Player player : Bukkit.getOnlinePlayers()) {
								if(!player.hasPermission("rpgproject.server.admin")) {
									player.kickPlayer("§cWartungsarbeiten");
								}
								
							}
						}
					}
				}, 0L, 20L);
				
			} else if(args.length == 1 && args[0].equalsIgnoreCase("cancel")) {
				if(isRunning) {
					Bukkit.getScheduler().cancelTask(task.getTaskId());
					Bukkit.getServer().broadcastMessage(Config.getPrefix() + "§aAktion wurde gestoppt!");
					isRunning = false;
					
				} else {
					player.sendMessage(Config.getPrefix() + "§cMomentan läuft keine Aktion!");
				}
			} 
		}
		
		return false;
	}

	public static boolean isRunning() {
		return isRunning;
	}

	public static int getTime() {
		return time;
	}

}
