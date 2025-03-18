DROP TABLE IF EXISTS WEATHER_DATA;

CREATE TABLE WEATHER_DATA
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    timestamp TIMESTAMP,
    station        VARCHAR NOT NULL,
    wmoCode        VARCHAR NOT NULL,
    airTemperature double,
    windSpeed      VARCHAR NOT NULL,
    phenomenon     VARCHAR NOT NULL
);