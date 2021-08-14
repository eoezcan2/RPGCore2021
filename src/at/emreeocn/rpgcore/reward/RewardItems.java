package at.emreeocn.rpgcore.reward;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RewardItems {

	public static ItemStack getLuckyShovel() {
		ItemStack item = new ItemStack(Material.GOLDEN_SHOVEL);
		ItemMeta meta = item.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§7[§bErde §7- §67§7]");
		
		meta.setDisplayName("§6Lucky-Shovel");
		meta.setLore(lore);
		meta.addEnchant(Enchantment.DIG_SPEED, 4, true);
		
		item.setItemMeta(meta);
		
		return item;
	}

	public static ItemStack getDiamonds(int amount) {
		ItemStack item = new ItemStack(Material.DIAMOND, amount);
		ItemMeta meta = item.getItemMeta();
		
		ArrayList<String> lore = new ArrayList<String>();
		lore.add("");
		lore.add("§7[§bEisen §7- §62§7]");
		meta.setDisplayName("§fDiamant");
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
