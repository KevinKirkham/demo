package rinosJavaSalesProject;

import java.awt.FileDialog;


import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Exports a text file that contains information on the current state of the program, including
 * Auctions, Customers, Bids, and Items
 * @author waveo
 *
 */

public class Write {
	
	// TO BE REMOVED WHEN IT IS CONCLUDED THAT THIS IS NO LONGER NECESSARY
	public static void main(String[] args) {
		Driver.accounts = new ArrayList<Account>();
		Driver.ongoingAuctions = new ArrayList<Auction>();
		Driver.completedAuctions = new ArrayList<Auction>();
		Driver.futureAuctions = new ArrayList<Auction>();
		Driver.items = new ArrayList<Item>();
		Read.read();
		write();
		Read.read();
	}
	
	/**
	 * Writes the information found in the current running program to a text file.
	 */
	public static void write() {
		ArrayList<Customer> customers = collectCustomers();
		ArrayList<Item> items = collectItems();
		ArrayList<Auction> activeAuctions = collectActiveAuctions();
		ArrayList<Auction> completedAuctions = collectCompletedAuctions();
		ArrayList<Auction> futureAuctions = collectFutureAuctions();
		ArrayList<Bid> bids = collectBids(completedAuctions, activeAuctions);
		
		String customerBlock = writeCustomers(customers);
		String itemsBlock = writeItems(items);
		String activeAuctionsBlock = writeActiveAuctions(activeAuctions);
		String completedAuctionsBlock = writeCompletedAuctions(completedAuctions);
		String futureAuctionsBlock = writeFutureAuctions(futureAuctions);
		String bidsBlock = writeBids(bids);
		
		String output = compileBlocks(customerBlock, itemsBlock, activeAuctionsBlock, completedAuctionsBlock, futureAuctionsBlock, bidsBlock);
		//System.out.println(output);
		outputData(output);
	}
	
	/**
	 * Gathers the customers present in the driver for further processing.
	 * 
	 * @return	an ArrayList of Customers
	 */
	public static ArrayList<Customer> collectCustomers() {
		ArrayList<Customer> customers = new ArrayList<>();
		
		ArrayList<Customer> accounts = new ArrayList<>();
		for (int i = 0; i < Driver.accounts.size();i++) {
			if (Driver.accounts.get(i) instanceof Customer) 
				customers.add((Customer) Driver.accounts.get(i));
		}
		ObjectALInsertionSort.insertionSort(customers);
		return customers;
	}
	
	/**
	 * Gathers the items present in the driver for further processing.
	 * 
	 * @return	an ArrayList of Items
	 */
	public static ArrayList<Item> collectItems() {
		ArrayList<Item> items = new ArrayList<>();
		for (int i = 0; i < Driver.items.size(); i++) {
			items.add(Driver.items.get(i));
		}
		ObjectALInsertionSort.insertionSort(items);
		return items;
	}
	
	/**
	 * Gathers the active auctions present in the driver for further processing.
	 * 
	 * @return	an ArrayList of Auctions
	 */
	public static ArrayList<Auction> collectActiveAuctions() {
		ArrayList<Auction> activeAuctions = new ArrayList<>();
		for (int i = 0; i < Driver.ongoingAuctions.size(); i++) {
			activeAuctions.add(Driver.ongoingAuctions.get(i));
		}
		ObjectALInsertionSort.insertionSort(activeAuctions);
		return activeAuctions;
	}
	
	/**
	 * Gathers the completed auctions present in the driver for further processing.
	 * 
	 * @return	an ArrayList of Auctions
	 */
	public static ArrayList<Auction> collectCompletedAuctions() {
		ArrayList<Auction> completedAuctions = new ArrayList<>();
		for (int i = 0; i < Driver.completedAuctions.size(); i++) {
			if (Driver.completedAuctions != null) {
				completedAuctions.add(Driver.completedAuctions.get(i));			
			}
		}
		ObjectALInsertionSort.insertionSort(completedAuctions);
		return completedAuctions;
	}
	
