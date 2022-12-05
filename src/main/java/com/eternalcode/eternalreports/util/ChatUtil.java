package com.eternalcode.eternalreports.util;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String legacyFormat(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
