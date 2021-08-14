package at.emreeocn.rpgcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class HeadCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length == 1) {
				if(Bukkit.getPlayer(args[0]) != null) {
					Player target = Bukkit.getPlayer(args[0]);
					player.getInventory().addItem(getHead(target));
					return true;
					
				} else {
					OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
					player.getInventory().addItem(getHead(target));
					return true;
				}
			} else {
				return false;
			}
			
		} else {
			sender.sendMessage("Du bist kein Spieler!");
		}
		
		return false;
	}

	@SuppressWarnings("deprecation")
	private static ItemStack getHead(OfflinePlayer target) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(target.getName());
        skull.setOwner(target.getName());
        item.setItemMeta(skull);
        return item;
	}
	
	@SuppressWarnings("deprecation")
	private static ItemStack getHead(Player target) {
		ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(target.getDisplayName());
        skull.setOwner(target.getName());
        item.setItemMeta(skull);
        return item;
	}
	
}
