package com.aquamobs.blugu;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.util.ArrayList;
import java.util.List;

public class GuiItem {

    private Material material;
    private int amount;
    private Component name;
    private List<Component> lore;

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public GuiItem() {}

    public ItemStack asItemStack() {

        name = name != null ? name : Component.text(material.name());

        ItemStack item = ItemStack.of(material, amount).withCustomName(name);

        if (item == null) {
            throw new IllegalArgumentException("ItemStack cannot be null. Ensure material is valid.");
        }
        if (amount < 1) {
            throw new IllegalArgumentException("Amount must be at least 1.");
        }

        return ItemStack.of(material, amount).withCustomName(name);
    }

    public GuiItem setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public GuiItem setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public GuiItem setName(String name) {
        this.name = miniMessage.deserialize(name);
        return this;
    }

    public GuiItem setLore(int index, Component loreAtIndex) {
        lore.set(index, loreAtIndex);
        return this;
    }

    public GuiItem addLore(Component loreAtLastIndex) {
        if (lore == null) {
            lore = new ArrayList<>();
        }
        lore.addLast(loreAtLastIndex);
        return this;
    }

    public GuiItem insertLore(int index, Component loreAtIndex) {
        if (lore == null) {
            lore = new ArrayList<>();
        }
        if (index < 0 || index > lore.size()) {
            throw new IndexOutOfBoundsException("Index out of bounds for lore list.");
        }
        lore.add(index, loreAtIndex);
        return this;
    }

    public GuiItem insertLoreAtFirstIndex(Component loreAtFirstIndex) {
        return insertLore(0, loreAtFirstIndex);
    }

    public List<Component> getLore() {
        return lore != null ? lore : new ArrayList<>();
    }

    public Component getName() {
        return name != null ? name : Component.text(material.name());
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }

}
