package de.mindmatters.weatherfail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx, WeatherServiceClient weatherServiceClient) {
        return args -> {
            System.out.println("Retrieving weather forecast ...");
//			System.out.println(weatherServiceClient.retrieveForecast());
        };
    }

    @Bean
    public WeatherServiceClient getWeatherServiceClient() {
        WeatherServiceAdapter client = new BrightskyWeatherServiceAdapter();
        return new WeatherServiceClient(client);
    }
}