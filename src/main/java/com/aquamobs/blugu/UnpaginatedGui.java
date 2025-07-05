package com.aquamobs.blugu;

import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import net.minestom.server.inventory.Inventory;
import net.minestom.server.inventory.InventoryType;

public class UnpaginatedGui extends Gui {

    private static Inventory INVENTORY;

    public UnpaginatedGui(int size, String title) {
        switch (size) {
            case 1 -> INVENTORY = new Inventory(InventoryType.CHEST_1_ROW, Component.text(title));
            case 2 -> INVENTORY = new Inventory(InventoryType.CHEST_2_ROW, Component.text(title));
            case 4 -> INVENTORY = new Inventory(InventoryType.CHEST_4_ROW, Component.text(title));
            case 5 -> INVENTORY = new Inventory(InventoryType.CHEST_5_ROW, Component.text(title));
            case 6 -> INVENTORY = new Inventory(InventoryType.CHEST_6_ROW, Component.text(title));
            default -> INVENTORY = new Inventory(InventoryType.CHEST_3_ROW, Component.text(title));
        }

    }

}
