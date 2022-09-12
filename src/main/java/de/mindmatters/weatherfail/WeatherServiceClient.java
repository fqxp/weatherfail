package de.mindmatters.weatherfail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class WeatherServiceClient {
    @Value("${weatherfail.location.latitude}")
    private double latitude;
    @Value("${weatherfail.location.longitude}")
    private double longitude;

    public WeatherForecast retrieveForecast(WeatherServiceAdapter adapter, LocalDate localDate) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var uri = adapter.generateURL(latitude, longitude, localDate);
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return adapter.parseWeatherForecast(response);
    }
}