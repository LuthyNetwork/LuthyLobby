package br.com.luthymc.lobby.listeners;

import br.com.luthymc.lobby.Lobby;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
        Player player = event.getPlayer();

        event.setCancelled(true);

        if (!activated && !player.hasPermission("lobby.bypass")) {
            player.sendMessage("§cThe chat is locked.");
            return;
        }

        if (!Lobby.getPlayerSettings(player.getUniqueId()).chat()) {
            player.sendMessage("§cYou can't send messages when you have the chat disabled.");
            return;
        }

        String FORMAT = "§7" + player.getDisplayName() + " » " + event.getMessage();

        Bukkit.getOnlinePlayers().forEach(online -> {
            if (Lobby.getPlayerSettings(online.getUniqueId()).chat()) online.sendMessage(FORMAT);
        });
    }

}
