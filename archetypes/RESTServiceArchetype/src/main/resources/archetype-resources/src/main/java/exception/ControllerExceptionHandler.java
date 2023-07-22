package ${package}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import ${package}.exception.ExceptionData;
import ${package}.exception.ParsingException;
import ${package}.exception.TemplateNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

	//Exception thrown when ID doesn't match anything in the datasource
		@ExceptionHandler(TemplateNotFoundException.class)
	    public ResponseEntity<ExceptionData>  handleTemplateNotFoundException(TemplateNotFoundException ex) {
	        return new ResponseEntity<>(new ExceptionData(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
	    }
		
		//Exception thrown when a PathVariable can't be parsed
		@ExceptionHandler(MethodArgumentTypeMismatchException.class)
		public ResponseEntity<ExceptionData>  handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
			//If this is caused by a number convertion exception, then it's from the controller endpoint: return HTTP 400.
			if(ex.getCause() instanceof NumberFormatException) {
				return handleParsingException(new ParsingException("Invalid parameter value", ex));
			}
			else { //If not, then it's an internal exception: return HTTP 500			
				return handleGenericException(ex);
			}
	    }
		
		//ID parsing exception handler (usually called by the handler above)
		@ExceptionHandler(ParsingException.class)
		public ResponseEntity<ExceptionData>  handleParsingException(ParsingException ex) {
			return new ResponseEntity<>(new ExceptionData(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
	    }
		
		//Generic, catch-all handler.
		@ExceptionHandler(Throwable.class)
	    public ResponseEntity<ExceptionData>  handleGenericException(Throwable ex) {
	        ex.printStackTrace();
			return new ResponseEntity<>(new ExceptionData(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error has occured."), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
}
