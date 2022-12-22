package com.eternalcode.reports.command;

import com.eternalcode.reports.configuration.implementation.PluginConfiguration;
import com.eternalcode.reports.notification.NotificationAnnouncer;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.InvalidUsageHandler;
import dev.rollczi.litecommands.schematic.Schematic;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import panda.utilities.text.Formatter;

import java.util.List;

@ApiStatus.ScheduledForRemoval(inVersion = "1.0")
public class InvalidCommandUsage implements InvalidUsageHandler<CommandSender> {

    private final NotificationAnnouncer notificationAnnouncer;
    private final PluginConfiguration pluginConfiguration;

    public InvalidCommandUsage(NotificationAnnouncer notificationAnnouncer, PluginConfiguration pluginConfiguration) {
        this.notificationAnnouncer = notificationAnnouncer;
        this.pluginConfiguration = pluginConfiguration;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, Schematic schematic) {
        List<String> schematics = schematic.getSchematics();

        Formatter formatter = new Formatter()
            .register("{USAGE}", schematics.get(0));

        if (schematics.size() == 1) {
            this.notificationAnnouncer.announceMessage(sender, formatter.format(this.pluginConfiguration.userMessages.invalidUsage));
            return;
        }

        this.notificationAnnouncer.announceMessage(sender, this.pluginConfiguration.userMessages.invalidUsageMultipleMethods);

        // TODO: add to config
        for (String schema : schematics) {
            this.notificationAnnouncer.announceMessage(sender, "&8&l >> &7" + schema);
        }
    }
}
