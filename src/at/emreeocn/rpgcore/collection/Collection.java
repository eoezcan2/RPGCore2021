package at.emreeocn.rpgcore.collection;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.util.Config;

public enum Collection {
	
	COBBLESTONE(Material.STONE, "COBBLESTONE", "Stein"),
	IRON_INGOT(Material.IRON_ORE, "IRON_INGOT", "Eisen"),
	LOG(Material.OAK_LOG, "LOG", "Holz"),
	DIRT(Material.DIRT, "DIRT", "Erde"),
	OBSIDIAN(Material.OBSIDIAN, "OBSIDIAN", "Obsidian");
	
	private Material material;
	private String column;
	private String title;
	
	private static int[] levels = { 64, 128, 256, 512, 1024, 2048 };
	
	Collection(Material material, String column, String title) {
		this.material = material;
		this.column = column;
		this.title = title;
	}
	
	public static int get(Collection collection, Player player) {
		ResultSet rs = null;
		int res = 0;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT * FROM collection WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			res = rs.getInt(collection.column);
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return res;
	}
	
	public static void add(Collection collection, Player player, int amount) {
		try {
			Main.getMain().prepareStatement("UPDATE collection SET "
					+ "NAME = '" + player.getDisplayName() + "', "
					+ collection.column + " = " + (get(collection, player) + amount) + " WHERE UUID = '" + player.getUniqueId().toString() + "';").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static int getAmountToNextLevel(Collection collection, Player player) {
		int progress = Collection.get(collection, player);
		int index = 0;
		for(int i = 0; i < Collection.getLevels().length; i++) {
			if(progress <= Collection.getLevels()[i]) {
				index = i;
				break;
				
			} else {
				progress -= Collection.getLevels()[i];
			}
		}
		
		return Collection.getLevels()[index] - progress;
	}
	
	public static int getLevel(Collection collection, Player player) {
		int progress = Collection.get(collection, player);
		int level = 0;
		for(int i = 0; i < Collection.getLevels().length; i++) {
			if(progress <= Collection.getLevels()[i]) {
				level = i + 1;
				break;
				
			} else {
				progress -= Collection.getLevels()[i];
				player.sendMessage("progress = " + progress);
			}
		}
		
		player.sendMessage("" + level + "/" + Collection.getLevels().length);
		
		return level;
	}
	
	public static boolean isMaxLevel(Collection collection, Player player) {
		if(getLevel(collection, player) >= Collection.getLevels().length + 1) return true;
		else return false;
	}
	
	public static boolean isInSystem(Player player) {
		ResultSet rs = null;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT COUNT(UUID) FROM collection WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			if(rs.getInt(1) != 0) {
				return true;
			}
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Insert the player into SQL
	 * @param player
	 */
	public static void insert(Player player) {
		try {
			Main.getMain().prepareStatement("INSERT INTO collection(NAME, UUID, COBBLESTONE, IRON_INGOT, LOG) VALUES("
					+ "'" + player.getDisplayName().toString() + "', "
					+ "'" + player.getUniqueId().toString() + "', "
					+ "DEFAULT, "
					+ "DEFAULT, "
					+ "DEFAULT);"
					+ "").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void levelUp(Collection collection, Player player) {
		player.getWorld().playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 8, 1);
		if(getLevel(collection, player) == -1) {
			player.sendMessage(Config.getPrefix() + "§aDu hast das maximale Level von §6" + collection.getTitle() + " §aerreicht");
			
		} else {
			player.sendMessage(Config.getPrefix() + "§aDu bist jetzt Level §6" + getLevel(collection, player) + " §ain §6" + collectionTextFormat(collection));
		}
		
		player.sendMessage(Config.getPrefix() + "§aErhalte deine Belohnung im §6RPG-Menü §abei deinen Belohnungen!");
		
//		reward(collection, player);
	}
	
	private static String collectionTextFormat(Collection collection) {
		return collection.getTitle();
	}
	
	/*public static void reward(Collection collection, Player player) {
		int level = Collection.getLevel(collection, player);
		
		RewardMenu rewardMenu = new RewardMenu(player);
		Inventory inv = rewardMenu.getGui();
		ItemStack item = null;
		
		try {
			item = Reward.getReward(collection, level).getItemStack();
			
		} catch(NullPointerException ex) {
			item = null;
		}
		
		if(item != null) {
			inv.addItem(item);
			Reward.saveInventory(player, inv);
		}
	}*/
	
	public static Collection getCollection(Material material) {
		for(Collection col : Collection.values()) {
			if(col.getMaterial() == material) {
				return col;
			}
		}
		
		return null;
	}

	public static int getLevelsSum() {
		int res = 0;
		for(int i : getLevels()) {
			res += i;
		}
		
		return res;
	}
	
	public static int[] getLevels() {
		return levels;
	}

	public Material getMaterial() {
		return material;
	}

	public String getColumn() {
		return column;
	}

	public String getTitle() {
		return title;
	}
}
