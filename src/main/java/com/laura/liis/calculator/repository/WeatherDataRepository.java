package com.laura.liis.calculator.repository;

import com.laura.liis.calculator.entity.WeatherDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherDataRepository extends JpaRepository<WeatherDataEntity, Long>{
}
