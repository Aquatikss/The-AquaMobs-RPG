package com.aquamobs;

import com.aquamobs.commands.GamemodeCommand;
import com.aquamobs.commands.StopCommand;
import com.aquamobs.data.playerdata.PlayerData;
import com.aquamobs.data.whitelistdata.WhiteListData;
import com.aquamobs.events.PlayerJoinLeaveEvent;
import com.aquamobs.items.ItemCommand;
import com.aquamobs.logging.LogType;
import com.aquamobs.logging.Logger;
import com.aquamobs.ui.normal.crafting.craftingtable.CraftingTableClickEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Pos;
import net.minestom.server.entity.Player;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.extras.velocity.VelocityProxy;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.instance.InstanceManager;
import net.minestom.server.instance.LightingChunk;
import net.minestom.server.instance.Weather;
import net.minestom.server.instance.anvil.AnvilLoader;

public class Main {

    private static GlobalEventHandler globalEventHandler;
    private static InstanceManager instanceManager;
    private static InstanceContainer instanceContainer;

    public static void main(String[] args) {

        //Server
        MinecraftServer server = MinecraftServer.init();

        // Initialize static fields after the server init
        globalEventHandler = MinecraftServer.getGlobalEventHandler();
        instanceManager = MinecraftServer.getInstanceManager();
        instanceContainer = instanceManager.createInstanceContainer();

        //Lighting
        instanceContainer.setChunkSupplier(LightingChunk::new);

        // Generation
        loadWorld();

        //Joining
        joining();

        //Registry
        registry();

        //Enable Velocity
        VelocityProxy.enable("SGrcXe0jvGOq");

        //Load Whitelist
        loadWhitelist(true);

        //Game rules
        gameRules();

        // Add a shutdown hook
        shutdown();

        //Start
        server.start("0.0.0.0", 25584);

        Logger.log("Server started.", LogType.INFO);
    }

    private static void registry() {
        MinecraftServer.getCommandManager().register(new GamemodeCommand());
        MinecraftServer.getCommandManager().register(new StopCommand());
        MinecraftServer.getCommandManager().register(new ItemCommand());

        PlayerJoinLeaveEvent.register(globalEventHandler);
        CraftingTableClickEvent.register(globalEventHandler);
    }

    private static void shutdown() {
        MinecraftServer.getSchedulerManager().buildShutdownTask(() -> {
            Logger.log("Shutting down...", LogType.INFO);

            // Save world chunks
            Logger.log("Saving world...", LogType.INFO);
            instanceContainer.saveChunksToStorage().join();

            // Save other data
            Logger.log("Saving whitelist...", LogType.INFO);
            WhiteListData.saveWhitelist();
            for (Player player : MinecraftServer.getConnectionManager().getOnlinePlayers()) {
                PlayerData.saveData(player);
            }
        });
    }

    private static void joining() {
        globalEventHandler.addListener(AsyncPlayerConfigurationEvent.class, event -> {
            final Player player = event.getPlayer();
            Logger.log(player.getUsername() + " (" + player.getUuid() + ") has joined with an IP of " +
                    player.getPlayerConnection().getRemoteAddress().toString() + ".", LogType.INFO);
            event.setSpawningInstance(instanceContainer);
            player.setRespawnPoint(new Pos(0, 71, 0));
        });
    }

    private static void loadWhitelist(boolean isWhitelistOn) {
        Logger.log("Loading whitelist...", LogType.INFO);
        WhiteListData.loadWhitelist();
        WhiteListData.setWhitelist(isWhitelistOn);
    }

    private static void loadWorld() {
        String worldName = "spawn";
        String worldPath = "worlds/" + worldName;

        Logger.log("Loading world...", LogType.INFO);
        AnvilLoader anvilLoader = new AnvilLoader(worldPath);
        instanceContainer.setChunkLoader(anvilLoader);
        instanceContainer.enableAutoChunkLoad(true);

        instanceContainer.loadChunk(0, 0).join();
    }

    private static void gameRules() {
        instanceContainer.setTimeRate(0);
        instanceContainer.setTime(6000);
        instanceContainer.setWeather(Weather.CLEAR, 2147483647);
    }
}
