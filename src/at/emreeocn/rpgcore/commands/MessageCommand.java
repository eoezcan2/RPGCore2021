package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class MessageCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(p.hasPermission("mephistogames.player.message")) {
			    if(args.length >= 2) {
			        Player target = Bukkit.getPlayer(args[0]);
			        String message = "";

                    if(target != null) {
                        for(int i = 1; i < args.length; i++) {
                            if(args[i] != null) {
                                message += args[i] + " ";
                            }
                        }

                        if(!message.isEmpty()) {
                            p.sendMessage(Config.getPrefix() + "§e[§6Du §e-> §6" + target.getDisplayName() + "§e] §l->§r§a " + message);
                            target.sendMessage(Config.getPrefix() + "§e[§6" + p.getDisplayName() + "§e-> §6Du§e] §l->§r§a " + message);
                        } else {
                            p.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Bitte gib eine gültige Nachricht ein!");
                        }
                    } else {
                        p.sendMessage(Config.getPrefix() + "§4§lFehler: §r§cDer Spieler §l" + args[0] + "§r§c existiert nicht oder ist nicht online!");
                    }
			    } else {
			        p.sendMessage(Config.getPrefix() + "§4§lSyntax:§c /msg <Player> <Message>");
			    }
			} else {
			    p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du hast dafür keine Berechtigung!");
			}
		} else {
		    sender.sendMessage(Config.getPrefix() + "§4Du musst ein Spieler sein!");
		}
		return true;
	}

}
