package com.aquamobs.blugu;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.AbstractInventory;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

abstract class Gui {

    public void open(Player player, Inventory inventory) {
        player.openInventory(inventory);
    }

    public Inventory getInventory(Player player) {
        AbstractInventory abstractInventory = player.getOpenInventory();
        return abstractInventory instanceof Inventory ? (Inventory) abstractInventory : null;
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public boolean addItem(Inventory inventory, GuiItem item) {
        if (inventory == null) return false;
        int firstEmptySlot;
        for (firstEmptySlot = 0; firstEmptySlot < inventory.getSize(); firstEmptySlot++) {
            if (inventory.getItemStack(firstEmptySlot) == ItemStack.AIR) break;
        }
        if (firstEmptySlot == inventory.getSize()) return false;
        inventory.setItemStack(firstEmptySlot, item.asItemStack());
        return true;
    }

    public boolean removeItem(Inventory inventory, int slot) {
        if (inventory == null) return false;
        inventory.setItemStack(slot, ItemStack.AIR);
        return true;
    }



}