	/**
	 * Gathers the future auctions present in the driver for further processing.
	 * 
	 * @return	an ArrayList of Auctions
	 */
	public static ArrayList<Auction> collectFutureAuctions() {
		ArrayList<Auction> futureAuctions = new ArrayList<>();
		for (int i = 0; i < Driver.futureAuctions.size(); i++) {
			if (Driver.futureAuctions != null) {
				futureAuctions.add(Driver.futureAuctions.get(i));	
			}
		}
		ObjectALInsertionSort.insertionSort(futureAuctions);
		return futureAuctions;
	}
	
	
	/**
	 * Gathers the bids present in the driver for further processing and
	 * sorts them to make the process of finding a specific bid easier in the future.
	 * 
	 * @return	an ArrayList of Bids
	 */
	public static ArrayList<Bid> collectBids(ArrayList<Auction> completedAuctions, ArrayList<Auction> activeAuctions) {
		ArrayList<Bid> bids = new ArrayList<>();
		for (int i = 0; i < completedAuctions.size(); i++) {
			Stack<Bid> completedBids = completedAuctions.get(i).getProcessedBids().clone();
			while (completedBids.size() > 0) {
				bids.add((Bid) completedBids.pop());
			}
		}
		
		for (int i = 0; i < activeAuctions.size(); i++) {
			if (!activeAuctions.get(i).getProcessedBids().isEmpty()) {
				Stack<Bid> processedBids = activeAuctions.get(i).getProcessedBids().clone();
				while (processedBids.size() > 0) {
					bids.add((Bid) processedBids.pop());
				}
			}
			
			if (!activeAuctions.get(i).getUnprocessedBids().isEmpty()) {
				Queue<Bid> unprocessedBids = activeAuctions.get(i).getUnprocessedBids().clone();
				for (int j = 0; j < unprocessedBids.size(); j++) {
					bids.add((Bid) unprocessedBids.dequeue());
				}
			}
		}
		ObjectALMergeSort.mergeSort(bids);
		return bids;
	}
	
	/**
	 * Writes the collections of customers from the list of customers to a block that will get
	 * added to a text file.
	 * 
	 * @param customers	the list of customers gathered from the driver
	 * @return			a String formatted as a block
	 */
	public static String writeCustomers(ArrayList<Customer> customers) {
		String block = "";
		for (int i = 0; i < customers.size(); i++) {
			if (i == customers.size() - 1) {
				block = block + customers.get(i).getUsername() + ","
						+ customers.get(i).getPassword() + ","
						+ customers.get(i).getUserID() + ","
						+ customers.get(i).getBalance() + ","
						+ compileBidIDs(customers.get(i).getWinningBids()) + ","
						+ compileBidIDs(customers.get(i).getActiveBids()) + ","
						+ compileBidIDs(customers.get(i).getHistoricBids());
			}
			else {
				block = block + customers.get(i).getUsername() + ","
						+ customers.get(i).getPassword() + ","
						+ customers.get(i).getUserID() + ","
						+ customers.get(i).getBalance() + ","
						+ compileBidIDs(customers.get(i).getWinningBids()) + ","
						+ compileBidIDs(customers.get(i).getActiveBids()) + ","
						+ compileBidIDs(customers.get(i).getHistoricBids()) + "|\n";
			}
		}
		return block;
	}
	
	/**
	 * Writes the collections of items from the list of items to a block that will get
	 * added to a text file.
	 * 
	 * @param items	the list of items gathered from the driver
	 * @return		a String formatted as a block
	 */
	public static String writeItems(ArrayList<Item> items) {
		String block = "";
		for (int i = 0; i < items.size(); i++) {
			if (i == items.size() - 1) {
				block = block + items.get(i).getStartingPrice() + ","
						+ items.get(i).getName()  + ","
						+ items.get(i).getIncrement() + ","
						+ items.get(i).getItemID() + ","
						+ checkPaidFor(items.get(i)) + ","
						+ checkAvailable(items.get(i));
			}
			else {
				block = block + items.get(i).getStartingPrice() + ","
						+ items.get(i).getName()  + ","
						+ items.get(i).getIncrement() + ","
						+ items.get(i).getItemID() + ","
						+ checkPaidFor(items.get(i)) + ","
						+ checkAvailable(items.get(i)) + "|\n";
			}
		}
		return block;
	}
	
	/**
	 * Writes the collections of active auctions from the list of active auctions to a block that will
	 * get added to a text file.
	 * 
	 * @param auctions	the list of auctions gathered from the driver
	 * @return			a String formatted as a block
	 */
	public static String writeActiveAuctions(ArrayList<Auction> auctions) {
		String block = "";
		if (auctions != null) {
			for (int i = 0; i < auctions.size(); i++) {
				if (i == auctions.size() - 1) {
					block = block + auctions.get(i).getAuctionID() + ","
							+ auctions.get(i).getItem().getItemID() + ","
							+ auctions.get(i).getCurrentSalesPrice() + ","
							+ checkNullProcessed(auctions.get(i)) + ","
							+ checkNullUnprocessed(auctions.get(i)) + ","
							+ compileDateTime(auctions.get(i).getStartDateTime()) + "," 
							+ compileDateTime(auctions.get(i).getEndDateTime());
				}
				else {
					block = block + auctions.get(i).getAuctionID() + ","
							+ auctions.get(i).getItem().getItemID() + "," + auctions.get(i).getCurrentSalesPrice() + "," 
							+ checkNullProcessed(auctions.get(i)) + ","
							+ checkNullUnprocessed(auctions.get(i)) + ","
							+ compileDateTime(auctions.get(i).getStartDateTime()) + "," 
							+ compileDateTime(auctions.get(i).getEndDateTime()) + "|\n";
				}
			}
		}
		return block;
	}
	
