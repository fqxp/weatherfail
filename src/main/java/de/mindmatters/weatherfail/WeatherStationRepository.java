package de.mindmatters.weatherfail;

import org.springframework.data.repository.CrudRepository;

public interface WeatherStationRepository extends CrudRepository<WeatherStation, Long> {
}
