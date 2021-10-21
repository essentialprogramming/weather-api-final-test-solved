package com.hackerrank.weather.repository;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;

import java.util.List;

public interface WeatherRepositoryCustom {

    List<Weather> searchWeather(WeatherSearchCriteria weatherSearchCriteria);
}
