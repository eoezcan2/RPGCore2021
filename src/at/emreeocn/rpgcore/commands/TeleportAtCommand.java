package at.emreeocn.rpgcore.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.task.Task;
import at.emreeocn.rpgcore.util.Config;

public class TeleportAtCommand implements CommandExecutor {

    private static HashMap<Player, Player> players = new HashMap<Player, Player>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (p.hasPermission("mephistogames.player.teleportat")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);

                    if (target != null) {
                        p.sendMessage(Config.getPrefix() + "§fDu hast eine TPA-Anfrage an §6" + target.getDisplayName() + "§f geschickt.");
                        target.sendMessage(Config.getPrefix() + "§6" + p.getDisplayName() + " §fmöchte sich zu dir teleportieren.\n" + "§6/tpa " + p.getDisplayName() + " accept§f um anzunehmen\n§6/tpa " + p.getDisplayName() + " decline§f um abzulehnen.");
                        players.put(p, target);
                    }
                } else if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("accept")) {
                        Player sentPlayer = Bukkit.getPlayer(args[0]);

                        if (sentPlayer != null) {
                            if(players.get(sentPlayer) == p) {
                            	p.sendMessage(Config.getPrefix() + "§fDu hast die Anfrage von §6" + sentPlayer.getDisplayName() + "§f §a§langenommen. §fTeleportiere...");
                            	sentPlayer.sendMessage(Config.getPrefix() + "§6" + p.getDisplayName() + "§f hat deine Anfrage §a§langenommen. §fTeleportiere...");
                            	
                                if (p.getWorld().equals(Farmworld.NETHER.getSpawn().getWorld())) {
                                     if (!Task.didTask(sentPlayer, Task.BREAK_OBSIDIAN_BLOCK)) {
                                        p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du kannst §l" + sentPlayer.getDisplayName() + "§r§c nicht zu dir teleportieren, da er den §4Nether §r§cnoch nicht freigeschalten hat!");
                                        sentPlayer.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du kannst dich nicht zu §l" + p.getDisplayName() + "§r§c teleportieren, da du noch nicht den §4Nether §r§cfreigeschalten hast!");
                                        players.remove(sentPlayer, p);
                                        return true;
                                     }
                                     
                                } else if(p.getWorld().equals(Farmworld.END.getSpawn().getWorld())) {
                                	if (!Task.didTask(sentPlayer, Task.WITHER_SKELETON_HEAD)) {
                                        p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du kannst §l" + sentPlayer.getDisplayName() + "§r§c nicht zu dir teleportieren, da er das §5End §r§cnoch nicht freigeschalten hat!");
                                        sentPlayer.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du kannst dich nicht zu §l" + p.getDisplayName() + "§r§c teleportieren, da du noch nicht das §5End §r§cfreigeschalten hast!");
                                        players.remove(sentPlayer, p);
                                        return true;
                                    }
                                	
                                } else if(p.getWorld().equals(Config.getCave().getWorld())) {
                                	if (!Task.didTask(sentPlayer, Task.WITHER_SKELETON_HEAD)) {
                                        p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du kannst §l" + sentPlayer.getDisplayName() + "§r§c nicht zu dir teleportieren, da er §2The Cave §r§cnoch nicht freigeschalten hat!");
                                        sentPlayer.sendMessage(Config.getPrefix() + "§4§lFehler:§c Du kannst dich nicht zu §l" + p.getDisplayName() + "§r§c teleportieren, da du noch nicht §2The Cave §r§cfreigeschalten hast!");
                                        players.remove(sentPlayer, p);
                                        return true;
                                    }
                                	
                                }
                                    
                                sentPlayer.teleport(p);
                                players.remove(sentPlayer, p);
                            } else {
                            	p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Der Spieler §l" + args[0] + " §r§chat dir keine Anfrage gesendet!");
                            }
                        } else {
                            p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Der Spieler §l" + args[0] + " §r§cexistiert nicht oder ist nicht online!");
                        }
                        
                    } else if (args[1].equalsIgnoreCase("deny")) {
                        Player sentPlayer = Bukkit.getPlayer(args[0]);

                        if (sentPlayer != null) {
                        	if(players.get(sentPlayer) == p) {
                        	    p.sendMessage(Config.getPrefix() + "§fDu hast die Anfrage von §6" + sentPlayer.getDisplayName() + "§f §4§labgelehnt.");
                        	    sentPlayer.sendMessage(Config.getPrefix() + "§6" + p.getDisplayName() + "§f hat deine Anfrage §4§labgelehnt.");

                            	players.remove(sentPlayer, p);
                            } else {
                            	p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Der Spieler §l" + args[0] + " §r§chat dir keine Anfrage gesendet!");
                            }
                        } else {
                            p.sendMessage(Config.getPrefix() + "§4§lFehler:§c Der Spieler §l" + args[0] + " §r§cexistiert nicht oder ist nicht online!");
                        }
                    } else {
                        p.sendMessage(Config.getPrefix() + "§4§lSyntax:§c /tpa <Player> <accept|deny>");
                    }
                } else {
                    p.sendMessage(Config.getPrefix() + "§4§lSyntax:§c /tpa <Player> <accept|deny>");
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
