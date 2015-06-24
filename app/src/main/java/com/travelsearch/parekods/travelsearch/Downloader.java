package com.travelsearch.parekods.travelsearch;

import android.app.DownloadManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by parekods on 23.06.2015.
 */
public class Downloader {

    public Downloader() {

    }

    public static String downloadFile(String httpUrl) throws IOException {
        InputStream is = null;


        try {
            URL url = new URL(httpUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d("DOWNLOAD", "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public static String readIt(InputStream stream) throws IOException, UnsupportedEncodingException {
        byte[] bytes = new byte[1000];

        StringBuilder content = new StringBuilder();
        int numRead = 0;
        while ((numRead = stream.read(bytes)) >= 0) {
            content.append(new String(bytes, 0, numRead));
        }
        return content.toString();
    }
}
