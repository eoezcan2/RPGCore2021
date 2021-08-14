package at.emreeocn.rpgcore.util;

import net.md_5.bungee.api.ChatColor;

public class MenuUtil {

	public static String convertToInvisibleString(String s) {
        String hidden = "";
        for (char c : s.toCharArray()) hidden += ChatColor.COLOR_CHAR + "" + c;
        return hidden;
    }
	
}
