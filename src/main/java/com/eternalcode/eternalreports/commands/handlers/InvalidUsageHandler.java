package com.eternalcode.eternalreports.commands.handlers;

import com.eternalcode.eternalreports.utils.ChatUtil;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidUsageHandler implements dev.rollczi.litecommands.handle.InvalidUsageHandler<CommandSender> {
    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        if(schematics.size() == 1) {
            sender.sendMessage(ChatUtil.legacyFormat("&c&lPoprawne użycie polecenia &8&l>> &7" + schematics.get(0)));
            return;
        }

        sender.sendMessage(ChatUtil.legacyFormat("&c&lNiepoprawne użycie polecenia!"));
        for(String schema : schematics) {
            sender.sendMessage(ChatUtil.legacyFormat( "&8&l >> &7" + schema));
        }
    }
}
