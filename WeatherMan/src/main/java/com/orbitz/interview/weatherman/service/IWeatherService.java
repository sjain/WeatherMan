package com.orbitz.interview.weatherman.service;

import com.orbitz.interview.weatherman.model.Weather;

import java.io.IOException;

/**
 * Created by sjain on 5/31/13.
 */
public interface IWeatherService {
    public Weather fetchCurrentWeather(String zipCode) throws Exception;
}
