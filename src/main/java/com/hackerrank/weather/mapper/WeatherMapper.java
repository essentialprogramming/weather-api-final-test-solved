package com.hackerrank.weather.mapper;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.model.WeatherInput;
import com.hackerrank.weather.output.WeatherJSON;
import com.hackerrank.weather.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class WeatherMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static Weather inputToWeather(WeatherInput input){

        return Weather.builder()
                .city(input.getCity())
                .state(input.getState())
                .lat(input.getLat())
                .lon(input.getLon())
                .date(LocalDate.parse(input.getDate(),formatter))
                .temperatures(input.getTemperatures().stream().map(BigDecimal::valueOf).collect(Collectors.toList()))
                .build();
    }

    public static WeatherJSON entityToJSON(Weather weather) {
        return WeatherJSON.builder()
                .id(weather.getId())
                .lat(weather.getLat())
                .lon(weather.getLon())
                .city(weather.getCity())
                .state(weather.getState())
                .date(weather.getDate().format(formatter))
                .temperatures(weather.getTemperatures().stream().map(BigDecimal::doubleValue).collect(Collectors.toList()))
                .build();
    }
}
