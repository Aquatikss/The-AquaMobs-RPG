package com.aquamobs.logging;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    static {
        AnsiConsole.systemInstall();
    }

    private static String convertMinecraftColorToAnsi(String message) {
        return message
                .replace("§0", Ansi.ansi().reset().fg(Ansi.Color.BLACK).toString())
                .replace("§1", Ansi.ansi().reset().fg(Ansi.Color.BLUE).toString())
                .replace("§2", Ansi.ansi().reset().fg(Ansi.Color.GREEN).toString())
                .replace("§3", Ansi.ansi().reset().fg(Ansi.Color.CYAN).toString())
                .replace("§4", Ansi.ansi().reset().fg(Ansi.Color.RED).toString())
                .replace("§5", Ansi.ansi().reset().fg(Ansi.Color.MAGENTA).toString())
                .replace("§6", Ansi.ansi().reset().fg(Ansi.Color.YELLOW).toString())
                .replace("§7", Ansi.ansi().reset().fg(Ansi.Color.WHITE).toString())
                .replace("§8", Ansi.ansi().reset().fg(Ansi.Color.BLACK).toString())
                .replace("§9", Ansi.ansi().reset().fg(Ansi.Color.BLUE).toString())
                .replace("§c", Ansi.ansi().reset().fg(Ansi.Color.RED).toString())
                .replace("§e", Ansi.ansi().reset().fg(Ansi.Color.YELLOW).toString())
                .replace("§f", Ansi.ansi().reset().toString());
    }

    public static void log(String message, LogType type) {
        String prefix = switch (type) {
            case WARN -> "§e[WARN] §f";         //  Yellow  //
            case ERROR -> "§c[ERROR] §f";       //   Red    //
            case DEBUG -> "§6[DEBUG] §f";       //   Gold   //
            case NONE -> "§7[NONE] §f";         //   Gray   //
            case UNKNOWN -> "§5[UNKNOWN] §f";   //  Purple  //
            default -> "§9[INFO] §f";           //   Blue   //
        };
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String time = "[" + LocalTime.now().format(formatter) + "] ";
        System.out.println( time + convertMinecraftColorToAnsi(prefix) + message);
    }

}