package vendingMachine;

import java.text.NumberFormat;

import snacks.ChocolateBar;
import snacks.Peanuts;
import snacks.Popcorn;
import snacks.Raisins;
import snacks.Soda;

/**
 * Made up of a variable number of shelves and three wallets, the vending machine allows the user or operator to
 * input specific pieces of currency into the machine.
 * @author Kevin Kirkham
 *
 */
public class VendingMachine {
	
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	private Wallet profitsRegister;
	private Wallet input;
	private Wallet changeDish;
	
	private Shelf shelf = new Shelf();
	
	private final double INPUT_MAX = 20.00;
	public final double PRODUCT_PRICE_LIMIT = 7.80;
	
	/**
	 * Constructs a vending machine object.
	 */
	public VendingMachine() {
		this.profitsRegister = new Wallet(10, 10, 10, 10, 10, 6, 4, 3);
		this.input = new Wallet();
		this.changeDish = new Wallet();
	}
	
	/**
	 * Increases the quantity of pennies present in the machine by one.
	 */
	public void insertPenny() {
		this.input.setPennies(this.input.getPennies() + 1);
	}
	
	/**
	 * Increases the quantity of nickels present in the machine by one.
	 */
	public void insertNickel() {
		this.input.setNickels(this.input.getNickels() + 1);
	}
	
	/**
	 * Increases the quantity of dimes present in the machine by one.
	 */
	public void insertDime() {
		this.input.setDimes(this.input.getDimes() + 1);
	}

	/**
	 * Increases the quantity of quarters present in the machine by one.
	 */
	public void insertQuarter() {
		this.input.setQuarters(this.input.getQuarters() + 1);
	}
	
	/**
	 * Increases the quantity of one dollar bills present in the machine by one.
	 */
	public void insertOne() {
		if (!reject()) this.input.setOnes(this.input.getOnes() + 1);
		else System.out.println("dollar bill!");
	}

	/**
	 * Increases the quantity of five dollar bills present in the machine by one.
	 */
	public void insertFive() {
		if (!reject()) this.input.setFives(this.input.getFives() + 1);
		else System.out.println("five dollar bill!");
	}
	
	/**
	 * Increases the quantity of ten dollar bills present in the machine by one.
	 */
	public void insertTen() {
		if (!reject()) this.input.setTens(this.input.getTens() + 1);
		else System.out.println("ten dollar bill!");
	}
	
	/**
	 * Increases the quantity of twenty dollar bills present in the machine by one.
	 */
	public void insertTwenty() {
		if (!reject()) this.input.setTwenties(this.input.getTwenties() + 1);
		else System.out.println("Jackson!");
	}
	
	/**
	 * Returns a boolean value which represents whether the machine will reject the inputted dollar bill or not.
	 * @return The status of the bill's rejection
	 */
	public boolean reject() {
		int num1 = (int) (Math.random() * 10);
		int num2 = (int) (Math.random() * 10);
		if (num1 == num2) { 
			System.out.print("The vending machine rejects your ");
			return true;
		}
		return false;
	}
	
	/**
	 * Prints the status of the three wallets present in the vending machine.
	 */
	public void status() {
		System.out.println("======================");
		System.out.println("Input tray: " + nf.format(this.input.total()));
		System.out.println("Change Dish: " + nf.format(this.changeDish.total()));
		System.out.println("Profits: " + nf.format(this.profitsRegister.total()));
		System.out.println("======================");
	}
	
	/**
	 * Prints the prices of the products in the vending machine.
	 */
	public void displayPrices() {
		System.out.println();
		System.out.println("\tX" + shelf.racks[0].quantity() + " Chocolate Bar: " + nf.format(ChocolateBar.price));
		System.out.println("\tX" + shelf.racks[1].quantity() + " Peanuts: " + nf.format(Peanuts.price));
		System.out.println("\tX" + shelf.racks[2].quantity() + " Popcorn: " + nf.format(Popcorn.price));
		System.out.println("\tX" + shelf.racks[3].quantity() + " Raisins: " + nf.format(Raisins.price));
		System.out.println("\tX" + shelf.racks[4].quantity() + " Soda: " + nf.format(Soda.price));
		System.out.println();
	}
	
	/**
	 * Initiates the transaction based on the product selected by the user.
	 * @param price The price of the selected object.
	 */
	public void select(double price, String name) {
		if (Transaction.validateSelection(price, this.input.total())) {
			Transaction.initiate(price, name);
		}
	}
	
	/**
	 * Only allows the insertion to take place if it will not result in the input maximum of the vending machine to be surpassed.
	 * @param insert The quantity of money attempting to be inserted into the vending machine.
	 * @return Approval status of the insertion.
	 */
	public boolean inputMax(double insert) {
		if ((this.input.total() + insert) <= this.INPUT_MAX) return true;
		else System.out.println("Rejected - inserting that quantity of money will surpass the vending machine's insert limit.");
		System.out.println();
		return false;
	}
	
	/**
	 * Initializes the vending machine's racks with products for use.
	 */
	public void init() {
		for (int i = 0; i < shelf.racks[0].getContents().length; i++) {
			shelf.racks[0].loadSnack(new ChocolateBar(0.99));
		}
		for (int i = 0; i < shelf.racks[2].getContents().length; i++) {
			shelf.racks[2].loadSnack(new Popcorn());
		}
		for (int i = 0; i < shelf.racks[1].getContents().length; i++) {
			shelf.racks[1].loadSnack(new Peanuts());
		}
		for (int i = 0; i < shelf.racks[3].getContents().length; i++) {
			shelf.racks[3].loadSnack(new Raisins());
		}
		for (int i = 0; i < shelf.racks[4].getContents().length; i++) {
			shelf.racks[4].loadSnack(new Soda());
		}
	}

	public Wallet getProfitsRegister() {
		return profitsRegister;
	}

	public void setProfitsRegister(Wallet mainTank) {
		this.profitsRegister = mainTank;
	}

	public Wallet getInputReservoir() {
		return input;
	}

	public void setInputReservoir(Wallet inputReservoir) {
		this.input = inputReservoir;
	}

	public Wallet getChangeDish() {
		return changeDish;
	}

	public void setChangeDish(Wallet changeReservoir) {
		this.changeDish = changeReservoir;
	}

	public Shelf getShelf() {
		return shelf;
	}

	public void setShelf(Shelf shelf) {
		this.shelf = shelf;
	}

}
