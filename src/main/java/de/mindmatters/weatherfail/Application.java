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
    public CommandLineRunner commandLineRunner(WeatherServiceClient weatherServiceClient, WeatherForecastRepository weatherForecastRepository) {
        return args -> {
            System.out.println("Retrieving weather forecast ...");
            WeatherForecast forecast = weatherServiceClient.retrieveForecast(53.33, 10.0, LocalDate.now());
            System.out.printf("Temperature forecast at %s: %.2f C\n", forecast.getTimestamp(), forecast.getTemperature());

            weatherForecastRepository.save(forecast);
        };
    }
}