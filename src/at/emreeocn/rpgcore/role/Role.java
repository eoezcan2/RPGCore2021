package at.emreeocn.rpgcore.role;

public enum Role {

	TANK("Tank", 1),
	DAMAGEDEALER("Schaden", 3),
	HEALER("Healer", 1);
	
	String displayName;
	int groupMaxAmount;
	
	Role(String displayName, int groupMaxAmount) {
		this.displayName = displayName;
		this.groupMaxAmount = groupMaxAmount;
	}
	
	public String getDisplayName() { return displayName; }
	public int getGroupMaxAmount() { return groupMaxAmount; }
	
}
