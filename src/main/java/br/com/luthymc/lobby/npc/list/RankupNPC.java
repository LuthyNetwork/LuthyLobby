package br.com.luthymc.lobby.npc.list;

import br.com.luthymc.lobby.Lobby;
import br.com.luthymc.lobby.npc.LobbyNPC;
import br.com.luthymc.lobby.utils.proxy.BungeeChannelApi;
import br.com.luthymc.lobby.utils.time.TimeUtilities;
import lombok.val;
import net.jitse.npclib.api.events.NPCInteractEvent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RankupNPC extends LobbyNPC {

    public RankupNPC(Location location) {
        super(Lobby.getInstance(),
                location,
                new ItemStack(Material.DIAMOND_PICKAXE),
                1260908926,
                "&e&lRANKUP - LUTHYMC", "&7Olá, tenha &e" + TimeUtilities.getDayPart() + "&7!", "", "&7Clique aqui para entrar no &eservidor&7!");
    }

    @Override
    public void interact(NPCInteractEvent event) {
        if (event.getClickType().equals(NPCInteractEvent.ClickType.RIGHT_CLICK)) {
            val player = event.getWhoClicked();
            val api = BungeeChannelApi.of(Lobby.getInstance());

            if (Lobby.getPlayerSettings(player.getUniqueId()).alertas()) player.sendMessage("§cVocê está na fila para entrar no servidor.");

            api.connect(player, "rankup");
        }
    }

}
