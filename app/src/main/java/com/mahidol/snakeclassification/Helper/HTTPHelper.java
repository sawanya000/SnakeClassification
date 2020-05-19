package com.mahidol.snakeclassification.Helper;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPHelper {
    static String stream = null;

    //    public Helper(){
//
//    }
    public String getHTTPData(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null)
                    sb.append(line);
                stream = sb.toString();
                urlConnection.disconnect();
            }

        } catch (MalformedURLException e) {
            Log.e("error",e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("error",e.toString());
            e.printStackTrace();
        }
        return stream;
    }
}
