package it.univaq.dandd.hotel_rest_service.exception;

public class HotelNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5298209072743611433L;

	public HotelNotFoundException() {
	}

	public HotelNotFoundException(String message) {
		super(message);
	}

	public HotelNotFoundException(Throwable cause) {
		super(cause);
	}

	public HotelNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public HotelNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
