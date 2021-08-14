package at.emreeocn.rpgcore.commands;

import at.emreeocn.rpgcore.util.Config;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpawnMobCommand implements CommandExecutor {

    @SuppressWarnings("deprecation")
	@Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;

            if(p.hasPermission("mephistogames.admin.spawnmob")) {
                if(args.length == 2) {
                    Location eyeLoc = p.getEyeLocation();
                    Vector vec = p.getLocation().getDirection();
                    Location frontLoc = eyeLoc.add(vec);

                    EntityType entity = EntityType.fromName(args[0]);

                    int amount = -1;
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch(NumberFormatException e) {
                        p.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Bitte gib eine gültige Zahl ein!");
                        return true;
                    }

                    if(amount > 0) {
                        for(int i = 0; i < amount; i++) {
                            p.getWorld().spawnEntity(frontLoc, entity);
                        }
                        p.sendMessage("§aEs wurden erfolgreich §l" + amount + " " + entity.name() + (amount > 1 ? "s" : "") + "§r§a gespawnt.");
                    } else {
                        p.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Bitte gib eine Anzahl von mindestens 1 an!");
                    }
                } else {
                    p.sendMessage(Config.getPrefix() + "§4§lSyntax:§r§c /spawnmob <Name> <Amount>");
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
