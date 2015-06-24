package com.travelsearch.parekods.travelsearch;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TravelSearchMainActivity extends AppCompatActivity {

    AutoCompleteTextView currentLocationView;
    AutoCompleteTextView toLocationView;
    EditText dateView;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_search_main);

        searchBtn = (Button) findViewById(R.id.searchBtn);
        dateView = (EditText) findViewById(R.id.datetext);
        currentLocationView = (AutoCompleteTextView)
                findViewById(R.id.currentLocation);
        currentLocationView.setSelection(currentLocationView.getText().length());

        toLocationView = (AutoCompleteTextView)
                findViewById(R.id.toLocation);
        toLocationView.setSelection(toLocationView.getText().length());

        currentLocationView.addTextChangedListener(new AutoCompleteTextWatcher(this, currentLocationView));
        toLocationView.addTextChangedListener(new AutoCompleteTextWatcher(this, toLocationView));

        //check for change in edit text and validate the form to show search button.
        dateView.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                validateForm();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dateView.setSelection(s.length());
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TravelSearchMainActivity.this,"Search not yet implemented",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_travel_search_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePicker();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    //Inner class DatePicker fragment.
    public static class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            Calendar c = Calendar.getInstance();
            int startYear = c.get(Calendar.YEAR);
            int startMonth = c.get(Calendar.MONTH);
            int startDay = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, startYear, startMonth, startDay);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            //Update the view.
            EditText dateView = (EditText) getActivity().findViewById(R.id.datetext);
            if (dateView != null) {
                dateView.setText(new StringBuilder().append(dayOfMonth).append(".").append(monthOfYear).append(".").append(year).toString());

            }
        }
    }

    //Validate the form, If valid show search button.
    public void validateForm() {
        String currentLocation = currentLocationView.getText().toString();
        String toLocation = currentLocationView.getText().toString();
        String dateVal = dateView.getText().toString();

        if (currentLocation != null && currentLocation.length() > 0 && toLocation != null && toLocation.length() > 0
                && isDateValid(dateVal)) {
            searchBtn.setVisibility(View.VISIBLE);
            ObjectAnimator animY = ObjectAnimator.ofFloat(searchBtn, "translationY", -100f, 0f);
            animY.setDuration(1000);//1sec
            animY.setInterpolator(new BounceInterpolator());
            animY.setRepeatCount(0);
            animY.start();
        }
    }

    public static boolean isDateValid(String date) {
        final String DATE_FORMAT = "dd.MM.yyyy";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

}
