package it.univaq.dandd.boat_rest_service.exception;

import org.springframework.http.HttpStatus;

public record ExceptionData(HttpStatus status, int code, String message) {

}
