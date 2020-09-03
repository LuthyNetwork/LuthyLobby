package br.com.luthymc.lobby.utils.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Item {

    ItemStack stack;
    ItemMeta meta;

    public Item(ItemStack stack) {
        this.stack = stack;
        this.meta = stack.getItemMeta();
    }

    public Item(Material material) {
        this.stack = new ItemStack(material);
        this.meta = stack.getItemMeta();
    }

    public Item(Material material, int amount) {
        this.stack = new ItemStack(material, amount);
        this.meta = stack.getItemMeta();
    }

    public Item(Material material, int amount, int data) {
        this.stack = new ItemStack(material, amount, (short) data);
    }

    public Item name(String name) {
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));

        return this;
    }

    public Item lore(String... lines) {
        for (int i = 0; i < lines.length; i++) {
            lines[i] = ChatColor.translateAlternateColorCodes('&', lines[i]);
        }

        meta.setLore(Arrays.stream(lines).collect(Collectors.toList()));

        return this;
    }

    public Item enchant(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);

        return this;
    }

    public Item flags(ItemFlag... flags) {
        meta.addItemFlags(flags);

        return this;
    }

    public ItemStack build() {
        stack.setItemMeta(meta);

        return stack;
    }

}
