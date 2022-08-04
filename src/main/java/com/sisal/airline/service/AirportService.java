package com.sisal.airline.service;

import com.sisal.airline.entity.AirportEntity;
import com.sisal.airline.exception.BusinessException;
import com.sisal.airline.repository.AirportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    public List<AirportEntity> getAirportList() {
        List<AirportEntity> airportList = airportRepository.findAll();
        if (airportList.isEmpty()) {
            throw new BusinessException("Airport Not Found");
        }
        return airportList;
    }
}
