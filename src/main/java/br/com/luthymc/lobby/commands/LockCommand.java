package br.com.luthymc.lobby.commands;

import br.com.luthymc.lobby.listeners.ChatListener;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Aliases;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import org.bukkit.Bukkit;

public class LockCommand extends VoidCommand {

    @Command(name = "lock", permission = "lobby.chat.lock")
    @Aliases({"desativar", "trancar"})
    public void command(Context context) {
        if (!ChatListener.isActivated()) {
            context.sender().sendMessage("§cO chat já está desativado, você pode usar /unlock para ativar o chat.");
            return;
        }

        Bukkit.broadcastMessage("§cO chat foi desativado por §l" + context.sender().getName() + "§c.");
        ChatListener.lock(true);
    }
}
