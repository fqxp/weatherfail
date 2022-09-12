package de.mindmatters.weatherfail;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
class BrightskyWeatherServiceAdapter extends WeatherServiceAdapter {
    public URI generateURL(double latitude, double longitude, LocalDate date) {
        String formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String uriString = String.format(
                Locale.US,
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
