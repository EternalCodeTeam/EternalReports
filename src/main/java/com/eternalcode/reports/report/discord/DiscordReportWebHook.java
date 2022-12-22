package com.eternalcode.reports.report.discord;

import com.eternalcode.reports.configuration.implementation.PluginConfiguration;
import de.raik.webhook.WebhookBuilder;
import de.raik.webhook.elements.Embed;
import de.raik.webhook.elements.embedelements.AuthorElement;
import de.raik.webhook.elements.embedelements.ImageElement;
import org.bukkit.entity.Player;
import panda.utilities.text.Formatter;

public class DiscordReportWebHook {

    private final PluginConfiguration pluginConfiguration;

    public DiscordReportWebHook(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration;
    }

    public void sendWebhookMessage(Player author, Player reported, String reason) {
        WebhookBuilder webhookBuilder = new WebhookBuilder(this.pluginConfiguration.webhookUrl);

        Formatter formatter = new Formatter()
            .register("{USER}", reported.getName())
            .register("{REASON}", reason)
            .register("{REPORTED_BY}", author.getName());

        String embedMessage = String.join("\n", this.pluginConfiguration.userMessages.reportForAdministrator);
        Embed embed = new Embed()
            .description(formatter.format(embedMessage))

            .title(this.pluginConfiguration.authorName)

            .author(new AuthorElement(author.getName())
                .icon("https://mc-heads.net/avatar/" + author.getUniqueId())
            )

            .thumbnail(
                new ImageElement("https://mc-heads.net/avatar/" + reported.getUniqueId())
            )

            .color(this.pluginConfiguration.color);

        webhookBuilder.username(this.pluginConfiguration.authorName);
        webhookBuilder.avatar(this.pluginConfiguration.profileImage);
        webhookBuilder.addEmbed(embed);
        webhookBuilder.build().execute();
    }
}
