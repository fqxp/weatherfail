package de.mindmatters.weatherfail;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class WeatherStation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long dwd_station_id;
    private double longitude;
    private double latitude;

    protected WeatherStation() {
    }

    public WeatherStation(Long dwd_station_id, double longitude, double latitude) {
        this.dwd_station_id = dwd_station_id;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}