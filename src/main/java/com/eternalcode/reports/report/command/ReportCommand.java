package com.eternalcode.reports.report.command;

import com.eternalcode.reports.configuration.ConfigurationManager;
import com.eternalcode.reports.configuration.implementation.PluginConfiguration;
import com.eternalcode.reports.notification.NotificationAnnouncer;
import com.eternalcode.reports.report.ReportAnnouncer;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.entity.Player;

@Route(name = "report", aliases = {"zglos"})
public class ReportCommand {

    private final PluginConfiguration pluginConfiguration;
    private final NotificationAnnouncer notificationAnnouncer;
    private final ConfigurationManager configurationManager;
    private final ReportAnnouncer reportAnnouncer;

    public ReportCommand(PluginConfiguration pluginConfiguration, NotificationAnnouncer notificationAnnouncer, ConfigurationManager configurationManager, ReportAnnouncer reportAnnouncer) {
        this.pluginConfiguration = pluginConfiguration;
        this.notificationAnnouncer = notificationAnnouncer;
        this.configurationManager = configurationManager;
        this.reportAnnouncer = reportAnnouncer;
    }

    @Execute(min = 2)
    public void reportPlayer(Player author, @Arg @Name("target") Player target, @Joiner @Name("message") String message) {
        if (author.getUniqueId() == target.getUniqueId()) {
            this.notificationAnnouncer.announceMessage(author.getUniqueId(), this.pluginConfiguration.userMessages.canNotReportYourself);
            return;
        }

        this.reportAnnouncer.sendReport(author, target, message, target.getName());
    }

    @Execute(route = "admin reload")
    @Permission("eternalreports.reload")
    public void execute(Player player) {
        this.configurationManager.reload();
        this.notificationAnnouncer.announceMessage(player.getUniqueId(), "<dark_red>[ EternalReports ]</dark_red> <dark_gray>»</dark_gray> <green>Configuration and messages reloaded!</green>");
    }

}
