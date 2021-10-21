package com.hackerrank.weather.repository.impl;

import com.hackerrank.weather.entities.Weather;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.repository.WeatherRepositoryCustom;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WeatherRepositoryCustomImpl implements WeatherRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Weather> searchWeather(WeatherSearchCriteria searchCriteria) {
        final CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Weather> criteriaQuery = builder.createQuery(Weather.class);
        final Root<Weather> weather = criteriaQuery.from(Weather.class);

        criteriaQuery.select(weather); //SELECT FROM

        List<Predicate> predicates = getFilterPredicates(searchCriteria, builder, weather);
        if (!predicates.isEmpty()) {
            criteriaQuery.where(predicates.toArray(new Predicate[]{})); //WHERE
        }

        if (StringUtils.isNotEmpty(searchCriteria.getSortKey())) {
            final String sortOrder = searchCriteria.getSortOrder();
            final String sortKey = searchCriteria.getSortKey();
            final Expression<String> expression = builder.trim(weather.get(sortKey));

            List<Order> orderList = new ArrayList<>();

            if ("desc".equalsIgnoreCase(sortOrder)) {
                orderList.add(builder.desc(expression));

            } else if ("asc".equalsIgnoreCase(sortOrder)){
                orderList.add(builder.asc(expression));
            }
            orderList.add(builder.asc(weather.get("id")));
            criteriaQuery.orderBy(orderList); //ORDER BY

        }

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    private List<Predicate> getFilterPredicates(WeatherSearchCriteria searchCriteria, CriteriaBuilder builder, Root<Weather> weather) {
        Predicate dateCondition = null;
        Predicate citiesCondition = null;

        if (searchCriteria.getDate() != null) {
            dateCondition = builder.equal(weather.get("date"), searchCriteria.getDate());
        }

        if (searchCriteria.getCities() != null) {
            citiesCondition = builder.upper(weather.get("city")).in(searchCriteria.getCities());
        }

        return Stream.of(dateCondition, citiesCondition).filter(Objects::nonNull).collect(Collectors.toList());
    }
}
