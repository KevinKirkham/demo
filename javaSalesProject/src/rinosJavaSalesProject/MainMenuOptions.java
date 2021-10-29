package rinosJavaSalesProject;

import java.util.Scanner;

/**
 * Contains the various decision trees responsible for governing the logic behind each menu option
 * @author waveo
 *
 */

public class MainMenuOptions {
	public static void menuNoUser() {
		Scanner scan = new Scanner(System.in);
		
		AccountValidation av = new AccountValidation();
		
		boolean menu = true;
		while (menu) {

			int menuChoice = Menu.mainMenuLoggedOut();

			if (menuChoice == 1) {
				Driver.loadInventory();
				SystemMessage.print("Data has been loaded into the ArrayList");
			}
			
			
			else if (menuChoice == 2) {
				Driver.printItems();
			}
			
			else if (menuChoice == 3) {
				av.login("admin");
				menu = false;
			}
			
			else if (menuChoice == 4) {
				customerSubMenu();
				menu = false;
			}
			
			else if (menuChoice == 5) {
				Read.read();
			}
			
			else if (menuChoice == 6) {
				Write.write();
			}
			
			else if (menuChoice == 7) {
				boolean sure = InputMethods.yesNoToBool("Are you sure you want to quit?");
				if (sure) {
					System.out.println("Ending program...");
					System.exit(0);
				}
			}
		}
	}
	
	public static void customerSubMenu() {
		
		AccountValidation av = new AccountValidation();
		
		boolean menu = true;
		while (menu) {
			
			int choice = Menu.customerSubMenu();
			
			//Returning customer
			if (choice == 1) {
				av.login("customer");
				menu = Driver.loginAttemptCheck(menu);
			}
			
			//New customer
			else if (choice == 2) {
				CreateAccount.createCustomerAccount();
				menu = Driver.loginAttemptCheck(menu);
			}
			
			//Return to the previous menu
			else if (choice == 3) {
				menu = false;
			}
		}
	}
	
	public static void menuAdminLoggedIn() { 
		Scanner scan = new Scanner(System.in);
		
		boolean menu = true;
		while (menu) {

			//This method handles printing the appropriate version of the menu
			//depending on whether the user is a customer or admin
			int menuChoice = Menu.mainMenuAdminLoggedIn();
			
			//Load sample data
			if (menuChoice == 1) {
				Driver.loadInventory();
				SystemMessage.print("Data has been loaded into the ArrayList");
			}
			
			// Load item data
			else if (menuChoice == 2) {
				Driver.printItems();
			}
			
			//log out
			else if (menuChoice == 3) {		
				boolean answer = InputMethods.yesNoToBool("Log out and return to main menu? (yes or no)");
				
				if (answer) {
					Driver.logout();
					menu = false;
				}
			}
			
			//Continue as admin
			else if (menuChoice == 4) {
				AdminOptions.adminMenu();

			}
			
			//Exit the application
			else if (menuChoice == 5) {
				boolean sure = InputMethods.yesNoToBool("Are you sure you want to quit? (yes or no)");
				if (sure) {
					System.out.println("Ending program...");
					System.exit(0);
				}
			}
		}
	}
	
	public static void menuCustomerLoggedIn() { 
		Scanner scan = new Scanner(System.in);
		
		boolean menu = true;
		while (menu) {

			//This method handles printing the appropriate version of the menu
			//depending on whether the user is a customer or admin
			int menuChoice = Menu.mainMenuCustLoggedIn();
			
			//Load sample data
			if (menuChoice == 1) {
				Driver.loadInventory();
			}
			
			// Load item data
			else if (menuChoice == 2) {
				Driver.printItems();
			}
			
			//log out
			else if (menuChoice == 3) {		
				boolean answer = InputMethods.yesNoToBool("Log out and return to main menu? (yes or no)");
				
				if (answer) {
					Driver.logout();
					menu = false;
				}
			}
			
			//Continue as customer
			else if (menuChoice == 4) {
				CustomerOptions.customerMenu();

			}

			//Exit the application
			else if (menuChoice == 5) {
				boolean sure = InputMethods.yesNoToBool("Are you sure you want to quit? (yes or no)");
				if (sure) {
					System.out.println("Ending program...");
					System.exit(0);
				}
			}
		}
	}


}
