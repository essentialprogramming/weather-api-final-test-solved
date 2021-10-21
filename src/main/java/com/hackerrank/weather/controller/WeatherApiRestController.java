package com.hackerrank.weather.controller;

import com.hackerrank.weather.mapper.WeatherMapper;
import com.hackerrank.weather.mapper.WeatherSearchCriteriaMapper;
import com.hackerrank.weather.model.WeatherInput;
import com.hackerrank.weather.model.WeatherSearchCriteria;
import com.hackerrank.weather.output.WeatherJSON;
import com.hackerrank.weather.service.WeatherService;
import com.hackerrank.weather.utils.Patterns;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Optional;

@RestController
public class WeatherApiRestController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherApiRestController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    @Operation(summary = "Returns weather information", tags = {"Weather",},
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Returns  weather information",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = WeatherInput.class)))
            })
    public List<WeatherJSON> getWeatherInfo(@Valid @Pattern(regexp = Patterns.YYYY_MM_DD_REGEXP) @RequestParam(required = false) String date, @RequestParam(required = false) String city, @RequestParam(required = false) String sort){
        final WeatherSearchCriteria searchCriteria = WeatherSearchCriteriaMapper.map(date, city, sort);
        return weatherService.getWeatherInfo(searchCriteria);
    }

    @GetMapping("/weather/{id}")
    @Operation(summary = "Load weather", description = "This method loads a weather record by ID",tags = {"Weather",},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successful response",
                            content = @Content(schema = @Schema(implementation = WeatherInput.class))),

            })
    public ResponseEntity<WeatherJSON> getWeatherById(@Valid @NotNull @PathVariable Integer id) {
        Optional<com.hackerrank.weather.entities.Weather> weatherOptional = weatherService.findById(id);

        return weatherOptional.map(weather -> ResponseEntity.status(HttpStatus.OK).body(WeatherMapper.entityToJSON(weather)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    /**
     * Submit weather.
     *
     */
    @PostMapping(path = "/weather")
    @Operation(summary = "Request to submit weather", description = "This method creates a new weather data record",tags = {"Weather",},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successful response",
                            content = @Content(schema = @Schema(implementation = WeatherInput.class))),

            })

    public ResponseEntity<WeatherJSON> submitWeather(@RequestBody @Valid final WeatherInput weatherInput) {
        final com.hackerrank.weather.entities.Weather weather = weatherService.save(weatherInput);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(WeatherMapper.entityToJSON(weather));
    }


}
