package rinosJavaSalesProject;

import java.util.Scanner;

/**
 * Contains the decision tree that governs the functions of the Customer menu
 * @author waveo
 *
 */

public class CustomerOptions {
	
	public static void customerMenu() {
		Customer c = (Customer) Driver.currentUser.getUser();
		boolean menu = true;
		Scanner scan = new Scanner(System.in);
		while (menu) {
			
			int choice = Menu.customerMenu();
			
			// Check my active bids
			if (choice == 1) {
				c.printActiveBids();
			}
			
			//Check my winning bids
			else if (choice == 2) {
				c.printWinningBids();
			}
			
			//Bid on an item
			else if (choice == 3) {
				c.placeBid();
			}
			
			//Add money to balance
			else if (choice == 4) {
				System.out.println("How much money would you like to add to your balance?");
				double moneyToBeAdded = scan.nextDouble();				
				c.setBalance(c.getBalance() + moneyToBeAdded);
			}
			
			//Pay for an item that I won
			else if (choice == 5) {
				if (!c.allPaidFor()) {
					c.payForWonAuction();
				}
				else System.out.println("You have no items to pay for.");
			}
			
			//Check my historic bids
			else if (choice == 6) {
				if (!c.getHistoricBids().isEmpty()) {
					c.printHistoricBids();
				}
				else System.out.println("You have no historic bids to check");
			}
			
			//Return to main menu
			else if (choice == 7) {
				menu = false;
			}
		}
	}

}
