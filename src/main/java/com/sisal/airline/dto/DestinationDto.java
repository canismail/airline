package com.sisal.airline.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class DestinationDto {
    private String from;
    private String to;
    private OffsetDateTime time;
}
