package de.mindmatters.weatherfail;

import de.mindmatters.weatherfail.adapters.WeatherServiceAdapter;
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

@SpringBootTest // <-- erlaubt dir dependency-injection in Tests und kann ggf. ne Ganze Anwendung für Integrationtests hochfahren
class WeatherServiceClientTest {

    private static MockWebServer mockWebServer;

    private final WeatherServiceClient client;

    @Autowired
    WeatherServiceClientTest(WeatherServiceClient client) {
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
        var fakeWeatherServiceAdapter = new FakeWeatherServiceAdapter(mockWebServer.getHostName(), mockWebServer.getPort());
        var mockResponse = new MockResponse()
                .setBody("{}")
                .addHeader("Content-Type", "application/json");
        mockWebServer.enqueue(mockResponse);

        WeatherForecast result = client.retrieveForecast(
                fakeWeatherServiceAdapter,
                LocalDate.of(2022, Calendar.SEPTEMBER, 6)
        );

        assertEquals(ZonedDateTime.parse("2022-05-09T12:00:00+00:00"), result.getTimestamp());
        assertEquals(32.0, result.getTemperature());
    }
}