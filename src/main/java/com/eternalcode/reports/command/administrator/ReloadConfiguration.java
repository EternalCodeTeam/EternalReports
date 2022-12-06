package com.eternalcode.reports.command.administrator;

import com.eternalcode.reports.configuration.ConfigurationManager;
import com.eternalcode.reports.configuration.MessagesConfiguration;
import com.eternalcode.reports.notification.NotificationManager;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "er-reload", aliases = {"eternalreports-reload"})
public class ReloadConfiguration {

    private final ConfigurationManager configurationManager;
    private final ConfigService messagesManager;
    private final NotificationManager notificationManager;

    public ReloadConfiguration(ConfigurationManager configurationManager, MessagesConfiguration messagesManager, NotificationManager notificationManager) {
        this.configurationManager = configurationManager;
        this.messagesManager = messagesManager;
        this.notificationManager = notificationManager;
    }
    
    @Execute
    @Permission("eternalcode.report.reload")
    public void execute(Player player) {
        this.configurationManager.reload();
        this.messagesManager.reload();
        this.notificationManager.announceMessage(player.getUniqueId(), "<b><dark_red>[ EternalReports ]</dark_red> <white>-></white> <green>Configuration and messages reloaded!</green></b>");
    }
}
