package at.emreeocn.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Modify;

public class ModifyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 0) {
				if(player.hasPermission("mephistogames.modify")) {
					Modify.setCanModify(player, !Modify.canModify(player));
					player.sendMessage("§aModifizier Modus: " + Modify.canModify(player));
				}
			}
			
		} else {
			sender.sendMessage("Du bist kein Spieler!");
		}
		
		return false;
	}

}
