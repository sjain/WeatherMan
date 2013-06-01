package com.orbitz.interview.weatherman;

import android.app.Activity;
import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListAdapter;

import com.orbitz.interview.weatherman.model.DailyWeather;
import com.orbitz.interview.weatherman.model.Weather;
import com.orbitz.interview.weatherman.service.IWeatherService;
import com.orbitz.interview.weatherman.service.WeatherService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjain on 6/1/13.
 */
public class DailyWeatherListActivity extends ListActivity {

    private static final String TAG = DailyWeatherListActivity.class.getSimpleName();
    private DailyWeatherListAdapter weatherListAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String zipcode = extras.getString("ZIPCODE");
        Log.i(TAG, "List ACtivity received zipcode " + zipcode);

        new FetchWeatherTask().execute(zipcode);
        List<DailyWeather> dailyWeatherList = new ArrayList<DailyWeather>(0);
        weatherListAdapter = new DailyWeatherListAdapter(DailyWeatherListActivity.this, dailyWeatherList);
        setListAdapter(weatherListAdapter);
    }

    class FetchWeatherTask extends AsyncTask<String, Integer, Weather> {

        @Override
        protected Weather doInBackground(String... arguments) {
            String zipCode = arguments[0];
            IWeatherService weatherService = new WeatherService();
            Weather weather = null;
            try {
                weather = weatherService.fetchWeather(zipCode);
                return weather;
            } catch (Exception e) {
                // TODO improve error handling
                return null;
            }
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather != null) {
                DailyWeatherListActivity.this.displayWeather(weather);
            }
        }
    }

    private void displayWeather(Weather weather) {
        Log.v(TAG, weather.toDebugString());
        weatherListAdapter.setList(weather.getDailyWeathers());
    }
}