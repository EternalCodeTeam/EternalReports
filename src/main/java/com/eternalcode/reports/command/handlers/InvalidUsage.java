package com.eternalcode.reports.command.handlers;

import com.eternalcode.reports.message.GlobalMessages;
import com.eternalcode.reports.util.ChatUtil;
import com.eternalcode.reports.util.NotificationManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    private NotificationManager notificationManager;
    private GlobalMessages globalMessages;

    public InvalidUsage(NotificationManager notificationManager, GlobalMessages globalMessages) {

        this.notificationManager = notificationManager;
        this.globalMessages = globalMessages;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();
        Player player = (Player) sender;

        if (schematics.size() == 1) {
            this.notificationManager.announceMessage(
                player.getUniqueId(),
                this.globalMessages.userMessages.invalidUsage.replace("{USAGE}", schematics.get(0))
            );
            return;
        }

        this.notificationManager.announceMessage(player.getUniqueId(), this.globalMessages.userMessages.invalidUsageMultipleMethods);

        for (String schema : schematics) {
            sender.sendMessage(ChatUtil.legacyFormat("&8&l >> &7" + schema));
        }
    }
}