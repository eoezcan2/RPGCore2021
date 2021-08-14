package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.scoreboard.RPGScoreboard;

public class PayCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			
			if(args.length == 2) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					int amount = 0;
					
					try {
						amount = Integer.parseInt(args[1]);
						if(amount <= 0) {
							player.sendMessage(Config.getPrefix() + "§cDu kannst keine negativen Zahlen oder 0 eingeben");
							return false;
						}
						
					} catch(NumberFormatException ex) {
						player.sendMessage(Config.getPrefix() + "§cNur Zahlen erlaubt!");
						return false;
					}
					
					if(amount <= Money.getMoney(player.getUniqueId())) {
						Money.removeMoney(player.getUniqueId(), player.getDisplayName(), amount);
						Money.addMoney(target.getUniqueId(), target.getDisplayName(), amount);
						player.sendMessage(Config.getPrefix() + "§aTransaktion wurde erfolgreich ausgeführt");
						target.sendMessage(Config.getPrefix() +  "§6" + player.getDisplayName() + " §ahat dir §6" + amount + " §agezahlt");
						
						RPGScoreboard sb1 = new RPGScoreboard(player);
						RPGScoreboard sb2 = new RPGScoreboard(target);
						sb1.display();
						sb2.display();
					}
				}
				
			} else {
				player.sendMessage(Config.getPrefix() + "§cNutze /pay <Betrag> <Spieler>");
			}
			
		} else {
			sender.sendMessage("Du bist kein Spieler!");
		}
		
		return false;
	}

}
