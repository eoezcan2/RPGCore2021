package at.emreeocn.rpgcore.coin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.PacketMethods;
import at.emreeocn.rpgcore.scoreboard.ServerScoreboard;

public class Money {
	
	public static boolean existsSQL(UUID uuid) {
		ResultSet rs;
		boolean b = false;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM coins WHERE UUID = '" + uuid.toString() + "';").executeQuery();
			rs.next();
			
			if(rs.getInt(1) != 0) {
				b = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return b;
	}
	
	public static void insertSQL(UUID uuid, String name) {
		
		try {
			Main.getMain().prepareStatement("INSERT INTO coins(NAME, UUID, COINS) VALUES('" + name + "', '" + uuid.toString() + "', DEFAULT);").executeUpdate();
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
	}
	
	public static int getMoney(String name) {
		ResultSet rs;
		int money = 0;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COINS FROM coins WHERE NAME = '" + name + "';").executeQuery();
			rs.next();
			
			money = rs.getInt("COINS");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return money;
	}

	public static int getMoney(UUID uuid) {
		ResultSet rs;
		int money = 0;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COINS FROM coins WHERE UUID = '" + uuid.toString() + "';").executeQuery();
			rs.next();
			
			money = rs.getInt("COINS");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return money;
	}
	
	public static void setMoney(UUID uuid, String name, int amount) {
		
		try {
			Main.getMain().prepareStatement("UPDATE coins SET COINS = " + amount + ", NAME = '" + name + "' WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(Bukkit.getPlayer(uuid) != null) {
			ServerScoreboard sb = new ServerScoreboard(Bukkit.getPlayer(uuid));
			sb.display();
		}
	}
	
	public static void addMoney(UUID uuid, String name, int amount) {
		int currentMoney = getMoney(uuid);
		int newMoney = currentMoney + amount;
		
		try {
			Main.getMain().prepareStatement("UPDATE coins SET COINS = " + newMoney + ", NAME = '" + name + "' WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(Bukkit.getPlayer(uuid) != null) {
			ServerScoreboard sb = new ServerScoreboard(Bukkit.getPlayer(uuid));
			sb.display();
			PacketMethods.sendActionBar(Bukkit.getPlayer(uuid), "§6+ " + amount + " Coins");
		}
	}
	
	public static void removeMoney(UUID uuid, String name, int amount) {
		int currentMoney = getMoney(uuid);
		int newMoney = currentMoney - amount;
		
		try {
			Main.getMain().prepareStatement("UPDATE coins SET COINS = " + newMoney + ", NAME = '" + name + "' WHERE UUID = '" + uuid.toString() + "';").executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(Bukkit.getPlayer(uuid) != null) {
			ServerScoreboard sb = new ServerScoreboard(Bukkit.getPlayer(uuid));
			sb.display();
		}
	}
	
	public static void buy(Player player, int price, ItemStack item) {
		if(Money.getMoney(player.getUniqueId()) >= price) {
			Money.removeMoney(player.getUniqueId(), player.getDisplayName(), price);
			if(player.getInventory().firstEmpty() != -1) player.getInventory().addItem(item);
		}
		
		else {
			player.sendMessage(Config.getPrefix() + "§cDu hast nicht genug Geld");
		}
		
	}
	
	public static void buy(Player player, int price) {
		if(Money.getMoney(player.getUniqueId()) >= price) {
			Money.removeMoney(player.getUniqueId(), player.getDisplayName(), price);
			
		} else {
			player.sendMessage(Config.getPrefix() + "§cDu hast nicht genug Geld!");
		}
		
	}
	
}
