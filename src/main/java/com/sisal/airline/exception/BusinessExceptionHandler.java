package com.sisal.airline.exception;

import com.sisal.airline.controller.AirlineController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = {AirlineController.class})
public class BusinessExceptionHandler {

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<String> handleBusinessException(BusinessException businessException, HttpServletRequest request) {
        log.error("Error : {} ", businessException);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(businessException.getMessage());
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<String> handleException(Exception exception, HttpServletRequest request) {
        log.error("Error : {} ", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}



