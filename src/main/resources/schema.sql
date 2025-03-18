DROP TABLE IF EXISTS WEATHER_DATA;

CREATE TABLE WEATHER_DATA
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    timestamp TIMESTAMP,
    station        VARCHAR NOT NULL,
    wmocode        VARCHAR NOT NULL,
    airtemperature double,
    windspeed      VARCHAR NOT NULL,
    phenomenon     VARCHAR NOT NULL
);