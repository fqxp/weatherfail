package de.mindmatters.weatherfail;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
class BrightskyWeatherServiceAdapter extends WeatherServiceAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BrightskyWeatherServiceAdapter.class);

    public URI generateURL(double latitude, double longitude, LocalDate date) {
        var formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        var uri = String.format(
                Locale.US,
                "https://api.brightsky.dev/weather?lat=%f&lon=%f&date=%s&tz=Europe/Berlin",
                latitude, longitude, formattedDate
        );

        logger.debug("generated URI: %s".formatted(uri));

        return URI.create(uri);
    }

    public WeatherForecast parseWeatherForecast(HttpResponse<String> response) {
        logger.debug("parsing response: %s".formatted(response));

        var jo = new JSONObject(response.body());
        var weather = jo.getJSONArray("weather").getJSONObject(0);
        var timestamp = ZonedDateTime.parse(weather.getString("timestamp"));
        var temperature = weather.getDouble("temperature");

        return new WeatherForecast(timestamp, temperature);
    }
}
