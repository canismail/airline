package com.sisal.airline.service;

import com.sisal.airline.dto.AircraftDto;
import com.sisal.airline.dto.DestinationDto;
import com.sisal.airline.dto.ResponseDto;
import com.sisal.airline.entity.AircraftEntity;
import com.sisal.airline.entity.AirlineEntity;
import com.sisal.airline.entity.AirportEntity;
import com.sisal.airline.exception.BusinessException;
import com.sisal.airline.mapper.AirlineMapper;
import com.sisal.airline.repository.AirlineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.sisal.airline.constant.AirlineConstant.*;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private static final Random random;

    static {
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private final AirlineRepository airlineRepository;
    private final AircraftService aircraftService;
    private final AirportService airportService;
    private final AirlineMapper airlineMapper;

    public List<ResponseDto> getPlanning(Integer airlineCode) {
        AirlineEntity airline = getAirline(airlineCode);
        List<AircraftEntity> aircraftList = aircraftService.getAircraftList(airline.getId());
        List<AirportEntity> airportList = airportService.getAirportList();
        List<AircraftDto> responseList = new ArrayList<>();

        aircraftList.forEach(v -> {
            List<DestinationDto> destinationList = new ArrayList<>(AIRCRAFT_DAILY_FLIGHT_MAX_SIZE);
            List<Integer> calculatedDestinationList = new ArrayList<>(AIRCRAFT_DAILY_FLIGHT_MAX_SIZE);

            responseList.add(AircraftDto.builder()
                    .name(v.getName()).destinationDto(destinationList)
                    .calculatedDestination(calculatedDestinationList).build());

            Integer nextFlightId = null;

            for (int i = 0; i < AIRCRAFT_DAILY_FLIGHT_MAX_SIZE; i++) {

                AirportEntity from = selectAirport(airportList, null, nextFlightId);
                AirportEntity to = selectAirport(airportList, from.getId(), null);

                if (isDestinationFlightNotAcceptable(getFlightCalculation(from, to), responseList)) {
                    to = reSelectAirport(airportList, from, responseList);
                }

                destinationList.add(i, DestinationDto.builder()
                        .from(from.getId() + "-" + from.getName())
                        .to(to.getId() + "-" + to.getName())
                        .time(from.getCreatedDate())
                        .build());
                calculatedDestinationList.add(getFlightCalculation(from, to));
                nextFlightId = to.getId();
            }
        });
        return airlineMapper.toDto(responseList);
    }

    private AirportEntity reSelectAirport(List<AirportEntity> airportList, AirportEntity from, List<AircraftDto> aircraftDtos) {
        AirportEntity selectedAirport = selectAirport(airportList, from.getId(), null);
        if (isDestinationFlightNotAcceptable(getFlightCalculation(from, selectedAirport), aircraftDtos)) {
            return reSelectAirport(airportList, from, aircraftDtos);
        }
        return selectedAirport;
    }

    private int getFlightCalculation(AirportEntity from, AirportEntity to) {
        // this algorithm can be more effective
        return from.getId() * to.getId() + from.getId() + to.getId();
    }

    public boolean isDestinationFlightNotAcceptable(int calculatedFlight, List<AircraftDto> airportList) {
        AtomicInteger count = new AtomicInteger();
        airportList.forEach(v -> v.getCalculatedDestination().forEach(a -> {
            if (calculatedFlight == a) {
                count.getAndIncrement();
            }
        }));
        return count.get() >= TWO_DESTINATIONS_MAX_FLIGHT_SIZE;
    }

    private AirportEntity selectAirport(List<AirportEntity> airportList, Integer exclusion, Integer selected) {
        int randomNumber = selected != null ? selected : getRandomNumber(airportList);
        if (exclusion != null && randomNumber == exclusion) {
            return selectAirport(airportList, exclusion, selected);
        } else {
            Optional<AirportEntity> selectedAirport = airportList.stream()
                    .filter(v -> v.getId() == randomNumber)
                    .findFirst();
            if (selectedAirport.isPresent()) {
                return selectedAirport.get();
            } else {
                throw new BusinessException("Selection Failed. Try Again Later..");
            }
        }
    }

    private AirlineEntity getAirline(Integer airlineCode) {
        Optional<AirlineEntity> airline = airlineRepository.findById(airlineCode);
        if (airline.isPresent()) {
            return airline.get();
        }
        throw new BusinessException("Airline Not Found");
    }

    public static int getRandomNumber(List<AirportEntity> airportList) {
        List<Integer> pickList = airportList.stream().map(AirportEntity::getId).collect(Collectors.toList());
        return pickList.get(random.nextInt(pickList.size()));
    }
}
