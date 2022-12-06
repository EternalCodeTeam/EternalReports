package com.eternalcode.reports.command.administrator;

import com.eternalcode.reports.configuration.ConfigurationManager;
import com.eternalcode.reports.message.ConfigService;
import com.eternalcode.reports.util.NotificationManager;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "er-reload", aliases = { "eternalreports-reload" })
public class ReloadConfiguration {
    private ConfigurationManager configurationManager;
    private ConfigService messagesManager;
    private NotificationManager notificationManager;

    public ReloadConfiguration(ConfigurationManager configurationManager, ConfigService messagesManager, NotificationManager notificationManager) {
        this.configurationManager = configurationManager;
        this.messagesManager = messagesManager;
        this.notificationManager = notificationManager;
    }

    @Permission("eternalcode.report.reload")
    @Execute()
    public void execute(Player player) {
        this.configurationManager.reload();
        this.messagesManager.reload();
        this.notificationManager.announceMessage(player.getUniqueId(), "<b><dark_red>[ EternalReports ]</dark_red> <white>-></white> <green>Configuration and messages reloaded!</green></b>");
    }
}
