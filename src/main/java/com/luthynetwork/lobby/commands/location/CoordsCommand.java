package com.luthynetwork.lobby.commands;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.lobby.utils.LocationSerializer;
import com.luthynetwork.core.commands.VoidCommand;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.context.Context;
import com.luthynetwork.core.commands.settings.Executor;
import lombok.val;

public class CoordsCommand extends VoidCommand {

    @Command(name = "coords", permission = "lobby.coords", executor = Executor.PLAYER_ONLY)
    public void command(Context context) {
        val player = context.player();

        Lobby.instance().getConfig().set("void.teleport", LocationSerializer.serialize(player.getLocation()));
        Message.withPrefix(player, "§aLocalização do spawn configurada com sucesso!");
    }
}
