package com.luthynetwork.lobby.listeners;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private static boolean activated = true;

    public static boolean isActivated() {
        return activated;
    }

    public static void lock(boolean lock) {
        activated = !lock;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        val player = event.getPlayer();

        if (!Lobby.getPlayerSettings(player.getUniqueId()).chat()) {
            Message.withPrefix(player, "§cVocê não pode enviar mensagens enquanto o seu chat está desativado.");
            return;
        }

        if (!activated && !player.hasPermission("lobby.bypass")) {
            Message.withPrefix(player, "§cO chat está bloqueado.");
            return;
        }

        val MEMBER_FORMAT = "§7" + player.getDisplayName() + " » " + event.getMessage();
        val metaData = Lobby.luckPerms().getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData();

        if (metaData.getPrefix() == null) {
            send(MEMBER_FORMAT);
            return;
        }

        val prefix = metaData.getPrefix();
        val color = ChatColor.translateAlternateColorCodes('&', prefix.substring(0, 2));

        if (prefix.contains("Membro")) {
            send(MEMBER_FORMAT);
        } else {
            send(" ", color + ChatColor.BOLD + prefix.substring(2).toUpperCase() + " " + color + player.getName() + " §f» " + event.getMessage(), " ");
        }
    }

    private void send(String... message) {
        Bukkit.getOnlinePlayers().forEach(online -> {
            if (Lobby.getPlayerSettings(online.getUniqueId()).chat()) for (String m : message) online.sendMessage(m);
        });
    }

}
