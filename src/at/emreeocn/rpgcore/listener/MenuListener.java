package at.emreeocn.rpgcore.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.collection.CollectionMenu;
import at.emreeocn.rpgcore.enderchest.Enderchest;
import at.emreeocn.rpgcore.enderchest.EnderchestMenu;
import at.emreeocn.rpgcore.events.TaskFinishedEvent;
import at.emreeocn.rpgcore.group.Group;
import at.emreeocn.rpgcore.group.GroupManager;
import at.emreeocn.rpgcore.group.GroupMenu;
import at.emreeocn.rpgcore.home.HomeManager;
import at.emreeocn.rpgcore.home.HomeMenu;
import at.emreeocn.rpgcore.playerinfo.PlayerInfoMenu;
import at.emreeocn.rpgcore.plotsurvive.PlotSurviveManager;
import at.emreeocn.rpgcore.reward.RewardManager;
import at.emreeocn.rpgcore.reward.RewardMenu;
import at.emreeocn.rpgcore.rpg.RPG;
import at.emreeocn.rpgcore.rpg.RPGMenu;
import at.emreeocn.rpgcore.shop.AdminShop;
import at.emreeocn.rpgcore.skill.Skill;
import at.emreeocn.rpgcore.skill.SkillsMenu;
import at.emreeocn.rpgcore.task.Task;
import at.emreeocn.rpgcore.task.TaskMenu;
import at.emreeocn.rpgcore.util.Config;

