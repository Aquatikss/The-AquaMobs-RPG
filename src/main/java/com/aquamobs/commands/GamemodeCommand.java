package com.aquamobs.commands;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;

public class GamemodeCommand extends Command {

    public GamemodeCommand() {
        super("gamemode", "gm");

        setDefaultExecutor((sender, context) -> {
            Player player = (Player) sender;

            player.sendMessage("Please specify a gamemode!");
        });

        var gamemodeArg = ArgumentType.String("gameMode");
        addSyntax((sender, context) -> {
            Player player = (Player) sender;
            String gamemode = context.get(gamemodeArg);

            switch (gamemode) {
                case "creative", "c":
                    player.setGameMode(GameMode.CREATIVE);
                    break;
                case "survival", "s":
                    player.setGameMode(GameMode.SURVIVAL);
                    break;
                case "adventure", "a":
                    player.setGameMode(GameMode.ADVENTURE);
                    break;
                case "spectator", "sp":
                    player.setGameMode(GameMode.SPECTATOR);
                    break;
            }
        }, gamemodeArg);
    }

}
