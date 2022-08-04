package com.sisal.airline.service;

import com.sisal.airline.entity.AircraftEntity;
import com.sisal.airline.exception.BusinessException;
import com.sisal.airline.repository.AircraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AircraftService {

    private final AircraftRepository aircraftRepository;

    public List<AircraftEntity> getAircraftList(Integer airlineId) {
        List<AircraftEntity> aircraftList = aircraftRepository.findByAirlineId(airlineId);
        if (aircraftList.isEmpty()) {
            throw new BusinessException("AirCraft Not Found");
        }
        return aircraftList;
    }
}
