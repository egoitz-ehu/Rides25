package exceptions;

public class AlertaAlreadyExistsException extends Exception {
	 private static final long serialVersionUID = 1L;
	 
	 	public AlertaAlreadyExistsException()
	  {
	    super();
	  }
	  /**This exception is triggered if the question already exists 
	  *@param s String of the exception
	  */
	  public AlertaAlreadyExistsException(String s)
	  {
	    super(s);
	  }
	}
