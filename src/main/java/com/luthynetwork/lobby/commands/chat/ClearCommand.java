package com.luthynetwork.lobby.commands;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.commands.VoidCommand;
import com.luthynetwork.core.commands.annotation.command.Aliases;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.context.Context;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class ClearCommand extends VoidCommand {

    @Command(name = "clearchat")
    @Aliases({ "cc", "chatclear", "limparchat", "lc" })
    public void command(Context context) {
        val metaData = Lobby.luckPerms().getUserManager().getUser(context.player().getUniqueId()).getCachedData().getMetaData();

        val color = ChatColor.translateAlternateColorCodes('&', metaData.getPrefix().substring(0, 2));

        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }
        Bukkit.broadcastMessage(Message.withPrefix(color + context.sender().getName() + " §7limpou o chat!"));
    }
}
