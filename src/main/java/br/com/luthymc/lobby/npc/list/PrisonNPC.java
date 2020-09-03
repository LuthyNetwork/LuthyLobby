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

public class PrisonNPC extends LobbyNPC {

    public PrisonNPC(Location location) {
        super(Lobby.getInstance(),
                location,
                new ItemStack(Material.DIAMOND_PICKAXE),
                1260908926,
                "&e&lPRISON - LUTHYMC", "&7Hello, have a good &e" + TimeUtilities.getDayPart() + "&7!", "", "&7Click here to join the &eserver&7!");
    }

    @Override
    public void interact(NPCInteractEvent event) {
        if (event.getClickType().equals(NPCInteractEvent.ClickType.RIGHT_CLICK)) {
            val player = event.getWhoClicked();
            val api = BungeeChannelApi.of(Lobby.getInstance());

            if (Lobby.getPlayerSettings(player.getUniqueId()).alerts()) player.sendMessage("§cYou're now in queue to join the Prison server!");

            api.connect(player, "prison");
        }
    }

}
