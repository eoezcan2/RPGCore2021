package at.emreeocn.rpgcore.dungeon;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;

public class DungeonManager {

	private static ArrayList<Dungeon> raids;

	private static File file;
	private static YamlConfiguration yml;
	
	public DungeonManager() {
		raids = new ArrayList<Dungeon>();
		
		for(int i = 0; i < Config.getDungeonAmount(); i++) {
			raids.add(new Dungeon(i));
		}
	}
	
	public static List<Dungeon> getRaids() { return raids; }
	
	public static boolean isPlaying(Player player) {
		for(Dungeon raid : raids) {
			if(raid.getPlayers().contains(player.getUniqueId())) return true;
		}
		return false;
	}
	
	public static Dungeon getDungeon(Player player) {
		for(Dungeon raid : raids) {
			if(raid.getPlayers().contains(player.getUniqueId())) return raid;
		}
		return null;
	}
	
	public static Dungeon getDungeon(int id) {
		for(Dungeon raid : raids) {
			if(raid.getID() == id) return raid;
		}
		return null;
	}
	
	public static boolean isRecruiting(int id) { return getDungeon(id).getState() == GameState.RECRUITING; }
	
	public static void init() throws IOException {
		file = new File(Main.getMain().getDataFolder() + "/dungeon.yml");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		yml = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void save() {
		try {
			yml.save(file);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void reload() {
		file = new File(Main.getMain().getDataFolder() + "/dungeon.yml");
		yml = YamlConfiguration.loadConfiguration(file);
	}
	
	public static YamlConfiguration getYml() {
		return yml;
	}

	public static File getFile() {
		return file;
	}
}
