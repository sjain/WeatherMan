package com.orbitz.interview.weatherman.service;

import com.orbitz.interview.weatherman.model.DailyWeather;
import com.orbitz.interview.weatherman.model.Weather;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sjain on 5/31/13.
 */
public class WeatherService implements IWeatherService {

    private static final String CURRENT_WEATHER_URL = "http://api.worldweatheronline.com/free/v1/weather.ashx?format=json&num_of_days=5&key=w242cfytkxe793yepgc46trr&q=";

    @Override
    public Weather fetchWeather(String zipCode) throws Exception {

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

        List<DailyWeather> dailyWeathers = parseAllDailyWeathers(object);

        Weather currentWeather = new Weather(currentTempF, currentWeatherDescription, currentWeatherIconUrl, precipitation, dailyWeathers);

        return currentWeather;
    }

    private List<DailyWeather> parseAllDailyWeathers(JSONObject object) throws JSONException {
        JSONArray dailyWeathersJson = object.getJSONObject("data").getJSONArray("weather");
        List<DailyWeather> dailyWeathers = new ArrayList<DailyWeather>(5);
        for(int i=0; i<dailyWeathersJson.length(); i++) {
            JSONObject dailyWeatherJson = dailyWeathersJson.getJSONObject(i);
            dailyWeathers.add(parseDailyWeather(dailyWeatherJson));
        }
        return dailyWeathers;
    }

    private DailyWeather parseDailyWeather(JSONObject dailyWeatherJson) throws JSONException {
        int tempMinF = dailyWeatherJson.getInt("tempMinF");
        int tempMaxF = dailyWeatherJson.getInt("tempMaxF");
        String weatherDate = dailyWeatherJson.getString("date");
        String weatherDescription = dailyWeatherJson.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
        String weatherIconUrl = dailyWeatherJson.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value");
        double precipitation = dailyWeatherJson.getDouble("precipMM");
        return new DailyWeather(tempMinF, tempMaxF, weatherDescription, weatherIconUrl, weatherDate, precipitation);
    }
}
