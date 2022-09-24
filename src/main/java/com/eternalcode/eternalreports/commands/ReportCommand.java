package com.eternalcode.eternalreports.commands;

import com.eternalcode.eternalreports.configuration.ConfigurationManager;
import com.eternalcode.eternalreports.configuration.PluginConfiguration;
import com.eternalcode.eternalreports.data.Statistics;
import com.eternalcode.eternalreports.messages.GlobalMessages;
import com.eternalcode.eternalreports.utils.DiscordUtil;
import com.eternalcode.eternalreports.utils.NotificationManager;
import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Section(route = "report", aliases = {"zglos"})
public class ReportCommand {
    private Statistics statistics;
    private GlobalMessages messages;
    private NotificationManager notificationManager;
    private DiscordUtil discordUtil;
    private PluginConfiguration pluginConfiguration;
    private ConfigurationManager configurationManager;
    public ReportCommand(
            Statistics statistics,
            GlobalMessages messages,
            NotificationManager notificationManager,
            PluginConfiguration pluginConfiguration,
            ConfigurationManager configurationManager
    ) {
        this.statistics = statistics;
        this.messages = messages;
        this.discordUtil = new DiscordUtil(pluginConfiguration);
        this.notificationManager = notificationManager;
        this.pluginConfiguration = pluginConfiguration;
        this.configurationManager = configurationManager;
    }

    @Execute(min = 2)
    public void reportPlayer(Player sender, @Arg @Name("target") Player target, @Joiner @Name("message") String message) {
        if(sender.getUniqueId() == target.getUniqueId())
        {
            this.notificationManager.announceMessage(sender.getUniqueId(), this.messages.userMessages.canNotReportYourself);
            return;
        }

        Bukkit
                .getOnlinePlayers()
                .stream()
                .filter(p -> p.hasPermission("eternalcode.report.recieve"))
                .forEach(p -> {
                    this.notificationManager.announceMessage(
                            p.getUniqueId(),
                            this.messages.userMessages.reportForAdministrator
                                    .replace("{USER}", target.getName())
                                    .replace("{REASON}", message)
                                    .replace("{REPORTED_BY}", sender.getName())
                    );
                });

        this.notificationManager.announceMessage(sender.getUniqueId(), this.messages.userMessages.reportSend);
        if(this.pluginConfiguration.discordSettings.enabled)
            this.discordUtil.sendMessage(sender, target, message);

        this.statistics.addReport();
        this.configurationManager.save(this.statistics);
    }

}
