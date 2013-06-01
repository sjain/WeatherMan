package com.orbitz.interview.weatherman;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.orbitz.interview.weatherman.model.Weather;
import com.orbitz.interview.weatherman.service.IWeatherService;
import com.orbitz.interview.weatherman.service.WeatherService;

/**
 * Created by sjain on 5/31/13.
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText _textZipCode = null;
    private TextView _viewCurrentWeather = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _textZipCode = (EditText) findViewById(R.id.text_zip_code);
        Button buttonDisplayCurrentWeather = (Button) findViewById(R.id.button_display_current_weather);
        buttonDisplayCurrentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.fetchCurrentWeather();
            }
        });
        _viewCurrentWeather = (TextView) findViewById(R.id.view_current_weather);
    }

    private void fetchCurrentWeather() {
        CharSequence zipCode = _textZipCode.getText();
        if (zipCode.length() != 5)
        {
            String message = "Please enter 5-digit zip code.";
            showError(message);
            return;
        }

        try {
            String zipCodeString = zipCode.toString();
            Log.v(TAG, zipCodeString);
            new FetchCurrentWeatherTask().execute(zipCodeString);
        } catch (Exception e)
        {
            _viewCurrentWeather.setText("Failed to fetch current weather: " + e.getMessage());
        }
    }

    private void showError(String message) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    class FetchCurrentWeatherTask extends AsyncTask<String, Integer, Weather> {

        @Override
        protected Weather doInBackground(String... arguments) {
            String zipCode = arguments[0];
            IWeatherService weatherService = new WeatherService();
            Weather currentWeather = null;
            try {
                currentWeather = weatherService.fetchCurrentWeather(zipCode);
                return currentWeather;
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather != null) {
                MainActivity.this.displayCurrentWeather(weather);
            }
        }
    }

    private void displayCurrentWeather(Weather weather) {
        _viewCurrentWeather.setText("Current Temperature (F): " + weather.getTempF());
    }
}