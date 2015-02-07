package com.example.gregolm.android_101.utilities;

import com.example.gregolm.android_101.dto.Map;

import java.util.HashMap;

/**
 * Created by Gregory on 2/6/2015.
 */
public final class MapLibrary {
    private static MapLibrary instance;
    private MapLibrary() {
        mapHashMap = new HashMap<>();
    }

    public static MapLibrary getInstance() {
        if (instance == null) instance = new MapLibrary();
        return instance;
    }

    protected HashMap<String, Map> mapHashMap;

    public Map getMap(String mapName) {
        return mapHashMap.get(mapName);
    }

    public void populateLibrary(Map[] maps) {
        for (int i = 0; i < maps.length; i++) mapHashMap.put(maps[i].getName(), maps[i]);
    }
}
