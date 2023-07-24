package it.univaq.dandd.boat_rest_service.exception;

public class RouteNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5298209072743611433L;

	public RouteNotFoundException() {
	}

	public RouteNotFoundException(String message) {
		super(message);
	}

	public RouteNotFoundException(Throwable cause) {
		super(cause);
	}

	public RouteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RouteNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
