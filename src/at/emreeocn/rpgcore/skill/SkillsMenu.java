package at.emreeocn.rpgcore.skill;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import at.emreeocn.rpgcore.util.ItemCreator;
import at.emreeocn.rpgcore.rpg.RPGMenu;

public class SkillsMenu {
	
	private Player player;
	
	private int invSize;
	
	private ItemStack healthItem;
	private ItemStack speedItem;
	private ItemStack pointsItem;
	private ItemStack resetItem;
	
	public SkillsMenu(Player player) {
		this.player = player;
		this.invSize = 9 * 3;
		
		createHealthItem();
		createSpeedItem();
		createPointsItem();
		createResetItem();
	}

	public Inventory getGui() {
		Inventory inv = Bukkit.createInventory(null, invSize, "§8Skills-Menu");
		
		for(int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) == null) {
				inv.setItem(i, RPGMenu.getNull());
			}
		}
		
		inv.setItem(0, resetItem);
		inv.setItem(12 - 1, healthItem);
		inv.setItem(14 - 1, speedItem);
		inv.setItem(5 - 1, pointsItem);
		inv.setItem(23 - 1, getStatusItem(Skill.getStatus().get(player)));
		inv.setItem(invSize - 1, getBackItem());
		
		
		return inv;
	}

	private void createResetItem() {
		resetItem = ItemCreator.createGuiItem(Material.PAPER, "§bReset", new ArrayList<String>());
	}
	
	private void createHealthItem() {
		ItemStack item = new ItemStack(Skill.HEALTH.getClickMaterial());
		
		ArrayList<String> lore = new ArrayList<>();
		lore.add("");
		lore.add("§2Klicken für §6+1");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cHealth: §6" + Skill.get(Skill.HEALTH, player));
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		healthItem = item;
	}
	
	private void createSpeedItem() {
		ItemStack item = new ItemStack(Skill.SPEED.getClickMaterial());
		
		ArrayList<String> lore = new ArrayList<>();
		lore.add("");
		lore.add("§2Klicken für §6+1");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§aSpeed: §6" + Skill.get(Skill.SPEED, player));
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		speedItem = item;
	}
	
	private void createPointsItem() {
		ItemStack item = new ItemStack(Skill.POINTS.getClickMaterial());
		
		ArrayList<String> lore = new ArrayList<>();
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Skill-Punkte: " + Skill.get(Skill.POINTS, player));
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		pointsItem = item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getHead(Player target) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        skull.setDisplayName(target.getName());
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("Custom head");
        skull.setLore(lore);
        skull.setOwner(target.getName());
        item.setItemMeta(skull);
        return item;
    }
	
	@SuppressWarnings("deprecation")
	public static ItemStack getStatusItem(boolean b) {
		ItemStack item = null;
		
		if(b) {
			item = new ItemStack(Material.GREEN_TERRACOTTA);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§a§lON");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			
			item.setItemMeta(meta);
			
		} else {
			item = new ItemStack(Material.RED_TERRACOTTA, 1, (byte) 14);
			
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName("§c§lOFF");
			meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
			
			item.setItemMeta(meta);
		}
		
		return item;
	}

	public static ItemStack getBackItem() {
		ItemStack item = new ItemStack(Material.ARROW);
		
		ArrayList<String> lore = new ArrayList<>();
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cZurück");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		return item;
	}

	public ItemStack getPointsItem() {
		return pointsItem;
	}

	public ItemStack getSpeedItem() {
		return speedItem;
	}
	
	public ItemStack getHealthItem() {
		return healthItem;
	}

	public ItemStack getResetItem() {
		return resetItem;
	}
	
	public static String getInventoryTitle(Player player) { return "§8Skills-Menu"; }
}