	/**
	 * Writes the collections of completed auctions from the list of completed auctions to a block that will
	 * get added to a text file.
	 * 
	 * @param auctions	the list of auctions gathered from the driver
	 * @return			a String formatted as a block
	 */
	public static String writeCompletedAuctions(ArrayList<Auction> auctions) {
		String block = "";
		for (int i = 0; i < auctions.size(); i++) {
			if (i == auctions.size() - 1) {
				block = block + auctions.get(i).getAuctionID() + ","
						+ auctions.get(i).getItem().getItemID() + ","
						+ auctions.get(i).getCurrentSalesPrice() + ","
						+ checkNullProcessed(auctions.get(i)) + ","
						+ compileDateTime(auctions.get(i).getStartDateTime()) + ","
						+ compileDateTime(auctions.get(i).getEndDateTime());
			}
			else {
				block = block + auctions.get(i).getAuctionID() + ","
						+ auctions.get(i).getItem().getItemID() + ","
						+ auctions.get(i).getCurrentSalesPrice() + ","
						+ checkNullProcessed(auctions.get(i)) + ","
						+ compileDateTime(auctions.get(i).getStartDateTime()) + ","
						+ compileDateTime(auctions.get(i).getEndDateTime()) + "|\n";
			}
		}
		return block;
	}
	
	/**
	 * Writes the collections of future auctions from the list of future auctions to a block that will
	 * get added to a text file.
	 * 
	 * @param auctions	the list of auctions gathered from the driver
	 * @return			a String formatted as a block
	 */
	public static String writeFutureAuctions(ArrayList<Auction> auctions) {
		String block = "";
		for (int i = 0; i < auctions.size(); i++) {
			if (i == auctions.size() - 1) {
				block = block + auctions.get(i).getAuctionID() + ","
						+ auctions.get(i).getItem().getItemID() + ","
						+ compileDateTime(auctions.get(i).getStartDateTime()) + ","
						+ compileDateTime(auctions.get(i).getEndDateTime());
			}
			else {
				block = block + auctions.get(i).getAuctionID() + ","
						+ auctions.get(i).getItem().getItemID() + ","
						+ compileDateTime(auctions.get(i).getStartDateTime()) + ","
						+ compileDateTime(auctions.get(i).getEndDateTime()) + "|\n";
			}
		}
		return block;
	}
	
	/**
	 * Writes the collections of bids from the list of bids to a block that will
	 * get added to a text file.
	 * 
	 * @param bids	the list of bids gathered from the driver
	 * @return		a String formatted as a block
	 */
	public static String writeBids(ArrayList<Bid> bids) {
		String block = "";
		for (int i = 0; i < bids.size(); i++) {
			if (i != 0 && bids.get(i - 1).getAuction().getAuctionID() != bids.get(i).getAuction().getAuctionID()) {
				block = block + "\n";
			}
			if (i == bids.size() - 1) {
				block = block + bids.get(i).getValue() + ","
						+ bids.get(i).getAuction().getAuctionID() + ","
						+ bids.get(i).getCustomer().getUserID() + ","
						+ bids.get(i).getBidID() + ","
						+ compileDateTime(bids.get(i).getDateTime());
			}
			else {
				block = block + bids.get(i).getValue() + ","
						+ bids.get(i).getAuction().getAuctionID() + ","
						+ bids.get(i).getCustomer().getUserID() + ","
						+ bids.get(i).getBidID() + ","
						+ compileDateTime(bids.get(i).getDateTime()) + "|\n";
			}
		}
		return block;
	}
	
