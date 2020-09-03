package br.com.luthymc.lobby.npc;

import br.com.luthymc.lobby.Lobby;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.jitse.npclib.NPCLib;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.events.NPCInteractEvent;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.state.NPCSlot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

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
        for (int i = 0; i < text.length; i++) {
            text[i] = ChatColor.translateAlternateColorCodes('&', text[i]);
        }

        MineSkinFetcher.fetchSkinFromIdAsync(skinId, skin -> {
            npc = lib.createNPC(Arrays.asList(text));
            npc.setLocation(location);
            npc.setItem(NPCSlot.MAINHAND, itemStack);
            npc.setSkin(skin);
            npc.create();
        });

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(NPCInteractEvent event) {
        if (event.getNPC().getId().equals(this.npc.getId())) {
            interact(event);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        npc.show(event.getPlayer());
    }

    public abstract void interact(NPCInteractEvent event);

}
