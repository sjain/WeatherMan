package com.orbitz.interview.weatherman.model;

/**
 * Created by sjain on 6/1/13.
 */
public class DailyWeather {
    private int _tempMinF;
    private int _tempMaxF;
    private String _weatherDescription;
    private String _weatherIconUrl;
    private String _date;
    private double _precipitation;

    public DailyWeather(int tempMinF, int tempMaxF, String weatherDescription, String weatherIconUrl, String date, double precipitation) {
        _tempMinF = tempMinF;
        _tempMaxF = tempMaxF;
        _weatherDescription = weatherDescription;
        _weatherIconUrl = weatherIconUrl;
        _date = date;
        _precipitation = precipitation;
    }

    public int getTempMinF() {
        return _tempMinF;
    }

    public int getTempMaxF() {
        return _tempMaxF;
    }

    public String getWeatherDescription() {
        return _weatherDescription;
    }

    public String getWeatherIconUrl() {
        return _weatherIconUrl;
    }

    public String getDate() {
        return _date;
    }

    public double getPrecipitation() {
        return _precipitation;
    }

    public String toDebugString() {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append(this.getDate());
        buffer.append("\n");
        buffer.append(this.getTempMinF());
        buffer.append("-");
        buffer.append(this.getTempMaxF());
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
