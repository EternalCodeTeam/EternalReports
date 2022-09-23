package com.eternalcode.eternalreports.commands.arguments.contextual;

import dev.rollczi.litecommands.command.Invocation;
import dev.rollczi.litecommands.contextual.Contextual;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.Result;

public class PlayerContextualArgument<MESSAGE> implements Contextual<CommandSender, Player> {
    private final MESSAGE onlyPlayerMessage;

    public PlayerContextualArgument(MESSAGE onlyPlayerMessage) {
        this.onlyPlayerMessage = onlyPlayerMessage;
    }

    @Override
    public Result<Player, Object> extract(CommandSender sender, Invocation<CommandSender> invocation) {
        return Option.of(sender).is(Player.class).toResult(this.onlyPlayerMessage);
    }
}
