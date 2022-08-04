package com.sisal.airline.controller;

import com.sisal.airline.dto.ResponseDto;
import com.sisal.airline.service.AirlineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "flight")
public class AirlineController {

    private final AirlineService airlineService;

    @GetMapping(value = "/planning/{airlineCode}")
    public ResponseEntity<List<ResponseDto>> getPlanning(@PathVariable(name = "airlineCode") Integer airlineCode) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(airlineService.getPlanning(airlineCode));
    }
}
