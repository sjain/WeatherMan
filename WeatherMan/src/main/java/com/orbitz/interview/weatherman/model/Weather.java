package com.orbitz.interview.weatherman.model;

import java.util.List;

/**
 * Created by sjain on 5/31/13.
 */
public class Weather {
    private int _tempF;
    private String _weatherDescription;
    private String _weatherIconUrl;
    private double _precipitation;
    private List<DailyWeather> _dailyWeathers;

    public Weather(int tempF, String weatherDescription, String weatherIconUrl, double precipitation, List<DailyWeather> dailyWeathers)
    {
        _tempF = tempF;
        _weatherDescription = weatherDescription;
        _weatherIconUrl = weatherIconUrl;
        _precipitation = precipitation;
        _dailyWeathers = dailyWeathers;
    }

    public int getTempF() {
        return _tempF;
    }

    public String getWeatherDescription() {
        return _weatherDescription;
    }

    public String getWeatherIconUrl() {
        return _weatherIconUrl;
    }

    public double getPrecipitation() {
        return _precipitation;
    }

    public List<DailyWeather> getDailyWeathers() {
        return _dailyWeathers;
    }

    public String toDebugString() {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append("Current:");
        buffer.append("\n");
        buffer.append(this.getTempF());
        buffer.append("F");
        buffer.append(" (");
        buffer.append(this.getWeatherDescription());
        buffer.append(")");
        buffer.append("\n");
        buffer.append("Precip: ");
        buffer.append(this.getPrecipitation());
        buffer.append("MM");
        buffer.append("\n");

        return buffer.toString();
    }
}
