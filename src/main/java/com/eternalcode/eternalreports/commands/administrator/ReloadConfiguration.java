package com.eternalcode.eternalreports.commands.administrator;

import com.eternalcode.eternalreports.configuration.ConfigurationManager;
import com.eternalcode.eternalreports.configuration.PluginConfiguration;
import com.eternalcode.eternalreports.messages.GlobalMessages;
import com.eternalcode.eternalreports.messages.MessagesManager;
import com.eternalcode.eternalreports.utils.NotificationManager;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "er-reload", aliases = {"eternalreports-reload"})
public class ReloadConfiguration {
    private ConfigurationManager configurationManager;
    private MessagesManager messagesManager;
    private NotificationManager notificationManager;

    public ReloadConfiguration(ConfigurationManager configurationManager, MessagesManager messagesManager, NotificationManager notificationManager) {
        this.configurationManager = configurationManager;
        this.messagesManager = messagesManager;
        this.notificationManager = notificationManager;
    }

    @Permission("eternalcode.report.reloadConfiguration")
    @Execute()
    public void execute(Player sender) {
        this.configurationManager.reload();
        this.messagesManager.reload();
        this.notificationManager.announceMessage(sender.getUniqueId(), "<b><dark_red>[ EternalReports ]</dark_red> <white>-></white> <green>Configuration and messages reloaded!</green></b>");
    }
}
