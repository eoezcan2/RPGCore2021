package at.emreeocn.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class FlyspeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("mephistogames.admin.flyspeed")) {
                if(args.length == 1) {
                    int flySpeed = 0;
                    try {
                        flySpeed = Integer.parseInt(args[0]);
                    } catch(NumberFormatException e) {
                    	p.sendMessage(Config.getPrefix() + "§4§lFehler: §r§cDu musst eine Flyspeed zwischen 1 und 10 eingeben");
                        return true;
                    }
                    
                    if(flySpeed <= 10 && flySpeed >= 1) {
                        p.setFlySpeed((float) flySpeed / 10);
                        p.sendMessage(Config.getPrefix() + "§aDu hast deine Flyspeed auf §l" + flySpeed + "§r§a gesetzt!"); 
                         
                    } else {
                        p.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du musst eine Flyspeed zwischen 1 und 10 eingeben");        	
                    }
                } else {
                    p.sendMessage(Config.getPrefix() + "§4§lSyntax: /flyspeed <speed>");
                }
            } else {
                p.sendMessage(Config.getPrefix() + "§4Du hast dafür keine Berechtigung!");
            }
        } else {
            sender.sendMessage(Config.getPrefix() + "§4Du musst ein Spieler sein!");
        }
        return true;
    }
}
