package de.mindmatters.weatherfail;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class WeatherServiceClientTest {
    private static MockWebServer mockWebServer;
    private final WeatherServiceClient client;

    @Autowired
    public WeatherServiceClientTest(WeatherServiceClient client) {
        this.client = client;
    }

    final static class FakeWeatherServiceAdapter extends WeatherServiceAdapter {
        private final String hostName;
        private final int port;

        public FakeWeatherServiceAdapter(String hostName, int port) {
            this.hostName = hostName;
            this.port = port;
        }

        public URI generateURL(double latitude, double longitude, LocalDate localDate) {
            String formattedDate = localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            String uriString = "http://%s:%d/weather?lat=%f&long=%f&date=%s".formatted(
                    hostName, port, latitude, longitude, formattedDate);

            return URI.create(uriString);
        }

        public Result parseWeatherForecast(HttpResponse<String> response) {
            return new Result(ZonedDateTime.parse("2022-05-09T12:00:00+00:00"), 32.0);
        }
    }

    @BeforeAll
    public static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void retrieveForecast_parsesWeatherServiceResponseIntoWeatherForecastModel() throws InterruptedException, IOException {
        var adapter = new FakeWeatherServiceAdapter(mockWebServer.getHostName(), mockWebServer.getPort());
        var mockResponse = new MockResponse()
                .setBody("{}")
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockResponse);
        var date = LocalDate.of(2022, Calendar.SEPTEMBER, 6);

        var result = client.retrieveForecast(adapter, 53.5623857, 9.9595470, date);

        assertEquals(ZonedDateTime.parse("2022-05-09T12:00:00+00:00"), result.getTimestamp());
        assertEquals(32.0, result.getTemperature());
    }
}