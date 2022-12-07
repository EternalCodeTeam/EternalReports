package com.eternalcode.reports.command.handler;

import com.eternalcode.reports.configuration.MessagesConfiguration;
import com.eternalcode.reports.notification.NotificationManager;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;

import java.util.List;

public class InvalidUsage implements InvalidUsageHandler<CommandSender> {

    private final NotificationManager notificationManager;
    private final MessagesConfiguration messagesConfiguration;

    public InvalidUsage(NotificationManager notificationManager, MessagesConfiguration messagesConfiguration) {
        this.notificationManager = notificationManager;
        this.messagesConfiguration = messagesConfiguration;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        if (schematics.size() == 1) {
            this.notificationManager.announceMessage(
                    sender,
                    this.messagesConfiguration.userMessages.invalidUsage.replace("{USAGE}", schematics.get(0))
            );
            return;
        }

        this.notificationManager.announceMessage(sender, this.messagesConfiguration.userMessages.invalidUsageMultipleMethods);

        // TODO dodac do configu
        for (String schema : schematics) {
            this.notificationManager.announceMessage(sender, "&8&l >> &7" + schema);
        }
    }
}
