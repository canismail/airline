package com.sisal.airline.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseDto {
    private String name;
    private List<DestinationDto> destinationDto;
}
