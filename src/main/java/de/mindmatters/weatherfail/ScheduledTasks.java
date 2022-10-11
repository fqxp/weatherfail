package de.mindmatters.weatherfail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class ScheduledTasks {
    private final static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @NonNull
    private final WeatherServiceClient client;
    @NonNull
    private final WeatherServiceAdapter adapter;
    @NonNull
    private final WeatherForecastRepository forecastRepository;
    @NonNull
    private final WeatherStationRepository stationRepository;

    @Autowired
    ScheduledTasks(WeatherServiceAdapter adapter,
                   WeatherServiceClient client,
                   WeatherForecastRepository forecastRepository,
                   WeatherStationRepository stationRepository) {
        this.adapter = adapter;
        this.client = client;
        this.forecastRepository = forecastRepository;
        this.stationRepository = stationRepository;
    }

    @Scheduled(cron = "${weatherfail.runner.cron}")
    public void run() {
        logger.debug("Retrieving weather forecast ...");

        stationRepository.findAll().forEach(station -> {
            try {
                var result = client.retrieveForecast(adapter,
                        station.getLongitude(),
                        station.getLatitude(),
                        LocalDate.now());
                logger.debug("Temperature forecast at %s: %.2f C\n".formatted(result.getTimestamp(), result.getTemperature()));

                var forecast = new WeatherForecast(result.getTimestamp(), result.getTemperature());
                forecastRepository.save(forecast);
            } catch (IOException | InterruptedException e) {
                logger.error("Error while retrieving weather data: %s".formatted(e));
                throw new RuntimeException(e);
            }

        });
    }
}