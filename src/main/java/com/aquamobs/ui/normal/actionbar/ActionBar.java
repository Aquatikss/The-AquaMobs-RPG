package com.aquamobs.ui.normal.actionbar;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.entity.Player;

public class ActionBar {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    private static Component actionBarMessage = Component.empty();

    public static void init(Player player) {
        player.sendActionBar(actionBarMessage);
    }

    public static void update(Player player) {
        player.sendActionBar(actionBarMessage);
    }

    public static void clear(Player player) {
        player.sendActionBar(Component.empty());
    }

    public static void setActionBarMessage(Component message) {
        actionBarMessage = message;
    }
}