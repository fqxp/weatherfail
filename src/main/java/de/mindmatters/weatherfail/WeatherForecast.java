package de.mindmatters.weatherfail;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    @ManyToOne
    private WeatherStation weatherStation;

    public WeatherForecast(ZonedDateTime timestamp, double temperature) {
        this.timestamp = timestamp;
        this.temperature = temperature;
    }
}