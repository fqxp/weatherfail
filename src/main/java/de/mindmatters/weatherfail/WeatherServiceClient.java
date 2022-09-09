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

    @Value("${weatherfail.location.latitude}") // <-- @Value zieht dir einzelne Werte aus deiner application.[yml|properties]. Wenn du mehrere Props zusammenfassen willst, gibts @ConfigurationProperties
    private double latitude;

    @Value("${weatherfail.location.longitude}")
    private double longitude;

    public WeatherForecast retrieveForecast(WeatherServiceAdapter adapter, LocalDate localDate) throws IOException, InterruptedException {
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder()
                .uri(adapter.generateURL(latitude, longitude, localDate))
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return adapter.parseWeatherForecast(response);
    }
}