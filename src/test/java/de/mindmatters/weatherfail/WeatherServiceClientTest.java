package de.mindmatters.weatherfail;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeatherServiceClientTest {
    final static class FakeWeatherServiceAdapter implements IWeatherServiceAdapter {
        private final String hostName;
        private final int port;

        public FakeWeatherServiceAdapter(String hostName, int port) {
            this.hostName = hostName;
            this.port = port;
        }

        public URI generateURL(double latitude, double longitude, LocalDate date) {
            String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
            String uriString = String.format(
                    "http://%s:%d/weather?lat=%f&long=%f&date=%s",
                    hostName, port, latitude, longitude, formattedDate
            );

            return URI.create(uriString);
        }

        public WeatherForecast parseWeatherForecast(HttpResponse<String> response) {
            return new WeatherForecast(ZonedDateTime.parse("2022-05-09T12:00:00+00:00"), 32.0);
        }
    }

    public static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void retrieveForecast_parsesWeatherServiceResponseIntoWeatherForecastModel() throws InterruptedException, IOException {
        var fakeWeatherServiceAdapter = new FakeWeatherServiceAdapter(mockWebServer.getHostName(), mockWebServer.getPort());
        var client = new WeatherServiceClient(fakeWeatherServiceAdapter);
        var mockResponse = new MockResponse()
                .setBody("{}")
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockResponse);

        WeatherForecast result = client.retrieveForecast(12.34, 56.78,
                LocalDate.of(2022, Calendar.SEPTEMBER, 6));

        assertEquals(ZonedDateTime.parse("2022-05-09T12:00:00+00:00"), result.getTimestamp());
        assertEquals(32.0, result.getTemperature());
    }
}