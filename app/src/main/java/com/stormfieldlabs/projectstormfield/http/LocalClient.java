package com.stormfieldlabs.projectstormfield.http;

import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Yashal on 8/23/2015.
 */
public class LocalClient {

    static String resp;
    public static String get(String url) throws IOException {
       OkHttpClient client = new OkHttpClient();
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            resp= response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }
public static String getDef(String urlInput) {
    URL url = null;
    try {
        url = new URL("http://" + urlInput);
        Log.d("http", "calling " + url.toString());
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    try {
        HttpURLConnection conn  = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Connection", "close");
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.connect();
        switch (conn.getResponseCode()){
            case 200:InputStream in = null;
                in = (conn.getInputStream());
                String response =readIt(in,500) ;
                return response;
            default:
                in =conn.getErrorStream();
                String response2 =readIt(in,5) ;
                return response2;
        }

        //response = org.apache.commons.io.IOUtils.toString(in, "UTF-8");
    } catch (IOException e) {
        Log.e("http",e.getMessage());
        e.printStackTrace();
    }
    return "LocalClient didnt work";
}
public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
    Reader reader = null;
    reader = new InputStreamReader(stream, "UTF-8");
    char[] buffer = new char[len];
    reader.read(buffer);
    return new String(buffer);
    }
}
