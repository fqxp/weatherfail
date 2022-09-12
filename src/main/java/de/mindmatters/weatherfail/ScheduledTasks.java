package de.mindmatters.weatherfail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
public class ScheduledTasks {
    private final static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private final WeatherServiceClient client;
    private final WeatherServiceAdapter adapter;
    private final WeatherForecastRepository repository;

    @Autowired
    ScheduledTasks(WeatherServiceAdapter adapter, WeatherServiceClient client, WeatherForecastRepository repository) {
        this.adapter = adapter;
        this.client = client;
        this.repository = repository;
    }

    @Scheduled(cron = "${weatherfail.runner.cron}")
    private void run() {
        logger.debug("Retrieving weather forecast ...");

        try {
            var forecast = client.retrieveForecast(adapter, LocalDate.now());
            logger.debug("Temperature forecast at %s: %.2f C\n".formatted(forecast.getTimestamp(), forecast.getTemperature()));

            repository.save(forecast);
        } catch (IOException | InterruptedException e) {
            logger.error("Error while retrieving weather data: %s".formatted(e));
            throw new RuntimeException(e);
        }
    }
}
