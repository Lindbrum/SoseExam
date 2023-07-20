package ${package}.exception;

public class TemplateNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5298209072743611433L;

	public TemplateNotFoundException() {
	}

	public TemplateNotFoundException(String message) {
		super(message);
	}

	public TemplateNotFoundException(Throwable cause) {
		super(cause);
	}

	public TemplateNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public TemplateNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
