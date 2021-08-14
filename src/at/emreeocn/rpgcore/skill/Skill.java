package at.emreeocn.rpgcore.skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import at.emreeocn.rpgcore.main.Main;

public enum Skill {

	POINTS("POINTS", "Skill-Punkte", Material.STONE_BUTTON),
	HEALTH("HEALTH", "Vitalität", Material.RED_DYE),
	SPEED("SPEED", "Speed", Material.FEATHER);
	
	private String column;
	private String title;
	private Material clickMaterial;
	
	private static HashMap<Player, Boolean> status = new HashMap<Player, Boolean>();
	
	Skill(String column, String title, Material clickMaterial) {
		this.column = column;
		this.title = title;
		this.clickMaterial = clickMaterial;
	}

	public static int get(Skill skill, Player player) {
		ResultSet rs = null;
		int res = 0;
		
		try {
			rs = Main.getMain().prepareStatement("SELECT * FROM skills WHERE UUID = '" + player.getUniqueId().toString() + "';").executeQuery();
			rs.next();
			res = rs.getInt(skill.getColumn());
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
		
		return res;
	}
	
	public static Skill getSkill(Material clickMaterial) {
		for(Skill skill : Skill.values()) {
			if(skill.getClickMaterial() == clickMaterial) return skill;
		}
		
		return null;
	}
	
	public static void add(Skill skill, Player player, int amount) {
		try {
			Main.getMain().prepareStatement("UPDATE skills SET "
					+ "NAME = '" + player.getDisplayName() + "', "
					+ skill.getColumn() + " = " + (get(skill, player) + amount) + " WHERE UUID = '" + player.getUniqueId().toString() + "';").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void set(Skill skill, Player player, int amount) {
		try {
			Main.getMain().prepareStatement("UPDATE skills SET "
					+ "NAME = '" + player.getDisplayName() + "', "
					+ skill.getColumn() + " = " + amount + " WHERE UUID = '" + player.getUniqueId().toString() + "';").executeUpdate();
			
		} catch(SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public static void reset(Player player) {
		int points = get(Skill.HEALTH, player) + get(Skill.SPEED, player) + get(Skill.POINTS, player);
		
		set(Skill.HEALTH, player, 0);
		set(Skill.SPEED, player, 0);
		set(Skill.POINTS, player, points);
	}
	
	@SuppressWarnings("deprecation")
	public static void updatePlayer(Player player) {
		if(status.get(player) == null) status.put(player, true);
		
		if(status.get(player)) {
			player.setMaxHealth(20 + (Skill.get(Skill.HEALTH, player) / 2));
			
			float speed = ((float) Skill.get(Skill.SPEED, player) / (float) 160) + 0.2f;
			
			if(speed > 1) player.setWalkSpeed(1);
			else player.setWalkSpeed(speed);
			
		} else {
			player.setMaxHealth(20);
			player.setWalkSpeed(0.2f);
		}
	}
	
	public static void setStatus(Player player, boolean b) {
		status.put(player, b);
	}
	
	public static void initHashMap() {
		for(Player player : Bukkit.getOnlinePlayers()) {
			setStatus(player, true);
			Skill.updatePlayer(player);
		}
	}
	
	public String getTitle() {
		return title;
	}

	public String getColumn() {
		return column;
	}

	public Material getClickMaterial() {
		return clickMaterial;
	}

	public static HashMap<Player, Boolean> getStatus() {
		return status;
	}
	
}
