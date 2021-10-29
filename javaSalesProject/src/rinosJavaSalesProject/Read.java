package rinosJavaSalesProject;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * This can read information from a text file and create objects that the program can manipulate.
 * The text file that this class reads in must meet a specific format. It must be divided into blocks
 * (separated by the } character), the first of which must contain all information for customer object
 * instantiation, the second must contain information for item instantiation, the third must contain
 * information for bid instantiation.
 * 
 * Within each block, the information that will be used to initialize the fields for each object must be
 * separated by pipes. The data that gets used to initialize the fields must be separated by commas. 
 * An example of such a text file may look like: 
 * 
 * Username, password, userID, balance, winningBids, activeBids, historicBids
 * Larry,password,3,7500,5#7,2#7#8#11#5,5#7#18#3#2#7#8#11#5|
 * Morton,password,4,6000,5#7,2#7#8#11#5,5#7#18#3#2#7#8#11#5|
 * Wendy,password,5,9000,5#7,2#7#8#11#5,5#7#18#3#2#7#8#11#5
 * }
 * 
 * startingPrice,name,increment,itemID,isPaidFor,isAvailable
 * 130,Nintendo GameCube,10,100,0,1|
 * 160,Sony PlayStation,10,101,0,0|
 * 150,Nintendo GameBoy,5,102,1,1
 * }
 * 
 * auctionID,itemID,sellingPrice,currentHighest,processedBids,unprocessedBids,openingTime*,closingTime*
 * 1000,100,890,504,503#507#508,510#511#512,2020#4#8#9#0#0,2020#4#15#17#0#0|
 * 1003,101,710,518,523#525#526#527,528,2020#4#11#9#0#0,2020#4#13#17#0#0
 * 
 * auctionID,itemID,sellingPrice,winningBid,historicBids,openingTime,closingTime
 * 1002,102,650,518,513#514#515,516#517#518,
 * }
 * 
 * auctionID, itemID, openingTime, closingTime
 * 1004,103,2020#4#9#12#0#0,2020#4#9#14#0#0
 * }
 * 
 * bidValue,auctionID,customerID,bidID,year,month,day,hour,minute,second
 * 140,0,3,500,2020#4#8#11#0#0|
 * 155,0,4,501,2020#4#8#11#2#0|
 * 165,0,5,502,2020#4#8#11#3#0
 * 
 * **A $ indicates that the auction being read in is a dynamic auction used for testing purposes
 * These auctions are set to close 5 minutes after opening when placed in the active auctions block,
 * and when they are placed in the future auctions block they are scheduled to open 2 minutes after
 * they get read in and close five minutes after they open.
 * @author waveo
 */
public class Read {
	
	// THIS MAIN METHOD WILL BE REMOVED AFTER THE READ CLASS HAS 
	// BEEN THOUROUGHLY TESTED AND RELIABLY WORKS
	public static void main(String[] args) {
		Driver.accounts = new ArrayList<Account>();
		Driver.ongoingAuctions = new ArrayList<Auction>();
		Driver.completedAuctions = new ArrayList<Auction>();
		Driver.futureAuctions = new ArrayList<Auction>();
		Driver.items = new ArrayList<Item>();
		read();
	}
	
	
	/**
	 * Governs the sub-functions of reading information in from a file
	 */
	public static void read() {

		ArrayList<String[]> blockArrays = new ArrayList<>();
		
		// 1. Convert the text file that you read in into a String
		//    then divide that string into blocks, persisting each block
		//	  as an element in an ArrayList
		
		String textFile = inputData();
			
		ArrayList<String> blocks = defineBlocks(textFile, "}");	
		
		// 2. Create empty arrays for each block from the text file, each array having
		//    a size specific to the number of objects that can be created given the 
		//	  information in the text file. These arrays are stored in an ArrayList
		//    that corresponds to the number of blocks that were found in the file.
		//    Each block has a corresponding array, the size of which depending on
		//    how many objects could be made based on what was found in the text file.
		
		// Creates an array for each block (customers, items, auctions, bids)
		for (int i = 0; i < blocks.size(); i++) {
			
			// Creates an array of equal size to the number of objects that can be created from the information
			// in that string
			blockArrays.add(new String[countDelimiters(blocks.get(i), '|') + 1]);
		}
		
		// 3. Fill those arrays with the information found in their corresponding blocks
		
		for (int i = 0; i < blockArrays.size(); i++) {
			fillArray(blockArrays.get(i), blocks.get(i));
		}
		
		// 4. Create objects based off of the information stored in the arrays
		//    and adds them to the rest of the program
		createObjects(blockArrays);
		
		System.out.println("No errors in the importing process");
	}
	
	
	public static void createObjects(ArrayList<String[]> blockArrays) {
		// Creates items
		ArrayList<Item> items = new ArrayList<Item>();
		for (int i = 0; i < blockArrays.get(1).length; i++) {
			if (blockArrays.get(1)[0] != null) {
				items.add(createItem(blockArrays.get(1)[i]));
			}
			
		}

		// Creates active auctions
		ArrayList<Auction> activeAuctions = new ArrayList<>();
		ArrayList<String[]> auctionBidBuffer = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(2).length; i++) {
			if  (blockArrays.get(2)[0] != null) {
				auctionBidBuffer.add(createActiveAuction(blockArrays.get(2)[i], items, activeAuctions));
			}
		}

