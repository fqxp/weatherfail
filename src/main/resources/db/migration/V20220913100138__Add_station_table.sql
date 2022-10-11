CREATE TABLE weather_station (
    id INT PRIMARY KEY,
    dwd_station_id INT NOT NULL,
    latitude NUMERIC,
    longitude NUMERIC
);

ALTER TABLE weather_forecast
    ADD COLUMN weather_station_id INT;

ALTER TABLE weather_forecast
    ADD CONSTRAINT fk_weather_station_id
        FOREIGN KEY(weather_station_id)
        REFERENCES weather_station(id);