package com.luthynetwork.lobby.commands;

import com.luthynetwork.lobby.listeners.ChatListener;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.commands.VoidCommand;
import com.luthynetwork.core.commands.annotation.command.Aliases;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.context.Context;
import org.bukkit.Bukkit;

public class LockCommand extends VoidCommand {

    @Command(name = "lock", permission = "lobby.chat.lock")
    @Aliases({"desativar", "trancar"})
    public void command(Context context) {
        if (!ChatListener.isActivated()) {
            Message.withPrefix(context.sender(), "§cO chat já está desativado, você pode usar /unlock para ativar o chat.");
            return;
        }

        Bukkit.broadcastMessage(Message.withPrefix("§cO chat foi desativado por §l" + context.sender().getName() + "§c."));
        ChatListener.lock(true);
    }
}
