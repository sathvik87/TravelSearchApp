package com.travelsearch.parekods.travelsearch;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by parekods on 23.06.2015.
 */
public class GeoLocatorTask extends AsyncTask<URL, Integer, String> {
    private Context mContext;
    private ArrayList<String> cities = new ArrayList<String>();

    private AutoCompleteTextView view;
    public GeoLocatorTask (Context context, AutoCompleteTextView view){
        mContext = context;
        this.view = view;
    }
    protected String doInBackground(URL... urls) {
        int count = urls.length;
        String geoLocationObj = null;
        for (int i = 0; i < count; i++) {
            try {
                geoLocationObj = Downloader.downloadFile(urls[i].toString());
            } catch(IOException e) {
                e.printStackTrace();
            }

            // Escape early if cancel() is called
            if (isCancelled()) break;
        }
        return geoLocationObj;
    }

    protected void onProgressUpdate(Integer... progress) {
        //Set progress.
    }

    protected void onPostExecute(String result) {
        //Update adapter.


        if(result != null && result.length() > 0) {
            Log.v("DOWNLOAD", result);
            try {
                JSONArray jArr = new JSONArray(result);
                cities.clear();
                for(int i = 0 ; i < jArr.length() ; i++) {
                    JSONObject jObj = jArr.getJSONObject(i);
                    String city = jObj.getString("name");
                    if(city != null) {
                        cities.add(city);
                    }
                }

                //Update adapter.
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                        android.R.layout.simple_dropdown_item_1line, cities);
                view.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}