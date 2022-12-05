package com.eternalcode.eternalreports.message;

import com.eternalcode.eternalreports.configuration.ReloadableConfig;
import net.dzikoysk.cdn.entity.Contextual;
import net.dzikoysk.cdn.entity.Description;
import net.dzikoysk.cdn.source.Resource;
import net.dzikoysk.cdn.source.Source;

import java.io.File;

public class GlobalMessages implements ReloadableConfig {
	@Description({
			"#",
			"# This is a main messages file for EternalReports",
			"#",
			"# If you need help with the configuration or have any questions related to EternalReports, join us in our Discord",
			"#",
			"# Discord: https://dc.eternalcode.pl/",
			"# Website: https://eternalcode.pl/",
			"# Source Code: https://github.com/EternalCodeTeam/EternalReports",
			"#"
	})
	public UserMessages userMessages = new UserMessages();

	@Contextual
	public static class UserMessages {
		public String invalidUsage = "&c&lWrong usage of the command correct usage is {USAGE}";
		public String invalidUsageMultipleMethods = "<gray>Wrong usage, please use one of these methods:</gray>";
		public String userNotFound = "<red>User was not found</red>";
		public String onlyUserCommand = "<red>This command is only for User, console was disabled</red>";
		public String reportSend = "<red>Report was sent!</red>";
		public String canNotReportYourself = "<red> U can not report urself </red>";

		@Description({
				"#",
				"# A content of the message sended to administrators",
				"#",
				"# Placeholders -> ",
				"# {REPORTED_BY} - Report sender name",
				"# {USER} - Reported player",
				"# {REASON} - Reason of the report",
				"#"
		})
		public String reportForAdministrator = "<red>User {REPORTED_BY} report {USER} for reason {REASON}";
	}


	@Override
	public Resource resource(final File folder) {
		return Source.of(folder, "messages.yml");
	}
}
