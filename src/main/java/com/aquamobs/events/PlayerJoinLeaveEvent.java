package com.aquamobs.events;

import com.aquamobs.data.playerdata.PlayerData;
import com.aquamobs.data.whitelistdata.WhiteListData;
import com.aquamobs.player.stats.Stats;
import com.aquamobs.ui.normal.actionbar.ActionBar;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minestom.server.MinecraftServer;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerPreLoginEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.timer.SchedulerManager;
import net.minestom.server.timer.TaskSchedule;

public class PlayerJoinLeaveEvent {
    public static void register(GlobalEventHandler eventHandler) {
        eventHandler.addListener(PlayerSpawnEvent.class, event -> {
            var player = event.getPlayer();

            PlayerData.loadData(player);
            joinMessage(player);
            ActionBar.init(player);
            Stats.init(player);

            SchedulerManager schedulerManager = MinecraftServer.getSchedulerManager();
            schedulerManager.buildTask(() -> {
                Stats.update(player);
                Stats.render(player);
                ActionBar.update(player);
            }).repeat(TaskSchedule.tick(1)).schedule();
        });

        eventHandler.addListener(PlayerDisconnectEvent.class, event -> {
            var player = event.getPlayer();

            PlayerData.saveData(player);
            leaveMessage(player);
        });

        eventHandler.addListener(AsyncPlayerPreLoginEvent.class, event -> {
            var uuid = event.getGameProfile().uuid();
            if (WhiteListData.isWhitelistOn()) {
                if (!WhiteListData.isWhitelisted(uuid)) {
                    event.getConnection().disconnect();
                }
            }
        });
    }

    private static void joinMessage(Player player) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        Component message = miniMessage.deserialize("<dark_gray>[<green>+<dark_gray>] <gray>" + player.getUsername());
        for (Player p : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }

    private static void leaveMessage(Player player) {
        MiniMessage miniMessage = MiniMessage.miniMessage();

        Component message = miniMessage.deserialize("<dark_gray>[<red>-<dark_gray>] <gray>" + player.getUsername());
        for (Player p : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }
}