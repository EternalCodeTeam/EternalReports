package com.eternalcode.eternalreports;

import com.eternalcode.eternalreports.commands.arguments.PlayerArgument;
import com.eternalcode.eternalreports.commands.arguments.contextual.PlayerContextualArgument;
import com.eternalcode.eternalreports.commands.ReportCommand;
import com.eternalcode.eternalreports.configuration.ConfigurationManager;
import com.eternalcode.eternalreports.configuration.PluginConfiguration;
import com.google.common.base.Stopwatch;
import dev.rollczi.litecommands.LiteCommands;
import dev.rollczi.litecommands.bukkit.LiteBukkitFactory;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.TimeUnit;

public class EternalReports extends JavaPlugin {
    private LiteCommands<CommandSender> liteCommands;
    public static EternalReports eternalReports;
    private ConfigurationManager configurationManager;
    private PluginConfiguration pluginConfiguration;
    @Override
    public void onEnable() {
        this.getLogger().info("Enabling EternalReports...");
        Stopwatch started = Stopwatch.createStarted();

        eternalReports = this;
        this.configurationManager = new ConfigurationManager(this.getDataFolder());
        this.pluginConfiguration = this.configurationManager.load(new PluginConfiguration());

        this.liteCommands = LiteBukkitFactory.builder(this.getServer(), "eternal-reports")
                .argument(Player.class, new PlayerArgument<>(this.getServer(), "&c&lTaki gracz nie jest dostępny na serwerze"))
                .contextualBind(Player.class, new PlayerContextualArgument<>("&c&lTo polecenie może wykonać tylko gracz!"))
                .command(
                        ReportCommand.class
                )
                .register();

        Metrics metrics = new Metrics(this, 16483);
        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        this.getLogger().info("EternalReports loaded in " + millis + "ms");
    }

    @Override
    public void onDisable() {
        this.liteCommands.getPlatform().unregisterAll();
    }

    public PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    public EternalReports getEternalReports() {
        return eternalReports;
    }
}
