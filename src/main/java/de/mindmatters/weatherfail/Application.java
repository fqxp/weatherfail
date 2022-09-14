package de.mindmatters.weatherfail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(WeatherServiceAdapter adapter, WeatherServiceClient client, WeatherForecastRepository repository) {
//        return args -> {
//            System.out.println("Retrieving weather forecast ...");
//            var forecast = client.retrieveForecast(adapter, 53.5623857, 9.9595470, LocalDate.now());
//            System.out.printf("Temperature forecast at %s: %.2f C\n", forecast.getTimestamp(), forecast.getTemperature());
//        };
//    }
}