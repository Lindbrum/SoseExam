package ${package}.exception;

import org.springframework.http.HttpStatus;

public record ExceptionData(HttpStatus status, int code, String message) {

}
