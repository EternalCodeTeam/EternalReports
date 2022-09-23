package com.eternalcode.eternalreports.configuration;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PluginConfiguration implements ReloadableConfig {
    @Description({
            "#",
            "# This is a main configuration file for EternalReports",
            "#",
            "# If you need help with the configuration or have any questions related to EternalReports, join us in our Discord",
            "#",
            "# Discord: https://dc.eternalcode.pl/",
            "# Website: https://eternalcode.pl/",
            "# Source Code: https://github.com/EternalCodeTeam/EternalReports",
            "#"
    })
    public Discord discordSettings = new Discord();


    @Contextual
    public static class Discord {
        @Description({
                "# Discord message configuration", "# If You want to disable this feature, set enabled to false"
        })
        public boolean enabled = true;
        public String webhookUrl = "https://discord.com";
        public String profileImage = "";
        public String authorName = "";
        public String color = "#fff";
        public List<String> messageContent = Arrays.asList("Gracz {PLAYER} zgłosił gracza {TARGET}", "Powód {REASON}");
    }


    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }
}
