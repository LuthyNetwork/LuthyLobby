package br.com.luthymc.lobby.scoreboard;

import br.com.luthymc.lobby.Lobby;
import com.google.common.collect.Lists;
import com.luthynetwork.core.libs.scoreboard.provider.CommonScoreboard;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class LobbyScoreboard implements CommonScoreboard {

    @Override
    public String getTitle(Player player) {
        TitleScroller.pass();
        return TitleScroller.get();
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = Lists.newArrayList();

        val metaData = Lobby.luckPerms().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData();

        if (metaData.getPrefix() == null) {
            lines.add(" §a");
            lines.add(" §7Olá " + player.getName() + ", seu status");
            lines.add(" §b");
            lines.add(" §7Grupo: " + "§7§lMEMBRO");
            lines.add(" §7Coins: §e§lSOON");
            lines.add(" §c");
            lines.add(" §7Lobby: §a#1");
            lines.add(" §6Online: §f" + Bukkit.getOnlinePlayers().size());
            lines.add(" §d");
            lines.add("§eluthynetwork.com");

            return lines;
        }

        val prefix = metaData.getPrefix();
        val color = ChatColor.translateAlternateColorCodes('&', prefix.substring(0, 2));

        lines.add(" §a");
        lines.add(" §7Olá " + color + player.getName() + "§7, seu status");
        lines.add(" §b");
        lines.add(" §7Grupo: " + color + ChatColor.BOLD + prefix.substring(2).toUpperCase());
        lines.add(" §7Coins: §e§lSOON");
        lines.add(" §c");
        lines.add(" §7Lobby: §a#1");
        lines.add(" §7Online: §f" + Bukkit.getOnlinePlayers().size());
        lines.add(" §d");
        lines.add("§eluthynetwork.com");

        return lines;
    }
}
