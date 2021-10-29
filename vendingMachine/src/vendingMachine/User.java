package vendingMachine;

import java.text.NumberFormat;

/**
 * Represents user functions such as retrieving change from the vending machine and from the input tray.
 * @author Kevin Kirkham
 *
 */
public class User {
	
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	private Wallet wallet;
	
	/**
	 * Constructs a user object.
	 */
	public User() {
		this.wallet = new Wallet();
	}
	
	/**
	 * Constructs a user with a specific wallet.
	 * @param wallet The wallet that is to be assigned to the user.
	 */
	public User(Wallet wallet) {
		this.wallet = wallet;
	}
	
	/**
	 * Retrieves the money that the user put in but decided not to commit to spending.
	 */
	public void takeInput() {
		if (Driver.vm.getInputReservoir().total() > 0) {
			double input = Driver.vm.getInputReservoir().total();
			this.wallet.merge(Driver.vm.getInputReservoir());
			Driver.vm.getInputReservoir().empty();
			System.out.println(nf.format(input) + " was retrieved from the input tray.");
			System.out.println();
		}
		else {
			System.out.println("There is no money in the input tray to take.");
			System.out.println();
		}
	}
	
	/**
	 * Takes the change from the change dish of the vending machine.
	 */
	public void takeChange() {
		if (Driver.vm.getChangeDish().total() > 0) {
			double change = Driver.vm.getChangeDish().total();
			this.wallet.merge(Driver.vm.getChangeDish());
			Driver.vm.getChangeDish().empty();
			System.out.println(nf.format(change) + " was retrieved from the change dish.");
			System.out.println();
		}
		else {
			System.out.println("There is no change to take.");
			System.out.println();
		}
	}

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
}
