package com.luthynetwork.lobby.commands;

import com.luthynetwork.core.commands.VoidCommand;
import com.luthynetwork.core.commands.annotation.command.Aliases;
import com.luthynetwork.core.commands.annotation.command.Command;
import com.luthynetwork.core.commands.annotation.subcommand.SubCommand;
import com.luthynetwork.core.commands.context.Context;
import com.luthynetwork.core.commands.settings.Executor;
import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.lobby.settings.Settings;
import org.bukkit.entity.Player;

public class SettingsCommand extends VoidCommand {

    @Command(name = "settings", executor = Executor.PLAYER_ONLY, invalid = { "§eLuthy §7» §cPor favor, use /config <list> para uma lista de configurações que você pode alterar."} )
    @Aliases({"config", "configuracoes"})
    public void command(Context context) {
        Message.withPrefix(context.player(), "§cPor favor, use /config <list> para uma lista de configurações que você pode alterar.");
    }

    @SubCommand(name = "list")
    public void list(Context context) {
        Message.withPrefix(context.player(), "§7Você pode alterar as seguintes configurações: §ealertas §7(§e/config alertas§7) §7e §echat §7(§e/config chat§7).");
    }

    @SubCommand(name = "alertas")
    public void alertas(Context context) {
        Player player = context.player();
        String[] args = context.args();

        Settings settings = Lobby.getPlayerSettings(player.getUniqueId());

        if (args.length < 2) {
            String actual = settings.alertas() ? "§aativada" : "§cdesativada";

            Message.withPrefix(player, "§7Atualmente a sua configuração '§ealertas§7' está '" + actual + "§7'.");
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
                Message.withPrefix(player, "§cPor favor, use /config alertas ativar/desativar.");
                return;
        }

        String change = activate ? "§aativada" : "§cdesativada";
        Message.withPrefix(player, "§7A sua configuração '§ealertas§7' agora está '" + change + "§7'.");

        settings.alertas(activate);
    }

    @SubCommand(name = "chat")
    public void chat(Context context) {
        Player player = context.player();
        String[] args = context.args();

        Settings settings = Lobby.getPlayerSettings(player.getUniqueId());

        if (args.length < 2) {
            String actual = settings.chat() ? "§aativada" : "§cdesativada";

            Message.withPrefix(player, "§7Atualmente a sua configuração '§echat§7' está '" + actual + "§7'.");
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
                Message.withPrefix(player, "§cPor favor, use /config chat ativar/desativar.");
                return;
        }

        String change = activate ? "§aativada" : "§cdesativada";
        Message.withPrefix(player, "§7A sua configuração '§echat§7' agora está '" + change + "§7'.");

        settings.chat(activate);
    }

}
