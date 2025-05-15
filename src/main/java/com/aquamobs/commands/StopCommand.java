package com.aquamobs.commands;

import com.aquamobs.logging.LogType;
import com.aquamobs.logging.Logger;
import net.minestom.server.MinecraftServer;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;

public class StopCommand extends Command {

    public StopCommand() {
        super("stop");

        setDefaultExecutor((sender, context) -> {
            // Log that the stop command is being executed
            Logger.log("Stopping server...", LogType.INFO);

            // Execute the stop command as if it's run by the console
            CommandSender consoleSender = MinecraftServer.getCommandManager().getConsoleSender();
            MinecraftServer.getCommandManager().execute(consoleSender, "stop");
        });
    }
}
