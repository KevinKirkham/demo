package vendingMachine;

import java.text.NumberFormat;
import java.util.Scanner;

import snacks.ChocolateBar;
import snacks.Peanuts;
import snacks.Popcorn;
import snacks.Raisins;
import snacks.Soda;

/**
 * This class handles the input validation and logic for transitioning into and out of the various menus the program 
 * has, as well as accomplishing other small tasks associated with specific menu items.
 * @author Kevin Kirkham
 *
 */
public class Menu {
	
	public static Scanner scan = new Scanner(System.in);
	public static NumberFormat nf = NumberFormat.getCurrencyInstance();
	
	/**
	 * Handles the logic for the main menu.
	 */
	public static void mainMenu() {
		boolean loop = true;
		while(loop) {
			Driver.vm.status();
			int choice = printMainMenu();

			if (choice == 1) insertMoney();			
			else if (choice == 2) makeSelection();
			else if (choice == 3) Driver.vm.displayPrices();
			else if (choice == 4) Driver.user.takeInput();
			else if (choice == 5) Driver.user.takeChange();
			else if (choice == 6) if (Driver.op.validatePassword()) operatorMenu();
		}
	}
	
	/**
	 * Prints and validates the inputs for the main menu.
	 * @return The menu choice provided by the user.
	 */
	public static int printMainMenu() {
		System.out.println("Main Menu");
		System.out.println("=========");
		System.out.println("1. Insert money");
		System.out.println("2. Make a selection");
		System.out.println("3. Display item prices");
		System.out.println("4. Retrieve money from input tray");
		System.out.println("5. Retrieve money from change dish");
		System.out.println("6. Enter operator mode");
		System.out.println();
		return inputValidation(1, 6);
	}
	
	/**
	 * Handles the logic associated with the Insert Money menu.
	 */
	public static void insertMoney() {
		boolean loop = true;
		while (loop) {
			int choice = insertMoneyMenu();
			
			if (choice == 1) {
				if (Driver.vm.inputMax(0.01)) Driver.vm.insertPenny();
			}
			else if (choice == 2) {
				if (Driver.vm.inputMax(0.05)) Driver.vm.insertNickel();
			}
			else if (choice == 3) {
				if (Driver.vm.inputMax(0.1)) Driver.vm.insertDime();
			}
			else if (choice == 4) { 
				if (Driver.vm.inputMax(0.25)) Driver.vm.insertQuarter();
			}
			else if (choice == 5) {
				if (Driver.vm.inputMax(1.0)) Driver.vm.insertOne();
			}
			else if (choice == 6) {
				if (Driver.vm.inputMax(5.0)) Driver.vm.insertFive();
			}
			else if (choice == 7) {
				if (Driver.vm.inputMax(10.0)) Driver.vm.insertTen();
			}
			else if (choice == 8) {
				if (Driver.vm.inputMax(20.0)) Driver.vm.insertTwenty();
			}
			else if (choice == 9) loop = false;
		}
	}
	
	/**
	 * Prints and validates the input for the Insert Money menu.
	 * @return The menu choice provided by the user.
	 */
	public static int insertMoneyMenu() {
		System.out.println("Select Amount to Insert");
		System.out.println("=======================");
		System.out.println("1. Insert penny");
		System.out.println("2. Insert nickel");
		System.out.println("3. Insert dime");
		System.out.println("4. Insert quarter");
		System.out.println("5. Insert one dollar bill");
		System.out.println("6. Insert five dollar bill");	
		System.out.println("7. Insert ten dollar bill");
		System.out.println("8. Insert twenty dollar bill");
		System.out.println("9. Return to previous menu");
		System.out.println();
		return inputValidation(1, 9);
	}
	
	/**
	 * Prints and handles the the input for the operator menu.
	 * @return The user's menu choice.
	 */
	public static int printOperatorMenu() {
		System.out.println("Operator Menu");
		System.out.println("=============");
		System.out.println("1. Restock shelves");
		System.out.println("2. Gather money from machine");
		System.out.println("3. Put money into the machine");
		System.out.println("4. Change the price of a product");
		System.out.println("5. Return to previous menu");
		System.out.println();
		return inputValidation(1, 5);
	}

