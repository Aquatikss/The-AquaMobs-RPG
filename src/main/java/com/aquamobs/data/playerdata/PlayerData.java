package com.aquamobs.data.playerdata;

import net.minestom.server.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerData {

    //The path where the file is created, as a String
    private static final String dataPath = "players/data/";

    //The path where the file is created, as a File
    private static final File dataPathFile = new File(dataPath);

    //The data map
    private static final ConcurrentHashMap<UUID, ConcurrentHashMap<Object, Object>> outerMap = new ConcurrentHashMap<>();

    //Load data
    public static void loadData(Player player) {
        //The player's UUID
        UUID uuid = player.getUuid();

        //The JSON parser
        JSONParser parser = new JSONParser();

        //The Data File
        File dataFile = new File(dataPath + uuid + ".json");

        //Check if the dataFile exists
        if (dataFile.exists()) {

            //If it does, then load from the JSON dataFile to the HashMap
            try (FileReader fileReader = new FileReader(dataFile)) {
                JSONObject obj = (JSONObject) parser.parse(fileReader);
                putToJson(obj);

                //Loop through the entire HashMap and add everything
                ConcurrentHashMap<Object, Object> innerMap = new ConcurrentHashMap<>();
                for (Object key : obj.keySet()) {
                    innerMap.put(key, obj.get(key));
                }
                outerMap.put(uuid, innerMap);

            //Catch Clause
            } catch (IOException | ParseException e) {
                throw new RuntimeException(e);
            }

        //If the dataFile does NOT exist
        } else {
            //Create a new JSON Object
            JSONObject obj = new JSONObject();

            //Make the directories.
            boolean didMakeDirs = dataPathFile.mkdirs();

            //Try/Catch for file creation
            try {
                //Create the file.
                boolean didMakeFile = dataFile.createNewFile();

                //Add values if player doesn't have
                putToJson(obj);

                //Create the inner/outer Map
                ConcurrentHashMap<Object, Object> innerMap = new ConcurrentHashMap<>();
                for (Object key : obj.keySet()) {
                    innerMap.put(key, obj.get(key));
                }
                outerMap.put(uuid, innerMap);

                // Write initial data to file
                try (FileWriter fileWriter = new FileWriter(dataFile)) {
                    fileWriter.write(obj.toJSONString());
                }

            //Catch clause for creating the file
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //Add to JSON if a value is absent
    private static void putToJson(JSONObject obj) {
        obj.putIfAbsent("coins", 2500);
        obj.putIfAbsent("level", 1);
        obj.putIfAbsent("xp", 1);

        JSONObject collections = new JSONObject();
        collections.putIfAbsent("oak", 0);
        collections.putIfAbsent("coal", 0);

        obj.putIfAbsent("collections", collections);
    }

    //Save data
    public static void saveData(Player player) {
        //The player's UUID
        UUID uuid = player.getUuid();

        //The JSON Object
        JSONObject obj = new JSONObject();

        //Loop through everything and add it to the JSON Object
        for (Map.Entry<UUID, ConcurrentHashMap<Object, Object>> entry : outerMap.entrySet()) {
            if (entry.getKey().equals(uuid)) {
                obj.putAll(entry.getValue());
                break;
            }
        }

        //Write the JSON to the File
        try (FileWriter fileWriter = new FileWriter(dataPath + uuid + ".json")) {
            fileWriter.write(obj.toJSONString());
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    public static Object getData(Player player, Object o) {
        //Player's UUID
        UUID uuid = player.getUuid();

        //Create innerMap
        ConcurrentHashMap<Object, Object> innerMap = outerMap.get(uuid);

        //Return the value.
        return innerMap.getOrDefault(o, 0);
    }

}