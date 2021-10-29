package rinosJavaSalesProject;


/**
 * Is an exception that is thrown when input is recieved that was not expected
 * @author waveo
 *
 */
public class InvalidInputException extends Exception {
	
	public InvalidInputException() {
		super();
	}
	
	public InvalidInputException(String s) {
		super(s);
	}
	
}
