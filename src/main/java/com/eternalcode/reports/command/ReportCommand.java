package com.eternalcode.reports.command;

import com.eternalcode.reports.configuration.ConfigurationManager;
import com.eternalcode.reports.configuration.MessagesConfiguration;
import com.eternalcode.reports.configuration.PluginConfiguration;
import com.eternalcode.reports.creator.DiscordWebHookCreator;
import com.eternalcode.reports.notification.NotificationManager;
import com.eternalcode.reports.statistics.StatisticsConfiguration;
import com.eternalcode.reports.statistics.StatisticsManager;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Route(name = "report", aliases = {"zglos"})
public class ReportCommand {
    private final StatisticsManager statisticsManager;
    private final StatisticsConfiguration statisticsConfiguration;

    private final MessagesConfiguration messages;
    private final NotificationManager notificationManager;

    private final DiscordWebHookCreator discordWebHookCreator;

    private final PluginConfiguration pluginConfiguration;
    private final ConfigurationManager configurationManager;

    private final Server server;

    public ReportCommand(StatisticsManager statisticsManager, StatisticsConfiguration statisticsConfiguration, MessagesConfiguration messages, NotificationManager notificationManager, DiscordWebHookCreator discordWebHookCreator, PluginConfiguration pluginConfiguration, ConfigurationManager configurationManager, Server server) {
        this.statisticsManager = statisticsManager;
        this.statisticsConfiguration = statisticsConfiguration;
        this.messages = messages;
        this.notificationManager = notificationManager;
        this.discordWebHookCreator = discordWebHookCreator;
        this.pluginConfiguration = pluginConfiguration;
        this.configurationManager = configurationManager;
        this.server = server;
    }

    @Execute(min = 2)
    public void reportPlayer(Player sender, @Arg @Name("target") Player target, @Joiner @Name("message") String message) {
        if (sender.getUniqueId() == target.getUniqueId()) {
            this.notificationManager.announceMessage(sender.getUniqueId(), this.messages.userMessages.canNotReportYourself);
            return;
        }

        this.server.getOnlinePlayers()
            .stream()
            .filter(player -> player.hasPermission("eternalcode.report.recieve"))
            .forEach(player -> {
                this.notificationManager.announceMessage(
                    player.getUniqueId(),
                    this.messages.userMessages.reportForAdministrator
                        .replace("{USER}", target.getName())
                        .replace("{REASON}", message)
                        .replace("{REPORTED_BY}", sender.getName())
                );
            });

        this.notificationManager.announceMessage(sender.getUniqueId(), this.messages.userMessages.reportSend);
        if (this.pluginConfiguration.discordSettings.enabled) {
            this.discordWebHookCreator.sendMessage(sender, target, message);
        }

        this.statisticsManager.addReport();
        this.configurationManager.save(this.statisticsConfiguration);
    }

}
