package com.example.gregolm.android_101.utilities.network;

import com.example.gregolm.android_101.dto.Map;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import java.util.ArrayList;


public class Network {
    static final String BASE_URL = "http://mean-gregolm.rhcloud.com/";
    static final int READ_TIMEOUT = 10000;
    static final int CONNECT_TIMEOUT = 15000;

    protected static HttpURLConnection createGetRequest(String endpoint) throws IOException {
        URL url = new URL(BASE_URL + endpoint);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setReadTimeout(READ_TIMEOUT);
        conn.setConnectTimeout(CONNECT_TIMEOUT);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        return conn;
    }

    public static Map[]getMaps() throws IOException{
        HttpURLConnection conn = createGetRequest("maps");
        conn.connect();
        int response = conn.getResponseCode();
        InputStream is = conn.getInputStream();
        Map[] result = new Gson().fromJson(new InputStreamReader(is, "UTF-8"), Map[].class);
        return result;
    }

    public static boolean login(String password) throws IOException {
        HttpURLConnection conn = createGetRequest("login?password=" + password);
        conn.connect();
        int response = conn.getResponseCode();
        InputStream is = conn.getInputStream();
        Boolean result = new Gson().fromJson(new InputStreamReader(is, "UTF-8"), Boolean.class);
        return result;
    }
}
