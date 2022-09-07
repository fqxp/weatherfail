package de.mindmatters.weatherfail;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public interface IWeatherServiceAdapter {
    URI generateURL(double latitude, double longitude, LocalDate date);

    WeatherForecast parseWeatherForecast(HttpResponse<String> response);
}
