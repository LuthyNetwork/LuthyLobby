package br.com.luthymc.lobby.commands;

import br.com.luthymc.lobby.listeners.ChatListener;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import org.bukkit.Bukkit;

public class LockCommand extends VoidCommand {

    @Command(name = "lock", permission = "lobby.chat.lock")
    public void command(Context context) {
        if (!ChatListener.isActivated()) {
            context.sender().sendMessage("§cThe chat is already locked, you can use /unlock to unlock the chat.");
            return;
        }

        Bukkit.broadcastMessage("§cChat has been locked by §l" + context.sender().getName() + "§c.");
        ChatListener.lock(true);
    }
}
