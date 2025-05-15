package com.aquamobs.ui.normal.crafting.craftingtable;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

public class CraftingTableUI {

    public static void init(Player player) {
        Inventory inventory = new Inventory(InventoryType.CRAFTING, "Crafting Table");

        inventory.addViewer(player);
    }

    public static void render() {

    }

    public static void update() {

    }

    public static void close() {

    }
}
