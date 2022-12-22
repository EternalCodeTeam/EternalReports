package com.eternalcode.reports;

import com.eternalcode.reports.command.ReportCommand;
import com.eternalcode.reports.command.handler.InvalidUsage;
import com.eternalcode.reports.configuration.ConfigurationManager;
import com.eternalcode.reports.configuration.MessagesConfiguration;
import com.eternalcode.reports.configuration.PluginConfiguration;
import com.eternalcode.reports.creator.DiscordWebHookCreator;
import com.eternalcode.reports.legacy.LegacyColorProcessor;
import com.eternalcode.reports.notification.NotificationManager;
import com.eternalcode.reports.statistics.StatisticsConfiguration;
import com.eternalcode.reports.statistics.StatisticsManager;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.adventure.platform.LiteBukkitAdventurePlatformFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class EternalReports extends JavaPlugin {

    private AudienceProvider audiences;
    private MiniMessage miniMessage;

    private NotificationManager notificationManager;
    private StatisticsManager statisticsManager;

    private ConfigurationManager configurationManager;
    private MessagesConfiguration messagesConfiguration;
    private PluginConfiguration pluginConfiguration;
    private StatisticsConfiguration statisticsConfiguration;

    private LiteCommands<CommandSender> liteCommands;

    @Override
    public void onEnable() {
        this.getLogger().info("Enabling EternalReports...");
        Stopwatch started = Stopwatch.createStarted();
        Server server = this.getServer();

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.messagesConfiguration = new MessagesConfiguration();
        this.pluginConfiguration = this.configurationManager.load(new PluginConfiguration());

        this.audiences = BukkitAudiences.create(this);
        this.miniMessage = MiniMessage.builder()
            .postProcessor(new LegacyColorProcessor())
            .tags(TagResolver.standard())
            .build();

        this.notificationManager = new NotificationManager(this.audiences, this.miniMessage);

        this.statisticsConfiguration = this.configurationManager.load(new StatisticsConfiguration());
        this.statisticsManager = new StatisticsManager(this.configurationManager, this.statisticsConfiguration);

        this.liteCommands = LiteBukkitAdventurePlatformFactory.builder(this.getServer(), "eternal-reports", this.audiences, this.miniMessage)
            .argument(Player.class, new BukkitPlayerArgument<>(this.getServer(), this.miniMessage.deserialize(this.messagesConfiguration.userMessages.userNotFound)))
            .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>(this.miniMessage.deserialize(this.messagesConfiguration.userMessages.onlyUserCommand)))
            .invalidUsageHandler(new InvalidUsage(this.notificationManager, this.messagesConfiguration))
            .commandInstance(new ReportCommand(this.statisticsManager, this.statisticsConfiguration, this.messagesConfiguration, this.notificationManager, new DiscordWebHookCreator(this.pluginConfiguration), this.pluginConfiguration, this.configurationManager, server))
            .register();

        this.enableMetrics();
        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("EternalReports loaded in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        if (this.liteCommands.getPlatform() != null) {
            this.liteCommands.getPlatform().unregisterAll();
        }

        if (this.audiences != null) {
            this.audiences.close();
        }
    }

    private void enableMetrics() {
        Metrics metrics = new Metrics(this, 16483);
        metrics.addCustomChart(new SingleLineChart("users_reported_globally", () -> this.statisticsConfiguration.reports));
    }

    public ConfigurationManager getConfigurationManager() {
        return this.configurationManager;
    }

    public MessagesConfiguration getMessagesConfiguration() {
        return this.messagesConfiguration;
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public AudienceProvider getAudiences() {
        return this.audiences;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public NotificationManager getNotificationManager() {
        return this.notificationManager;
    }

    public StatisticsConfiguration getStatisticsConfiguration() {
        return this.statisticsConfiguration;
    }

    public StatisticsManager getStatisticsManager() {
        return this.statisticsManager;
    }

    public LiteCommands<CommandSender> getLiteCommands() {
        return this.liteCommands;
    }
}
