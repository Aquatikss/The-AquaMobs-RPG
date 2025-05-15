package com.aquamobs.player.stats;

import com.aquamobs.ui.normal.actionbar.ActionBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.entity.Player;

public class Stats {

    public static void init(Player player) {
        StatUtil.initializeStats(player);
    }

    public static void update(Player player) {
        StatUtil.updateStats(player);
    }

    public static void render(Player player) {
        Component message = MiniMessage.miniMessage().deserialize("<red>" + StatUtil.getStat(player, StatType.HEALTH));

        ActionBar.setActionBarMessage(message);
    }

    public static void dispose(Player player) {
        StatUtil.clearStats(player);
    }
}
