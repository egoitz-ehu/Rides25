package exceptions;

public class DagoenekoOnartuaException extends Exception {
	private static final long serialVersionUID = 1L;

	public DagoenekoOnartuaException() {
		super();
	}

	/** This exception is triggered if the question already exists
	 * 
	 * @param s String of the exception
	 */
	public DagoenekoOnartuaException(String s) {
		super(s);
	}

}
