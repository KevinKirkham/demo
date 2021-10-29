package rinosJavaSalesProject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;

/**
 * Contains everything relevant to the creation and functions of the Admin account
 * @author waveo
 *
 */
public class Admin extends Account {

	public Admin() {
		super();
	}

	public Admin(String username, String password, String privileges) {
		super(username, password, privileges);
	}

	// Constructor used when (re)creating accounts imported from a text file
	public Admin(String username, String password, int userID, String privileges) {
		super(username, password, userID, privileges);

	}

	/**
	 * Creates a new Auction object with a starting time and
	 * ending time specified to the user's input
	 */
	public void createAuction() {
		if (Driver.items.size() > 0) {
			LocalDateTime startDateTime = null;
			LocalDateTime endDateTime = null;

			Item item = getItemToBeSold();
			if (item != null) {
				startDateTime = getAuctionStartDateTime();
				if (startDateTime != null) {
					
					boolean valid = false;
					while (valid == false) {
						endDateTime = getAuctionEndDateTime();
						if (endDateTime.isAfter(startDateTime)) 
							valid = true;
						else System.out.println("End date cannot be before start date.\n");
					}
					
					if (endDateTime != null) {
						Auction a = new Auction(item, startDateTime, endDateTime);
						SystemMessage.print("Auction Added:\n" + a.toString());
						Driver.futureAuctions.add(a);
					}
				}
			}
		}
		else {
			System.out.println("An auction cannot be created because there are no items for which to create an auction");
		}
	}

	/**
	 * Returns an item that the user selects from a menu. 
	 * 
	 * @return	an Item object
	 */
	private Item getItemToBeSold() {
		
		ArrayList<Item> merge = availabilitySort();
		
		int numAvailable = 0;
		for (int i = 0; i < merge.size(); i++) {
			if (merge.get(i).isAvailable()) 
				numAvailable++; 
		}
		
		System.out.println("Select an item:");
		for (int i = 0; i < numAvailable; i++) {
			System.out.println((i + 1) + ". " + merge.get(i).toString());
		}
		int choice = InputMethods.validateInput(numAvailable, 1);
		
		return Driver.items.get(choice - 1);
		
		/*
		int indexOfItem = Menu.pickItemMenu();
		if (indexOfItem == -1) {
			return null;
		}
		else
			return Driver.items.get(indexOfItem);
		*/
	}
	
	/**
	 * Sorts the items in the driver by their availability, putting all
	 * available items in the front of the driver's list of items.
	 * 
	 * @return	a list of items, sorted by their availability
	 */
	public ArrayList<Item> availabilitySort() {
		ArrayList<Item> available = new ArrayList<>();
		ArrayList<Item> unavailable = new ArrayList<>();
		ArrayList<Item> merge = new ArrayList<>();
		
		for (int i = 0; i < Driver.items.size(); i++) {
			if (Driver.items.get(i).isAvailable()) {
				available.add(Driver.items.get(i));
			}
			else {
				unavailable.add(Driver.items.get(i));
			}
		}
		
		for (int i = 0; i < available.size(); i++) {
			merge.add(available.get(i));
		}
		for (int i = 0; i < unavailable.size(); i++) {
			merge.add(unavailable.get(i));	
		}
		return merge;
		
	}

	/**
	 * Displays all ongoing auctions present in the Driver's list of ongoing auctions.
	 */
	public void showOngoingAuctions() {
		if (Driver.ongoingAuctions.size() == 0) {
			System.out.println("There are no ongoing auctions");
		} else {
			System.out.println("Ongoing Auctions");
			System.out.println("=================");
			for (int i = 0; i < Driver.ongoingAuctions.size(); i++) {
				System.out.println(Driver.ongoingAuctions.get(i).toString());
			}
		}
	}

	/**
	 * Displays all future auctions present in the Driver's list of future auctions.
	 */
	public void showFutureAuctions() {
		if (Driver.futureAuctions.size() == 0) {
			System.out.println("There are no future auctions");
		} else {
			System.out.println("Future Auctions");
			System.out.println("=================");
			for (int i = 0; i < Driver.futureAuctions.size(); i++) {
				System.out.println(Driver.futureAuctions.get(i).toString());
			}
		}
	}
	
