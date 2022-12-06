package com.eternalcode.reports.util;

import com.eternalcode.reports.configuration.PluginConfiguration;
import de.raik.webhook.WebhookBuilder;
import de.raik.webhook.elements.Embed;
import de.raik.webhook.elements.embedelements.AuthorElement;
import de.raik.webhook.elements.embedelements.ImageElement;
import org.bukkit.entity.Player;

public class DiscordWebHookCreator {

    private PluginConfiguration pluginConfiguration;

    public DiscordWebHookCreator(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    public void sendMessage(Player reporter, Player reported, String reason) {
        WebhookBuilder webhookBuilder = new WebhookBuilder(this.pluginConfiguration.discordSettings.webhookUrl);

        Embed embed = new Embed();
        embed.description(
            String.join("\n", this.pluginConfiguration.discordSettings.messageContent)
                .replace("{USER}", reported.getName())
                .replace("{REASON}", reason)
                .replace("{REPORTED_BY}", reporter.getName())
        );

        embed.title(this.pluginConfiguration.discordSettings.authorName);

        embed.author(
            new AuthorElement(reporter.getName())
                .icon("https://mc-heads.net/avatar/" + reporter.getUniqueId())
        );

        embed.thumbnail(
            new ImageElement("https://mc-heads.net/avatar/" + reported.getUniqueId())
        );

        embed.color(this.pluginConfiguration.discordSettings.color);
        webhookBuilder.username(this.pluginConfiguration.discordSettings.authorName);
        webhookBuilder.avatar(this.pluginConfiguration.discordSettings.profileImage);
        webhookBuilder.addEmbed(embed);
        webhookBuilder
            .build()
            .execute();
    }
}
