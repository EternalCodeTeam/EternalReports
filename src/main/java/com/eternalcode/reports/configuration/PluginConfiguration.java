package com.eternalcode.reports.configuration;

import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;
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

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }

    @Contextual
    public static class Discord {

        @Description({
            "# Discord message configuration", "# If You want to disable this feature, set enabled to false"
        })
        @Description("Should the discord feature be enabled?")
        public boolean enabled = true;

        @Description("The webhook URL.")
        public String webhookUrl = "https://discord.com";

        @Description("The webhook profile image.")
        public String profileImage = "";

        @Description("The webhook author name")
        public String authorName = "";

        @Description("The webhook embed color.")
        public String color = "#fff";

        @Description({
            "#",
            "# A content of the message sended to discord",
            "#",
            "# Placeholders -> ",
            "# {REPORTED_BY} - Report sender name",
            "# {USER} - Reported player",
            "# {REASON} - Reason of the report",
            "#"
        })
        public List<String> messageContent = Arrays.asList("Gracz {REPORTED_BY} zgłosił gracza {USER}", "Powód {REASON}");
    }
}
