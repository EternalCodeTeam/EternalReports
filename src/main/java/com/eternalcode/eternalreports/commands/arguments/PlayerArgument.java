package com.eternalcode.eternalreports.commands.arguments;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import org.bukkit.Server;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.stream.Collectors;

@ArgumentName("player")
public class PlayerArgument<MESSAGE> implements OneArgument<Player> {
    private final Server server;
    private final MESSAGE playerNotFoundMessage;

    public PlayerArgument(Server server, MESSAGE playerNotFoundMessage) {
        this.server = server;
        this.playerNotFoundMessage = playerNotFoundMessage;
    }

    @Override
    public Result<Player, Object> parse(LiteInvocation invocation, String argument) {
        return Option.of(this.server.getPlayer(argument)).toResult(this.playerNotFoundMessage);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return server.getOnlinePlayers()
                .stream()
                .map(HumanEntity::getName)
                .map(Suggestion::of)
                .collect(Collectors.toList());
    }
}
