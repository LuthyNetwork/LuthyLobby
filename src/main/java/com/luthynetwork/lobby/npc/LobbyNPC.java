package com.luthynetwork.lobby.npc;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.login.events.PlayerLoginEvent;
import lombok.Getter;
import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.events.NPCInteractEvent;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.state.NPCSlot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

@Getter
public abstract class LobbyNPC implements Listener {

    private NPC npc;

    private final JavaPlugin plugin;
    private final String[] text;
    private final Location location;
    private final ItemStack itemStack;
    private final int skinId;

    public LobbyNPC(JavaPlugin plugin, Location location, ItemStack itemStack, int skinId, String... text) {
        this.plugin = plugin;
        this.text = text;
        this.location = location;
        this.skinId = skinId;
        this.itemStack = itemStack;
    }

    public void build(NPCLib lib) {
        MineSkinFetcher.fetchSkinFromIdAsync(skinId, skin -> {
            npc = lib.createNPC(Arrays.asList(text));
            npc.setLocation(location);
            npc.setItem(NPCSlot.MAINHAND, itemStack);
            npc.setSkin(skin);
            npc.create();
        });

        Bukkit.getPluginManager().registerEvents(this, plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().size() != 0) npc.setText(update(npc));
            }
        }.runTaskTimer(Lobby.instance(), 20L * 5, 20L * 5);
    }

    @EventHandler
    public void onInteract(NPCInteractEvent event) {
        if (event.getNPC().getId().equals(this.npc.getId())) {
            interact(event);
        }
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        npc.show(event.getPlayer());
    }

    public abstract void interact(NPCInteractEvent event);
    public abstract List<String> update(NPC npc);

}
