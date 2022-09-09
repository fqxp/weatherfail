package de.mindmatters.weatherfail.adapters;

import de.mindmatters.weatherfail.WeatherForecast;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;

// Diese Klasse existiert nur, um im ScheduledRunner zu zeigen, wie man mehrere Adapter nutzen könnte
public class FakeWeatherServiceAdapter extends WeatherServiceAdapter {

    @Override
    public URI generateURL(double latitude, double longitude, LocalDate date) {
        return URI.create("https://mindmatters.de"); // HTTP 200 wäre zu hoffen
    }

    @Override
    public WeatherForecast parseWeatherForecast(HttpResponse<String> response) {
        return new WeatherForecast(ZonedDateTime.now(), 13.37);
    }
}

