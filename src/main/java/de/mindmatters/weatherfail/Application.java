package de.mindmatters.weatherfail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;

@SpringBootApplication
@EntityScan
@ComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(WeatherServiceAdapter adapter, WeatherServiceClient client, WeatherForecastRepository repository) {
        return args -> {
            System.out.println("Retrieving weather forecast ...");
            var forecast = client.retrieveForecast(adapter, LocalDate.now());
            System.out.printf("Temperature forecast at %s: %.2f C\n", forecast.getTimestamp(), forecast.getTemperature());

            repository.save(forecast);
        };
    }
}