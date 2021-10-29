package exceptions;

/**
 * To be thrown in the event that some operation is attempted to be done on a rack that has nothing in it.
 * @author Kevin Kirkham
 *
 */
public class EmptyRackException extends Exception {
	
	public EmptyRackException() {
		System.err.println("Vending machine rack empty");
	}
	
}
