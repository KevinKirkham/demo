package rinosJavaSalesProject;


/**
 * Contains the decision tree that governs the functions of the Admin menu
 * @author waveo
 *
 */

public class AdminOptions {

	public static void adminMenu() {

		Admin a = (Admin) Driver.currentUser.getUser();
		boolean menu = true;
		while (menu) {

			int choice = Menu.adminMenu();

			// List current ongoing auctions
			if (choice == 1) {
				a.showOngoingAuctions();
			}

			// List future auctions
			else if (choice == 2) {
				a.showFutureAuctions();
			}

			// Chose an ongoing auction and check the bidding history
			else if (choice == 3) {
				a.printAuctionBids();
			}

			// List information about completed auctions
			else if (choice == 4) {
				a.printCompletedAuctions();
			}

			// Summary data of winnings bids
			else if (choice == 5) {
				a.printWinningBids();
			}

			// Add and activate a new auction
			else if (choice == 6) {
				a.createAuction();
				// AuctionMethods.startNewAuction();
			}

			// Create a new admin account.
			else if (choice == 7) {
				CreateAccount.createAdminAccount();
				Driver.loginAttemptCheck(menu);
			}
			
			
			// Read from database
			else if (choice == 8) {
				Driver.items = a.readItems();
				SystemMessage.print("The items have been read in from the database");
				a.readAuctions();
				SystemMessage.print("The auctions have been read in from the database");
				a.readCustomers();
				SystemMessage.print("The customers have been read in from the database");
			}
			
			// Write to database
			else if (choice == 9) {
				a.writeItems();
				SystemMessage.print("The items have been written to the database");
				a.writeAuctions();
				SystemMessage.print("The auctions have been written to the database");
				a.writeCustomers();
				SystemMessage.print("The customers have been written to the database");
			}
			
			// Return to main menu
			else if (choice == 10) {
				menu = false;
			}
		}
	}
}
