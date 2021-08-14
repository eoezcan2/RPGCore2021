package at.emreeocn.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.home.HomeManager;

public class HomeCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;

			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("set")) {
				    if(!HomeManager.exists(player, args[1])) {
				        if(HomeManager.getAmount(player) <= HomeManager.getLimit(player)) {
				            if(!args[1].equalsIgnoreCase("list")) {
                                HomeManager.add(player, args[1], player.getLocation());
                                player.sendMessage(Config.getPrefix() + "§aDu hast erfolgreich das Home §l" + args[1] + " gesetzt §r§a(Noch §l" + (HomeManager.getLimit(player) - HomeManager.getAmount(player)) + "§r§a übrig)");
				            } else {
				                player.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du kannst dein Home nicht §llist§r§c nennen!");
				                return true;
				            }
				        } else {
				            player.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du hast schon 5 Homes!");
				        }
                    } else {
                        player.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du hast bereits ein Home namens §l" + args[1] + "§r§c!");
                    }

                } else if(args[0].equals("delete") || args[0].equals("remove")) {
                    if(HomeManager.exists(player, args[1])) {
                        HomeManager.remove(player, args[1]);
                        player.sendMessage(Config.getPrefix() + "§aDu hast erfolgreich das Home §6" + args[1] + " §r§a§lentfernt!");

                    } else {
                        player.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du hast kein Home namens §l" + args[1] + "§r§c!");
                    }

                }
				
			} else if(args.length == 1) {
				if(args[0].equalsIgnoreCase("list")) {
				    String homes = HomeManager.getList(player);

				    if(!homes.isEmpty()) {
				        player.sendMessage(Config.getPrefix() + "§aDeine Homes: \n§r§f" + HomeManager.getList(player));
				    } else {
				        player.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du hast keine Homes!");
				    }
                    
				} else {
				    if(HomeManager.exists(player, args[0])) {
                        player.teleport(HomeManager.get(player, args[0]));
                        player.sendMessage(Config.getPrefix() + "§aDu wurdest zu deinem Home §6" + args[0] + "§r§a teleportiert.");
                    } else {
                        player.sendMessage(Config.getPrefix() + "§4§lFehler:§r§c Du hast kein Home namens §l" + args[0] + "§r§c!");
                    }
				}
			} else {
				player.sendMessage(Config.getPrefix() + "§4§lSyntax:§r§c /home <name|set|remove> [name]");
			}
		} else {
		    sender.sendMessage(Config.getPrefix() + "§4Du musst ein Spieler sein!");
		}
		
		return true;
	}
}
