package com.orbitz.interview.weatherman.model;

/**
 * Created by sjain on 5/31/13.
 */
public class Weather {
    private int _tempF;
    private String _weatherDescription;
    private String _weatherIconUrl;
    private double _precipitation;

    public Weather(int tempF, String weatherDescription, String weatherIconUrl, double precipitation)
    {
        _tempF = tempF;
        _weatherDescription = weatherDescription;
        _weatherIconUrl = weatherIconUrl;
        _precipitation = precipitation;
    }

    public int getTempF() {
        return _tempF;
    }

    public void setTempF(int _tempF) {
        this._tempF = _tempF;
    }

    public String getWeatherDescription() {
        return _weatherDescription;
    }

    public void setWeatherDescription(String _weatherDescription) {
        this._weatherDescription = _weatherDescription;
    }

    public String getWeatherIconUrl() {
        return _weatherIconUrl;
    }

    public void setWeatherIconUrl(String _weatherIconUrl) {
        this._weatherIconUrl = _weatherIconUrl;
    }

    public double getPrecipitation() {
        return _precipitation;
    }

    public void setPrecipitation(double _precipitation) {
        this._precipitation = _precipitation;
    }

    public String toDebugString() {
        StringBuffer buffer = new StringBuffer(100);
        buffer.append("Temperature(F): ");
        buffer.append(this.getTempF());
        buffer.append("\n");
        buffer.append("Description: ");
        buffer.append(this.getWeatherDescription());
        buffer.append("\n");
        buffer.append("Icon: ");
        buffer.append(this.getWeatherIconUrl());
        buffer.append("\n");
        buffer.append("Precipication: ");
        buffer.append(this.getPrecipitation());
        buffer.append("\n");

        return buffer.toString();
    }
}
