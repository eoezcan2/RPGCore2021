package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrashCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("mephistogames.player.trash")) {
                if(args.length == 0) {
                    p.openInventory(Bukkit.createInventory(null, 9 * 6, "§9Trash"));

                } else {
                    p.sendMessage("§4§lSyntax: /trash");
                }
            } else {
                p.sendMessage("§4Du hast dafür keine Berechtigung!");
            }
        } else {
            sender.sendMessage("§4Du musst ein Spieler sein!");
        }
        return true;
    }
}
