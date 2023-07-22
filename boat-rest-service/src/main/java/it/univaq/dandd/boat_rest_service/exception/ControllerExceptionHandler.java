package it.univaq.dandd.boat_rest_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(RouteNotFoundException.class)
    public ResponseEntity<ExceptionData>  handleTemplateNotFound(RouteNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionData(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(Throwable.class)
    public ResponseEntity<ExceptionData>  handleGenericException(Throwable ex) {
        return new ResponseEntity<>(new ExceptionData(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error has occured."), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
