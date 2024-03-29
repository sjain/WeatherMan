package com.orbitz.interview.weatherman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
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
    private Button _buttonDisplay5DayWeather = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _textZipCode = (EditText) findViewById(R.id.text_zip_code);
        _imageCurrentWeather = (ImageView) findViewById(R.id.image_weather_icon);
        _viewCurrentWeather = (TextView) findViewById(R.id.view_weather_info);

        Button buttonDisplayCurrentWeather = (Button) findViewById(R.id.button_display_current_weather);
        buttonDisplayCurrentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.displayCurrentWeather();
            }
        });

        _buttonDisplay5DayWeather = (Button) findViewById(R.id.button_display_5day_weather);
        _buttonDisplay5DayWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.display5DayWeather();
            }
        });
        _buttonDisplay5DayWeather.setVisibility(View.INVISIBLE);
    }

    private void displayCurrentWeather() {
        CharSequence zipCode = _textZipCode.getText();
        if (zipCode.length() != 5)
        {
            _viewCurrentWeather.setText(""); // clear the previous weather information
            _imageCurrentWeather.setVisibility(View.INVISIBLE);
            _buttonDisplay5DayWeather.setVisibility(View.INVISIBLE);
            String message = "Please enter a 5-digit zip-code.";
            showError(message);
            return;
        }

        String zipCodeString = zipCode.toString();
        Log.v(TAG, zipCodeString);
        new FetchWeatherTask().execute(zipCodeString);
    }

    private void display5DayWeather() {
        CharSequence zipCode = _textZipCode.getText();

        String zipCodeString = zipCode.toString();
        Log.v(TAG, zipCodeString);
        Intent intent = new Intent(getBaseContext(), DailyWeatherListActivity.class);
        intent.putExtra("ZIPCODE", zipCodeString);
        startActivity(intent);
    }

    private void showError(String message) {
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
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
                MainActivity.this.displayWeather(weather);
            }
        }
    }

    private void displayWeather(Weather weather) {
        Log.v(TAG, weather.toDebugString());
        _viewCurrentWeather.setText(weather.toDebugString());
        _imageCurrentWeather.setVisibility(View.VISIBLE);
        _buttonDisplay5DayWeather.setVisibility(View.VISIBLE);
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
                Log.e(TAG, "failed to fetch image", e);
                return null;
            } catch (IOException e) {
                // TODO improve error handling
                Log.e(TAG, "failed to fetch image", e);
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
        _imageCurrentWeather.setImageDrawable(drawable);
    }
}