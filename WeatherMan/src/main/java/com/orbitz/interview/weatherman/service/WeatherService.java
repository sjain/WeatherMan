package com.orbitz.interview.weatherman.service;

import com.orbitz.interview.weatherman.model.Weather;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by sjain on 5/31/13.
 */
public class WeatherService implements IWeatherService {

    private static final String CURRENT_WEATHER_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?format=json&date=today&key=w242cfytkxe793yepgc46trr&q=";

    @Override
    public Weather fetchCurrentWeather(String zipCode) throws Exception {

        // fetch JSON
        HttpGet request = new HttpGet(CURRENT_WEATHER_URL + zipCode);
        HttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = httpClient.execute(request);
        HttpEntity entityResponse = response.getEntity();
        String json = EntityUtils.toString(entityResponse);

        // parse JSON
        JSONObject object = new JSONObject(json);
        JSONArray currentConditions = object.getJSONObject("data").getJSONArray("current_condition");
        JSONObject currentCondition = currentConditions.getJSONObject(0);
        int currentTempF = currentCondition.getInt("temp_F");
        String currentWeatherDescription = currentCondition.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
        String currentWeatherIconUrl = currentCondition.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value");
        double precipitation = currentCondition.getDouble("precipMM");

        Weather currentWeather = new Weather(currentTempF, currentWeatherDescription, currentWeatherIconUrl, precipitation);
        return currentWeather;
    }
}
