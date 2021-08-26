package at.emreeocn.rpgcore.rpg;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;

import at.emreeocn.rpgcore.group.GroupManager;
import at.emreeocn.rpgcore.home.HomeManager;
import at.emreeocn.rpgcore.plotsurvive.PlotSurviveManager;
import at.emreeocn.rpgcore.reward.RewardManager;
import at.emreeocn.rpgcore.task.Task;
import at.emreeocn.rpgcore.util.Config;
import at.emreeocn.rpgcore.util.ItemCreator;
import at.emreeocn.rpgcore.util.RPGMethods;

public class RPGMenu {

    private Player player;

    private int invSize;

    private ItemStack skillItem;
    private ItemStack closeItem;
    private ItemStack groupItem;
    private ItemStack spawnItem;
    private ItemStack collectionItem;
    private ItemStack rewardItem;
    private ItemStack enderchest;
    private ItemStack citybuilditem;
    private ItemStack taskItem;
    private ItemStack farmworldItem;
    private ItemStack netherItem;
    private ItemStack endItem;
    private ItemStack infoItem;
    private ItemStack trashItem;
    private ItemStack plotSurvivalItem;
    private ItemStack homesItem;
    private ItemStack roleSelectionItem;
    
    private static String website = "X";
    private static String discord = "https://discord.gg/H9V92TY8Yp";

    public RPGMenu(Player player) {
        this.player = player;
        this.invSize = 9 * 6;

        createCloseItem();
        createSkillItem();
        createGroupItem();
        createSpawnItem();
//		createCollectionItem();
        createRewardItem();
        createEnderchest();
        createCitybuildItem();
        createTaskItem();
        createFarmworldItem();
        createNetherItem();
        createEndItem();
        createInfoItem();
        createTrashItem();
        createPlotSurvivalItem();
        createHomesItem();
        createRoleSelectionItem();
    }

