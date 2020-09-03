package com.luthynetwork.lobby.listeners;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.inventory.CompassMenu;
import com.luthynetwork.lobby.inventory.ProfileMenu;
import com.luthynetwork.lobby.settings.Settings;
import com.luthynetwork.core.libs.item.HeadBuilder;
import com.luthynetwork.core.libs.item.Item;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        val player = event.getPlayer();
        val uuid = player.getUniqueId();

        player.setGameMode(GameMode.ADVENTURE);
        player.setMaxHealth(2.0D);
        player.setHealth(2.0D);

        Settings settings = Lobby.getPlayerSettings(uuid);

        if (settings == null) Lobby.defaultSettings(uuid);

        event.setJoinMessage(null);

        player.getInventory().clear();

        player.getInventory().setItem(1, new Item(new HeadBuilder().owner(player.getName()).create()).name("§ePerfil").build());
        player.getInventory().setItem(4, new Item(Material.COMPASS).name("§aServidores").build());
        player.getInventory().setItem(7, new Item(Material.STAINED_GLASS_PANE, 1, 5).name("§fJogadores: §a§lHAB").build());

        player.updateInventory();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (!event.getPlayer().hasPermission("lobby.bypass")) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (!event.getPlayer().hasPermission("lobby.bypass")) event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (!event.getPlayer().hasPermission("lobby.bypass")) event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) event.getEntity().teleport(Lobby.spawnLocation());
    }

    @EventHandler
    public void onDamage(FoodLevelChangeEvent event) {
        if (event.getFoodLevel() != 20) event.setFoodLevel(20);
    }

    @EventHandler
    public void onInteractAtCompass(PlayerInteractEvent event) {
        val player = event.getPlayer();
        val stack = player.getItemInHand();

        if (stack == null || stack.getType() == Material.AIR || stack.getItemMeta().getDisplayName() == null)
            return;
        val meta = stack.getItemMeta();

        if (stack.getType() == Material.COMPASS && meta.getDisplayName().equals("§aServidores")) {
            event.setCancelled(true);
            CompassMenu.INVENTORY.open(player);
        }

        if (stack.getType() == Material.SKULL_ITEM && meta.getDisplayName().equals("§ePerfil")) {
            event.setCancelled(true);
            ProfileMenu.INVENTORY.open(player);
        }

        if (stack.getType() == Material.STAINED_GLASS_PANE && meta.getDisplayName().equals("§fJogadores: §a§lHAB")) {
            event.setCancelled(true);
            ItemStack disabled = new Item(Material.STAINED_GLASS_PANE, 1, 14).name("§fJogadores: §c§lDES").build();

            player.getInventory().setItem(7, disabled);

            Bukkit.getOnlinePlayers().forEach(online -> {
                if (online != player && !online.hasPermission("luthy.util.showplayer")) online.hidePlayer(player);
            });
        }

        if (stack.getType() == Material.STAINED_GLASS_PANE && meta.getDisplayName().equals("§fJogadores: §c§lDES")) {
            event.setCancelled(true);
            ItemStack enabled = new Item(Material.STAINED_GLASS_PANE, 1, 5).name("§fJogadores: §a§lHAB").build();

            player.getInventory().setItem(7, enabled);

            Bukkit.getOnlinePlayers().forEach(online -> {
                if (online != player && !online.hasPermission("luthy.util.showplayer")) online.showPlayer(player);
            });
        }
    }

}
