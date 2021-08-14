package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class InvseeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("mephistogames.admin.invsee")) {
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target != null) {
                        p.openInventory(target.getInventory());
                    } else {
                        p.sendMessage(Config.getPrefix() + "�cDer Spieler �4" + args[0] + "�c existiert nicht oder ist nicht online!");
                    }
                } else {
                    p.sendMessage(Config.getPrefix() + "�4�lSyntax:�r�c /invsee <Player>");
                }
            } else {
                p.sendMessage(Config.getPrefix() + "�4Daf�r hast du keine Rechte!");
            }
        } else {
            sender.sendMessage(Config.getPrefix() + "�4Du musst ein Spieler sein!");
        }
        return true;
    }
    
}
