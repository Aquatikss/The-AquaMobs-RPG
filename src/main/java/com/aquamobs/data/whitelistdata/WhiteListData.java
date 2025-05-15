package com.aquamobs.data.whitelistdata;

import net.minestom.server.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WhiteListData {

    //The data map
    private static final List<UUID> list = new ArrayList<>();

    //Is the whitelist enabled?
    private static boolean isWhitelistOn = true;

    public static void loadWhitelist() {
        File file = new File("whitelist.json");
        list.clear();

        if (!file.exists() || file.length() == 0) {
            // Create new file with default structure
            JSONObject obj = new JSONObject();
            obj.put("whitelist", new JSONArray());
            saveJsonToFile(obj, file);
            return;
        }

        // Read existing file
        try (FileReader reader = new FileReader(file)) {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(reader);
            JSONArray jsonList = (JSONArray) obj.get("whitelist");

            for (Object item : jsonList) {
                String uuidString = item.toString();
                UUID uuid = UUID.fromString(uuidString);
                list.add(uuid);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            // Create default structure if parsing fails
            JSONObject obj = new JSONObject();
            obj.put("whitelist", new JSONArray());
            saveJsonToFile(obj, file);
        }
    }

    private static void saveJsonToFile(JSONObject obj, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(obj.toJSONString());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveWhitelist() {
        File file = new File("whitelist.json");
        JSONObject obj = new JSONObject();
        JSONArray jsonList = new JSONArray();

        for (UUID uuid : list) {
            jsonList.add(uuid.toString());
        }

        obj.put("whitelist", jsonList);
        saveJsonToFile(obj, file);
    }

    public static boolean isWhitelisted(UUID uuid) {
        return list.contains(uuid);
    }

    public static boolean isWhitelistOn() {
        return isWhitelistOn;
    }

    public static void setWhitelist(boolean isWhitelistOn) {
        WhiteListData.isWhitelistOn = isWhitelistOn;
    }

    public static void addToWhitelist(Player player) {
        list.add(player.getUuid());
        saveWhitelist();
    }

    public static void removeFromWhitelist(Player player) {
        list.remove(player.getUuid());
        saveWhitelist();
    }
}
