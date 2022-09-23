package com.eternalcode.eternalreports.commands;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.argument.Name;
import dev.rollczi.litecommands.argument.joiner.Joiner;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.section.Section;
import org.bukkit.entity.Player;

@Section(route = "report", aliases = {"zglos"})
public class ReportCommand {

    @Execute(min = 2)
    public void reportPlayer(Player sender, @Arg @Name("target") Player target, @Joiner String message) {


    }

}
