package com.eternalcode.reports.command;

import com.eternalcode.reports.configuration.ConfigurationManager;
import com.eternalcode.reports.configuration.MessagesConfiguration;
import com.eternalcode.reports.configuration.PluginConfiguration;
import com.eternalcode.reports.creator.DiscordWebHookCreator;
import com.eternalcode.reports.notification.NotificationManager;
import com.eternalcode.reports.statistics.StatisticsConfiguration;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.route.Route;
import org.bukkit.Server;
import org.bukkit.entity.Player;

@Route(name = "report", aliases = {"zglos"})
public class ReportCommand {
    private StatisticsConfiguration statisticsConfiguration;

    private MessagesConfiguration messages;
    private NotificationManager notificationManager;

    private DiscordWebHookCreator discordWebHookCreator;

    private PluginConfiguration pluginConfiguration;
    private ConfigurationManager configurationManager;

    private Server server;

    public ReportCommand(
            StatisticsConfiguration statisticsConfiguration,
            MessagesConfiguration messages,
            NotificationManager notificationManager,
            PluginConfiguration pluginConfiguration,
            ConfigurationManager configurationManager,
            Server server
    ) {
        this.statisticsConfiguration = statisticsConfiguration;
        this.messages = messages;
        this.discordWebHookCreator = new DiscordWebHookCreator(pluginConfiguration);
        this.notificationManager = notificationManager;
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

        this.statisticsConfiguration.addReport();
        this.configurationManager.save(this.statisticsConfiguration);
    }

}
