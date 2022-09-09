package de.mindmatters.weatherfail.adapters;

import de.mindmatters.weatherfail.WeatherForecast;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public abstract class WeatherServiceAdapter {
    public abstract URI generateURL(double latitude, double longitude, LocalDate date);

    public abstract WeatherForecast parseWeatherForecast(HttpResponse<String> response);
}
