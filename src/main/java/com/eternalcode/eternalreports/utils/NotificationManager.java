package com.eternalcode.eternalreports.utils;

import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.UUID;

public class NotificationManager {
    private final AudienceProvider audienceProvider;
    private final MiniMessage miniMessage;

    public NotificationManager(AudienceProvider audienceProvider, MiniMessage miniMessage) {
        this.audienceProvider = audienceProvider;
        this.miniMessage = miniMessage;
    }

    public void announceMessage(UUID uniqueId, String message) {
        this.audienceProvider.player(uniqueId).sendMessage(this.miniMessage.deserialize(message));
    }
}
