package com.orbitz.interview.weatherman;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sjain on 5/31/13.
 */
public class MainActivity extends Activity {

    private EditText _textZipCode = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _textZipCode = (EditText) findViewById(R.id.text_zip_code);
        Button buttonDisplayCurrentWeather = (Button) findViewById(R.id.button_display_current_weather);
        buttonDisplayCurrentWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.displayCurrentWeather();
            }
        });

    }

    private void displayCurrentWeather() {
        System.out.println("display current weather clicked.");
        Context context = getApplicationContext();
        CharSequence text = _textZipCode.getText();
        if (text.length() != 5)
        {
            text = "Please enter 5-digit zip code.";
        }
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}