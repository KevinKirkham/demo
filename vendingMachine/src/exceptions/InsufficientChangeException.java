package exceptions;

/**
 * To be thrown when change is attempted to be made when there isn't a sufficient amount of coins or bills in the vending machine
 * to make change for the transaction.
 * @author Kevin Kirkham
 *
 */
public class InsufficientChangeException extends Exception {

	public InsufficientChangeException() {
		System.err.println("There is an insufficient amount of coins in the vending machine to accurately make change for this transaction");
	}
}
