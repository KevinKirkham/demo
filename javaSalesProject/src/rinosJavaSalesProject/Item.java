
package rinosJavaSalesProject;

import java.text.NumberFormat;

/**
 * Contains information and actions associted with items
 * @author waveo
 *
 */

public class Item implements Comparable<Item> {
	
	/**
	 * The starting price that the bidding is to start at for this item
	 */
	private double startingPrice;
	
	/**
	 * The name of the item
	 */
	private String name;
	
	/**
	 * The price for which the item was sold
	 */
	private double priceSold;
	
	/**
	 * The increment by which values of bids are to increase.
	 * Provides a minimum dollar amount that the next bid has to be
	 * to successfully overtake the current highest bid.
	 */
	private int increment;
	
	/**
	 * Unique identifier for each item
	 */
	private int itemID;
	
	/**
	 * Number that the ID of the next Item object to be made will have,
	 * Purpose is to allow for the incrementation of item IDs as they are created
	 */
	private static int nextNum = 100;
	
	/**
	 * Represents the availability of the item. Items that have been won in auctions
	 * are no longer available to be put in another auction.
	 */
	private boolean available = true;
	
	/**
	 * Indicates if the item has been paid for. True if it has been paid for,
	 * false if it has not.
	 */
	private boolean paidFor;
	
	
	public Item() {
		increment = 100;
		itemID = nextNum;
		nextNum++;
	}
	
	
	public Item(double startingPrice, String name, int increment) {
		this.startingPrice = startingPrice;
		this.name = name;
		this.increment = increment;
		itemID = nextNum;
		nextNum++;
	}
	
	public Item(double startingPrice, String name, int increment, int itemID, boolean paidFor, boolean available) {
		this.startingPrice = startingPrice;
		this.name = name;
		this.increment = increment;
		this.itemID = itemID;
		this.paidFor = paidFor;
		this.available = available;
	}
	
	public String toString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		return "\tItem name: " + name + "\n" + 
			   "\tStarting Price: " + cf.format(startingPrice) + "\n" +
			   "\tItem ID: " + itemID + "\n" + 
			   "\tIncrement: " + increment + "\n";
		
	}
	
	public String selectAuctionToString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		return "\tItem name: " + name + "\n" + 
			   "\tStarting Price: " + cf.format(startingPrice) + "\n" +
			   "\tIncrement: " + increment + "\n";
	}
	
	
	public int compareTo(Item e) {
		if (e.getItemID() > this.getItemID())
			return 1;
		else if (e.getItemID() < this.getItemID()) 
			return -1;
		else
			return 0;
	}
	
	/**
	 * Compares two Item objects
	 * @param e - The item to which this instance of Item is to be compared
	 * @return Returns an integer representing the status of comparison of the two Item objects
	 */
	public int compareToA(Item e) {
		if (e.available && this.available)
			return 0;
		else if (e.available) 
			return 1;
		else
			return -1;
	}

	
	public double getStartingPrice() {
		return startingPrice;
	}


	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public double getPriceSold() {
		return priceSold;
	}


	public void setPriceSold(double priceSold) {
		this.priceSold = priceSold;
	}


	public int getIncrement() {
		return increment;
	}


	public int getItemID() {
		return itemID;
	}


	public void setItemID(int itemID) {
		this.itemID = itemID;
	}


	public void setIncrement(int increment) {
		this.increment = increment;
	}


	public boolean isAvailable() {
		return available;
	}


	public void setAvailable(boolean available) {
		this.available = available;
	}


	public boolean isPaidFor() {
		return paidFor;
	}


	public void setPaidFor(boolean paidFor) {
		this.paidFor = paidFor;
	}
	
}
