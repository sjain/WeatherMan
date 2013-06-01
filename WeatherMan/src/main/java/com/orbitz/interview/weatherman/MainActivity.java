package com.orbitz.interview.weatherman;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _textZipCode = (EditText) findViewById(R.id.text_zip_code);
        Button buttonDisplayCurrentWeather = (Button) findViewById(R.id.button_display_current_weather);

        buttonDisplayCurrentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.displayLocalWeather();
            }
        });
    }

    private void displayLocalWeather() {
        CharSequence zipCode = _textZipCode.getText();
        if (zipCode.length() != 5)
        {
            String message = "Please enter a 5-digit zip-code.";
            showError(message);
            return;
        }

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

//    class FetchImageTask extends AsyncTask<String, Integer, Drawable> {
//
//        @Override
//        protected Drawable doInBackground(String... arguments) {
//            String url = arguments[0];
//            try {
//                InputStream is = (InputStream) new URL(url).getContent();
//                Drawable d = Drawable.createFromStream(is, "src");
//                return d;
//            } catch (MalformedURLException e) {
//                // TODO improve error handling
//                Log.e(TAG, "failed to fetch image", e);
//                return null;
//            } catch (IOException e) {
//                // TODO improve error handling
//                Log.e(TAG, "failed to fetch image", e);
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Drawable drawable) {
//            if (drawable != null) {
//                MainActivity.this.displayCurrentWeatherImage(drawable);
//            }
//        }
//    }
//
//    private void displayCurrentWeatherImage(Drawable drawable) {
////        _imageCurrentWeather.setVisibility(View.VISIBLE);
////        _imageCurrentWeather.setImageDrawable(drawable);
//    }
}