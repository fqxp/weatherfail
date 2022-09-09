package de.mindmatters.weatherfail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@Component
public class ScheduledRunner {

  private static final Logger logger = LoggerFactory.getLogger(ScheduledRunner.class);

  private static final Set<WeatherServiceAdapter> adapters = Set.of( // <-- Set.of und List.of geben dir immutable collections und sind ganz praktisch
          new BrightskyWeatherServiceAdapter()
//          new AnotherWeatherServiceAdapter(), <-- hier kämen andere Adapter rein
  );

  private final WeatherServiceClient client;

  @Autowired // <-- @Autowired am Konstruktor injected dir alle seine Parameter, wenn es Beans dafür gibt. Die Annotation geht auch direkt am Feld, aber davon wird stark abgeraten
  public ScheduledRunner(WeatherServiceClient client) {
    this.client = client;
  }

  @Scheduled(cron = "${weatherfail.runner.cron}")
  private void run() {
    var now = LocalDate.now();
    logger.info("Retrieving forecasts for " + now);

    adapters.forEach(adapter -> { // <-- ich geb zu, das Set oben ist auch da, um die Lambda Syntax einmal zu benutzen
      try {
        var forecast = client.retrieveForecast(adapter, now);
        logger.info("Got " + forecast.getTemperature() + " degrees from " + adapter.getClass().getName());
      } catch (IOException | InterruptedException e) { // <-- mehrere Annotationtypen abzufangen geht jetzt mit der Pipe-Syntax
        logger.error("Error while retrieving weather data: " + e.getMessage());
        throw new RuntimeException(e);
      }
    });
  }

}
