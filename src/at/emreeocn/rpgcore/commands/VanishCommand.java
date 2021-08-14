package at.emreeocn.rpgcore.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.emreeocn.rpgcore.util.Config;

public class VanishCommand implements CommandExecutor {

    public static List<Player> vanished = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("mephistogames.admin.vanish")) {
                if(args.length == 0) {
                    if(vanished.contains(p)) {
                        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            onlinePlayer.showPlayer(p);
                        }
                        vanished.remove(p);

                        p.removePotionEffect(PotionEffectType.INVISIBILITY);

                        p.sendMessage("§fDu bist jetzt §lsichtbar.");
                    } else {
                        for(Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            if(!onlinePlayer.hasPermission("mephistogames.admin.vanish.show")) {
                                onlinePlayer.hidePlayer(p);
                            }
                        }
                        vanished.add(p);

                        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 1, false, false));

                        p.sendMessage("§fDu bist jetzt §7§lunsichtbar.");
                    }
                } else {
                    p.sendMessage(Config.getPrefix() + "§4§lSyntax: /vanish");
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