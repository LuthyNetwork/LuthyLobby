package com.luthynetwork.lobby.commands.chat;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.commands.VoidCommand;
import com.luthynetwork.core.commands.annotation.command.Aliases;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.context.Context;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ClearCommand extends VoidCommand {

    @Command(name = "clearchat", permission = "lobby.chat.clear")
    @Aliases({ "cc", "chatclear", "limparchat", "lc" })
    public void command(Context context) {
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }

        if (context.sender() instanceof Player) {
            val metaData = Lobby.luckPerms().getUserManager().getUser(context.player().getUniqueId()).getCachedData().getMetaData();
            val color = ChatColor.translateAlternateColorCodes('&', metaData.getPrefix().substring(0, 2));

            Bukkit.broadcastMessage(Message.withPrefix(color + context.sender().getName() + " §7limpou o chat!"));
            return;
        }

        Bukkit.broadcastMessage(Message.withPrefix(context.sender().getName() + " §7limpou o chat!"));
    }
}
