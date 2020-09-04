package com.luthynetwork.lobby.listeners.chat;

import com.google.common.collect.Lists;
import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.login.LuthyLogin;
import lombok.Getter;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class ChatListener implements Listener {

    List<String> loginCommands = Lists.newArrayList( "/register", "/registrar", "/login", "/logar");

    @Getter private static boolean activated = true;

    public static void lock(boolean lock) {
        activated = !lock;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        val player = event.getPlayer();
        val loginService = LuthyLogin.getService();

        if (loginCommands.stream().filter(x -> event.getMessage().startsWith(x)).findFirst().orElse(null) == null) {
            if (!loginService.isLogged(player.getUniqueId())) {
                event.setCancelled(true);

                Message.withPrefix(player, "Você deve se autenticar antes de usar comandos.");
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        val player = event.getPlayer();
        val loginService = LuthyLogin.getService();

        event.setCancelled(true);

        if (!loginService.isLogged(player.getUniqueId())) {
            Message.withPrefix(player, "Você deve se autenticar antes de digitar no chat.");
            return;
        }

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
