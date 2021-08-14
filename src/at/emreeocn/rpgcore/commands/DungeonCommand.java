package at.emreeocn.rpgcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.dungeon.Dungeon;
import at.emreeocn.rpgcore.dungeon.DungeonManager;
import at.emreeocn.rpgcore.util.Config;

public class DungeonCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("leave")) {
					if(DungeonManager.isPlaying(player)) {
						DungeonManager.getDungeon(player).removePlayer(player);
						return true;
					}
					
				} else if(args[0].equalsIgnoreCase("list")) {
					player.sendMessage("§aVerfügbare Dungeons:");
					for(Dungeon dungeon : DungeonManager.getRaids()) {
						player.sendMessage("§7- §e" + dungeon.getTitle() + " §6[" + dungeon.getID() + "]");
					}
					return true;
					
				} else if(args[0].equalsIgnoreCase("reload")) {
					DungeonManager.reload();
					player.sendMessage("§aConfig neugeladen.");
					return true;
				}
			}
			
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("join")) {
					if(!DungeonManager.isPlaying(player)) {
						int id = 0;
						try {
							id = Integer.parseInt(args[1]);
							
						} catch(NumberFormatException ex) {
							player.sendMessage("§cDie eingegebene §4ID §cist ungültig!");
							return true;
						}
						
						if(isIdValid(id)) {
							DungeonManager.getDungeon(id).addPlayer(player);
							return true;
							
						} else {
							player.sendMessage("§cDie eingegebene §4ID §cist ungültig!");
							return true;
						}
						
					} else {
						player.sendMessage("§cDu befindest dich schon in einem Raid!\nNutze §4/raid leave");
						return true;
					}
				}
				
			} else if(args.length == 3) {
				if(player.hasPermission("mephistogames.raid.admin")) {
					if(args[0].equalsIgnoreCase("set")) {
						int id = 0;
						try {
							id = Integer.parseInt(args[2]);
							
						} catch(NumberFormatException ex) {
							player.sendMessage("§cDie eingegebene §4ID §cist ungültig!");
							return true;
						}
						
						if(!isIdValid(id)) {
							player.sendMessage("§cDie eingegebene §4ID §cist ungültig!");
							return true;
						}
						
						if(args[1].equalsIgnoreCase("spawn")) {
							Config.setDungeonSpawn(player.getLocation(), id);
							player.sendMessage("§aSpawn von Raid §6" + id + " §agesetzt!");
							return true;
							
						}
					}
				}
				
			} else {
				
			}
			
		} else {
			sender.sendMessage("Du bist kein Spieler!");
			return true;
		}
		
		return false;
	}
	
	private static boolean isIdValid(int id) {
		if(id >= 0 && id < (Config.getDungeonAmount())) return true;
		else return false;
	}

}