    public Inventory getGui() {
        Inventory inv = Bukkit.createInventory(null, invSize, "§8RPG-Menü");

        inv.setItem(3 - 1, groupItem);
        inv.setItem(12 - 1, skillItem);
//		inv.setItem(5 - 1, collectionItem);
        inv.setItem(5 - 1, taskItem);
        inv.setItem(9 - 1, infoItem);
        inv.setItem(14 - 1, rewardItem);
        inv.setItem(16 - 1, enderchest);
        inv.setItem(1 - 1, spawnItem);
        inv.setItem(19 - 1, farmworldItem);
        inv.setItem(10 - 1, citybuilditem);
        inv.setItem(21 - 1, homesItem);
        inv.setItem(7 - 1, trashItem);

        if (Task.didTask(player, Task.WITHER_SKELETON_HEAD)) inv.setItem(37 - 1, endItem);

        if (Task.didTask(player, Task.BREAK_OBSIDIAN_BLOCK)) inv.setItem(28 - 1, netherItem);

        if (Task.didTask(player, Task.BEAT_ENDERDRAGON))
            if (player.getLocation().getWorld() == Config.getCitybuild().getWorld())
                inv.setItem(18 - 1, plotSurvivalItem);

        inv.setItem(invSize - 1, closeItem);

        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) {
                inv.setItem(i, getNull());
            }
        }

        return inv;
    }

    public static void sendInformationMessage(Player player) {
        player.sendMessage(Config.getPrefix() + "§f– §2Website: §f" + website + "\n" + "§f– §2Discord: §f" + discord);
    }

    private void createCloseItem() {
        ItemStack item = new ItemStack(Material.BARRIER);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cSchliessen");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        closeItem = item;
    }

    private void createHomesItem() {
        ItemStack item = new ItemStack(Material.WHITE_BED);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Teleportiere dich zu deinen Plots oder Homes");
        lore.add("§7Homes: §7" + HomeManager.getAmount(player) + "§f/" + HomeManager.getLimit(player));
        lore.add("§7Plots: §7" + PlotPlayer.get(player.getDisplayName()).getPlots().size() + "§f/" +
                PlotPlayer.get(player.getDisplayName()).getAllowedPlots());

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fPlots & Homes");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        homesItem = item;
    }

    private void createPlotSurvivalItem() {
        ItemStack item = new ItemStack(Material.IRON_SWORD);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Kämpfe gegen Mobs und erhalte Skillpunkte");
        lore.add("§7Level: §f" + PlotSurviveManager.getLevel(player));

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fÜberlebenskampf");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        plotSurvivalItem = item;
    }
    
    private void createRoleSelectionItem() {
        ItemStack item = new ItemStack(Material.ENDER_PEARL);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Wähle dir eine Rolle aus");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fRollen");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        roleSelectionItem = item;
    }

    private void createEndItem() {
        ItemStack item = new ItemStack(Material.END_STONE);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Teleportiere dich zum End");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fEnd");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        endItem = item;
    }

    private void createRewardItem() {
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add("§2Siehe deine Post");
        lore.add("§2Items von Aufgaben landen hier");
        if (!RewardManager.getInventory(player).isEmpty()) {
            lore.add("§7Enthält:");
            for (ItemStack item : RewardManager.getInventory(player).getContents()) {
                if (item != null) {
                    lore.add("§7- " + item.getItemMeta().getDisplayName());
                }
            }
        }

        rewardItem = ItemCreator.createGuiItem(Material.CHEST, "§fPost", lore);
        if (RPGMethods.countFullSlots(RewardManager.getInventory(player)) > 1)
            rewardItem.setAmount(RPGMethods.countFullSlots(RewardManager.getInventory(player)));
    }

    private void createInfoItem() {
        ItemStack item = new ItemStack(Material.MAP);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§f- §2Website: §6" + website);
        lore.add("§f- §2Discord: §6" + discord);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fInformationen");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        infoItem = item;
    }

    private void createNetherItem() {
        ItemStack item = new ItemStack(Material.NETHER_BRICK);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Teleportiere dich zum Nether");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fNether");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        netherItem = item;
    }

    private void createSkillItem() {
        ItemStack item = new ItemStack(Material.IRON_CHESTPLATE);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Verwalte deine Skills");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fSkills");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        skillItem = item;
    }

    private void createTaskItem() {
        ItemStack item = new ItemStack(Material.BOOKSHELF);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Siehe deine Aufgaben");
        lore.add("§7" + Task.getAmountOfDoneTasks(player) + "§f/" + Task.values().length + " §7Aufgaben abgeschlossen");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fAufgaben");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        taskItem = item;
    }

    private void createFarmworldItem() {
        ItemStack item = new ItemStack(Material.OAK_SAPLING);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Teleportiere dich zur Farmwelt");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fFarmwelt");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        farmworldItem = item;
    }

    private void createCitybuildItem() {
        ItemStack item = new ItemStack(Material.IRON_AXE);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Teleportiere dich zu Citybuild");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fCitybuild");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        citybuilditem = item;
    }

    private void createTrashItem() {
        ItemStack item = new ItemStack(Material.HOPPER);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Ein Mülleimer zum Entfernen von Items");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fMüll");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        trashItem = item;
    }

    // @SuppressWarnings("deprecation")
    private void createGroupItem() {
        ItemStack item = getHead(player);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        // lore.add("§2Spielzeit: §6" + millisToTime(PlayTime.getTotal(player)));
        // lore.add("§7Rang: " + PermissionsEx.getUser(player).getGroups()[0].getPrefix());
        if(GroupManager.isInGroup(player)) {
        	lore.add("§7In einer Gruppe: §aJa");
        } else {
        	lore.add("§7In einer Gruppe: §cNein");
        	lore.add("§7(§6Linksklick §7zum Erstellen)");
        }
        
        if(GroupManager.hasInvitation(player)) {
        	lore.add(" ");
        	lore.add("§7Einladung von: §a" + GroupManager.getInvites().get(player).getLeader().getDisplayName());
        	lore.add("§7(§6Rechtsklick §7zum Annehmen)");
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fGruppe");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        groupItem = item;
    }

    private void createSpawnItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Teleportiere dich zum Spawn");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fSpawn");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        spawnItem = item;
    }
	
	/*private void createCollectionItem() {
		ItemStack item = new ItemStack(Material.BOOK);
		
		ArrayList<String> lore = new ArrayList<>();
		lore.add("");
		lore.add("§2Siehe deine Sammlungen");
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§3Sammlungen");
		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		
		item.setItemMeta(meta);
		
		collectionItem = item;
	}*/

    private void createEnderchest() {
        ItemStack item = new ItemStack(Material.ENDER_CHEST);

        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§2Siehe deine Enderchest");
        lore.add("§2Bewahre deine wichtigen Gegenstände in dieser Chest auf");

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fEnderchest");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        enderchest = item;
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

    public static ItemStack getNull() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);

        ArrayList<String> lore = new ArrayList<>();

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

        item.setItemMeta(meta);

        return item;
    }
	
	/*private static String millisToTime(long millis) {
		long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
		
		return days + "d " + hours + "h " + minutes + "m " + seconds + "s";
	}*/

    public ItemStack getSkillItem() {
        return skillItem;
    }

    public ItemStack getCloseItem() {
        return closeItem;
    }

    public ItemStack getPlayerItem() {
        return groupItem;
    }

    public ItemStack getSpawnItem() {
        return spawnItem;
    }

    public ItemStack getCollectionItem() {
        return collectionItem;
    }

    public ItemStack getRewardItem() {
        return rewardItem;
    }

    public ItemStack getEnderchest() {
        return enderchest;
    }

    public ItemStack getCitybuilditem() {
        return citybuilditem;
    }

    public ItemStack getTaskItem() {
        return taskItem;
    }

    public ItemStack getFarmworldItem() {
        return farmworldItem;
    }

    public ItemStack getNetherItem() {
        return netherItem;
    }

    public ItemStack getEndItem() {
        return endItem;
    }

    public ItemStack getInfoItem() {
        return infoItem;
    }

    public ItemStack getTrashItem() {
        return trashItem;
    }

    public static String getInventoryTitle(Player player) {
        return "§8RPG-Menü";
    }

    public ItemStack getPlotSurvivalItem() {
        return plotSurvivalItem;
    }

    public ItemStack getHomesItem() {
        return homesItem;
    }

	public ItemStack getRoleSelectionItem() {
		return roleSelectionItem;
	}

}
