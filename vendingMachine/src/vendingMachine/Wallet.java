package vendingMachine;

import java.text.NumberFormat;

import exceptions.InsufficientFundsException;

/**
 * The wallet acts as a record keeper of currency types. The quantity of each type of currency is stored as integer values 
 * rather than objects for simplicity.
 * @author Kevin Kirkham
 *
 */
public class Wallet {
	
	NumberFormat df = NumberFormat.getCurrencyInstance();
	
	private int pennies;
	private int nickels;
	private int dimes;
	private int quarters;
	private int ones;
	private int fives;
	private int tens;
	private int twenties;
	
	/**
	 * Constructs a wallet object with no quantities specified for any of the currency types.
	 */
	public Wallet() {
		
	}
	
	/**
	 * Constructs a wallet object with a specified amount of each currency type.
	 * @param pennies Amount of pennies the wallet is to have
	 * @param nickels Amount of nickels the wallet is to have
	 * @param dimes Amount of dimes the wallet is to have
	 * @param quarters Amount of quarters the wallet is to have
	 * @param ones Amount of ones the wallet is to have
	 * @param fives Amount of fives the wallet is to have
	 * @param tens Amount of tens the wallet is to have
	 * @param twenties Amount of twenties the wallet is to have
	 */
	public Wallet(int pennies, int nickels, int dimes, int quarters, int ones, int fives, int tens, int twenties) {
		this.pennies = pennies;
		this.nickels = nickels;
		this.dimes = dimes;
		this.quarters = quarters;
		this.ones = ones;
		this.fives = fives;
		this.tens = tens;
		this.twenties = twenties;
	}
	
	/**
	 * Returns the total monetary value of the money contained within the wallet as a double
	 * @return the total value of all of the money in the wallet in dollars
	 */
	public double total() {
		double total = this.pennies * 0.01 + this.nickels * 0.05 + this.dimes * 0.1 + this.quarters * 0.25
				+ this.ones * 1 + this.fives * 5 + this.tens * 10 + this.twenties * 20;
		return total;
	}
	
	/**
	 * Prints the contents of the wallet.
	 */
	public void displayContents() {
		System.out.println("Contents");
		System.out.println("========");
		System.out.println("Qty. Pennies: " + this.pennies);
		System.out.println("Qty. Nickels: " + this.nickels);
		System.out.println("Qty. Dimes" + this.dimes);
		System.out.println("Qty. Quarters" + this.quarters);
		System.out.println("Qty. Ones: " + this.ones);
		System.out.println("Qty. Fives: " + this.fives);
		System.out.println("Qty. Tens:" + this.tens);
		System.out.println("Qty. Twenties: " + this.twenties);
		System.out.println();
		System.out.println("Total: " + df.format(this.total()));
	}
	
	/**
	 * Sets each field of this wallet equal to those of the argument.
	 * @param w Wallet object
	 */
	public void merge(Wallet w) {
		this.setTwenties(this.getTwenties() + w.getTwenties());
		this.setTens(this.getTens() + w.getTens());
		this.setFives(this.getFives() + w.getFives());
		this.setOnes(this.getOnes() + w.getOnes());
		this.setQuarters(this.getQuarters() + w.getQuarters());
		this.setDimes(this.getDimes() + w.getDimes());
		this.setNickels(this.getNickels() + w.getNickels());
		this.setPennies(this.getPennies() + w.getPennies());
	}
	
	/**
	 * Returns a copy of the Wallet object.
	 * @return Copy of this wallet object.
	 */
	public Wallet clone() {
		Wallet clone = new Wallet(this.pennies, this.nickels, this.dimes, this.quarters, this.ones, this.fives, this.tens, this.twenties);
		return clone;
	}
	
