package com.orbitz.interview.weatherman;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.orbitz.interview.weatherman.model.Weather;
import com.orbitz.interview.weatherman.service.IWeatherService;
import com.orbitz.interview.weatherman.service.WeatherService;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sjain on 5/31/13.
 */
public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private EditText _textZipCode = null;
    private TextView _viewCurrentWeather = null;
    private ImageView _imageCurrentWeather = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _textZipCode = (EditText) findViewById(R.id.text_zip_code);
        Button buttonDisplayCurrentWeather = (Button) findViewById(R.id.button_display_current_weather);
        _imageCurrentWeather = (ImageView) findViewById(R.id.image_current_weather);

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
            _viewCurrentWeather.setText(""); // clear the previous weather information
            _imageCurrentWeather.setVisibility(View.INVISIBLE);
            String message = "Please enter a 5-digit zip-code.";
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
                // TODO improve error handling
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
        Log.v(TAG, weather.toDebugString());
        _viewCurrentWeather.setText(weather.toDebugString());
        new FetchImageTask().execute(weather.getWeatherIconUrl());
    }

    class FetchImageTask extends AsyncTask<String, Integer, Drawable> {

        @Override
        protected Drawable doInBackground(String... arguments) {
            String url = arguments[0];
            try {
                InputStream is = (InputStream) new URL(url).getContent();
                Drawable d = Drawable.createFromStream(is, "src");
                return d;
            } catch (MalformedURLException e) {
                // TODO improve error handling
                return null;
            } catch (IOException e) {
                // TODO improve error handling
                return null;
            }
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            if (drawable != null) {
                MainActivity.this.displayCurrentWeatherImage(drawable);
            }
        }
    }

    private void displayCurrentWeatherImage(Drawable drawable) {
        _imageCurrentWeather.setVisibility(View.VISIBLE);
        _imageCurrentWeather.setImageDrawable(drawable);
    }
}