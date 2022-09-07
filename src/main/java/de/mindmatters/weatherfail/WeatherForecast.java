package de.mindmatters.weatherfail;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@AllArgsConstructor
public class WeatherForecast {
    private ZonedDateTime timestamp;
    private double temperature;
}