		// Creates completed auctions
		ArrayList<Auction> completedAuctions = new ArrayList<>();
		ArrayList<String> completedAuctionBuffer = new ArrayList<>();
		if (blockArrays.get(3)[0] != null) {
			for (int i = 0; i < blockArrays.get(3).length; i++) {
				if (blockArrays.get(3).length != 0) {
					completedAuctionBuffer.add(createCompletedAuction(blockArrays.get(3)[i], items, completedAuctions));
				}
			}
		}
		
		//Creates future auctions
		ArrayList<Auction> futureAuctions = new ArrayList<>();
		if (blockArrays.get(4)[0] != null) {
			for (int i = 0; i < blockArrays.get(4).length; i++) {
				if (blockArrays.get(4).length != 0) {
					futureAuctions.add(createFutureAuction(blockArrays.get(4)[i], items));
				}
			}
		}

		// An ArrayList full of bid information that corresponds to the ArrayList of
		// customersAdded
		ArrayList<Customer> customersAdded = new ArrayList<>();
		ArrayList<String[]> customerBidBuffer = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(0).length; i++) {
			if (blockArrays.get(0)[0] != null) {
				String[] bidInfo = createCustomer(blockArrays.get(0)[i], customersAdded);
				customerBidBuffer.add(bidInfo);
			}
			
		}

		// Creates an ArrayList that stores all of the bids found in the text file
		ArrayList<Bid> bids = new ArrayList<>();
		for (int i = 0; i < blockArrays.get(5).length; i++) {
			if (blockArrays.get(5)[0] != null) {
				bids.add(createBid(blockArrays.get(5)[i], customersAdded, activeAuctions, completedAuctions));
			}
		}
		
		populateCustomerBids(customersAdded, customerBidBuffer, bids);
		populateAuctionBids(activeAuctions, auctionBidBuffer, bids);
		populateCompletedAuctionBids(completedAuctionBuffer, completedAuctions, bids);
		addToDriver(items, activeAuctions, completedAuctions, futureAuctions, customersAdded);
	}
	
	/**
	 * Returns the blocks found in the text file as an array of Strings.
	 * Blocks are determined by the location of the delimiting character.
	 * 
	 * @param string	string created from the text of the flat file
	 * @param delimiter	character that is to used as a delimiter
	 * @return			String array in which each element is a block
	 */
	public static ArrayList<String> defineBlocks(String string, String delimiter) {
		ArrayList<String> substrings = new ArrayList<>();
		StringTokenizer st = new StringTokenizer(string, delimiter);
		while (st.hasMoreTokens()) {
			substrings.add(st.nextToken().trim());
		}
		return substrings;
	}
	
	
	/**
	 * Returns the number of delimiters present in the String supplied.
	 * Counts the number of times the specified delimiting character appears
	 * in the string.
	 * 
	 * @param string	string to be scanned for delimiters
	 * @param delimiter	character to be used as the delimiter
	 * @return 			the number of delimiters in the string
	 */
	public static int countDelimiters(String string, char delimiter) {
		int numDelimiters = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == delimiter) {
				numDelimiters++;
			}
		}
		return numDelimiters;
	}
	
	
	/**
	 * Fills an array with the information found in the associated block of the text file.
	 * 
	 * As part of a larger process, at this point the arrays will be filled with Strings such as
	 * "Ronald,blue_plant" or "275,Test item 2,10"
	 * in which each element will contain commas separating pieces of data that will be further
	 * divided later.
	 * 
	 * @param array	empty array of information that is to be filled
	 * @param block	block of information that is to be used to fill the array
	 */
	public static void fillArray(String[] array, String block) {
		StringTokenizer st = new StringTokenizer(block, "|");
		int counter = 0;
		while (st.hasMoreTokens()) {
			array[counter] = st.nextToken().trim();
			counter++;
		}
	}
	
	
	/**
	 * Returns an array of Strings containing bid information that was found in
	 * the flat file to be processed later.
	 * 
	 * Takes in something like "Jake The Dog,Gr@yl!n3,500#508#512#515" and
	 * instantiates a customer object using the information separated by the comma.
	 * The bid information gets stored in an array of strings for later use.
	 * 
	 * @param data				the data that is to be used to create a Customer object
	 * @param customersAdded	ArrayList of Customers that have been created from the flat file so far
	 * @return					bid information that will be further divided later in the process
	 */
	public static String[] createCustomer(String data, ArrayList<Customer> customersAdded) {
		String[] info = toArray(data);
		
		String username = nullCheckString(info, 0);
		String password = nullCheckString(info, 1);
		int userID = nullCheckInteger(info, 2);
		double balance = nullCheckDouble(info, 3);
			
		Customer customer = new Customer(username, password, userID, "customer", balance);
		//Driver.accounts.add(customer);
		customersAdded.add(customer);
		
		// Return the bid IDs back to the ArrayList so we can load the customer's
		// bids once we load them into the program.
		String[] bidInfo = createBidInfo(info);
		return bidInfo;
	}

	/**
	 * Returns an array in which each element is a list of bid IDs found in
	 * the String parameter supplied to it.
	 * 
	 * @param info	String from which to create the elements of the array
	 * @return		array of separated bid information
	 */
	public static String[] createBidInfo(String[] info) {
		String[] bidInfo = new String[3];
		
		if (info.length > 4) {
			if(!info[4].equals("")) {
				bidInfo[0] = info[4];
			}
			else
				bidInfo[0] = "";
			if (!info[5].equals("")) {
				bidInfo[1] = info[5];
			}
			else
				bidInfo[1] = "";
			if (!info[6].equals("")) {
				bidInfo[2] = info[6];
			}
			else 
				bidInfo[2] = "";
		}
		
		return bidInfo;
	}
	
	
	/**
	 * Returns an Item object that is created from the information provided in the parameter
	 * 
	 * @param data a String object that holds the information necessary to create an item
	 * @return		the item created from the information in the String object
	 */
	public static Item createItem(String data) {
		String[] info = toArray(data);
		boolean paidFor = false;
		int paidForFlag = nullCheckInteger(info, 4);
		if (paidForFlag == 1) paidFor = true;
		boolean available = false;
		int availableFlag = nullCheckInteger(info, 5);
		if (availableFlag == 1) available = true;
		//Driver.items.add(new Item(Integer.parseInt(info[0]), info[1], Integer.parseInt(info[2])));
		Item item = new Item(Double.parseDouble(info[0]), info[1], Integer.parseInt(info[2]), Integer.parseInt(info[3]), paidFor, available);
		return item;
	}
	
	
	/**
	 * 
	 * Creates an active auction from the data read in from the text file.
	 * Returns an array of Strings containing bid information that was found in
	 * the flat file to be processed later.
	 * Divides the data parameter into an array for use, and those elements are
	 * used as values from which to create an Auction object.
	 * 
	 * @param data		data read in from the text file
	 * @param items		collection of items that have been created from the flat file so far
	 * @param auctions	collection of auctions that have been created from the flat file so far
	 * @return 			bid information that will be further divided later in the process 
	 */
	public static String[] createActiveAuction(String data, ArrayList<Item> items, ArrayList<Auction> auctions) {
		String[] info = toArray(data);
		
		int auctionID = nullCheckInteger(info, 0);
		Item item = findItem(Integer.parseInt(info[1]), items);
		double currentSellingPrice = nullCheckDouble(info, 2);
		LocalDateTime openingTime;
		LocalDateTime closingTime;
		Auction auction;		
		if (info[6].equals("$")) {
			openingTime = LocalDateTime.now();
			closingTime = openingTime.plusMinutes(5);
		}
		else {
			openingTime = createDateTime(info[6]);
			closingTime = createDateTime(info[7]);
		}
		auction = new Auction(item, auctionID, currentSellingPrice, openingTime, closingTime);
		auctions.add(auction);
		
		String[] bidInfo = new String[2];
		if (info[4] != null)
			bidInfo[0] = info[4];
		else
			bidInfo[0] = null;
		if (info.length >= 6 && info[5] != null)
			bidInfo[1] = info[5];
		else
			bidInfo[1] = null;
		
		return bidInfo;
	}
	
	/**
	 * Creates a completed auction from the data read in from the text file.
	 * Returns the String object that contains the bid information for the specified
	 * a particular auction.
	 * Divides the data parameter into an array for use, and those elements are
	 * used as values from which to create an Auction object.
	 * 
	 * @param data				data read in from the text file
	 * @param items				collection of items that have been created from the flat file so far
	 * @param completedAuctions	collection of auctions that have been created from the flat file so far
	 * @return					bid information that will be further divided later in the process
	 */
	public static String createCompletedAuction(String data, ArrayList<Item> items, ArrayList<Auction> completedAuctions) {
		String[] info = toArray(data);
		Item item = findItem(Integer.parseInt(info[1]), items);
		LocalDateTime openingTime = createDateTime(info[5]);
		LocalDateTime closingTime = createDateTime(info[6]);
		Auction auction = new Auction(item, Integer.parseInt(info[0]), openingTime, closingTime);
		if (item != null) item.setAvailable(false);
		auction.setActive(false);
		completedAuctions.add(auction);
		return info[4];
	}
	
	/**
	 * Creates a future auction from the data read in from the text file.
	 * Returns the auction object created from the data in the 
	 * Divides the data parameter into an array for use, and those elements are
	 * used as values from which to create an Auction object.
	 * 
	 * @param data				data read in from the text file
	 * @param items				collection of items that have been created from the flat file so far
	 * @return					bid information that will be further divided later in the process
	 */
	public static Auction createFutureAuction(String data, ArrayList<Item> items) {
		String[] info = toArray(data);
		Item item = findItem(Integer.parseInt(info[1]), items);
		LocalDateTime openingTime;
		LocalDateTime closingTime;
		if (info[2].equals("$")) {
			openingTime = LocalDateTime.now().plusMinutes(2);
			closingTime = openingTime.plusMinutes(5);
		}
		else {
			openingTime = createDateTime(info[2]);
			closingTime = createDateTime(info[3]);
		}
		Auction auction = new Auction(item, Integer.parseInt(info[0]), openingTime, closingTime);
		auction.setActive(false);
		return auction;
	}
	
	/**
	 * Returns a bid object created from the data parameter.
	 * Divides the data found in the parameter into an array of Strings
	 * wherein each element is used to create the Bid object.
	 * 
	 * @param data						data read in from the text file
	 * @param customersAdded			collection of customers that have been created from the flat file so far
	 * @param auctionsAdded				collection of auctions that have been created from the flat file so far
	 * @param completedAuctionsAdded	collection of completed auctions that have been created from the flat file so far
	 * @return							valid bid that belongs to a previously created auction 
	 */
	public static Bid createBid(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> auctionsAdded, ArrayList<Auction> completedAuctionsAdded) {
		String[] info = toArray(data);
		
		// info[1] contains the String version of the auctionID of the auction that the bid belongs to
		for (int i = 0; i < auctionsAdded.size(); i++) {
			if (auctionsAdded.get(i).getAuctionID() == Integer.parseInt(info[1])) {
				return createBidActive(data, customersAdded, auctionsAdded);
			}
		}
		return createBidCompleted(data, customersAdded, completedAuctionsAdded);
	}
	
	/**
	 * Returns a valid bid that belongs to an auction that has been determined to be ongoing.
	 * Divides the data in the data into an array of Strings and uses the elements therein
	 * to create the Bid object.
	 * 
	 * @param data				data from which the bid is to be created
	 * @param customersAdded	collection of customers already created from the flat file
	 * @param auctionsAdded		collection of auctions already created from the flat file
	 * @return					valid bid that belongs to a previously created active auction
	 */
	public static Bid createBidActive(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> auctionsAdded) {
		String[] info = toArray(data);
		LocalDateTime dateTime = createDateTime(info[4]);
		Bid bid = new Bid(Double.parseDouble(info[0]), findAuction(Integer.parseInt(info[1]), auctionsAdded), findCustomer(Integer.parseInt(info[2]), customersAdded), Integer.parseInt(info[3]), dateTime);
		return bid;
	}
	
	/**
	 * Returns a valid bid that belongs to an auction that has been previously determined
	 * to have already ended.
	 * Divides the data in the data into an array of Strings and uses the elements therein
	 * to create the Bid object.
	 * 
	 * @param data						data from which the bid is to be created
	 * @param customersAdded			collection of customers already created from the flat file
	 * @param completedAuctionsAdded	collection of completed auctions already created from the flat file
	 * @return							valid bid that belongs to a previously created completed auction
	 */
	public static Bid createBidCompleted(String data, ArrayList<Customer> customersAdded, ArrayList<Auction> completedAuctionsAdded) {
		String[] info = toArray(data);
		LocalDateTime dateTime = createDateTime(info[4]);
		Bid bid = new Bid(Double.parseDouble(info[0]), findAuction(Integer.parseInt(info[1]), completedAuctionsAdded), findCustomer(Integer.parseInt(info[2]), customersAdded), Integer.parseInt(info[3]), dateTime);
		return bid;
	}
	
	/**
	 * Populates the winningBids, activeBids, and historicBids collections of each of the customers
	 * that the class has thus far created from the text file. It does this by taking the data inside the bid buffer
	 * and applying the correct bids to each of the proper collections. 
	 * 
	 * @param customers	collection of customers already created from the flat file
	 * @param bidBuffer	collection of bid information 
	 * @param bids		collection of bids already created from the flat file
	 */
	public static void populateCustomerBids(ArrayList<Customer> customers, ArrayList<String[]> bidBuffer, ArrayList<Bid> bids) {
		for (int i = 0; i < customers.size(); i++) {		
				
			String[] winningBidIDs = {};
			if (bidBuffer.get(i)[0] != null) {
				winningBidIDs = bidBuffer.get(i)[0].split("#");
			}
			
			String[] activeBidIDs = {};
			if (bidBuffer.get(i)[1] != null) {
				activeBidIDs = bidBuffer.get(i)[1].split("#");
			}
			
			String[] historicBidIDs = {};
			if (bidBuffer.get(i)[2] != null) {
				historicBidIDs = bidBuffer.get(i)[2].split("#");
			}
				
			for (int j = 0; j < winningBidIDs.length; j++) {
				if (!winningBidIDs[0].equals("")) {
					customers.get(i).getWinningBids().add(findBid(Integer.parseInt(winningBidIDs[j]), bids));
				}
			}
			
			for (int j = 0; j < activeBidIDs.length; j++) {
				if (!activeBidIDs[0].equals("")) {
					customers.get(i).getActiveBids().add(findBid(Integer.parseInt(activeBidIDs[j]), bids));
				}
			}
			
			for (int j = 0; j < historicBidIDs.length; j++) {
				if (!historicBidIDs[0].equals("")) {
					customers.get(i).getHistoricBids().add(findBid(Integer.parseInt(historicBidIDs[j]), bids));
				}
			}
		}
	}
	
	/**
	 * Populates the collections of bids in each auction from 
	 * that the class has thus far created from the text file. It does this by taking the data inside the bid buffer
	 * and applying the correct bids to each of the proper collections. 
	 * 
	 * @param auctions			collection of auctions already created from the flat file
	 * @param auctionBidBuffer	list of bid IDs that belong to each auction
	 * @param bids				collection of bids already created from the flat file
	 */
	public static void populateAuctionBids(ArrayList<Auction> auctions, ArrayList<String[]> auctionBidBuffer, ArrayList<Bid> bids) {
		for (int i = 0; i < auctions.size(); i++) {
			
			String[] processedBidIDs = {};
			if (auctionBidBuffer.get(i)[0] != null) {
				processedBidIDs = auctionBidBuffer.get(i)[0].split("#");
			}
			
			String[] unprocessedBidIDs = {};
			if (auctionBidBuffer.get(i)[1] != null) {
				unprocessedBidIDs = auctionBidBuffer.get(i)[1].split("#");
			}
			
			if (!processedBidIDs[0].equals("")) {
				for (int j = 0; j < processedBidIDs.length; j++) {
					auctions.get(i).getProcessedBids().push(findBid(Integer.parseInt(processedBidIDs[j]), bids));
				}
			}

			if (!unprocessedBidIDs[0].equals("") || unprocessedBidIDs.length == 0) {
				for (int j = 0; j < unprocessedBidIDs.length; j++) {
					auctions.get(i).getUnprocessedBids().enqueue(findBid(Integer.parseInt(unprocessedBidIDs[j]), bids));
				}
			}

			if (!processedBidIDs[0].equals("")) {
				Bid highest = auctions.get(i).getProcessedBids().peek();
				auctions.get(i).setCurrentHighest(highest);
			}
		}
	}
	
	/**
	 * Populates the completed bids collection for each of the completed auctions that
	 * have been read in from the flat file. Each element in the historicBids parameter
	 * is a list of bid IDs, and each element corresponds to the element with the same
	 * index in the completedAuctions parameter. The string of IDs is broken into individual IDs,
	 * and these are used to search for the bid objects present list of bids that have been created
	 * from the flat file.
	 * 
	 * @param historicBids		list of bid IDs to be associated with each auction
	 * @param completedAuctions	list of completed auctions created from the flat file
	 * @param bids				list of bids already created from the flat file
	 */
	public static void populateCompletedAuctionBids(ArrayList<String> historicBids, ArrayList<Auction> completedAuctions, ArrayList<Bid> bids) {
		for (int i = 0; i < historicBids.size(); i++) {
			String[] bidIDs = historicBids.get(i).split("#");
			
			if (!bidIDs[0].equals("")) { 
				for (int j = 0; j < bidIDs.length; j++) {
					completedAuctions.get(i).getProcessedBids().push(findBid(Integer.parseInt(bidIDs[j]), bids));
				}
			}
			
			if (!completedAuctions.get(i).getProcessedBids().isEmpty()) {
				completedAuctions.get(i).setCurrentHighest(completedAuctions.get(i).getProcessedBids().peek());
				completedAuctions.get(i).setSellingPrice(estSellingPrice(completedAuctions.get(i), bidIDs, bids));
			}
		}
	}

	/**
	 * Returns a double that represents the selling price of an item in a specified completed auction.
	 * 
	 * @param auction			a completed auction object
	 * @param processedBidIDs	IDs of bids that are associated with the specified auction
	 * @param bids				list of all bids brought in from the text file
	 */
	public static double estSellingPrice(Auction auction, String[] processedBidIDs, ArrayList<Bid> bids) {
		int search = processedBidIDs.length - 2;
		Bid b = findBid(Integer.parseInt(processedBidIDs[search]), bids);
		
		
		boolean testBoolean = b.isValid();
		double value = b.getValue();
		double value2 = auction.getCurrentHighest().getValue();
		
		boolean found = false;
		while (!found) {
			if (b.isValid() && b.getValue() < auction.getCurrentHighest().getValue()) {
				auction.setSellingPrice(b.getValue());
				found = true;
			}
			else search -= 1;
		}
		return b.getValue();
	}
	
	/**
	 * Adds the various collections of Items, Auctions, and Customers created in the process of reading
	 * the flat file to the driver file for use with the rest of the program.
	 * 
	 * @param items				list of items created from the information in the text file
	 * @param activeAuctions	list of ongoing auctions created from the information in the text file
	 * @param completedAuctions	list of completed auctions created from the information in the text file
	 * @param futureAuctions	list of future auctions created from the information in the text file
	 * @param customersAdded	list of customers created from the information in the text file
	 */
	public static void addToDriver(ArrayList<Item> items, ArrayList<Auction> activeAuctions, ArrayList<Auction> completedAuctions, ArrayList<Auction> futureAuctions, ArrayList<Customer> customersAdded) {
		
		if (!items.isEmpty()) {
			ObjectALInsertionSort.insertionSort(items);
			for (int i = 0; i < items.size(); i++) {
				if (!searchDriver(Driver.items, items.get(i))) {
					Driver.items.add(items.get(i));
				}
			}
		}
		
		for (int i = 0; i < activeAuctions.size(); i++) {
			ObjectALInsertionSort.insertionSort(activeAuctions);
			if (!activeAuctions.isEmpty()) {
				if (!searchDriver(Driver.ongoingAuctions, activeAuctions.get(i))) {
					Driver.ongoingAuctions.add(activeAuctions.get(i));
				}
			}
		}
		
		for (int i = 0; i < completedAuctions.size(); i++) {
			ObjectALInsertionSort.insertionSort(completedAuctions);
			if (!completedAuctions.isEmpty()) {
				if (!searchDriver(Driver.completedAuctions, completedAuctions.get(i))) {
					Driver.completedAuctions.add(completedAuctions.get(i));
				}
			}
		}
		
		for (int i = 0; i < futureAuctions.size(); i++) {
			ObjectALInsertionSort.insertionSort(futureAuctions);
			if (!futureAuctions.isEmpty()) {
				if (!searchDriver(Driver.futureAuctions, futureAuctions.get(i))) {
					Driver.futureAuctions.add(futureAuctions.get(i));
				}
			}
		}
		
		for (int i = 0; i < customersAdded.size(); i++) {
			ObjectALInsertionSort.insertionSort(customersAdded);
			if (!customersAdded.isEmpty()) {
				Driver.accounts.add(customersAdded.get(i));
			}
		}
	}
	
	/**
	 * Returns the Customer with the specified customer ID.
	 * 
	 * @param customerID	ID of the customer being searched for
	 * @param customers		the list of customers created from the information in the flat file
	 * @return				the specified customer
	 */
	public static Customer findCustomer(int customerID, ArrayList<Customer> customers) {
		for (int i = 0; i < customers.size(); i++) {
			if (customerID == customers.get(i).getUserID()) {
				return customers.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns the Auction with the specified auction ID.
	 * 
	 * @param auctionID	ID of the auction being searched for
	 * @param auctions	list of auctions created from the information in the flat file
	 * @return			the auction with the specified ID
	 */
	public static Auction findAuction(int auctionID, ArrayList<Auction> auctions) {
		for (int i = 0; i < auctions.size(); i++) {
			if (auctionID == auctions.get(i).getAuctionID()) {
				return auctions.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns the Item with the specified item ID.
	 * 
	 * @param itemID	ID of the item being searched for
	 * @param items		list of items created from the information in the flat file
	 * @return			the item with the specified ID
	 */
	public static Item findItem(int itemID, ArrayList<Item> items) {
		for (int i = 0; i < items.size(); i++) {
			if (itemID == items.get(i).getItemID()) {
				return items.get(i);
			}
		}
		return null;
	}

	/**
	 * Returns the Bid with the specified bid ID.
	 * 
	 * @param bidID	ID of the auction being searched for
	 * @param bids	list of bids created from the information in the flat file
	 * @return		the bid with the specified ID
	 */
	public static Bid findBid(int bidID, ArrayList<Bid> bids) {
		for (int i = 0; i < bids.size(); i++) {
			if (bidID == bids.get(i).getBidID()) {
				return bids.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Searches a list in the driver for a specific object, returns true if the object is present in the
	 * specified list, returns false if it is not.
	 * 
	 * @param driverList	collection in the driver to be searched
	 * @param object		the object being searched for
	 * @return				boolean indicating the status of the object
	 */
	public static <E extends Comparable<E>> boolean searchDriver(ArrayList<E> driverList, E object) {
		for (int i = 0; i < driverList.size(); i++) {
			if (driverList.get(i).compareTo(object) == 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks the element of an array of Strings at the specified index to determine if it has no characters.
	 * Returns the contents of the specified element if it is not void of characters, returns null if it is.
	 * 
	 * @param info	array of Strings
	 * @param index	location in the array at which to perform a check 
	 * @return		the status of the element
	 */
	public static String nullCheckString(String[] info, int index) {
		try {
			if (!info[index].equals("")) {
				return info[index];
			}
			throw new NullDataException("Missing Customer data: username or password not found");
		}
		catch (Exception e) {
			System.out.println("The text file didn't contain sufficient information to populate the program. "
					+ "Check to make sure that the text file includes all necessary information.");
		}
		return null;
	}

	/**
	 * Checks the element of an array of Strings at the specified index to determine if it has no characters.
	 * Returns the contents of the specified element if it is not void of characters, returns null if it is.
	 * 
	 * @param info	array of Strings
	 * @param index	location in the array at which to perform a check
	 * @return		the status of the element
	 */
	public static int nullCheckInteger(String[] info, int index) {
		try {
			if (!info[index].equals("")) {
				return Integer.parseInt(info[index]);
			}
			throw new NullDataException("Null data at info[" + index + "]");
		}
		catch (Exception E) {
			
		}
		return 0;
	}
	
	/**
	 * Checks the element of an array of Strings at the specified index to determine if it has no characters.
	 * Returns the contents of the specified element if it is not void of characters, returns null if it is.
	 * 
	 * @param info	array of Strings
	 * @param index	location in the array at which to perform a check
	 * @return		the status of the element
	 */
	public static double nullCheckDouble(String[] info, int index) {
		if (!info[index].equals("")) {
			return Double.parseDouble(info[index]);
		}
		else return 0.0;
	}
	
	/**
	 * Creates a LocalDateTime object out of the information found in the data parameter. 
	 * The data parameter is split up and each piece of data is used to create the LocalDateTime object.
	 * 
	 * @param data	the data to create the LocalDateTime object from
	 * @return		a LocalDateTime object
	 */
	public static LocalDateTime createDateTime(String data) {
		String[] info = data.split("#");
		int year = Integer.parseInt(info[0]);
		int month = Integer.parseInt(info[1]);
		int day = Integer.parseInt(info[2]);
		int hour = Integer.parseInt(info[3]);
		int minute = Integer.parseInt(info[4]);
		int second = Integer.parseInt(info[5]);
		
		LocalDate date = LocalDate.of(year, month, day);
		LocalTime time = LocalTime.of(hour, minute, second);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		return dateTime;
	}

	/**
	 * Returns an array of Strings created from a String. The delimiting character is ','.
	 * 
	 * @param data	the String to be broken up
	 * @return		an array of Strings
	 */
	public static String[] toArray(String data) {
		String[] array = data.split(",");
		return array;
	}
	
	/**
	 * Handles dialouge box creation for reading a file into the program from the user's computer.
	 * 
	 * @return	a BufferedReader object
	 */
	public static BufferedReader openRead() {
		Frame f = new Frame();
		// decide from where to read the file
		FileDialog foBox = new FileDialog(f, "Pick location for reading your file", FileDialog.LOAD);
		System.out.println("The dialog box will appear behind Eclipse.  " + 
		      "\n   Choose where you would like to read from.");
		foBox.setVisible(true);
		// get the absolute path to the file
		String foName = foBox.getFile();
		String dirPath = foBox.getDirectory();

		// create a file instance for the absolute path
		File inFile = new File(dirPath + foName);
		if (!inFile.exists()) {
			System.out.println("That file does not exist");
			System.exit(0);
		}
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(inFile));
		} catch (IOException e) {
			System.out.println("You threw an exception. ");
		}
		return in;
	}
	
	/**
	 * Handles the way a file is dissected for integration with the program
	 * 
	 * @return a String object that represents the text file that was read in
	 */
	public static String inputData() {
		String concatLine = "";
		BufferedReader bf = null;
		try {
			// Open the file.
			bf = openRead();

			// read in the first line
			String line = "";
			try {
				line = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// while there is more data in the file, process it
			while (line != null) {
				concatLine = concatLine + line + "\n";
				// read in the next line
				try {
					line = bf.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end of reading in the data.

		}
		// catch any other type of exception
		catch (Exception e) {
			System.out.println("Other weird things happened");
			e.printStackTrace();
		} finally {
			try {
				bf.close();
			} catch (Exception e) {
			}
		}		
		return concatLine;
	}
	
}
