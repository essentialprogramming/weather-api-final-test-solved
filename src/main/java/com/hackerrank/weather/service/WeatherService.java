package com.hackerrank.weather.service;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.mapper.WeatherMapper;
import com.hackerrank.weather.model.WeatherInput;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.output.WeatherJSON;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final WeatherRepository weatherRepository;

    @Autowired
    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public Weather save(final WeatherInput weatherInput) {
        final Weather weather = WeatherMapper.inputToWeather(weatherInput);
        weatherRepository.save(weather);

        return weather;
    }

    public List<WeatherJSON> getWeatherInfo(WeatherSearchCriteria searchCriteria) {
        final List<Weather> weatherList;
        if (searchCriteria.isEmpty()) {
            weatherList = weatherRepository.findAll();
        } else {
            weatherList = weatherRepository.searchWeather(searchCriteria);
        }

        return weatherList.stream()
                .map(WeatherMapper::entityToJSON)
                .collect(Collectors.toList());

    }

    public Optional<Weather> findById(int id) {
        return weatherRepository.findById(id);
    }
}
