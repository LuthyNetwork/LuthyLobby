package com.luthynetwork.lobby.inventory;

import com.luthynetwork.lobby.Lobby;
import com.luthynetwork.lobby.settings.Message;
import com.luthynetwork.core.libs.item.Item;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ProfileMenu implements InventoryProvider {

    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("profile")
            .provider(new ProfileMenu())
            .manager(Lobby.inventoryManager())
            .size(3, 9)
            .title("§ePerfil")
            .closeable(true)
            .build();

    @Override
    public void init(Player player, InventoryContents contents) {
        contents.set(1, 4, ClickableItem.of(new Item(Material.BARRIER).name("&c&lEM BREVE").lore("", "&7Esta função será adicionada em breve, aguarde por novidades!").build(), e -> {
            if (Lobby.getPlayerSettings(player.getUniqueId()).alertas()) Message.withPrefix(player, "§cEm breve!");

            INVENTORY.close(player);
        }));
    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {
    }
}
