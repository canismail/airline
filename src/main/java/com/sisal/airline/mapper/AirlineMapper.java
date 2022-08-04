package com.sisal.airline.mapper;

import com.sisal.airline.dto.AircraftDto;
import com.sisal.airline.dto.ResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    List<ResponseDto> toDto(List<AircraftDto> aircraftList);
}



