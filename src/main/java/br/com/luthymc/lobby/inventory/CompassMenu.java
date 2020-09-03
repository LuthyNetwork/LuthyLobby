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
            .title("§8Selecione um servidor para entrar!")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 4, ClickableItem.of(new Item(Material.DIAMOND_PICKAXE).name("&e&lRANKUP").lore("", "&7Clique aqui para entrar no &lRANKUP INDUSTRIAL&7!").build(), e -> {
            val api = BungeeChannelApi.of(Lobby.getInstance());

            if (Lobby.getPlayerSettings(player.getUniqueId()).alertas()) player.sendMessage("§cVocê está na fila para entrar no servidor.");
            api.connect(player, "rankup");

            INVENTORY.close(player);
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {}
}
