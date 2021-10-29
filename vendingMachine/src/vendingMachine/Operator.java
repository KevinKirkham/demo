package vendingMachine;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

import exceptions.FullRackException;
import snacks.ChocolateBar;
import snacks.Peanuts;
import snacks.Popcorn;
import snacks.Raisins;
import snacks.Soda;

/**
 * Represents the operator of the vending machine, has the ability to perform certain elevated operations on the vending machine that regular users don't have.
 * @author Kevin Kirkham
 *
 */
public class Operator {
	
	NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	private String password;
	private Wallet profits = new Wallet();

	/**
	 * Constructs an Operator.
	 * @param password The password that the Operator is to have
	 */
	public Operator(String password) {
		this.password = password;
	}
	
	/**
	 * Returns the status of the inputted password. If the user inputted the correct password, returns true.
	 * If the user does not input the correct password, it returns false.
	 * @return Status of the password attempt.
	 */
	public boolean validatePassword() {
		Scanner scan = new Scanner(System.in);		
		System.out.print("Password: ");
		String password = scan.nextLine();
		System.out.println();
		if (password.equals(this.password)) return true;
		System.out.println("\nIncorrect password - Returning to main menu\n");
		return false;
	}
	
	/**
	 * Restocks the specified rack until it is full.
	 * @param rack The index of the rack as it sits in the vending machine's shelf to be restocked.
	 */
	public void restock(int rack) {
		try {
			Driver.vm.getShelf().getRacks()[rack].restock();
			System.out.println("The rack has been restocked with product.");
			System.out.println();
		}
		catch (FullRackException fre) {
			System.out.println("The rack is full");
		}
	}
	
	/**
	 * Changes the price of the specified product.
	 * @param choice The index of the rack which contains the desired product whose price is to be changed.
	 */
	public void changePrice(int choice) {
		double newPrice = priceValidation();
		if (choice == 1) { 
			Driver.vm.getShelf().getRacks()[choice - 1].getType().setPrice(newPrice);
			Driver.vm.getShelf().getRacks()[choice - 1].setType(new ChocolateBar(newPrice));
		}
		else if (choice == 2) {
			Driver.vm.getShelf().getRacks()[choice - 1].getType().setPrice(newPrice);
			Driver.vm.getShelf().getRacks()[choice - 1].setType(new Peanuts(newPrice));
		}
		else if (choice == 3) {
			Driver.vm.getShelf().getRacks()[choice - 1].getType().setPrice(newPrice);
			Driver.vm.getShelf().getRacks()[choice - 1].setType(new Popcorn(newPrice));
		}
		else if (choice == 4) {
			Driver.vm.getShelf().getRacks()[choice - 1].getType().setPrice(newPrice);
			Driver.vm.getShelf().getRacks()[choice - 1].setType(new Raisins(newPrice));
		}
		else if  (choice == 5) {
			Driver.vm.getShelf().getRacks()[choice - 1].getType().setPrice(newPrice);
			Driver.vm.getShelf().getRacks()[choice - 1].setType(new Soda(newPrice));
		}
	}
	
	
	/**
	 * Returns the number that will be overwrite the current price of a particular snack. Validates the user's input as being a 
	 * usable number for the price of a product.
	 * @return Price of a snack.
	 */
	public double priceValidation() {
		Scanner scan = new Scanner(System.in);
		double price = 0.0;
		boolean valid = false;
		while (valid == false) {
			try {
				System.out.print("Change price to (maximum allowed - " + nf.format(Driver.vm.PRODUCT_PRICE_LIMIT) + "): ");
				String priceString= scan.nextLine();
				price = Double.parseDouble(priceString);
				if (price >= 0 && price <= Driver.vm.PRODUCT_PRICE_LIMIT) valid = true; 
			}
			catch (Exception e) {
				System.out.println("You need to input a valid price here");
			}
		}
		return price;
	}
	
	/**
	 * Gathers money from the machine, puts all money made in profits at the vending machine and moves it into the operator's wallet.
	 */
	public void gatherMoney() {
		DecimalFormat df = new DecimalFormat("$##0.00");
		Wallet safeProfits = Driver.vm.getProfitsRegister().gatherProfits();
		this.profits.merge(safeProfits);
		System.out.println("Gathered " + df.format(safeProfits.total()) + " from the vending machine in profits.");
		System.out.println();
	}
	
	/**
	 * Allows the operator to add money to the vending machine
	 */
	public void addMoney() {
		boolean loop = true;
		while (loop) {
			int choice = Menu.insertMoneyMenu();
			if (choice == 1) Driver.vm.getProfitsRegister().setPennies(Driver.vm.getProfitsRegister().getPennies() + 1);
			else if (choice == 2) Driver.vm.getProfitsRegister().setNickels(Driver.vm.getProfitsRegister().getNickels() + 1);
			else if (choice == 3) Driver.vm.getProfitsRegister().setDimes(Driver.vm.getProfitsRegister().getDimes() + 1);
			else if (choice == 4) Driver.vm.getProfitsRegister().setQuarters(Driver.vm.getProfitsRegister().getQuarters() + 1);
			else if (choice == 5) Driver.vm.getProfitsRegister().setOnes(Driver.vm.getProfitsRegister().getOnes() + 1);
			else if (choice == 6) Driver.vm.getProfitsRegister().setFives(Driver.vm.getProfitsRegister().getFives() + 1);
			else if (choice == 7) Driver.vm.getProfitsRegister().setTens(Driver.vm.getProfitsRegister().getTens() + 1);
			else if (choice == 8) Driver.vm.getProfitsRegister().setTwenties(Driver.vm.getProfitsRegister().getTwenties() + 1);
			else if (choice == 9) loop = false;
		}
	}

	public Wallet getProfits() {
		return profits;
	}

	public void setProfits(Wallet profits) {
		this.profits = profits;
	}
}
