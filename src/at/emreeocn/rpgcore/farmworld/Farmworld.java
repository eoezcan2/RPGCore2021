package at.emreeocn.rpgcore.farmworld;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import at.emreeocn.rpgcore.util.Config;

public enum Farmworld {
	
	NORMAL(Config.getFarmworld(), "Farmwelt", Material.GRASS_BLOCK),
	NETHER(Config.getNether(), "Nether", Material.NETHER_BRICK),
	END(Config.getEnd(), "End", Material.END_STONE);
	
	private Location location;
	private String displayName;
	private Material clickMaterial;
	
	private static Material[] ores = { 
			Material.COAL_ORE, 
			Material.DIAMOND_ORE, 
			Material.EMERALD_ORE, 
			Material.GOLD_ORE, 
			Material.IRON_ORE, 
			Material.LAPIS_ORE };
	
	Farmworld(Location location, String displayName, Material clickMaterial) {
		this.location = location;
		this.displayName = displayName;
		this.clickMaterial = clickMaterial;
	}
	
	public static boolean isProtected(Location location, Farmworld type) {
		int radius = 50;
		
		Location world = type.getSpawn();
		
		Location loc1 = new Location(world.getWorld(), world.getX() - radius, world.getY() - radius, world.getZ() - radius);
		Location loc2 = new Location(world.getWorld(), world.getX() + radius, world.getY() + radius, world.getZ() + radius);
		if(inArea(location, loc1, loc2)) {
			return true;
			
		} else return false;
	}
	
	public static Farmworld getType(Location location) {
		for(Farmworld f : Farmworld.values()) {
			if(f.getSpawn().getWorld() == location.getWorld()) return f;
		}
		
		return null;
	}
	
	public static boolean contains(World world) {
		for(Farmworld f : Farmworld.values()) {
			if(f.getSpawn().getWorld() == world) return true;
		}
		
		return false;
	}
	
	public static boolean inArea(Location targetLocation, Location inAreaLocation1, Location inAreaLocation2){
	    if(inAreaLocation1.getWorld().getName() == inAreaLocation2.getWorld().getName()){
	        if(targetLocation.getWorld().getName() == inAreaLocation1.getWorld().getName()){
	            if((targetLocation.getBlockX() >= inAreaLocation1.getBlockX() && targetLocation.getBlockX() <= inAreaLocation2.getBlockX()) || (targetLocation.getBlockX() <= inAreaLocation1.getBlockX() && targetLocation.getBlockX() >= inAreaLocation2.getBlockX())){
	                if((targetLocation.getBlockZ() >= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() <= inAreaLocation2.getBlockZ()) || (targetLocation.getBlockZ() <= inAreaLocation1.getBlockZ() && targetLocation.getBlockZ() >= inAreaLocation2.getBlockZ())){
	                    return true;
	                }
	            }
	        }
	    }
	    return false;
	}
	
	public static boolean isProtectedAnyworld(Location loc) {
		for(Farmworld f : Farmworld.values()) {
			if(isProtected(loc, f)) return true;
		}
		
		return false;
	}
	
	public static boolean isOre(Block block) {
		for(int i = 0; i < ores.length; i++) {
			if(ores[i] == block.getType()) return true;
		}
		return false;
	}
	
	public Location getSpawn() { return location; }

	public String getDisplayName() {
		return displayName;
	}

	public Material getClickMaterial() {
		return clickMaterial;
	}

	public static Material[] getOres() {
		return ores;
	}
	
}
