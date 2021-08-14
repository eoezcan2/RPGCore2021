package at.emreeocn.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 0) {
				Config.teleportToSpawn(player);
				
			} else if(args.length == 1) {
				if(player.hasPermission("rpgproject.spawn.set")) {
					if(args[0].equalsIgnoreCase("set")) {
						Config.setSpawn(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aSpawn wurde gesetzt");
					}
					
					else if(args[0].equalsIgnoreCase("cb")) {
						Config.setCitybuild(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aCitybuild wurde gesetzt");
					}
					
					else if(args[0].equalsIgnoreCase("fw")) {
						Config.setFarmworld(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aFarmwelt wurde gesetzt");
					}
					
					else if(args[0].equalsIgnoreCase("nether")) {
						Config.setNether(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aNether wurde gesetzt");
					}
					
					else if(args[0].equalsIgnoreCase("end")) {
						Config.setEnd(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aEnd wurde gesetzt");
						
					} else if(args[0].equalsIgnoreCase("enderdragonraid")) {
						Config.setEnderdragonraid(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aEnderdragonraid wurde gesetzt");
						
					} else if(args[0].equalsIgnoreCase("cave")) {
						Config.setCave(player.getLocation());
						player.sendMessage(Config.getPrefix() + "§aCave wurde gesetzt");
					}
				}
				
			} else {
				if(player.hasPermission("rpgproject.spawn.set")) {
					player.sendMessage(Config.getPrefix() + "§cBitte nutze /spawn [set]");
					
				} else {
					player.sendMessage(Config.getPrefix() + "§cBitte nutze /spawn");
				}
			}
			
		} else {
			sender.sendMessage(Config.getPrefix() + "Du bist kein Spieler!");
		}
		
		return false;
	}
	
}