	/**
	 * Handles the logic associated with the operator menu.
	 */
	public static void operatorMenu() {
		boolean loop = true;
		while (loop) {
			int choice = printOperatorMenu();
			if (choice == 1) restockMenu();
			else if (choice == 2) Driver.op.gatherMoney();
			else if (choice == 3) Driver.op.addMoney();
			else if (choice == 4) changePriceMenu();
			else if (choice == 5) loop = false;
		}
	}
	
	/**
	 * Handles the actions associated with transactions unique to the chosen product.
	 */
	public static void makeSelection() {
		int choice = productsMenu();
		double input = Driver.vm.getInputReservoir().total();
		if (choice == 6) return;
		Transaction.checkRackEmpty(choice - 1);
	}
	
	/**
	 * Prints and handles the input for the products menu.
	 * @return The user's selected menu option
	 */
	public static int productsMenu() {
		System.out.println("Select a Product");
		System.out.println("================");
		System.out.println("1. Chocolate Bar - " + nf.format(ChocolateBar.price));
		System.out.println("2. Peanuts -" + nf.format(Peanuts.price));
		System.out.println("3. Popcorn - " + nf.format(Popcorn.price));
		System.out.println("4. Raisins - " + nf.format(Raisins.price));
		System.out.println("5. Soda - " + nf.format(Soda.price));
		System.out.println("6. Return to previous menu");
		System.out.println();
		return inputValidation(1, 6);
	}
	
	/**
	 * Prints and handles the input validation for the restock menu.
	 * @return The user's choice.
	 */
	public static int printRestockMenu() {
		System.out.println("Select a rack to restock");
		System.out.println("========================");
		System.out.println("1. Chocolate Bar Rack");
		System.out.println("2. Peanuts Rack");
		System.out.println("3. Popcorn Rack");
		System.out.println("4. Raisins Rack");
		System.out.println("5. Soda Rack");
		System.out.println("6. Return to previous menu");
		System.out.println();
		return inputValidation(1, 6);
	}
	
	/**
	 * Handles the logic for the restock menu.
	 */
	public static void restockMenu() {
		int choice = printRestockMenu();
		if (choice == 1) Driver.op.restock(0); // Passing 0 into the Operator's restock method because chocolate sits in the 0th position in vending machine's shelf
		else if (choice == 2) Driver.op.restock(1);
		else if (choice == 3) Driver.op.restock(2);
		else if (choice == 4) Driver.op.restock(3);
		else if (choice == 5) Driver.op.restock(4);
		else if (choice == 6) return;
	}
	
	/**
	 * Prints and handles the input validation for the change price menu.
	 * @return The user's menu choice.
	 */
	public static int printChangePriceMenu() {
		System.out.println("Select Product to Change Price");
		System.out.println("==============================");
		System.out.println("1. Chocolate Bar");
		System.out.println("2. Peanuts");
		System.out.println("3. Popcorn");
		System.out.println("4. Raisins");
		System.out.println("5. Soda");
		System.out.println("6. Return to previous menu");
		System.out.println();
		return inputValidation(1, 6);
	}
	
	/**
	 * Handles the logic associated with the change price menu.
	 */
	public static void changePriceMenu() {
		int choice = printChangePriceMenu();
		if (choice == 6) return;
		Driver.op.changePrice(choice);
	}
	
	/**
	 * Ensures that the user inputs a number between the upper and lower bounds specified in the parameter list.
	 * @param lower The highest a valid input can be.
	 * @param upper The lowest a valid input can be.
	 * @return The validated user input.
	 */
	public static int inputValidation(int lower, int upper) {
		int choice = 0;
		boolean valid = false;
		while (valid == false) {
			try {
				System.out.print("Choice: ");
				String input = scan.nextLine();
				choice = Integer.parseInt(input);
				System.out.println();
				if (choice >= lower && choice <= upper) valid = true;
				else System.out.println("Your choice must be an option from the menu.");
			}
			catch (Exception e) {
				System.out.println("Your input must be an integer.");
			}
		}
		return choice;
	}
}
