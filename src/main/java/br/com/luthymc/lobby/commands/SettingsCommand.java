package br.com.luthymc.lobby.commands;

import br.com.luthymc.lobby.Lobby;
import br.com.luthymc.lobby.utils.settings.Settings;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Aliases;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.annotation.subcommand.SubCommand;
import com.ianlibanio.voidcommand.context.Context;
import com.ianlibanio.voidcommand.settings.Executor;
import org.bukkit.entity.Player;

public class SettingsCommand extends VoidCommand {

    @Command(name = "settings", executor = Executor.PLAYER_ONLY)
    @Aliases({"config", "configuracoes"})
    public void command(Context context) {
        context.player().sendMessage("§cPor favor, use /config <list> para uma lista de configurações que você pode alterar.");
    }

    @SubCommand(name = "list")
    public void list(Context context) {
        context.player().sendMessage("§7Você pode alterar as seguintes configurações: §ealertas §7(§e/config alertas§7) §7e §echat §7(§e/config chat§7).");
    }

    @SubCommand(name = "alerts")
    public void alerts(Context context) {
        Player player = context.player();
        String[] args = context.args();

        Settings settings = Lobby.getPlayerSettings(player.getUniqueId());

        if (args.length < 2) {
            String actual = settings.alertas() ? "§aativada" : "§cdesativada";

            player.sendMessage("§7Atualmente a sua configuração '§ealertas§7' está '" + actual + "§7'.");
            return;
        }

        boolean activate;

        switch (args[1]) {
            case "ativar":
                activate = true;
                break;
            case "desativar":
                activate = false;
                break;
            default:
                player.sendMessage("§cPor favor, use /config alertas ativar/desativar.");
                return;
        }

        String change = activate ? "§aativada" : "§cdesativada";
        player.sendMessage("§7A sua configuração '§ealertas§7' agora está '" + change + "§7'.");

        settings.alertas(activate);
    }

    @SubCommand(name = "chat")
    public void chat(Context context) {
        Player player = context.player();
        String[] args = context.args();

        Settings settings = Lobby.getPlayerSettings(player.getUniqueId());

        if (args.length < 2) {
            String actual = settings.chat() ? "§aativada" : "§cdesativada";

            player.sendMessage("§7Atualmente a sua configuração '§echat§7' está '" + actual + "§7'.");
            return;
        }

        boolean activate;

        switch (args[1]) {
            case "ativar":
                activate = true;
                break;
            case "desativar":
                activate = false;
                break;
            default:
                player.sendMessage("§cPor favor, use /config alertas ativar/desativar.");
                return;
        }

        String change = activate ? "§aativada" : "§cdesativada";
        player.sendMessage("§7A sua configuração '§echat§7' agora está '" + change + "§7'.");

        settings.chat(activate);
    }

}
