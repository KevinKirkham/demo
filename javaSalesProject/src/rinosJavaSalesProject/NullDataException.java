package rinosJavaSalesProject;

/**
 * Exception that is thrown when no data was found in a certain field in which it was expected there would be
 * @author waveo
 *
 */

public class NullDataException extends Exception {

	public NullDataException() {
		super();
		System.out.println("One of the arrays used during the read processed contains null data and is inhibiting the creation of objects.");
	}
	
	public NullDataException(String s) {
		super(s);
		System.out.println("One of the arrays used during the read processed contains null data and is inhibiting the creation of objects.");
	}
}
