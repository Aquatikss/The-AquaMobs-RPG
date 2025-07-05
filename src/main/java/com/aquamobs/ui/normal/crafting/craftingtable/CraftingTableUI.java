package com.aquamobs.ui.normal.crafting.craftingtable;

import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

public class CraftingTableUI {

    private static final Inventory inventory = new Inventory(InventoryType.CRAFTING, "Crafting Table");

    public static void init(Player player) {
        inventory.addViewer(player);
    }

    public static void render() {

    }

    public static void update() {

    }

    public static void close() {

    }
}
