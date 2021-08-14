package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;

public class TeleportAllCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {

            Player p = (Player) sender;

            if (p.hasPermission("mephistogames.admin.teleportall")) {
                if (args.length == 0) {
                    for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if(!onlinePlayer.getDisplayName().equalsIgnoreCase(p.getDisplayName())) {
                            onlinePlayer.teleport(p);
                            onlinePlayer.sendMessage(Config.getPrefix() + "§fDu wurdest zu §6" + p.getDisplayName() + "§f teleportiert.");
                        }
                    }

                    p.sendMessage(Config.getPrefix() + "§fEs wurden alle Spieler zu dir teleportiert.");
                } else {
                    p.sendMessage(Config.getPrefix() + "§4§lSyntax: §c/tpall");
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