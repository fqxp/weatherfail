package de.mindmatters.weatherfail;

import lombok.Data;
import lombok.NonNull;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public abstract class WeatherServiceAdapter {
    @Data
    static class Result {
        @NonNull
        private ZonedDateTime timestamp;
        @NonNull
        private Double temperature;
    }

    public abstract URI generateURL(double latitude, double longitude, LocalDate date);

    public abstract Result parseWeatherForecast(HttpResponse<String> response);
}
