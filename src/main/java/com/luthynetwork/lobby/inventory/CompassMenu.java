package com.luthynetwork.lobby.inventory;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.libs.item.Item;
import com.luthynetwork.core.libs.proxy.BungeeChannelApi;
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
            .manager(Lobby.inventoryManager())
            .size(3, 9)
            .title("§aServidores")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 4, ClickableItem.of(new Item(Material.IRON_PICKAXE).name("&a&lRANKUP").lore("", "&7Clique aqui para entrar no &lRANKUP INDUSTRIAL&7!").build(), e -> {
            val api = BungeeChannelApi.of(Lobby.instance());

            if (Lobby.getPlayerSettings(player.getUniqueId()).alertas()) Message.withPrefix(player, "§cVocê está na fila para entrar no servidor.");
            api.connect(player, "rankup");

            INVENTORY.close(player);
        }));
    }

    @Override
    public void update(Player player, InventoryContents contents) {}
}
