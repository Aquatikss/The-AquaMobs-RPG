package com.aquamobs.items;

import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.arguments.ArgumentType;

public class ItemCommand extends Command {

    public ItemCommand() {
        super("item", "i");

        setDefaultExecutor((sender, context) -> {
            sender.sendMessage("Please specify a valid subcommand!");
        });

        var subcommandArg = ArgumentType.String("subCommand");
        addSyntax((sender, context) -> {
            String subcommand = context.get(subcommandArg);

            switch (subcommand) {
                case "add":
                    break;
                case "remove":
                    break;
                case "list":
                    break;
                case "get":
                    break;
                default:
                    sender.sendMessage("Please specify a valid subcommand!");
                    break;
            }
        });
    }

    private void add() {
        //TODO create item json file and save
    }

    private void remove() {
        //TODO remove item json file
    }

    private void list() {
        //TODO list all items
    }

    private void get() {
        //TODO get the item in game
    }

}
