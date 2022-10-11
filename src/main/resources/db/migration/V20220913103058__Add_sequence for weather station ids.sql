CREATE SEQUENCE weather_station_id_seq;

ALTER TABLE weather_station
    ALTER COLUMN id SET DEFAULT nextval('weather_station_id_seq');

ALTER TABLE weather_station
    ALTER COLUMN id SET NOT NULL;

ALTER SEQUENCE weather_station_id_seq OWNED BY weather_station.id;