public class MenuListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player player = (Player) e.getWhoClicked();
			
			if(e.getCurrentItem() != null) {	
				String title = e.getView().getTitle();
				
				// RPG
				if(title.equals(RPGMenu.getInventoryTitle(player))) {
					e.setCancelled(true);
					switch(e.getCurrentItem().getType()) {
					default:
						break;
					case BARRIER:
						player.closeInventory();
						break;
					case IRON_CHESTPLATE:
						SkillsMenu skillsMenu = new SkillsMenu(player);
						player.openInventory(skillsMenu.getGui());
						break;
					case NETHER_STAR:
						Config.teleportToSpawn(player);
						break;
					case CHEST:
						RewardMenu rewardMenu = new RewardMenu(player, title);
						player.openInventory(rewardMenu.getGui());
						break;
					case ENDER_CHEST:
						EnderchestMenu ec = new EnderchestMenu(player, title);
						player.openInventory(ec.getGui());
						break;
					case IRON_AXE:
						Config.teleportToCitybuild(player);
						break;
					case BOOKSHELF:
						TaskMenu taskMenu = new TaskMenu(player);
						player.openInventory(taskMenu.getGui());
						break;
					case OAK_SAPLING:
						Config.teleportToFarmworld(player);
						break;
					case NETHER_BRICK:
						Config.teleportToNether(player);
						break;
					case END_STONE:
						Config.teleportToEnd(player);
						break;
					case MAP:
						RPGMenu.sendInformationMessage(player);
						player.closeInventory();
						break;
					case IRON_SWORD:
						PlotSurviveManager.onGuiClick(player);
						break;
					case WHITE_BED:
						new HomeMenu(player).display();
						break;
					case HOPPER:
						player.openInventory(Bukkit.createInventory(null, 9 * 6, "§9Trash"));
						break;
					case PLAYER_HEAD:
						if(!GroupManager.isInGroup(player)) new Group(player);
						new GroupMenu(player).display(player);
						break;
					}
				}
				
				// SKILLS
				if(title.equals(SkillsMenu.getInventoryTitle(player))) {
					e.setCancelled(true);
					ItemStack item = e.getCurrentItem();
					SkillsMenu skillsMenu = new SkillsMenu(player);
					RPGMenu rpgMenu = new RPGMenu(player);
					
					Material material = e.getCurrentItem().getType();
					
					if(item.getType() == SkillsMenu.getBackItem().getType()) {
						player.openInventory(rpgMenu.getGui());
						return;
					}
					
					if(e.getCurrentItem().getType() == skillsMenu.getResetItem().getType()) {
						Skill.reset(player);
						Skill.updatePlayer(player);
						player.closeInventory();
						player.sendMessage(Config.getPrefix() + "§aDeine Stats wurden zurückgesetzt");
					}
					
					if(material == Material.GREEN_TERRACOTTA || material == Material.RED_TERRACOTTA) {
						Skill.setStatus(player, !Skill.getStatus().get(player));
						player.openInventory(new SkillsMenu(player).getGui());
						Skill.updatePlayer(player);
					}
					
					if(Skill.getSkill(e.getCurrentItem().getType()) != null) {
						if(e.getCurrentItem().getType() != Skill.POINTS.getClickMaterial()) {
							if(Skill.get(Skill.POINTS, player) >= 1) {
								Skill.add(Skill.getSkill(e.getCurrentItem().getType()), player, 1);
								Skill.add(Skill.POINTS, player, -1);
								skillsMenu = new SkillsMenu(player);
								player.openInventory(skillsMenu.getGui());
								Bukkit.getPluginManager().callEvent(new TaskFinishedEvent(player, Task.USE_SKILLPOINT));
								Skill.updatePlayer(player);
								
							} else {
								player.closeInventory();
								player.sendMessage(Config.getPrefix() + "§cNicht genug Skill-Punkte!");
							}
						}
					}
					
				}
				
				// COLLECTION
				if(title.equals(CollectionMenu.getInventoryTitle(player))) {
					e.setCancelled(true);
					ItemStack item = e.getCurrentItem();
					RPGMenu rpgMenu = new RPGMenu(player);
					
					if(item.getType() == SkillsMenu.getBackItem().getType()) {
						player.openInventory(rpgMenu.getGui());
						return;
					}
				}
				
				// TASK
				if(title.equals(TaskMenu.getInventoryTitle(player))) {
					e.setCancelled(true);
					ItemStack item = e.getCurrentItem();
					TaskMenu taskMenu = new TaskMenu(player);
					RPGMenu rpgMenu = new RPGMenu(player);
					
					if(item.getType() == taskMenu.getBackItem().getType()) {
						player.openInventory(rpgMenu.getGui());
						return;
					}
				}
				
				// REWARD
				if(title.equals(RewardMenu.getInventoryTitle(player))) {
					e.setCancelled(true);
					RewardMenu rewardMenu = new RewardMenu(player, title);
					if(e.getClickedInventory().getType() == InventoryType.PLAYER) {
						e.setCancelled(true);
						return;
					}
					if(player.getInventory().firstEmpty() != -1) {
						player.getInventory().addItem(e.getCurrentItem());
						Inventory temp = RewardMenu.copyInventory(rewardMenu.getGui(), title);
						temp.clear(e.getSlot());
						RewardManager.saveInventory(player, temp);
						player.closeInventory();
					}
				}
				
				// ADMINSHOP
				if(title.equalsIgnoreCase(AdminShop.getInventoryTitle(player))) {
					e.setCancelled(true);
					AdminShop adminShop = new AdminShop(player);
					player.openInventory(adminShop.getGui());
					
					if(e.getCurrentItem().isSimilar(RPGMenu.getNull())) return;
					if(player.getInventory().firstEmpty() == -1) return;
					if(e.getClickedInventory().getType() == InventoryType.PLAYER) return;
					
					ItemStack item = e.getCurrentItem();
					
					Money.buy(player, AdminShop.getItemPrice(item.getType()), AdminShop.processItem(item));
					player.sendMessage(Config.getPrefix() + "§c- §6" + AdminShop.getItemPrice(item.getType()) + " Coins");
				}
				
				// PLAYERINFO
				if(title.equalsIgnoreCase(PlayerInfoMenu.getInventoryTitle(player))) {
					e.setCancelled(true);
				}
				
				if(e.getCurrentItem().isSimilar(RPG.getMenuItem())) {
					e.setCancelled(true);
					if(e.isRightClick()) {
						RPGMenu menu = new RPGMenu(player);
						player.openInventory(menu.getGui());
						player.playSound(player.getLocation(), Sound.BLOCK_CHEST_OPEN, 8, 1);
					}
				}
				
				// HOMES AND PLOTS
				if(title.equalsIgnoreCase(HomeMenu.getInventoryTitle())) {
					e.setCancelled(true);
					if(!e.getCurrentItem().isSimilar(RPGMenu.getNull())) {
						if(e.getCurrentItem().getType() == Material.ARROW) {
							player.openInventory(new RPGMenu(player).getGui());
							
						} else if(e.getCurrentItem().getType() == HomeMenu.getPlotMaterial()) {
							/* PLOT */
							String s = e.getCurrentItem().getItemMeta().getDisplayName().substring(2);
							Bukkit.dispatchCommand(player, "p h " + s);
							
						} else {
							/* HOME */
							ItemStack clicked = e.getCurrentItem();
							String home = clicked.getItemMeta().getDisplayName().substring(2);
							player.teleport(HomeManager.get(player, home));
						}
					}
				}
				
				// GROUP
				if(title.equalsIgnoreCase(GroupMenu.getInventoryTitle())) {
					e.setCancelled(true);
					
					if(e.getCurrentItem().isSimilar(GroupMenu.getLeaveItem())) {
						if(GroupManager.isInGroup(player)) {
							GroupManager.getGroup(player).leave(player);
						} else {
							player.sendMessage(Config.getPrefix() + "§4Fehler: §cDu bist in keiner Gruppe");
						}
						player.closeInventory();
					}
					
					if(e.getCurrentItem().isSimilar(GroupMenu.getReturnItem())) {
						player.openInventory(new RPGMenu(player).getGui());
					}
					
					if(e.getCurrentItem().getType() == Material.PLAYER_HEAD) {
						if(e.isRightClick()) {
							Player target = GroupManager.getPlayerByMemberItem(e.getCurrentItem());
							if(target != player) {
								if(GroupManager.isInGroup(player)) {
									if(GroupManager.isGroupLeader(player)) {
										GroupManager.getGroup(player).kick(player);
										
									} else {
										player.sendMessage("§4Fehler: §cDu bist nicht der Gruppenleiter");
									}
									
								} else {
									player.sendMessage("§4Fehler: §cDu bist in keiner Gruppe");
								}
								
							} else {
								player.sendMessage("§4Fehler: §cDu kannst dich nicht selbst kicken");
							}
							
							player.closeInventory();
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(e.getPlayer() instanceof Player) {
			Player player = (Player) e.getPlayer();
			
			if(e.getView().getTitle().equals(EnderchestMenu.getInventoryTitle(player))) {
				Enderchest.saveInventory(player, e.getInventory());
			}
		}
	}
	
}
