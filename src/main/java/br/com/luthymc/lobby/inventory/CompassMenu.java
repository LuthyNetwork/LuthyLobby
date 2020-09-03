package br.com.luthymc.lobby.inventory;

import br.com.luthymc.lobby.Lobby;
import br.com.luthymc.lobby.utils.items.Item;
import br.com.luthymc.lobby.utils.proxy.BungeeChannelApi;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import lombok.val;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CompassMenu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("compass")
            .provider(new CompassMenu())
            .manager(Lobby.getInventoryManager())
            .size(3, 9)
            .title("§8Select a server to join!")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 4, ClickableItem.of(new Item(Material.DIAMOND_PICKAXE).name("&e&lPRISON").lore("", "&7Click here to join the &lPRISON &7server!").build(), e -> {
            val api = BungeeChannelApi.of(Lobby.getInstance());

            player.sendMessage("§cYou're now in queue to join the Prison server!");
            api.connect(player, "prison");

            INVENTORY.close(player);
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {}
}
