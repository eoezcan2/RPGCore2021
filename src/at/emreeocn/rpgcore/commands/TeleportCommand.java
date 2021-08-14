package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("mephistogames.admin.teleport")) {
                if(args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if(target != null) {
                        p.teleport(target);
                        p.sendMessage(Config.getPrefix() + "§fDu wurdest zu §6" + target.getDisplayName() + "§f teleportiert.");
                    } else {
                        p.sendMessage(Config.getPrefix() + "§cDer Spieler §4" + args[0] + "§c existiert nicht oder ist nicht online!");
                    }
                } else if(args.length == 2) {
                    Player target1 = Bukkit.getPlayer(args[0]);
                    Player target2 = Bukkit.getPlayer(args[1]);

                    if(target1 != null && target2 != null) {
                        target1.teleport(target2);
                        p.sendMessage(Config.getPrefix() + "§fDer Spieler §6" + args[0] + "§f wurde zu dem Spieler §6" + args[1] + "§f teleportiert.");
                    } else {
                        p.sendMessage(Config.getPrefix() + target1 == null && target2 == null ?
                                "§cDie Spieler §4" + args[0] + "§c und §4" + args[1] + "§c sind nicht online" :
                                target1 == null ? "§cDer Spieler §4" + args[0] + "§c ist nicht online" :
                                        target2 == null ? "§cDer Spieler §4" + args[1] + "§c ist nicht online" :
                                                "§4Unbekannter Fehler!");
                    }
                } else {
                    p.sendMessage(Config.getPrefix() + "§4§lSyntax:§c /tp <Player> [Other Player]");
                }
            } else {
                p.sendMessage(Config.getPrefix() + "§4Dafür hast du keine Rechte!");
            }
        } else {
            sender.sendMessage(Config.getPrefix() + "§4Du musst ein Spieler sein!");
        }
        return true;
    }
}
