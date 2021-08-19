package at.emreeocn.rpgcore.task;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.reward.Reward;

public enum Task {

	TELEPORT_CITYBUILD(1, "Unsere Stadt", "Betrete den Citybuild.", new Reward(null, 50, 1, null)),
	TELEPORT_FARMWORLD(2, "Unsere Farmwelt", "Betrete die Farmwelt.", new Reward(null, 50, 1, null)),
	USE_SKILLPOINT(3, "Verschwende sie nicht!", "Gebe einen Skillpunkt aus.", new Reward(null, 50, 1, null)),
	BREAK_OBSIDIAN_BLOCK(4, "Harter Brocken", "Baue einen Obsidianblock ab!", new Reward(obsidianPickaxe(), 300, 3, netherText())),
	DIE(5, "Blut fliesst...", "Sterbe einmal durch jegliche Art. Du hast nichts zu verlieren!", new Reward(null, 100, 1, null)),
	TOUCH_ADMIN(6, "Goldener Finger", "Interagiere (Rechtsklick) einmal mit einem Admin", new Reward(goldIngot(9), 500, 6, null)),
	PLOT_CLAIM(7, "Ein Stückchen Heimat", "Ergattere dir ein Grundstück (Command: /p auto oder /p claim)", new Reward(null, 50, 1, null)),
	WITHER_SKELETON_HEAD(8, "Schwarze Knochen", "Töte ein Wither-Skelett und erhalte seinen Kopf", new Reward(null, 500, 6, endText())),
	MAKE_GROUP(9, "Gemeinsam sind wir stärker", "Lasse jemanden deiner Gruppe beitreten (/group invite <Spieler>)", new Reward(null, 50, 1, null)),
	DIAMOND_CHESTPLATE(10, "Drip", "Crafte dir eine Diamant Brustplatte", new Reward(null, 100, 3, null)),
	TELEPORT_NETHER(11, "Furchtlos durch die Hölle", "Betrete den Nether", new Reward(null, 50, 1, null)),
	TELEPORT_CAVE(12, "Ressourcen ohne Ende!", "Betrete The Cave", new Reward(null, 50, 1, null)),
	BEAT_ENDERDRAGON(13, "Held des End", "Helfe dabei den Enderdrachen zu besiegen", new Reward(null, 1000, 10, plotSurvivalText())),
	PLAY_PLOTSURVIVE(14, "Bis heute überlebt", "Spiele eine Runde überlebenskampf", new Reward(null, 50, 1, null));
	
	private int id;
	private String title, description;
	private Reward reward;

	private static File file;
	private static YamlConfiguration yml;
	
	Task(int id, String title, String description, Reward reward) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.reward = reward;
	}

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}
	
	public static boolean didTask(Player player, Task task) {
		if(yml.contains("tasks." + player.getUniqueId().toString() + "." + task.getId())) return true;
		else return false;
	}
	
	public static void init() throws IOException {
		file = new File(Main.getMain().getDataFolder() + "/tasks.yml");
		if(!file.exists()) {
			file.createNewFile();
		}
		
		yml = YamlConfiguration.loadConfiguration(file);
	}
	
	public static void setTask(Player player, Task task) {
		yml.set("tasks." + player.getUniqueId().toString() + "." + task.getId(), true);
		save();
	}
	
	public static void removeTask(Player player, Task task) {
		yml.set("tasks." + player.getUniqueId().toString() + "." + task.getId(), false);
		save();
	}
	
	private static void save() {
		try {
			yml.save(file);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getFile() {
		return file;
	}

	public static YamlConfiguration getYml() {
		return yml;
	}

	public Reward getReward() {
		return reward;
	}
	
	private static ArrayList<ItemStack> obsidianPickaxe() {
		ArrayList<ItemStack> a = new ArrayList<ItemStack>();
		
		ItemStack item = createRewardItem(Material.DIAMOND_PICKAXE, 1, "§5Legendäre Obsidian Spitzhacke", Enchantment.DIG_SPEED, 3);
		ItemMeta meta = item.getItemMeta();
		
		item.setItemMeta(meta);
		
		a.add(item);
		
		return a;
	}
	
	private static ArrayList<ItemStack> goldIngot(int amount) {
		ArrayList<ItemStack> a = new ArrayList<ItemStack>();
		
		ItemStack item = createRewardItem(Material.GOLD_INGOT, amount, "§6Gold aus einer Hosentasche");
		ItemMeta meta = item.getItemMeta();
		
		item.setItemMeta(meta);
		
		a.add(item);
		
		return a;
	}
	
	private static ArrayList<String> netherText() {
		ArrayList<String> a = new ArrayList<String>();
		
		a.add("Teleportation zum Nether freischalten");
		
		return a;
	}
	
	private static ArrayList<String> endText() {
		ArrayList<String> a = new ArrayList<String>();
		
		a.add("Teleportation zum End freischalten");
		
		return a;
	}
	
	private static ArrayList<String> plotSurvivalText() {
		ArrayList<String> a = new ArrayList<String>();
		
		a.add("Überlebenskampf freischalten");
		
		return a;
	}
	
	public static ItemStack createRewardItem(Material material, int amount, String displayName) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(displayName);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack createRewardItem(Material material, int amount, byte b, String displayName) {
		ItemStack item = new ItemStack(material, amount, b);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(displayName);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ItemStack createRewardItem(Material material, int amount, String displayName, Enchantment enchantment, int enchantmentLevel) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(displayName);
		meta.addEnchant(enchantment, enchantmentLevel, true);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ArrayList<String> rewardLores(Task task) {
		ArrayList<String> lore = new ArrayList<String>();
		
		lore.add("§7– §e" + task.getReward().getCoins() + " Coins");
		lore.add("§7– §e" + task.getReward().getSkillPoints() + "x Skillpunkt");
		if(task.getReward().getItems() != null) {
			for(ItemStack item : task.getReward().getItems()) {
				lore.add("§7– §e" + item.getAmount() + "x " + item.getItemMeta().getDisplayName());
			}
		}
		if(task.getReward().getTexts() != null) {
			for(String s : task.getReward().getTexts()) {
				lore.add("§7– §e" + s);
			}
		}
		
		return lore;
	}
	
	public static String getTasksString(Player player) {
		String res = "";
		
		boolean b;
		
		for(Task t : Task.values()) {
			if(Task.didTask(player, t)) b = true;
			else b = false;
			res += "§7- §a" + t.getTitle() + " §6[" + t.getId() + "] §7 -> §6" + b + "\n";
		}
		
		return res;
	}
	
	public static String getTaskString(Player player, Task task) {
		String res = "";
		
		boolean b = false;
		
		if(Task.didTask(player, task)) b = true;
		
		res += "§7- §a" + task.getTitle() + " §6[" + task.getId() + "]§7 -> §6" + b + "\n";
		
		return res;
	}
	
	public static Task getTaskById(int id) {
		for(Task t : Task.values()) {
			if(t.getId() == id) return t;
		}
		
		return null;
	}
	
	public static int getAmountOfDoneTasks(Player player) {
		int res = 0;
		for(Task t : Task.values()) {
			if(Task.didTask(player, t)) res++;
		}
		return res;
	}
	
}