	/**
	 * Establishes the starting time for the an auction.
	 * Returns the LocalDateTime object that the user creates.
	 * 
	 * @return	a LocalDateTime object
	 */
	public LocalDateTime getAuctionStartDateTime() {
		LocalDateTime startDateTime = null;
		
		int input = Menu.startDateMenu();

		if (input == 1) { // Immediately
			startDateTime = LocalDateTime.now();
		}
		else if (input == 2) { // Later today
			LocalDate ld = LocalDate.now();
			LocalTime lt = markAuctionTime("start");
			startDateTime = LocalDateTime.of(ld, lt);
		}
		else if (input == 3) { // A later date
			LocalDate ld = markAuctionDate("start");
			LocalTime lt = markAuctionTime("start");
			startDateTime = LocalDateTime.of(ld, lt);
		}
		return startDateTime;
	}

	/**
	 * Establishes the ending time for the an auction.
	 * Returns the LocalDateTime object that the user creates.
	 * 
	 * @return	a LocalDateTime object
	 */
	public LocalDateTime getAuctionEndDateTime() {
		LocalDateTime edt = null;

		int input = Menu.endDateMenu();
		
		if (input == 1) { // Later today
			LocalDate ld = LocalDate.now();
			LocalTime lt = markAuctionTime("end");
			edt = LocalDateTime.of(ld, lt);
		}
		else if (input == 2) { // A later date
			LocalDate ld = markAuctionDate("end");
			LocalTime lt = markAuctionTime("end");
			edt = LocalDateTime.of(ld, lt);
		}
		return edt;
	}
	
	/**
	 * Gets the hour and minute that the user would like to specify for the creation of a LocalTime object.
	 * "mark" is either "start" or "end."
	 * 
	 * @param mark	String used in the message displayed to the user
	 * @return		a LocalTime object on which the auction will start or end
	 */
	public LocalTime markAuctionTime(String mark) {
		int hour = InputMethods.getIntOrStuckForever(1, 12, "What hour would you like the auction to " + mark + "?");
		int minute = InputMethods.getIntOrStuckForever(0, 59, "What minute would you like the auction to " + mark + "?");
		LocalTime time = specifyHour(hour, minute);
		return time;
	}
	
	/**
	 * Gets the year, month, and day that the user would like to specify for
	 * the creation of a LocalDate object.
	 * "mark" is either "start" or "end."
	 * 
	 * @param mark	String used in the message displayed to the user
	 * @return		a LocalDate object on which the auctions will start or end
	 */
	public LocalDate markAuctionDate(String mark) {
		LocalDate date = null;
		int year = 0;
		Month month = null;
		int dayOfMonth = 0;
		
		String dayQuestion = "What day would you like the auction to " + mark + "? (" + "1" + " - "
				+ LocalDate.now().getMonth().maxLength() + ")";
		String yearQuestion = "What year would you like the auction to end? (" + LocalDate.now().getYear() + " - "
				+ LocalDate.now().plusYears(6).getYear() + ")";

		// Get a year from this year to this year + 6 years
		year = InputMethods.getIntOrStuckForever(LocalDate.now().getYear(), LocalDate.now().plusYears(6).getYear(),
				yearQuestion);
		
			int monthInt = InputMethods.getIntOrStuckForever(1, 12, "What month would you like the auction to " + mark + "?");
			month = Month.of(monthInt);
			dayOfMonth = InputMethods.getIntOrStuckForever(1, month.maxLength(), dayQuestion);
			date = LocalDate.of(year, month, dayOfMonth);

		return date;
		
	}

	/**
	 * Takes in a hour and a minute and asks the user if these numbers refer to AM or PM.
	 * Returns a LocalTime object that reflects the user's choice.
	 * 
	 * @param hour 		hour at which the auction will start or end
	 * @param minute	minute at which the auction will start or end
	 * @return 			a LocalTime object of the user's specification
	 */
	public LocalTime specifyHour(int hour, int minute) {
		LocalTime endTime;
		int amPm = Menu.amPmMenu();
		
		if (amPm == 1) { // am
			if (hour == 12) hour = 0;
			endTime = LocalTime.of(hour, minute);
		}
		else { // pm
			if (hour != 12) {
				endTime = LocalTime.of(hour + 12, minute);
			}
			else {
				endTime = LocalTime.of(hour, minute);
			}
		}
		return endTime;
	}

