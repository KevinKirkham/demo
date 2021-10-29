package rinosJavaSalesProject;

/**
 * Contains the String versions of the various menus used in the application
 * @author waveo
 *
 */

public class Menu {

	// Main menu when there is no user logged in
	public static int mainMenuLoggedOut() {

		String menu = "Main Menu\n" 
				+ "=========\n" 
				+ "1. Load sample data\r\n" 
				+ "2. Print items ArrayList\n" 
				+ "3. Log in as administrator\r\n" 
				+ "4. Log in as customer\r\n"
				+ "5. Load information from external file\n"
				+ "6. Save program's status to external file\n"
				+ "7. Exit the application\r\n";
		
		int choice = InputMethods.getIntFromMenu(1, 7, menu);
		return choice;
	}

	// Main menu when there is an admin is logged in
	public static int mainMenuAdminLoggedIn() {
		String menu = "Main Menu\n" 
				+ "=========\n" 
				+ "1. Load sample data\r\n"
				+ "2. Print items ArrayList\n" 
				+ "3. Log out\r\n" 
				+ "4. Admin Options\r\n"
				+ "5. Exit the application\r\n";
		int choice = InputMethods.getIntFromMenu(1, 6, menu);
		return choice;
	}

	// Main menu for when there is a customer logged in
	public static int mainMenuCustLoggedIn() {

		String menu = "Main Menu\n" 
				+ "=========\n" 
				+ "1. Load sample data\r\n" 
				+ "2. Print items ArrayList\n" 
				+ "3. Log out\r\n" 
				+ "4. Open customer menu\r\n"
				+ "5. Exit the application\r\n";

		int choice = InputMethods.getIntFromMenu(1, 6, menu);
		return choice;
	}

	public static int customerMenu() {
		String menu = "Customer Menu\n" + "=============\n" + "1. Check my active bids\r\n"
				+ "2. Check my winning bids\r\n" + "3. Bid on an item\r\n"+ "4. Add money to balance\r\n" + "5. Pay for an item that I won\r\n"
				+ "6. Check my historic bids\n" + "7. Return to the previous menu\r\n";
		int choice = InputMethods.getIntFromMenu(1, 7, menu);
		return choice;
	}

	public static int adminMenu() {
		String menu = "Admin Menu\n" + "==========\n"
				+ "1. List current ongoing auctions\r\n"
				+ "2. List future auctions\r\n"
				+ "3. Choose an ongoing auction and check the bidding history\r\n"
				+ "4. List information about completed auctions\r\n"
				+ "5. Summary data of winning bids\r\n"
				+ "6. Add and activate a new auction\r\n"
				+ "7. Create new admin account\r\n"
				+ "8. Read from database\n"
				+ "9. Write to database\n"
				+ "10. Return to the previous menu\r\n";
		int choice = InputMethods.getIntFromMenu(1, 10, menu);
		return choice;
	}

	public static int customerSubMenu() {
		String menu = "Customer Sub Menu\n" + "=================\n" + "1. Returning Customer\n" + "2. New Customer\n"
				+ "3. Return to previous menu\n";
		int choice = InputMethods.getIntFromMenu(1, 3, menu);
		return choice;
	}
	
	public static int startDateMenu() {
		String menu = "When would you like the auction to start?\n" + "1. Immediately\n" + "2. Later today\n" + "3. A later date\n"
				+ "4. Return to previous menu\n";
		int choice = InputMethods.getIntFromMenu(1, 4, menu);
		return choice;
	}
	
	public static int endDateMenu() {
		String menu = "When would you like the auction to end?\n" + "1. Later today\n" +  "2. A later date\n"
				+ "3. Return to previous menu\n";
		int choice = InputMethods.getIntFromMenu(1, 3, menu);
		return choice;
	}
	
	public static int amPmMenu() {
		String menu = "a.m or p.m?\n" + "1. a.m\n" + "2. p.m\n";
		int choice = InputMethods.getIntFromMenu(1, 2, menu);
		return choice;
	}

	// returns the index of the item chosen
	public static int pickItemMenu() {
		int offset = 0;
		int counter = 1;
		if (Driver.items.size() > 0) {
			String menu = "Select the item to be sold:\n";
			for (int i = 0; i < Driver.items.size(); ++i) {
				if (Driver.items.get(i).isAvailable()) {
					menu += (counter++) + ". " + Driver.items.get(i).toString() + "\n";
				}
				else offset++;
			}
			int choice = InputMethods.getIntOrReturnNeg1(1, Driver.items.size(), menu);
			if(choice > 0) {
				return (choice - 1) + offset;
			}
		}
		return -1;
	}


	// returns the index of the ongoing auction.
	// returns -1 if the user backs out
	public static int selectAuction() {

		String menu = "Select an auction:\n";
		for (int i = 0; i < Driver.ongoingAuctions.size(); ++i) {
			menu += (i + 1) + ". " + Driver.ongoingAuctions.get(i).custVersionToString() + "\n";
		}
		int userSelection = InputMethods.getIntOrReturnNeg1(1, Driver.ongoingAuctions.size(), menu);
		if(userSelection > 0) {
			return (userSelection - 1);
		}
		return -1;
	}
	
	public static int selectWinningAuction(Customer c) {

		if (!c.getWinningBids().isEmpty()) {
			System.out.println("Select an auction that you have won: ");
			for (int i = 0; i < c.getWinningBids().size(); ++i) {
				if (!c.getWinningBids().get(i).getAuction().getItem().isPaidFor()) {
					System.out.println((i + 1) + ". " + c.getWinningBids().get(i).getAuction().toString());
				}
			}
			int userSelection = InputMethods.validateInput(1, c.getWinningBids().size());
			if (userSelection > 0) {
				return (userSelection - 1);

			}

		}
		else {
			System.out.println("There no auctions to choose from");
		}
			
		return -1;
	}
	
	
}
