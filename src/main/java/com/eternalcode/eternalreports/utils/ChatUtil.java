package com.eternalcode.eternalreports.utils;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String legacyFormat(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
