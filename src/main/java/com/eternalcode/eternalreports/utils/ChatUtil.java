package com.eternalcode.eternalreports.utils;

import com.eternalcode.eternalreports.EternalReports;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;

public class ChatUtil {
    public static String legacyFormat(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
