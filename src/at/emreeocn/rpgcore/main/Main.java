package at.emreeocn.rpgcore.main;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import at.emreeocn.rpgcore.commands.DragonRaidCommand;
import at.emreeocn.rpgcore.commands.FlyspeedCommand;
import at.emreeocn.rpgcore.commands.HeadCommand;
import at.emreeocn.rpgcore.commands.HomeCommand;
import at.emreeocn.rpgcore.commands.InvseeCommand;
import at.emreeocn.rpgcore.commands.MessageCommand;
import at.emreeocn.rpgcore.commands.ModifyCommand;
import at.emreeocn.rpgcore.commands.MoneyCommand;
import at.emreeocn.rpgcore.commands.PayCommand;
import at.emreeocn.rpgcore.commands.ServerCommand;
import at.emreeocn.rpgcore.commands.SpawnCommand;
import at.emreeocn.rpgcore.commands.SpawnMobCommand;
import at.emreeocn.rpgcore.commands.TaskCommand;
import at.emreeocn.rpgcore.commands.TeleportAllCommand;
import at.emreeocn.rpgcore.commands.TeleportAtCommand;
import at.emreeocn.rpgcore.commands.TeleportCommand;
import at.emreeocn.rpgcore.commands.TrashCommand;
import at.emreeocn.rpgcore.commands.VanishCommand;
import at.emreeocn.rpgcore.dungeon.DungeonManager;
import at.emreeocn.rpgcore.farmworld.FarmworldListener;
import at.emreeocn.rpgcore.group.GroupListener;
import at.emreeocn.rpgcore.listener.CaveListener;
import at.emreeocn.rpgcore.listener.DropListener;
import at.emreeocn.rpgcore.listener.LobbyListener;
import at.emreeocn.rpgcore.listener.MainListener;
import at.emreeocn.rpgcore.listener.MenuListener;
import at.emreeocn.rpgcore.listener.RPGListener;
import at.emreeocn.rpgcore.plotsurvive.PlotSurvivalListener;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.PlayTime;
import at.emreeocn.rpgcore.util.SQL;
import at.emreeocn.rpgcore.collection.Collection;
import at.emreeocn.rpgcore.dragonraid.DragonRaidManager;
import at.emreeocn.rpgcore.skill.Skill;
import at.emreeocn.rpgcore.task.Task;
import at.emreeocn.rpgcore.task.TaskListener;

public class Main extends JavaPlugin {

	private static Main pl;
	private SQL sql;
	
	public void onEnable() {
		/* Instance */
		pl = this;
		
		/* Config */
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		/* SQL */
		sql = Config.getSql();
		try {
			sql.connect();
			createTables();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/* YAML */
		try {
			Task.init();
			DungeonManager.init();
			
		} catch(IOException ex) {
			ex.printStackTrace();
		}
		
		initLoginTime();
		
		/* Listener */
		Bukkit.getPluginManager().registerEvents(new MainListener(), this);
		Bukkit.getPluginManager().registerEvents(new RPGListener(), this);
		Bukkit.getPluginManager().registerEvents(new DropListener(), this);
//		Bukkit.getPluginManager().registerEvents(new GameListener(), this);
		Bukkit.getPluginManager().registerEvents(new CaveListener(), this);
		Bukkit.getPluginManager().registerEvents(new TaskListener(), this);
		Bukkit.getPluginManager().registerEvents(new FarmworldListener(), this);
		Bukkit.getPluginManager().registerEvents(new LobbyListener(), this);
		Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlotSurvivalListener(), this);
		Bukkit.getPluginManager().registerEvents(new GroupListener(), this);
		
		/* Command */
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("modify").setExecutor(new ModifyCommand());
		getCommand("coins").setExecutor(new MoneyCommand());
		getCommand("pay").setExecutor(new PayCommand());
		getCommand("server").setExecutor(new ServerCommand());
		getCommand("invsee").setExecutor(new InvseeCommand());
		getCommand("teleport").setExecutor(new TeleportCommand());
		getCommand("teleportall").setExecutor(new TeleportAllCommand());
		getCommand("teleportat").setExecutor(new TeleportAtCommand());
		getCommand("vanish").setExecutor(new VanishCommand());
		getCommand("message").setExecutor(new MessageCommand());
		getCommand("home").setExecutor(new HomeCommand());
		getCommand("flyspeed").setExecutor(new FlyspeedCommand());
		getCommand("trash").setExecutor(new TrashCommand());
		getCommand("spawnmob").setExecutor(new SpawnMobCommand());
		getCommand("task").setExecutor(new TaskCommand());
		getCommand("head").setExecutor(new HeadCommand());
//		getCommand("dungeon").setExecutor(new DungeonCommand());
		getCommand("dragonraid").setExecutor(new DragonRaidCommand());
		
		/* HashMap */
		Skill.initHashMap();
	
		/* Dungeon-Addon */
//		new DungeonManager();
		
		addRecipes();
	}

