package at.emreeocn.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.dragonraid.DragonRaidManager;

public class DragonRaidCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(player.hasPermission("mephistogames.dragonraid.admin")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("get")) {
						player.getInventory().addItem(DragonRaidManager.spawnItem());
					}
					
				} else {
					return false;
				}
				
			} else {
				player.sendMessage("§cDu hast keine Rechte dazu!");
			}
			
			return true;
			
		} else {
			sender.sendMessage("Du bist kein Spieler");
		}
		
		return false;
	}

}
