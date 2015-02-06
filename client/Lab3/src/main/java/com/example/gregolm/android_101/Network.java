package com.example.gregolm.android_101;

import com.example.gregolm.android_101.dto.Map;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;


public class Network {
    protected HttpURLConnection connection;

    public static Map[]getMaps() throws IOException{
        Map[] result = new Gson().fromJson("[{\"startX\":0,\"startY\":0,\"name\":\"Level 1\",\"map\":[[\"01\",\"23\",\"45\"],[\"67\",\"89\",\"AB\"],[\"CD\",\"EF\",\"00\"]]},{\"startX\":1,\"startY\":1,\"name\":\"Level 2\",\"map\":[[\"10\",\"32\",\"54\"],[\"76\",\"98\",\"ba\"],[\"dc\",\"fe\",\"00\"]]}]", Map[].class);
        return result;
    }

    public static boolean login(String password) throws IOException {
        if (password.equalsIgnoreCase("password")) return true;
        return false;
    }
}