	public void onDisable() {
		System.out.println("RPGCore wird beendet");
	}
	
	public static void addRecipes() {
		Bukkit.getServer().addRecipe(DragonRaidManager.spawnItemRecipe());
	}
	
	public PreparedStatement prepareStatement(String query) {
		return sql.prepareStatement(query);
	}
	
	private void initLoginTime() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			PlayTime.setLoginTime(player, System.currentTimeMillis());
		}
	}
	
	private void createTables() {
		try {
			prepareStatement("CREATE TABLE IF NOT EXISTS skills("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ "	SPEED INT NOT NULL DEFAULT '1',"
					+ "	HEALTH INT NOT NULL DEFAULT '1',"
					+ " POINTS INT NOT NULL DEFAULT '3',"
					+ "	PRIMARY KEY (UUID)"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
			prepareStatement("CREATE TABLE IF NOT EXISTS playtime("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ "	TOTAL BIGINT NOT NULL,"
					+ "	PRIMARY KEY (UUID)"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
			String s1 = "CREATE TABLE IF NOT EXISTS collection(" + " NAME VARCHAR(25) NOT NULL," + " UUID VARCHAR(36) NOT NULL,";
			String s2 = " PRIMARY KEY (UUID)" + ") ENGINE=InnoDB;";
			
			for(Collection collection : Collection.values()) {
				s1 += collection.getColumn() + " INT NOT NULL DEFAULT '0',";
			}
			
			s1 += s2;
			
			prepareStatement(s1).executeUpdate();
			
			prepareStatement("CREATE TABLE IF NOT EXISTS rewards("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ "	INVENTORY TEXT NOT NULL,"
					+ "	PRIMARY KEY (UUID)"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
			prepareStatement("CREATE TABLE IF NOT EXISTS enderchest("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ "	INVENTORY TEXT NOT NULL,"
					+ "	PRIMARY KEY (UUID)"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
			prepareStatement("CREATE TABLE IF NOT EXISTS coins("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ "	COINS INT(20) NOT NULL DEFAULT '5000',"
					+ "	PRIMARY KEY (UUID)"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
			prepareStatement("CREATE TABLE IF NOT EXISTS homes("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ " HOME TEXT NOT NULL,"
					+ "	WORLD TEXT NOT NULL,"
					+ " X DOUBLE NOT NULL,"
					+ " Y DOUBLE NOT NULL,"
					+ " Z DOUBLE NOT NULL,"
					+ " YAW DOUBLE NOT NULL,"
					+ " PITCH DOUBLE NOT NULL"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
			prepareStatement("CREATE TABLE IF NOT EXISTS plotsurvive("
					+ "	NAME VARCHAR(25) NOT NULL,"
					+ "	UUID VARCHAR(36) NOT NULL,"
					+ "	LEVEL INT(5) NOT NULL DEFAULT '1',"
					+ " PRIMARY KEY (UUID)"
					+ ") ENGINE=InnoDB;").executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public SQL getSql() { return sql; }
	
	public static Main getMain() { return pl; }
	
}
