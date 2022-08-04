package com.sisal.airline.repository;

import com.sisal.airline.entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, Integer> {
    public List<AircraftEntity> findByAirlineId(Integer airlineId);
}
