package de.mindmatters.weatherfail;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class WeatherForecast {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private ZonedDateTime timestamp;
    private double temperature;

    public WeatherForecast(ZonedDateTime timestamp, double temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
    }
}