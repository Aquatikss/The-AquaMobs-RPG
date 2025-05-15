package com.aquamobs.player.stats;

import net.minestom.server.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class StatUtil {

    private static final List<StatType> statTypes = new ArrayList<>();

    private static final ConcurrentHashMap<UUID, ConcurrentHashMap<StatType, Object>> stats = new ConcurrentHashMap<>();

    public static List<StatType> getAllStatTypes() {
        for (StatType statType : StatType.values()) {
            statTypes.add(statType);
        }
        return statTypes;
    }

    public static void setStat(Player player, StatType statType, Object value) {
        ConcurrentHashMap<StatType, Object> innerMap = new ConcurrentHashMap<>();
        innerMap.put(statType, value);
        stats.put(player.getUuid(), innerMap);
    }

    public static Object getStat(Player player, StatType statType) {
        return stats.get(player.getUuid()).get(statType);
    }

    public static void removeStat(Player player, StatType statType) {
        stats.get(player.getUuid()).remove(statType);
    }

    public static void clearStats(Player player) {
        stats.remove(player.getUuid());
    }

    public static boolean hasStat(Player player, StatType statType) {
        return stats.get(player.getUuid()).containsKey(statType);
    }

    public static boolean hasStats(Player player) {
        return stats.containsKey(player.getUuid());
    }

    public static void clearAllStats() {
        stats.clear();
    }

    public static ConcurrentHashMap<UUID, ConcurrentHashMap<StatType, Object>> getStats() {
        return stats;
    }

    public static boolean hasStatValue(Player player, StatType statType, Object value) {
        return stats.get(player.getUuid()).containsValue(value);
    }

    public static void automaticStatCalculation(Player player, StatType statType) {
        int weaponValue = 0;
        int offHandValue = 0;
        int helmetValue = 0;
        int chestplateValue = 0;
        int leggingsValue = 0;
        int bootsValue = 0;

        int value = weaponValue + offHandValue + helmetValue + chestplateValue + leggingsValue + bootsValue;

        ConcurrentHashMap<StatType, Object> innerMap = new ConcurrentHashMap<>();
        innerMap.put(statType, value);
        stats.put(player.getUuid(), innerMap);
    }

    public static void initializeStats(Player player) {
        for (StatType statType : StatType.values()) {
            automaticStatCalculation(player, statType);
        }
    }

    public static void updateStats(Player player) {
        for (StatType statType : StatType.values()) {
            automaticStatCalculation(player, statType);
        }
    }
}
