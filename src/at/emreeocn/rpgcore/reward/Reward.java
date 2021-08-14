package at.emreeocn.rpgcore.reward;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import at.emreeocn.rpgcore.coin.Money;
import at.emreeocn.rpgcore.skill.Skill;

public class Reward {

	private static final int maxMobCoins = 10;
	
	private ArrayList<ItemStack> items;
	private ArrayList<String> texts;
	private int coins, skillPoints;
	
	public Reward(ArrayList<ItemStack> items, int coins, int skillPoints, ArrayList<String> texts) {
		if(items == null) items = new ArrayList<ItemStack>();
		else this.items = items;
		
		if(texts == null) texts = new ArrayList<String>();
		else this.texts = texts;
		
		this.coins = coins;
		this.skillPoints = skillPoints;
	}
	
	public void reward(Player player) {
		if(items != null) {
			for(ItemStack item : items) {
				RewardManager.addItemStackToInventory(player, item);
			}
		}
		Money.addMoney(player.getUniqueId(), player.getDisplayName(), coins);
		Skill.add(Skill.POINTS, player, skillPoints);
	}
	
	public void setCoins(int coins) {
		this.coins = coins;
	}
	
	public void setSkillPoints(int skillPoints) {
		this.skillPoints = skillPoints;
	}
	
	public void addItem(ItemStack item) {
		items.add(item);
	}
	
	public int getCoins() { return coins; }
	public ArrayList<ItemStack> getItems() { return items; }

	public int getSkillPoints() {
		return skillPoints;
	}

	public ArrayList<String> getTexts() {
		return texts;
	}

	public void setTexts(ArrayList<String> texts) {
		this.texts = texts;
	}

	public static int getMaxMobCoins() {
		return maxMobCoins;
	}
	
}
