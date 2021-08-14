package at.emreeocn.rpgcore.collection;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.emreeocn.rpgcore.util.ItemCreator;
import at.emreeocn.rpgcore.rpg.RPGMenu;

public class CollectionMenu {

    private Player player;

    private int invSize;

    private ArrayList<String> emptyLore = new ArrayList<String>();

    private ItemStack woodItem;
    private ItemStack cobblestoneItem;
    private ItemStack ironIngotItem;
    private ItemStack backItem;

    public CollectionMenu(Player player) {
        this.player = player;
        this.invSize = 27;

        createBackItem();
    }

    public Inventory getGui() {
        Inventory inv = Bukkit.createInventory(null, invSize, "§bSammlungen-" + player.getDisplayName());

        for(int i = 0; i < inv.getSize(); i++) {
            if(inv.getItem(i) == null) {
                inv.setItem(i, RPGMenu.getNull());
            }
        }

        inv.setItem(26, backItem);

        initGui(inv);

        return inv;
    }

    private void initGui(Inventory inv) {
        int i = 9;
        for(Collection collection : Collection.values()) {
            if(i < 18) {
                ItemStack item = ItemCreator.createGuiItem(collection.getMaterial(), "§b" + collection.getTitle(), emptyLore);
                ItemMeta meta = item.getItemMeta();
                meta = progressLore(collection, meta, Collection.get(collection, player));
                item.setItemMeta(meta);

                inv.setItem(i, item);
                i++;

            } else break;
        }
    }

    private void createBackItem() {
        ArrayList<String> lore = new ArrayList<>();

        backItem = ItemCreator.createGuiItem(Material.ARROW,  "§cZurück", lore);
    }

    private ItemMeta progressLore(Collection collection, ItemMeta meta, int progress) {
        if(Collection.get(collection, player) >= Collection.getLevelsSum()) {
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(progressBar(Collection.getLevels()[Collection.getLevels().length - 1], Collection.getLevels()[Collection.getLevels().length - 1]));
            lore.add("§7" + Collection.getLevels()[Collection.getLevels().length - 1] + "§6/" + Collection.getLevels()[Collection.getLevels().length - 1]);
            meta.setDisplayName(meta.getDisplayName() + " §6" + (Collection.getLevels().length) + "˜…");
            meta.setLore(lore);

        } else {
            int level = 0;
            for(int i = 0; i < Collection.getLevels().length; i++) {
                if(progress <= Collection.getLevels()[i]) {
                    level = i + 1;
                    i = 1;
                    break;

                } else {
                    progress -= Collection.getLevels()[i];
                }
            }
            ArrayList<String> lore = new ArrayList<String>();
            lore.add(progressBar(progress, Collection.getLevels()[level - 1]));
            lore.add("§7" + progress + "§6/" + Collection.getLevels()[level - 1]);
            meta.setDisplayName(meta.getDisplayName() + " §6" + (level) + "˜…");
            meta.setLore(lore);
        }

        return meta;
    }

    private String progressBar(int progress, int max) {
        float percent = (progress * 100) / max;
        float rest = (100 - percent);

        String res = "";
        for(int i = 0; i < ((int) percent / 10); i++) {
            res += "§a–‘";
        }
        res += "§f";
        for(int i = 0; i < ((int) rest / 10); i++) {
            res += "§7–‘";
        }
        
        res += " §a" + (percent) + "%";

        if(progress == max) {
            res += " §a[œ”]";
        }

        return res;
    }

    public ItemStack getWoodItem() {
        return woodItem;
    }

    public ItemStack getCobblestoneItem() {
        return cobblestoneItem;
    }

    public ItemStack getIronIngotItem() {
        return ironIngotItem;
    }
    
    public static String getInventoryTitle(Player player) { return "§bSammlungen-" + player.getDisplayName(); }
}