	public int compareTo(Account o) {
		if (this.userID < o.userID) {
			return 1;
		}
		else if (this.userID > o.userID) {
			return -1;
		}
		else
			return 0;
	}
	
	/**
	 * Displays a list of completed auctions.
	 */
	public void printCompletedAuctions() {
		System.out.println("Summary of Completed Auctions: ");
		if (Driver.completedAuctions.size() >= 1) {
			for (int i = 0; i < Driver.completedAuctions.size(); i++) {
				System.out.println(Driver.completedAuctions.get(i).toString());
			}
		} else {
			System.out.println("There are no completed Auctions at this time.");
		}
	}
	
	/**
	 * Shows the winning bid associated with each completed auction, if there is one.
	 */
	public void printWinningBids() {
		if (!Driver.completedAuctions.isEmpty()) {
			for (int i = 0; i < Driver.completedAuctions.size(); i++) {
				if (!Driver.completedAuctions.get(i).getProcessedBids().isEmpty()) {
					if (Driver.completedAuctions.get(i).getProcessedBids().peek() != null) {
						System.out.println("Auction " + (i + 1));
						System.out.println(Driver.completedAuctions.get(i).getCurrentHighest().toString());	
					}
				}
				else {
					System.out.println("There are no winning bids");
				}
			}
		}
		else {
			System.out.println("No winning bids - no auctions have been completed");
		}
	}
	
	/**
	 * Shows the bids associated with an auction of the user's choosing.
	 */
	public void printAuctionBids() {
		if (!Driver.ongoingAuctions.isEmpty()) {
			int auctionIndex = Menu.selectAuction();
			Stack<Bid> clone = Driver.ongoingAuctions.get(auctionIndex).getProcessedBids().clone();
			if (!clone.isEmpty()) {
				while (clone.size() > 0) {
					System.out.println(clone.pop().toString());
				}
			}
			else {
				System.out.println("No bids have been submitted to this auction");
			}
		}
		else {
			System.out.println("There are no ongoing auctions to check the bidding history for");
		}
	}
	
	/**
	 * Initiates the connection to the database and triggers the reading of items from it.
	 * 
	 * @return a list of items read from the database
	 */
	public ArrayList<Item> readItems() {
		ArrayList<Item> items = DBUtilities.readItems();
		return items;
	}
	
	/**
	 * Triggers the writing of items to the database.
	 */
	public void writeItems() {
		DBUtilities.storeItems(Driver.items);
	}
	
	/**
	 * Triggers the writing of customers to the database
	 */
	public void writeCustomers() {
	    DBUtilities.storeCustomers();
	}
	
	/**
	 * Triggers the reading of customers from the database
	 */
	public void readCustomers() {
		ArrayList<Customer> customers = DBUtilities.readCustomers();
		for(Customer c: customers) {
			Driver.accounts.add(c);
		}
	}
	
	/**
	 * Triggers the reading of auctions from the database
	 */
	public void readAuctions() {
		ArrayList<Auction> auctions = DBUtilities.readAuctions();
		sortAuctions(auctions);
	}
	
	/**
	 * Triggers the writing of customers from the database
	 */
	public void writeAuctions() {
		DBUtilities.storeAuctions();
	}
	
	/**
	 * Sorts the auctions that were read in from the database into three
	 * categories so they may be placed in the right collection in the driver.
	 * 
	 * @param auctions	list of auctions read in from the database
	 */
	public void sortAuctions(ArrayList<Auction> auctions) {
		for (int i = 0; i < auctions.size(); i++) {
			if (auctions.get(i).isActive()) {
				Driver.ongoingAuctions.add(auctions.get(i));
			}
			else if (auctions.get(i).getStartDateTime().isAfter(LocalDateTime.now())) {
				Driver.futureAuctions.add(auctions.get(i));
			}
			else Driver.completedAuctions.add(auctions.get(i));
		}
	}
	
	
}
