package com.aquamobs.ui.normal.crafting.craftingtable;

import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerBlockInteractEvent;
import net.minestom.server.item.Material;

public class CraftingTableClickEvent {

    public static void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerBlockInteractEvent.class, event -> {
            if (event.getBlock().registry().material() == Material.CRAFTING_TABLE) {
                CraftingTableUI.init(event.getPlayer());
            }
        });
    }

}
