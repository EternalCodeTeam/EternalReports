package com.eternalcode.eternalreports;

import com.eternalcode.eternalreports.command.ReportCommand;
import com.eternalcode.eternalreports.command.administrator.ReloadConfiguration;
import com.eternalcode.eternalreports.command.handlers.InvalidUsageHandler;
import com.eternalcode.eternalreports.configuration.ConfigurationManager;
import com.eternalcode.eternalreports.configuration.PluginConfiguration;
import com.eternalcode.eternalreports.data.Statistics;
import com.eternalcode.eternalreports.message.GlobalMessages;
import com.eternalcode.eternalreports.message.MessagesManager;
import com.eternalcode.eternalreports.util.NotificationManager;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.adventure.platform.LiteBukkitAdventurePlatformFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.bukkit.tools.BukkitPlayerArgument;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SingleLineChart;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class EternalReports extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;
    public static EternalReports eternalReports;
    private ConfigurationManager configurationManager;
    private MessagesManager messagesManager;
    private PluginConfiguration pluginConfiguration;
    private NotificationManager notificationManager;
    private GlobalMessages messages;
    private MiniMessage miniMessage;
    private BukkitAudiences audiences;
    private Statistics statistics;

    @Override
    public void onEnable() {
        this.getLogger().info("Enabling EternalReports...");
        Stopwatch started = Stopwatch.createStarted();

        eternalReports = this;
        this.miniMessage = MiniMessage
                .builder()
                .tags(TagResolver.standard())
                .build();

        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        File messagesDir = new File(this.getDataFolder() + "/messages");
        if (!messagesDir.exists()) {
            if (!messagesDir.mkdir()) {
                this.getLogger().info("Error with creating messages directory");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }

        File dataDirectory = new File(this.getDataFolder() + "/data");
        if (!dataDirectory.exists()) {
            if (!dataDirectory.mkdir()) {
                this.getLogger().info("Error with creating data directory");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }

        this.messagesManager = new MessagesManager(messagesDir);
        this.pluginConfiguration = this.configurationManager.load(new PluginConfiguration());
        this.messages = this.messagesManager.load(new GlobalMessages());
        this.statistics = this.configurationManager.load(new Statistics());
        this.audiences = BukkitAudiences.create(this);
        this.notificationManager = new NotificationManager(this.audiences, this.miniMessage);

        this.liteCommands = LiteBukkitAdventurePlatformFactory
                .builder(
                        this.getServer(),
                        "eternal-reports",
                        this.audiences,
                        this.miniMessage
                )
                .argument(
                        Player.class,
                        new BukkitPlayerArgument<>(
                                this.getServer(),
                                this.miniMessage.deserialize(this.messages.userMessages.userNotFound)
                        )
                )
                .contextualBind(
                        Player.class,
                        new BukkitOnlyPlayerContextual<>(
                                this.miniMessage.deserialize(this.messages.userMessages.onlyUserCommand)
                        )
                )
                .invalidUsageHandler(
                        new InvalidUsageHandler(this.notificationManager, this.messages)
                )
                .commandInstance(
                        new ReportCommand(
                                this.statistics,
                                this.messages,
                                this.notificationManager,
                                this.pluginConfiguration,
                                this.configurationManager
                        )
                )
                .commandInstance(
                        new ReloadConfiguration(
                                this.configurationManager,
                                this.messagesManager,
                                this.notificationManager
                        )
                )
                .register();

        this.enableMetricts();
        final long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("EternalReports loaded in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatform().unregisterAll();
        if (this.audiences != null) {
            this.audiences.close();
            this.audiences = null;
        }
    }

    private void enableMetricts() {
        final Metrics metrics = new Metrics(this, 16483);
        metrics.addCustomChart(new SingleLineChart("users_reported_globally", () -> this.statistics.getReports()));
    }

    public PluginConfiguration getPluginConfiguration() {
        return this.pluginConfiguration;
    }

    public static EternalReports getEternalReports() {
        return eternalReports;
    }

    public MiniMessage getMiniMessage() {
        return this.miniMessage;
    }

    public BukkitAudiences getAudiences() {
        return this.audiences;
    }

    public GlobalMessages getMessages() {
        return this.messages;
    }
}
