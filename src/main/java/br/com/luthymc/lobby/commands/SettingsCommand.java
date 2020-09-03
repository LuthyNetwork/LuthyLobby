package br.com.luthymc.lobby.commands;

import br.com.luthymc.lobby.Lobby;
import br.com.luthymc.lobby.utils.settings.Settings;
import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.annotation.command.Command;
import com.ianlibanio.voidcommand.annotation.subcommand.SubCommand;
import com.ianlibanio.voidcommand.context.Context;
import com.ianlibanio.voidcommand.settings.Executor;
import org.bukkit.entity.Player;

public class SettingsCommand extends VoidCommand {

    @Command(name = "settings", executor = Executor.PLAYER_ONLY)
    public void command(Context context) {
        context.player().sendMessage("§cPlease, use /settings <list> for the list of settings you can change.");
    }

    @SubCommand(name = "list")
    public void list(Context context) {
        context.player().sendMessage("§7You can change the following settings: §ealerts §7(§e/settings alerts§7) §7and §echat §7(§e/settings chat§7).");
    }

    @SubCommand(name = "alerts")
    public void alerts(Context context) {
        Player player = context.player();
        String[] args = context.args();

        Settings settings = Lobby.getPlayerSettings(player.getUniqueId());

        if (args.length < 2) {
            String actual = settings.alerts() ? "§atrue" :"§cfalse";

            player.sendMessage("§7Actually your setting '§ealerts§7' is set to '" + actual + "§7'.");
            return;
        }

        boolean activate;
        try {
            activate = Boolean.parseBoolean(args[1]);
        } catch (Exception ex) {
            player.sendMessage("§cPlease, use /settings alerts true/false.");
            return;
        }

        String change = activate ? "§atrue" :"§cfalse";
        player.sendMessage("§7Your setting '§ealerts§7' has been set to '" + change + "§7'.");

        settings.alerts(activate);
    }

    @SubCommand(name = "chat")
    public void chat(Context context) {
        Player player = context.player();
        String[] args = context.args();

        Settings settings = Lobby.getPlayerSettings(player.getUniqueId());

        if (args.length < 2) {
            String actual = settings.chat() ? "§atrue" :"§cfalse";

            player.sendMessage("§7Actually your setting '§echat§7' is set to '" + actual + "§7'.");
            return;
        }

        boolean activate;
        try {
            activate = Boolean.parseBoolean(args[1]);
        } catch (Exception ex) {
            player.sendMessage("§cPlease, use /settings chat true/false.");
            return;
        }

        String change = activate ? "§atrue" :"§cfalse";
        player.sendMessage("§7Your setting '§echat§7' has been set to '" + change + "§7'.");

        settings.chat(activate);
    }

}
