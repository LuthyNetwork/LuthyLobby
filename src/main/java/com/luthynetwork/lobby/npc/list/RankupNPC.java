package com.luthynetwork.lobby.npc.list;

import com.luthynetwork.core.LuthyCore;
import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.npc.LobbyNPC;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.libs.time.TimeUtilities;
import lombok.val;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.events.NPCInteractEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class RankupNPC extends LobbyNPC {

    public RankupNPC(Location location) {
        super(Lobby.instance(),
                location,
                new ItemStack(Material.IRON_PICKAXE),
                1260908926,
                "§e§lRANKUP - LUTHYMC", "§7Olá, tenha §e" + TimeUtilities.getDayPart() + "§7!", "", "§7Clique aqui para entrar no §eservidor§7!");
    }

    @Override
    public void interact(NPCInteractEvent event) {
        if (event.getClickType().equals(NPCInteractEvent.ClickType.RIGHT_CLICK)) {
            val player = event.getWhoClicked();
            val api = LuthyCore.getApi().getBungeeChannelApi();

            if (Lobby.getPlayerSettings(player.getUniqueId()).alertas())
                Message.withPrefix(player, "§cVocê está na fila para entrar no servidor.");

            api.connect(player, "rankup");
        }
    }

    @Override
    public List<String> update(NPC npc) {
        return Arrays.asList("§e§lRANKUP - LUTHYMC", "§7Olá, tenha §e" + TimeUtilities.getDayPart() + "§7!", "", "§7Clique aqui para entrar no §eservidor§7!");
    }

}
