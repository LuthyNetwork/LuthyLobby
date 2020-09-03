package br.com.luthymc.lobby.commands;

import br.com.luthymc.lobby.listeners.ChatListener;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Aliases;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import org.bukkit.Bukkit;

public class UnlockCommand extends VoidCommand {

    @Command(name = "unlock", permission = "lobby.chat.unlock")
    @Aliases({"ativar", "destrancar"})
    public void command(Context context) {
        if (ChatListener.isActivated()) {
            context.sender().sendMessage("§cO chat já está ativado, você pode usar /lock para desativar o chat.");
            return;
        }

        Bukkit.broadcastMessage("§bO chat foi ativado por §l" + context.sender().getName() + "§b.");
        ChatListener.lock(true);
    }

}
