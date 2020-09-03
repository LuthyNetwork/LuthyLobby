package br.com.luthymc.lobby.commands;

import br.com.luthymc.lobby.listeners.ChatListener;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import org.bukkit.Bukkit;

public class UnlockCommand extends VoidCommand {

    @Command(name = "unlock", permission = "lobby.chat.unlock")
    public void command(Context context) {
        if (ChatListener.isActivated()) {
            context.sender().sendMessage("§cThe chat is already unlocked, you can use /unlock to unlock the chat.");
            return;
        }

        Bukkit.broadcastMessage("§bChat has been unlocked by §l" + context.sender().getName() + "§b.");
        ChatListener.lock(false);
    }

}
