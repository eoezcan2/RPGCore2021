package at.emreeocn.rpgcore.role;

import org.bukkit.inventory.ItemStack;

public enum Role {

	ADVENTURER("Abenteurer", 0, RoleManager.getAdventurerItem()),
	TANK("Tank", 1, RoleManager.getTankItem()),
	DAMAGEDEALER("Schaden", 3, RoleManager.getDamagerItem()),
	HEALER("Healer", 1, RoleManager.getHealerItem());
	
	String displayName;
	int groupMaxAmount;
	ItemStack clickItem;
	
	Role(String displayName, int groupMaxAmount, ItemStack clickItem) {
		this.displayName = displayName;
		this.groupMaxAmount = groupMaxAmount;
		this.clickItem = clickItem;
	}
	
	public String getDisplayName() { return displayName; }
	public int getGroupMaxAmount() { return groupMaxAmount; }
	public ItemStack getClickMaterial() { return clickItem; }
	
	public static Role getRoleByName(String name) {
		for(Role r : Role.values()) {
			if(r.name().equals(name)) return r;
		}
		return null;
	}
	
}
