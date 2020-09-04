package com.luthynetwork.lobby.settings;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Message {

    public static String INTERNAL_PREFIX = "§eLuthy §7» ";

    public static String withPrefix(String message) {
        return INTERNAL_PREFIX + message;
    }

    public static void withPrefix(Player player, String message) {
        player.sendMessage(withPrefix(message));
    }

    public static void withPrefix(CommandSender sender, String message) {
        sender.sendMessage(withPrefix(message));
    }

}
