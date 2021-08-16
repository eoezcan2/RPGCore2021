package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.scoreboard.ServerScoreboard;

public class MoneyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(args.length == 0) {
			
			if(sender instanceof Player) {
				Player player = (Player) sender;
				
				int money = Money.getMoney(player.getUniqueId());
				
				player.sendMessage(Config.getPrefix() + "§a" + money + " Coins");
			}
			
			else {
				sender.sendMessage("Du bist kein Spieler!");
			}
			
		}
		
		else if(args.length == 1) {
			
			if(sender.hasPermission("roleplaysystem.money.others")) {
				
				if(Bukkit.getPlayer(args[0]) != null) {
					int money = Money.getMoney(Bukkit.getPlayer(args[0]).getUniqueId());
					
					sender.sendMessage(Config.getPrefix() + "§a" + money + " Coins");
				}
				
				else {
					sender.sendMessage(Config.getPrefix() + "§4Dieser Spieler ist nicht online");
				}
				
			}
			
			else {
				sender.sendMessage(Config.getPrefix() + "§4Du hast keine Rechte dazu");
			}
			
		}
		
		else if(args.length == 3 && args[1].equalsIgnoreCase("set")) {
			
			if(sender.hasPermission("roleplaysystem.money.admin")) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					int amount = 0;
					
					try {
						amount = Integer.parseInt(args[2]);
						
						Money.setMoney(target.getUniqueId(), target.getDisplayName(), amount);
						sender.sendMessage(Config.getPrefix() + "§aGeld von §6" + target.getDisplayName() + " §awurde aktualisiert");
						target.sendMessage(Config.getPrefix() + "§aDein Geld wurde aktualisiert");
						
					} catch(NumberFormatException ex) {
						sender.sendMessage(Config.getPrefix() + "§4Nur Zahlen sind erlaubt");
					}
				}
			}
			
			else {
				sender.sendMessage(Config.getPrefix() + "§4Du hast keine Rechte dazu");
			}
			
		}
		
		else if(args.length == 3 && args[1].equalsIgnoreCase("remove")) {
			
			if(sender.hasPermission("roleplaysystem.money.admin")) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					int amount = 0;
					
					try {
						amount = Integer.parseInt(args[2]);
						
						Money.removeMoney(target.getUniqueId(), target.getDisplayName(), amount);
						sender.sendMessage(Config.getPrefix() + "§aGeld von §6" + target.getDisplayName() + " §awurde aktualisiert");
						target.sendMessage(Config.getPrefix() + "§aDein Geld wurde aktualisiert");
						
						if(sender instanceof Player) {
							ServerScoreboard sb = new ServerScoreboard((Player) sender);
							sb.display();
						}
						
					} catch(NumberFormatException ex) {
						sender.sendMessage(Config.getPrefix() + "§4Nur Zahlen sind erlaubt");
					}
				}
			}
			
			else {
				sender.sendMessage(Config.getPrefix() + "§4Du hast keine Rechte dazu");
			}
			
		}
		
		else if(args.length == 3 && args[1].equalsIgnoreCase("add")) {
			
			if(sender.hasPermission("roleplaysystem.money.admin")) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					int amount = 0;
					
					try {
						amount = Integer.parseInt(args[2]);
						
						Money.addMoney(target.getUniqueId(), target.getDisplayName(), amount);
						sender.sendMessage(Config.getPrefix() + "§aGeld von §6" + target.getDisplayName() + " §awurde aktualisiert");
						target.sendMessage(Config.getPrefix() + "§aDein Geld wurde aktualisiert");
						
						if(sender instanceof Player) {
							ServerScoreboard sb = new ServerScoreboard((Player) sender);
							sb.display();
						}
						
					} catch(NumberFormatException ex) {
						sender.sendMessage(Config.getPrefix() + "§4Nur Zahlen sind erlaubt");
					}
				}
			}
			
			else {
				sender.sendMessage(Config.getPrefix() + "§4Du hast keine Rechte dazu");
			}
		}
		
		else {
			sender.sendMessage(Config.getPrefix() + "§4Falsche Argumente");
		}
		
		return false;
	}

}
