package com.laura.liis.calculator.repository;

import com.laura.liis.calculator.dto.ObservationsDto;
import com.laura.liis.calculator.entity.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, String>{
    Optional<WeatherDataEntity> findByStation(String station);
}
