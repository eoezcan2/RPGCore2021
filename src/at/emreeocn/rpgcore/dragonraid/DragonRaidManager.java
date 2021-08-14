package at.emreeocn.rpgcore.dragonraid;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.events.TaskFinishedEvent;
import at.emreeocn.rpgcore.main.Main;
import at.emreeocn.rpgcore.farmworld.Farmworld;
import at.emreeocn.rpgcore.task.Task;

public class DragonRaidManager {

	private static ArrayList<Player> damagers = new ArrayList<Player>();
	public static ArrayList<Player> getDamagers() { return damagers; }
	
	public static boolean dragonRaidRunning() {
		for(Entity e : Farmworld.END.getSpawn().getWorld().getEntities()) {
			if(e.getType() == EntityType.ENDER_DRAGON) return true;
		}
		return false;
	}
	
	public static ItemStack spawnItem() {
		ItemStack item = new ItemStack(Material.EGG);
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§2Nutze das Ei im End um einen §5Drachenraid §2zu starten");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§5Drachenraid-Ei");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public static ShapedRecipe spawnItemRecipe() {
		ShapedRecipe s = new ShapedRecipe(new NamespacedKey(Main.getMain(), "abcd"), spawnItem());
		s.shape("***", "*/*", "***");
		s.setIngredient('*', Material.DIAMOND);
		s.setIngredient('/', Material.EGG);
		
		return s;
	}
	
	public static void spawnDragon(Location loc) {
		loc.setY(loc.getY() + 10);
		Entity e = loc.getWorld().spawnEntity(loc, EntityType.ENDER_DRAGON);
		((EnderDragon) e).setGlowing(true);
		((EnderDragon) e).setPhase(Phase.SEARCH_FOR_BREATH_ATTACK_TARGET);
	}
	
	public static void taskAllDamagers() {
		for(Player player : damagers) {
			new TaskFinishedEvent(player, Task.BEAT_ENDERDRAGON);
		}
	}
	
}
