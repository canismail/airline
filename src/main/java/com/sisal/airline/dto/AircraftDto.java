package com.sisal.airline.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AircraftDto {
    private String name;
    private List<DestinationDto> destinationDto;
    private List<Integer> calculatedDestination;
}
