package com.eternalcode.reports.configuration.implementation;

import com.eternalcode.reports.configuration.ReloadableConfig;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

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
    public UserMessages userMessages = new UserMessages();
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

    @Override
    public Resource resource(File folder) {
        return Source.of(folder, "config.yml");
    }

    @Contextual
    public static class UserMessages {

        public String invalidUsage = "&cWrong usage of the command correct usage is {USAGE}";
        public String invalidUsageMultipleMethods = "<gray>Wrong usage, please use one of these methods:</gray>";
        public String userNotFound = "<red>User was not found</red>";
        public String onlyUserCommand = "<red>This command is only for User, console was disabled</red>";
        public String reportSend = "<red>Report was sent!</red>";
        public String canNotReportYourself = "<red> U can not report yourself </red>";

        @Description({
            "#",
            "# A content of the message sent to administrators",
            "#",
            "# Placeholders -> ",
            "# {REPORTED_BY} - Report sender name",
            "# {USER} - Reported player",
            "# {REASON} - Reason of the report",
            "#"
        })
        public String reportForAdministrator = "<red>User {REPORTED_BY} report {USER} for reason {REASON}";
    }
}