	/**
	 * Compiles blocks of information together into a single String object
	 * 
	 * @param customers			block of information regarding customers
	 * @param items				block of information regarding items
	 * @param activeAuctions	block of information regarding active auctions
	 * @param completedAuctions	block of information regarding completed auctions
	 * @param futureAuctions	block of information regarding future auctions
	 * @param bids				block of information regarding bids
	 * @return					a compilation of blocks of information
	 */
	public static String compileBlocks(String customers, String items, String activeAuctions, String completedAuctions, String futureAuctions, String bids) {
		return customers + "\n}\n\n"
					+ items + "\n}\n\n"
					+ activeAuctions + "\n}\n\n"
					+ completedAuctions + "\n}\n\n"
					+ futureAuctions + "\n}\n\n"
					+ bids;
	}
	
	/**
	 * Compiles the bid IDs of the bids found in a list of bids into a single String object.
	 * 
	 * @param bids	a list of bid objects
	 * @return		a String containing bid IDs
	 */
	public static String compileBidIDs(ArrayList<Bid> bids) {
		String bidIDs = "";
		if (bids != null) {
			for (int i = 0; i < bids.size(); i++) {
				if (i == bids.size() - 1)
					bidIDs = bidIDs + bids.get(i).getBidID();
				else 
					bidIDs = bidIDs + bids.get(i).getBidID() + "#";
			}
		}
		return bidIDs;
	}
	
	/**
	 * Returns a String created from the information in a LocalDateTime object.
	 * 
	 * @param dateTime	a LocalDateTime object
	 * @return			a String containing information from a LocalDateTime object
	 */
	public static String compileDateTime(LocalDateTime dateTime) {
		return dateTime.getYear() + "#" + dateTime.getMonthValue() + "#" 
				+ dateTime.getDayOfMonth() + "#" + dateTime.getHour() + "#" 
				+ dateTime.getMinute() + "#" + dateTime.getSecond();
	}
	
	/**
	 * Returns a String that represents the contents of the collection of processed
	 * bids for a specific auction. Needed for the section of the text file that has information about 
	 * which bid IDs pertain to it.
	 * 
	 * @param auction	an Auction object
	 * @return			a String representing the contents of a collection of bids for a specific auction
	 */
	public static String checkNullProcessed(Auction auction) {
		String output = "";
		if (auction.getProcessedBids().size() != 0) {
			output = output + auction.getCurrentHighest().getBidID() + ","
					+ compileBidIDs(auction.getProcessedBids().toArrayList()); 
		}
		else {
			output = output + ",";
		}
		return output;
	}
	
	/**
	 * Returns a String that represents the contents of the collection of unprocessed
	 * bids for a specific auction. Needed for the section of the text file that has information about 
	 * which bid IDs pertain to it.
	 * 
	 * @param auction	an Auction object
	 * @return			a String representing the contents of a collection of bids for a specific auction
	 */
	public static String checkNullUnprocessed(Auction auction) {
		String output = "";
		if (auction.getUnprocessedBids() != null) {
			output = output + compileBidIDs(auction.getUnprocessedBids().toArrayList());
		}
		return output;
	}
	
	/**
	 * Returns a string object that represents the status of the paidFor field in the Item class.
	 * 
	 * @param i	an Item object
	 * @return	String that represents the paidFor field
	 */
	public static String checkPaidFor(Item i) {
		if (!i.isPaidFor()) return "0";
		else return "1";
	}
	
	/**
	 * Returns a string object that represents the status of the available field in the Item class.
	 * 
	 * @param i	an Item object
	 * @return	String that represents the available field
	 */
	public static String checkAvailable(Item i) {
		if (!i.isAvailable()) return "0";
		else return "1";
	}
	
	/**
	 * Handles the creation of the dialouge box that allows the user to pick the location
	 * in which to store a file that the program will create.
	 * 
	 * @return	a PrintWriter object
	 */
	public static PrintWriter openWrite() {
		Frame f = new Frame();
		// decide from where to read the file
		FileDialog foBox = new FileDialog(f, "Pick location for writing your file", FileDialog.SAVE);
		System.out.println("The dialog box will appear behind Eclipse.  " + 
			      "\n   Choose where you would like to write your data.");
		foBox.setVisible(true);
		// get the absolute path to the file
		String foName = foBox.getFile();
		String dirPath = foBox.getDirectory();

		// create a file instance for the absolute path
		File outFile = new File(dirPath + foName);
		PrintWriter out = null;

		try {
			out = new PrintWriter(outFile);
		} catch (IOException e) {
			System.out.println("You threw an exception");
		}
		return out;
	}
	
	/**
	 * Outputs the data in the program to a text file. 
	 * 
	 * @param output	String that contains the contents to be written to a file
	 */
	public static void outputData(String output) {
		PrintWriter out = null;
		try {
			out = openWrite();
			out.print(output);
		} finally {
			try {
				out.close();
			} catch (Exception e) {
			}
		}
	}
	
}
