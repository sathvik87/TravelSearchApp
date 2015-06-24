package com.travelsearch.parekods.travelsearch;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by parekods on 23.06.2015.
 */
public class AutoCompleteTextWatcher implements TextWatcher {
    private Context context;
    private AutoCompleteTextView view;
    AutoCompleteTextWatcher(Context context, AutoCompleteTextView view) {
        this.context = context;
        this.view = view;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //check for connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String url = "http://api.goeuro.com/api/v2/position/suggest/de/"+s.toString();
            try {
                new GeoLocatorTask(context, view).execute(new URL(url));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            // display error
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
