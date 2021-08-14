package at.emreeocn.rpgcore.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomRecipe {

	@SuppressWarnings("deprecation")
	public static ShapedRecipe getBoostBootsRecipe() {
		ShapedRecipe s = new ShapedRecipe(getBoostBoots());
		s.shape("$$$", "%$%", "%$%");
		
		s.setIngredient('$', Material.AIR);
		s.setIngredient('%', Material.DIAMOND, 16);
		
		return s;
	}
	
	public static ItemStack getBoostBoots() {
		ItemStack item = new ItemStack(Material.DIAMOND_BOOTS, 1);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§9Boost-Boots");
		
		item.setItemMeta(meta);
		
		return item;
	}
	
}
