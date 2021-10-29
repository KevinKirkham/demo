package vendingMachine;

import java.text.NumberFormat;

/**
 * Abstract class which performs certain transaction-specific operations.
 * @author Kevin Kirkham
 *
 */
public abstract class Transaction {

	/**
	 * Validates that the money that was inputted into the machine is of equal or greater value than the price of the selected item
	 * @param price The price of the product
	 * @param moneyInDish The money the user provided to the machine
	 * @return validation status of the transaction based on the price and provided money
	 */
	public static boolean validateSelection(double price, double moneyInDish) {
		if (moneyInDish >= price) return true;
		else System.out.println("There is not enough money in the machine yet. Keep adding money until there is enough money to buy a snack!");
		return false;
	}
	
	/**
	 * Checks to make sure the rack is not empty before continuing with the transaction. If the rack is empty, the transaction is not initiated 
	 * and a message prints to the console informing the user that the rack he is trying to access does not contain any products.
	 * @param rack
	 */
	public static void checkRackEmpty(int rack) {
		if (!Driver.vm.getShelf().getRacks()[rack].isEmpty()) {
			Driver.vm.select(Driver.vm.getShelf().getRacks()[rack].getType().getPrice(), Driver.vm.getShelf().getRacks()[rack].getType().getName());
			Driver.vm.getShelf().getRacks()[rack].dispenseProduct();
		}
		else System.out.println("There are no more " + formattedName(Driver.vm.getShelf().getRacks()[rack].getType().getName().toLowerCase()) + "s on the rack.");
	}
	
	/**
	 * Takes the S off of a name so that it doesn't print with two Ss.
	 * @param name Name of snack.
	 * @return the properly formatted name.
	 */
	private static String formattedName(String name) {
		if (name.endsWith("s")) { 
			StringBuffer sb = new StringBuffer(name);
			return sb.substring(0,sb.length()-1);
		}
		else return name;
	}
	
	/**
	 * Initiates the transaction, going through all of the steps associated with it as long as the transaction is valid and the vending machine has the necessary 
	 * pieces of currency to make the change after the price of the product is subtracted from the user's inputted money
	 * @param price Price of the product being purchased
	 */
	public static void initiate(double price, String snackName) {
		
		// Determines the monetary value that needs to be achieved using bills or coins
		double change = Driver.vm.getInputReservoir().total() - price;
		
		// Grabs the inputted money for later use
		double input = Driver.vm.getInputReservoir().total();
		
		// Creates a collection/profile of what the optimal number of coins/bills looks like to amount to the change
		Wallet changeProfile = makeChange(change);
		
		// Checks to make sure that change profile can be deducted from the profit register (if the profit register has the coins/bills necessary to make change)
		if (Driver.vm.getProfitsRegister().deductionCheck(changeProfile)) {
			
			// Dumps the money from the input tray into the profits tank
			emptyInputTray();
			
			// Takes the configuration of bills/coins out of the profits register to put into the change dish
			Driver.vm.getProfitsRegister().deduct(changeProfile);
			
			// Puts the change into the change dish
			Driver.vm.getChangeDish().merge(changeProfile);
			
			// Print a summary of the successful transaction
			transactionSummary(input, change, snackName);
		}
		else {
			System.out.println();
			System.out.println(" * Transaction cannot be completed - Call operator to put money into the machine * ");
		}
	}
	
	/**
	 * Empties the contents of the input tray into the profit reservoir of the vending machine by first merging the two and then 
	 * resetting the input tray.
	 */
	public static void emptyInputTray() {
		Driver.vm.getProfitsRegister().merge(Driver.vm.getInputReservoir());	// merge input with profits
		Driver.vm.getInputReservoir().empty();	// clear out input reservoir
	}
	
	/**
	 * Organizes the change due to the user after the transaction into a wallet object
	 * @param change The monetary value that is to be achieved 
	 * @return Wallet containing the minimum number of bills and coins to amount to the change
	 */
	public static Wallet makeChange(double change) {
		double remaining = change;

		// Keep track of change to be taken from the profits bin via a temporary wallet object
		Wallet changeWallet = new Wallet();
		
		changeWallet.setTwenties((int) (remaining / 20));
		remaining %= 20;
		changeWallet.setTens((int) (remaining / 10));
		remaining %= 10;
		changeWallet.setFives((int) (remaining / 5));
		remaining %= 5;
		changeWallet.setOnes((int) (remaining / 1));
		remaining %= 1;
		changeWallet.setQuarters((int) (remaining / 0.25));
		remaining %= 0.25;
		changeWallet.setDimes((int) (remaining / 0.1));
		remaining %= 0.1;
		changeWallet.setNickels((int) (remaining / 0.05));
		remaining %= 0.05;
		changeWallet.setPennies((int) (remaining / 0.01));
		
		return changeWallet;	// This wallet object contains the change (in least number of coins and bills) that needs to be deposited into the change dish
	}

	/**
	 * Provides a summary of the transaction based on the parameters
	 * @param input How much money was put into the machine for the transaction
	 * @param snackName Name of the snack that was purchased
	 */
	public static void transactionSummary(double input, double change, String snackName) {
		NumberFormat df = NumberFormat.getCurrencyInstance();
		System.out.println("You put " + df.format(input) + " into the machine to purchase a " + snackName + ". " + df.format(change) + " is your change.");
		System.out.println();
	}
}
