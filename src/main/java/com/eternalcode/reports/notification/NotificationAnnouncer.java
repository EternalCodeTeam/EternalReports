package com.eternalcode.reports.notification;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NotificationAnnouncer {
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public NotificationAnnouncer(AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    public void announceMessage(UUID uniqueId, String message) {
        this.audienceProvider.player(uniqueId).sendMessage(this.miniMessage.deserialize(message));
    }

    public void announceMessage(CommandSender sender, String message) {
        this.audience(sender).sendMessage(this.miniMessage.deserialize(message));
    }

    private Audience audience(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            return this.audienceProvider.player(player.getUniqueId());
        }

        return this.audienceProvider.console();
    }

}
