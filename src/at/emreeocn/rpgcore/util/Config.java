package at.emreeocn.rpgcore.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.dungeon.DungeonManager;
import at.emreeocn.rpgcore.events.TaskFinishedEvent;
import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.task.Task;

public class Config {

	public static Location getSpawn() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("spawn.world")),
				Main.getMain().getConfig().getDouble("spawn.x"),
				Main.getMain().getConfig().getDouble("spawn.y"),
				Main.getMain().getConfig().getDouble("spawn.z"),
				(float) Main.getMain().getConfig().getDouble("spawn.yaw"),
				(float) Main.getMain().getConfig().getDouble("spawn.pitch"));
	}
	
	public static Location getCitybuild() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("cb.world")),
				Main.getMain().getConfig().getDouble("cb.x"),
				Main.getMain().getConfig().getDouble("cb.y"),
				Main.getMain().getConfig().getDouble("cb.z"),
				(float) Main.getMain().getConfig().getDouble("cb.yaw"),
				(float) Main.getMain().getConfig().getDouble("cb.pitch"));
	}
	
	public static Location getFarmworld() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("fw.world")),
				Main.getMain().getConfig().getDouble("fw.x"),
				Main.getMain().getConfig().getDouble("fw.y"),
				Main.getMain().getConfig().getDouble("fw.z"),
				(float) Main.getMain().getConfig().getDouble("fw.yaw"),
				(float) Main.getMain().getConfig().getDouble("fw.pitch"));
	}
	
	public static Location getNether() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("nether.world")),
				Main.getMain().getConfig().getDouble("nether.x"),
				Main.getMain().getConfig().getDouble("nether.y"),
				Main.getMain().getConfig().getDouble("nether.z"),
				(float) Main.getMain().getConfig().getDouble("nether.yaw"),
				(float) Main.getMain().getConfig().getDouble("nether.pitch"));
	}
	
	public static Location getEnd() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("end.world")),
				Main.getMain().getConfig().getDouble("end.x"),
				Main.getMain().getConfig().getDouble("end.y"),
				Main.getMain().getConfig().getDouble("end.z"),
				(float) Main.getMain().getConfig().getDouble("end.yaw"),
				(float) Main.getMain().getConfig().getDouble("end.pitch"));
	}
	
	public static Location getEnderdragonraid() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("enderdragonraid.world")),
				Main.getMain().getConfig().getDouble("enderdragonraid.x"),
				Main.getMain().getConfig().getDouble("enderdragonraid.y"),
				Main.getMain().getConfig().getDouble("enderdragonraid.z"),
				(float) Main.getMain().getConfig().getDouble("enderdragonraid.yaw"),
				(float) Main.getMain().getConfig().getDouble("enderdragonraid.pitch"));
	}
	
	public static Location getCave() throws IllegalArgumentException {
		return new Location(
				Bukkit.getWorld(Main.getMain().getConfig().getString("cave.world")),
				Main.getMain().getConfig().getDouble("cave.x"),
				Main.getMain().getConfig().getDouble("cave.y"),
				Main.getMain().getConfig().getDouble("cave.z"),
				(float) Main.getMain().getConfig().getDouble("cave.yaw"),
				(float) Main.getMain().getConfig().getDouble("cave.pitch"));
	}
	
	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', Main.getMain().getConfig().getString("prefix"));
	}
	
	public static SQL getSql() {
		return new SQL(
				Main.getMain().getConfig().getString("sql.database"),
				Main.getMain().getConfig().getString("sql.host"),
				Main.getMain().getConfig().getString("sql.username"),
				Main.getMain().getConfig().getString("sql.password"),
				Main.getMain().getConfig().getInt("sql.port"));
	}
	
	public static void setSpawn(Location loc) {
		Main.getMain().getConfig().set("spawn.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("spawn.x", loc.getX());
		Main.getMain().getConfig().set("spawn.y", loc.getY());
		Main.getMain().getConfig().set("spawn.z", loc.getZ());
		Main.getMain().getConfig().set("spawn.yaw", loc.getYaw());
		Main.getMain().getConfig().set("spawn.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void setFarmworld(Location loc) {
		Main.getMain().getConfig().set("fw.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("fw.x", loc.getX());
		Main.getMain().getConfig().set("fw.y", loc.getY());
		Main.getMain().getConfig().set("fw.z", loc.getZ());
		Main.getMain().getConfig().set("fw.yaw", loc.getYaw());
		Main.getMain().getConfig().set("fw.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void setCitybuild(Location loc) {
		Main.getMain().getConfig().set("cb.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("cb.x", loc.getX());
		Main.getMain().getConfig().set("cb.y", loc.getY());
		Main.getMain().getConfig().set("cb.z", loc.getZ());
		Main.getMain().getConfig().set("cb.yaw", loc.getYaw());
		Main.getMain().getConfig().set("cb.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void setNether(Location loc) {
		Main.getMain().getConfig().set("nether.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("nether.x", loc.getX());
		Main.getMain().getConfig().set("nether.y", loc.getY());
		Main.getMain().getConfig().set("nether.z", loc.getZ());
		Main.getMain().getConfig().set("nv.yaw", loc.getYaw());
		Main.getMain().getConfig().set("nether.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void setEnd(Location loc) {
		Main.getMain().getConfig().set("end.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("end.x", loc.getX());
		Main.getMain().getConfig().set("end.y", loc.getY());
		Main.getMain().getConfig().set("end.z", loc.getZ());
		Main.getMain().getConfig().set("end.yaw", loc.getYaw());
		Main.getMain().getConfig().set("end.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void setEnderdragonraid(Location loc) {
		Main.getMain().getConfig().set("enderdragonraid.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("enderdragonraid.x", loc.getX());
		Main.getMain().getConfig().set("enderdragonraid.y", loc.getY());
		Main.getMain().getConfig().set("enderdragonraid.z", loc.getZ());
		Main.getMain().getConfig().set("enderdragonraid.yaw", loc.getYaw());
		Main.getMain().getConfig().set("enderdragonraid.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void setCave(Location loc) {
		Main.getMain().getConfig().set("cave.world", loc.getWorld().getName().toString());
		Main.getMain().getConfig().set("cave.x", loc.getX());
		Main.getMain().getConfig().set("cave.y", loc.getY());
		Main.getMain().getConfig().set("cave.z", loc.getZ());
		Main.getMain().getConfig().set("cave.yaw", loc.getYaw());
		Main.getMain().getConfig().set("cave.pitch", loc.getPitch());
		
		Main.getMain().saveConfig();
	}
	
	public static void teleportToSpawn(Player player) {
		try {
			player.teleport(Config.getSpawn());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zum Spawn teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cSpawn wurde noch nicht gesetzt!");
		}
	}
	
	public static void teleportToCave(Player player) {
		try {
			player.teleport(Config.getCave());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zum Spawn teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cSpawn wurde noch nicht gesetzt!");
		}
	}
	
	public static void teleportToCitybuild(Player player) {
		try {
			player.teleport(Config.getCitybuild());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zum Citybuild teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			Bukkit.getPluginManager().callEvent(new TaskFinishedEvent(player, Task.TELEPORT_CITYBUILD));
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cCitybuild wurde noch nicht gesetzt!");
		}
	}
	
	public static void teleportToFarmworld(Player player) {
		try {
			player.teleport(Config.getFarmworld());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zur Farmwelt teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			new TaskFinishedEvent(player, Task.TELEPORT_FARMWORLD);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cFarmwelt wurde noch nicht gesetzt!");
		}
	}
	
	public static void teleportToNether(Player player) {
		try {
			player.teleport(Config.getNether());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zur Farmwelt teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			new TaskFinishedEvent(player, Task.TELEPORT_NETHER);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cNether wurde noch nicht gesetzt!");
		}
	}
	
	public static void teleportToEnd(Player player) {
		try {
			player.teleport(Config.getEnd());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zur Farmwelt teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cNether wurde noch nicht gesetzt!");
		}
	}

	public static void teleportToEnderdragonraid(Player player) {
		try {
			player.teleport(Config.getEnderdragonraid());
//			player.sendMessage(Config.getPrefix() + "§aDu wurdest zur Farmwelt teleportiert");
			player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 8, 1);
			
		} catch(IllegalArgumentException ex) {
			player.sendMessage(Config.getPrefix() + "§cEnderdragonraid wurde noch nicht gesetzt!");
		}
	}
	
	/* Dungeon */
	
	public static int getRequiredPlayers(int id) {
		return DungeonManager.getYml().getInt("raids." + id + ".required-players");
	}
	
	public static int getCountdownSeconds() {
		return DungeonManager.getYml().getInt("countdown-seconds");
	}
	
	public static Location getDungeonSpawn(int id) {
		return new Location(
				Bukkit.getWorld(DungeonManager.getYml().getString("raids." + id + ".world")),
				DungeonManager.getYml().getDouble("raids." + id + ".x"),
				DungeonManager.getYml().getDouble("raids." + id + ".y"),
				DungeonManager.getYml().getDouble("raids." + id + ".z"),
				(float) DungeonManager.getYml().getDouble("raids." + id + ".pitch"),
				(float) DungeonManager.getYml().getDouble("raids." + id + ".yaw"));
	}

	public static int getDungeonAmount() {
		return DungeonManager.getYml().getConfigurationSection("raids.").getKeys(false).size();
	}
	
	public static int getKillScore(int id) {
		return DungeonManager.getYml().getInt("raids." + id + ".kill-score");
	}

	public static String getDungeonTitle(int id) {
		return DungeonManager.getYml().getString("raids." + id + ".title");
	}
	
	public static void setDungeonSpawn(Location loc, int id) {
		DungeonManager.getYml().set("raids." + id + ".world", loc.getWorld().getName().toString());
		DungeonManager.getYml().set("raids." + id + ".x", loc.getX());
		DungeonManager.getYml().set("raids." + id + ".y", loc.getY());
		DungeonManager.getYml().set("raids." + id + ".z", loc.getZ());
		DungeonManager.getYml().set("raids." + id + ".pitch", loc.getPitch());
		DungeonManager.getYml().set("raids." + id + ".yaw", loc.getYaw());
		
		DungeonManager.save();
	}
	
	public static void setRequiredPlayers(int amount, int id) {
		DungeonManager.getYml().set("raids." + id + ".required-players", amount);
		
		DungeonManager.save();
	}
	
}
