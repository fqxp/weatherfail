package de.mindmatters.weatherfail;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;

public class BrightskyWeatherServiceAdapter implements IWeatherServiceAdapter {
    public URI generateURL(double latitude, double longitude, LocalDate date) {
        String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String uriString = String.format(
                "https://api.brightsky.dev/weather?lat=%f&lon=%f&date=%s&tz=Europe/Berlin",
                latitude, longitude, formattedDate
        );

        return URI.create(uriString);
    }

    public WeatherForecast parseWeatherForecast(HttpResponse<String> response) {
        JSONObject jo = new JSONObject(response.body());
        JSONObject weather = jo.getJSONArray("weather").getJSONObject(0);
        ZonedDateTime timestamp = ZonedDateTime.parse(weather.getString("timestamp"));
        float temperature = weather.getFloat("temperature");

        return new WeatherForecast(timestamp, temperature);
    }
}