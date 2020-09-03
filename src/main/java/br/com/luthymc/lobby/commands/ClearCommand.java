package br.com.luthymc.lobby.commands;

import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Aliases;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.context.Context;
import org.bukkit.Bukkit;

public class ClearCommand extends VoidCommand {

    @Command(name = "clearchat")
    @Aliases({ "cc", "chatclear", "limparchat", "lc" })
    public void command(Context context) {
        for (int i = 0; i < 100; i++) {
            Bukkit.broadcastMessage("");
        }
        Bukkit.broadcastMessage("§e" + context.sender().getName() + "§7 limpou o chat!");
    }
}
