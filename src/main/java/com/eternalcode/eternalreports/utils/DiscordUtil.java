package com.eternalcode.eternalreports.utils;

import com.eternalcode.eternalreports.configuration.PluginConfiguration;
import de.raik.webhook.WebhookBuilder;
import de.raik.webhook.elements.Embed;
import de.raik.webhook.elements.embedelements.AuthorElement;
import de.raik.webhook.elements.embedelements.ImageElement;
import org.bukkit.entity.Player;

public class DiscordUtil {

    private PluginConfiguration pluginConfiguration;

    public DiscordUtil(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    public void sendMessage(Player sender, Player player, String reason)
    {
        WebhookBuilder webhookBuilder = new WebhookBuilder(this.pluginConfiguration.discordSettings.webhookUrl);

        Embed embed = new Embed();
        embed.description(
                String.join("\n", this.pluginConfiguration.discordSettings.messageContent)
                        .replace("{USER}", player.getName())
                        .replace("{REASON}", reason)
                        .replace("{REPORTED_BY}", sender.getName())
        );

        embed.title(this.pluginConfiguration.discordSettings.authorName);

        embed.author(
                new AuthorElement(sender.getName())
                        .icon("https://mc-heads.net/avatar/" + sender.getUniqueId())
        );

        embed.thumbnail(
                new ImageElement("https://mc-heads.net/avatar/" + player.getUniqueId())
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
