package exceptions;

/**
 * To be thrown in the event that a snack is attempted to be placed onto a rack that has no more room for snacks.
 * @author Kevin Kirkham
 *
 */
public class FullRackException extends Exception {

	public FullRackException() {
		System.err.println("Rack is full");
	}
	
}
