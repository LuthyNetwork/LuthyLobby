package br.com.luthymc.lobby.listeners;

import br.com.luthymc.lobby.Lobby;
import br.com.luthymc.lobby.inventory.CompassMenu;
import br.com.luthymc.lobby.utils.items.Item;
import br.com.luthymc.lobby.utils.settings.Settings;
import br.com.luthymc.lobby.utils.time.MasterCooldown;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class PlayerListeners implements Listener {

    private final MasterCooldown cooldown = Lobby.getCooldown();
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        val player = event.getPlayer();
        val uuid = player.getUniqueId();

        Settings settings = Lobby.getPlayerSettings(uuid);

        if (settings == null) Lobby.defaultSettings(uuid);

        event.setJoinMessage(null);

        player.getInventory().clear();
        player.getInventory().setItem(3, new Item(Material.COMPASS).name("&7Server Compass").lore("", "&7Click here to join a server of our network.").build());
        player.getInventory().setItem(5, new Item(Material.FIREWORK).name("&7Kangaroo").lore("", "&7Have fun while waiting for join the server.").build());
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
    }

    @EventHandler
    public void onInteractAtCompass(PlayerInteractEvent event) {
        val player = event.getPlayer();
        val stack = player.getItemInHand();

        if (stack == null || stack.getType() == Material.AIR || stack.getItemMeta().getDisplayName() == null || stack.getItemMeta().getLore() == null)
            return;
        val meta = stack.getItemMeta();

        if (stack.getType() == Material.COMPASS && meta.getDisplayName().equals("§7Server Compass")) {
            event.setCancelled(true);
            CompassMenu.INVENTORY.open(player);
        }

        if (player.getItemInHand().getType() == Material.FIREWORK && meta.getDisplayName().equals("§7Kangaroo")) {
            if (!(event.getAction() == Action.PHYSICAL)) event.setCancelled(true);

            if (cooldown.secondsLeft(player.getUniqueId().toString(), "kangaroo") == 0) {
                if (!(player.isSneaking())) {
                    player.setFallDistance(- (4F + 1));
                    org.bukkit.util.Vector vector = player.getEyeLocation().getDirection();
                    vector.multiply(0.6F);
                    vector.setY(1.2F);
                    player.setVelocity(vector);
                } else {
                    player.setFallDistance(-(4F + 1));
                    Vector vector = player.getEyeLocation().getDirection();
                    vector.multiply(1.2F);
                    vector.setY(0.8);
                    player.setVelocity(vector);
                }
                cooldown.add(player.getUniqueId().toString(), "kangaroo", 6, TimeUnit.SECONDS);
            } else {
                if (Lobby.getPlayerSettings(player.getUniqueId()).alerts()) {
                    player.sendMessage("§cYou must wait " + cooldown.timeLeft(player.getUniqueId().toString(), "kangaroo") + " or hit the ground before using the kangaroo again.");
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (cooldown.contains(player.getUniqueId().toString(), "kangaroo")) {
            Block block = player.getLocation().getBlock();

            if (block.getType() != Material.AIR || block.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                cooldown.remove(player.getUniqueId().toString(), "kangaroo");
            }
        }
    }
    
}
