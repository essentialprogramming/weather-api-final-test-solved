package com.hackerrank.weather.mapper;

import com.hackerrank.weather.model.WeatherSearchCriteria;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class WeatherSearchCriteriaMapper {

    private static final String SPLIT_CHAR = ",";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static WeatherSearchCriteria map(final String date, final String cities, String sort){
        final WeatherSearchCriteria searchCriteria = new WeatherSearchCriteria();

        if (date != null){
            searchCriteria.setDate(LocalDate.parse(date, formatter));
        }

        if (cities != null){
            searchCriteria.setCities(Collections.list(new StringTokenizer(cities.trim(), SPLIT_CHAR)).stream()
                    .map(token -> (String) token)
                    .map(String::toUpperCase)
                    .collect(Collectors.toList()));
        }

        if (sort != null && sort.equalsIgnoreCase("date")) {
            searchCriteria.setSortKey("date");
            searchCriteria.setSortOrder("ASC");
        }

        if (sort != null && sort.equalsIgnoreCase("-date")) {
            searchCriteria.setSortKey("date");
            searchCriteria.setSortOrder("DESC");
        }

        return searchCriteria;
    }
}
