package de.mindmatters.weatherfail;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class WeatherServiceClient {
    private final WeatherServiceAdapter weatherServiceAdapter;

    public WeatherServiceClient(WeatherServiceAdapter weatherServiceAdapter) {
        this.weatherServiceAdapter = weatherServiceAdapter;
    }

    public WeatherForecast retrieveForecast(double latitude, double longitude, LocalDate localDate) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var uri = weatherServiceAdapter.generateURL(latitude, longitude, localDate);
        var request = HttpRequest.newBuilder()
                .uri(uri)
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return weatherServiceAdapter.parseWeatherForecast(response);
    }
}