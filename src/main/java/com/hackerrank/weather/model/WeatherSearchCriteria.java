package com.hackerrank.weather.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class WeatherSearchCriteria {

    private LocalDate date;
    private List<String> cities;
    private String sortKey;
    private String sortOrder;

    public boolean isEmpty(){
        return date == null && cities == null && sortKey == null;
    }
}
