package com.luthynetwork.lobby.commands;

import com.luthynetwork.lobby.listeners.ChatListener;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.commands.VoidCommand;
import com.luthynetwork.core.commands.annotation.command.Aliases;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.context.Context;
import org.bukkit.Bukkit;

public class UnlockCommand extends VoidCommand {

    @Command(name = "unlock", permission = "lobby.chat.unlock")
    @Aliases({"ativar", "destrancar"})
    public void command(Context context) {
        if (ChatListener.isActivated()) {
            Message.withPrefix(context.sender(), "§cO chat já está ativado, você pode usar /lock para desativar o chat.");
            return;
        }

        Bukkit.broadcastMessage(Message.withPrefix("§bO chat foi ativado por §l" + context.sender().getName() + "§b."));
        ChatListener.lock(true);
    }

}
