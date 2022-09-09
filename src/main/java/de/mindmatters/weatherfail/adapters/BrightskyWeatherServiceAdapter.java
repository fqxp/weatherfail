package de.mindmatters.weatherfail.adapters;

import de.mindmatters.weatherfail.WeatherForecast;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class BrightskyWeatherServiceAdapter extends WeatherServiceAdapter {

    private static final Logger logger = LoggerFactory.getLogger(BrightskyWeatherServiceAdapter.class);

    public URI generateURL(double latitude, double longitude, LocalDate date) {
        var formattedDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        var uriString = String.format(
                "https://api.brightsky.dev/weather?lat=%f&lon=%f&date=%s&tz=Europe/Berlin",
                latitude, longitude, formattedDate
        );

        logger.debug("generated uriString " + uriString);
        return URI.create(uriString);
    }

    public WeatherForecast parseWeatherForecast(HttpResponse<String> response) {
        logger.debug("parsing response " + response);

        var jo = new JSONObject(response.body());
        var weather = jo.getJSONArray("weather").getJSONObject(0);
        var timestamp = ZonedDateTime.parse(weather.getString("timestamp"));
        var temperature = weather.getDouble("temperature");

        return new WeatherForecast(timestamp, temperature);
    }
}