	/**
	 * Returns true if the argument has the same quantity of each currency type as the object on which it is called.
	 * @param w Wallet being used for comparison against this one.
	 * @return True if clone, false is not.
	 */
	public boolean isClone(Wallet w) {
		if (this.pennies == w.getPennies() && this.nickels == w.getNickels() && this.dimes == w.getDimes()
				&& this.quarters == w.getQuarters() && this.ones == w.getOnes() && this.fives == w.getFives()
				&& this.tens == w.getTens() && this.twenties == w.getTwenties()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Empties the contents of the wallet by reducing each of the instance variables to zero.
	 */
	public void empty() {
		this.twenties = 0;
		this.tens = 0;
		this.fives = 0;
		this.ones = 0;
		this.quarters = 0;
		this.dimes = 0;
		this.nickels = 0;
		this.pennies = 0;
	}
	
	/**
	 * Withdrawal the contents of the parameter from the contents of this object
	 * @param w The wallet which contains the bill/coin configuration to be withdrawn from this object.
	 */
	public void deduct(Wallet w) {
		this.twenties = this.twenties - w.getTwenties();
		this.tens = this.tens - w.getTens();
		this.fives = this.fives - w.getFives();
		this.ones = this.ones - w.getOnes();
		this.quarters = this.quarters - w.getQuarters();
		this.dimes = this.dimes - w.getDimes();
		this.nickels = this.nickels - w.getNickels();
		this.pennies = this.pennies - w.getPennies();
	}
	
	/**
	 * Performs the check for the deduct method to make sure that the deduction can take place without causing the quantity of 
	 * one of the types of currency to enter into the negative.
	 * @param w Wallet containing the deduction amounts
	 * @return Approval/disapproval status of the deduction
	 */
	public boolean deductionCheck(Wallet w) {
		try {
			int deductionTwenties = this.twenties - w.getTwenties();
			if (deductionTwenties < 0) throw new InsufficientFundsException("Twenty dollar bills");
			
			int deductionTens = this.tens - w.getTens();
			if (deductionTens < 0) throw new InsufficientFundsException("Ten dollar bills");
			
			int deductionFives = this.fives - w.getFives();
			if (deductionFives < 0) throw new InsufficientFundsException("Five dollar bills");
			
			int deductionOnes = this.ones - w.getOnes();
			if (deductionOnes < 0) throw new InsufficientFundsException("One dollar bills");
			
			int deductionQuarters = this.quarters - w.getQuarters();
			if (deductionQuarters < 0) throw new InsufficientFundsException("Quarters");
			
			int deductionDimes = this.dimes- w.getDimes();
			if (deductionDimes < 0) throw new InsufficientFundsException("Dimes");
			
			int deductionNickels = this.nickels - w.getNickels();
			if (deductionNickels < 0) throw new InsufficientFundsException("Nickels");
			
			int deductionPennies= this.pennies- w.getPennies();
			if (deductionPennies < 0) throw new InsufficientFundsException("Pennies");
			
			return true;
		}
		catch (InsufficientFundsException ife) {
			System.out.println("Deduction cannot be completed - There is not enough of some type of currency in the target wallet to perform the deduction");
			return false;
		}
	}
	
	/**
	 * Sets the contents of this wallet equal to that of the parameter.
	 * @param w Model wallet whose contents will serve as basis for this one
	 */
	public void set(Wallet w) {
		this.twenties = w.getTwenties();
		this.tens = w.getTens();
		this.fives = w.getFives();
		this.ones = w.getOnes();
		this.quarters = w.getQuarters();
		this.dimes = w.getDimes();
		this.nickels = w.getNickels();
		this.pennies = w.getPennies();
	}
	
	/**
	 * Returns a profile of currency that can be taken out of the profits register of the vending machine as profits. 
	 * It leaves 10 of each type of currency in the register if there were 10 in there to begin with.
	 * @return Wallet of profits
	 */
	public Wallet gatherProfits() {
		Wallet w = new Wallet();
		if (this.pennies > 10) {
			int profits = this.pennies - 10;
			this.pennies -= profits;
			w.setPennies(profits);
		}
		if (this.nickels > 10) {
			int profits = this.nickels - 10;
			this.nickels -= profits;
			w.setNickels(profits);
		}
		if (this.dimes > 10) {
			int profits = this.dimes - 10;
			this.dimes -= profits;
			w.setDimes(profits);
		}
		if (this.quarters > 10) {
			int profits = this.quarters - 10;
			this.quarters -= profits;
			w.setQuarters(profits);
		}
		if (this.ones > 10) {
			int profits = this.ones - 10;
			this.ones -= profits;
			w.setOnes(profits);
		}
		if (this.fives > 6) {
			int profits = this.fives - 10;
			this.fives -= profits;
			w.setFives(profits);
		}
		if (this.tens > 4) {
			int profits = this.tens - 10;
			this.tens -= profits;
			w.setTens(profits);
		}
		if (this.twenties > 3) {
			int profits = this.twenties - 10;
			this.twenties-= profits;
			w.setTwenties(profits);
		}
		return w;
	}
	
	public int getPennies() {
		return pennies;
	}

	public void setPennies(int pennies) {
		this.pennies = pennies;
	}

	public int getNickels() {
		return nickels;
	}

	public void setNickels(int nickels) {
		this.nickels = nickels;
	}

	public int getDimes() {
		return dimes;
	}

	public void setDimes(int dimes) {
		this.dimes = dimes;
	}

	public int getQuarters() {
		return quarters;
	}

	public void setQuarters(int quarters) {
		this.quarters = quarters;
	}

	public int getOnes() {
		return ones;
	}

	public void setOnes(int ones) {
		this.ones = ones;
	}

	public int getFives() {
		return fives;
	}

	public void setFives(int fives) {
		this.fives = fives;
	}

	public int getTens() {
		return tens;
	}

	public void setTens(int tens) {
		this.tens = tens;
	}

	public int getTwenties() {
		return twenties;
	}

	public void setTwenties(int twenties) {
		this.twenties = twenties;
	}

}
