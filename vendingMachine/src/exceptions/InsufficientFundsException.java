package exceptions;

/**
 * To be thrown when a type of currency is attempted to be drawn from a wallet object but that wallet contains none of that type of currency.
 * @author Kevin Kirkham
 *
 */
public class InsufficientFundsException extends Exception {

	public InsufficientFundsException(String currency) {
		System.err.println("Insufficient type: " + currency);
	}
	
}
