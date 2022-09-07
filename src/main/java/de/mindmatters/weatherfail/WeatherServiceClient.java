package de.mindmatters.weatherfail;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
public class WeatherServiceClient {
    private final IWeatherServiceAdapter weatherServiceAdapter;

    public WeatherServiceClient(IWeatherServiceAdapter weatherServiceAdapter) {
        this.weatherServiceAdapter = weatherServiceAdapter;
    }

    public WeatherForecast retrieveForecast(double latitude, double longitude, LocalDate date) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(weatherServiceAdapter.generateURL(latitude, longitude, date))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return weatherServiceAdapter.parseWeatherForecast(response);
    }
}