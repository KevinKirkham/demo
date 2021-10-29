package rinosJavaSalesProject;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Contains all things associated with creation and actions of Auctions
 * @author waveo Auctions will be filled with bid objects
 *
 */

public class Auction implements Comparable<Auction> {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMM dd, YYYY h:mm a");
	NumberFormat cf = NumberFormat.getCurrencyInstance();
	private Stack<Bid> processedBids = new Stack<>(); // Stack of bids for keeping track of bids that are part of an
														// active auction. Also for record keeping
	private Queue<Bid> unprocessedBids = new Queue<>(); // This is a queue full of bids yet to be processed in the
														// auction
	private Item item;
	private Bid currentHighest; // bid with highest max value willing to pay lives here
	private double currentSalesPrice;
	private int increment;
	private boolean active;

	private int auctionID;
	private static int nextNum = 1000;

	private int numBids = 0;

	private LocalDateTime startDateTime;
	private LocalDateTime endDateTime;

	public Auction() {
		auctionID = nextNum;
		nextNum++;
	}

	public Auction(Item item) {
		this.item = item;
		item.setAvailable(false);
	}
	
	public Auction(Item item, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.item = item;
		item.setAvailable(false);
		currentHighest = null;
		currentSalesPrice = item.getStartingPrice();
		increment = item.getIncrement();
		auctionID = findNextNum();
		//Driver.items.remove(item);
		nextNum++;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public Auction(Item item, int auctionID, LocalDateTime startDateTime,  LocalDateTime endDateTime) {
		this.item = item;
		this.auctionID = auctionID;
		currentHighest = null;
		if (item != null) {
			currentSalesPrice = item.getStartingPrice();
			increment = item.getIncrement();
			item.setAvailable(false);
		}
		else {
			currentSalesPrice = 0;
			increment = 0;
		}
		//Driver.items.remove(item);
		active = true;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public Auction(Item item, int auctionID, double currentSalesPrice, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		this.item = item;
		item.setAvailable(false);
		this.auctionID = auctionID;
		currentHighest = null;
		this.currentSalesPrice = currentSalesPrice;
		increment = item.getIncrement();
		//Driver.items.remove(item);
		active = true;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}
	
	public Auction(Item item, int auctionID, double currentSalesPrice, LocalDateTime startDateTime, LocalDateTime endDateTime, boolean active) {
		this.item = item;
		item.setAvailable(false);
		this.auctionID = auctionID;
		currentHighest = null;
		this.currentSalesPrice = currentSalesPrice;
		increment = item.getIncrement();
		//Driver.items.remove(item);
		this.active = active;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}


	public String toString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + item.selectAuctionToString() + "\tCurrent Sales Price: "
				+ cf.format(currentSalesPrice) + "\n" + "\tStart Time: " + dtf.format(startDateTime) + "\n\tEnd Time: " + dtf.format(endDateTime) + "\n";
		if (currentHighest != null) {
			result += "\tCurrent high bidder: " + currentHighest.getCustomer().getUsername() + "\n";
			result += "\tMax current high bidder is willing to pay: " + cf.format(currentHighest.getValue()) + "\n";
		}
		return result;
	}

	public String custVersionToString() {
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		String result = "\tAuction ID: " + auctionID + "\n" + item.selectAuctionToString() + "\tCurrent Sales Price: "
				+ cf.format(currentSalesPrice) + "\n"+ "\tStart Time: " + dtf.format(startDateTime) + "\n\tEnd Time: " + dtf.format(endDateTime);
		if (currentHighest != null) {
			if (Driver.currentUser.getUser().getUserID() == currentHighest.getCustomer().getUserID()) {
				result += "\tYou are the high bidder\n";
			} else {
				result += "\tCurrent high bidder: " + currentHighest.getCustomer().getUsername() + "\n";
			}
			result += "\tMax current high bidder is willing to pay: " + cf.format(currentHighest.getValue()) + "\n";
		}
		return result;
	}
	
	/**
	 * Automates the processing of bids in an auction
	 */
	public void automateAuction() {
		while (unprocessedBids.size() > 0) {
			process(unprocessedBids.dequeue());
		}
	}

	public void process(Bid bid) {
		if (currentHighest == null)
			firstBid(bid);
		else if (isValid(bid)) {
			if (bid.getValue() <= currentHighest.getValue()) {
				currentSalesPrice = bid.getValue();
			}
			else {
				currentSalesPrice = currentHighest.getValue();
				currentHighest = bid;
			}
			bid.getCustomer().addCurrentBid(bid); // Add bid to Customer's list of bids
			processedBids.push(bid); // Add list to Auction's list of bids
			numBids++;
		}
		else {
			System.out.println("Invalid bid");
		}
	}

	/**
	 * Ensures that a particular bid is valid to an auction.
	 * 
	 * @param bid	a Bid object being submitted to this auction
	 * @return		true or false depending on the validity of the bid
	 */
	public boolean isValid(Bid bid) {
		if (bid.getValue() >= (currentSalesPrice + increment)) {
			bid.setValid(true);
			return bid.isValid();
		}
		else {
			return false;
		}
	}

	/**
	 * The first bid in an auction has special rules and they are dealt with here. 
	 * 
	 * @param bid the first bid submitted to this auction
	 */
	public void firstBid(Bid bid) {
		if (bid.getValue() >= currentSalesPrice) {
			bid.getCustomer().addCurrentBid(bid); // Add bid to Customer's list of bids
			processedBids.push(bid); // Add list to Auction's list of bids
			currentHighest = processedBids.peek();
			numBids++;
			bid.setValid(true);
		}
		else {
			System.out.println("First bid must be at least the sales price of the item\n");
		}
	}

	/**
	 * Ends the auction and saves the bid history for record keeping.
	 * The active status is set to false and the auction object is stored in
	 * the completedAuctions collection in the Driver.
	 */
	public void endAuction() {
		active = false;
		//System.out.println("Auction over");
		clearActiveBids();
		Driver.completedAuctions.add(this);
		Driver.ongoingAuctions.remove(this);
		if (currentHighest != null) {
			//System.out.println("The winner is " + currentHighest.getCustomer().usernameIdString());
			currentHighest.getCustomer().addWinningBid(currentHighest);
		}
		this.item.setAvailable(true);
	}
	
	/**
	 * Clears the active bids of an auction.
	 */
	public void clearActiveBids() {
		Stack<Bid> copy = processedBids.clone();
		while (copy.size() > 0) {
			Bid b = copy.pop();
			b.getCustomer().removeActiveBid(b);
		}
	}

	/**
	 * Prints a quick summary of the status of a given auction.
	 */
	public void printAuctionStatus() {
		System.out.println("Item: " + item.getName());
		if (currentHighest != null) {
			System.out.println("Highest Bid: " + cf.format(currentHighest.getValue()));
		} else {
			System.out.println("Highest Bid: none");
		}
		System.out.println("Selling Price: " + cf.format(currentSalesPrice));
		System.out.println("Increment: " + cf.format(increment));
		System.out.println("Active: " + active);
		System.out.println("Auction ID: " + auctionID);
		System.out.println("Number of Bids: " + numBids);
		System.out.println();
	}
	
	/**
	 * Finds the value of the nextNum field according to the status of the most recent
	 * completed auction in the collection in the driver.
	 * @return	an integer that is to be the value of the nextNum field
	 */
	public int findNextNum() {

		if (Driver.completedAuctions.isEmpty() && Driver.ongoingAuctions.isEmpty() && Driver.futureAuctions.isEmpty()) {
			return 1000;

		}

		else {
			int completed = 0;
			int active = 0;
			int future = 0;
			if (!Driver.completedAuctions.isEmpty()) {
				completed = Driver.completedAuctions.get(Driver.completedAuctions.size() - 1).getAuctionID();
			}
			if (!Driver.ongoingAuctions.isEmpty()) {
				active = Driver.ongoingAuctions.get(Driver.ongoingAuctions.size() - 1).getAuctionID();
			}
			if (!Driver.futureAuctions.isEmpty()) {
				future = Driver.futureAuctions.get(Driver.futureAuctions.size() - 1).getAuctionID();
			}
			int[] integers = {completed, active, future};
			return returnHighestInt(integers) + 1;	
		}


	}

	public int returnHighestInt(int[] integers) {
		int highest = 0;
		for (int i = 0; i < integers.length; i++) {
			if (integers[i] > highest) {
				highest = integers[i];
			}
		}
		return highest;
	}
	
	public int compareTo(Auction a) {
		if (this.auctionID < a.auctionID) {
			return 1;
		}
		else if (this.auctionID > a.auctionID) {
			return -1;
		}
		else return 0;
	}

	public void clearProcessedBids() {
		processedBids.clear();
	}

	public void loadQueue(Bid bid) {
		unprocessedBids.enqueue(bid);
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}


	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Bid getCurrentHighest() {
		return currentHighest;
	}

	public void setCurrentHighest(Bid currentHighest) {
		this.currentHighest = currentHighest;
	}

	public double getCurrentSalesPrice() {
		return currentSalesPrice;
	}

	public int getAuctionID() {
		return auctionID;
	}

	public void setSellingPrice(double sellingPrice) {
		this.currentSalesPrice = sellingPrice;
	}

	public Stack<Bid> getBids() {
		return processedBids;
	}

	public void setBids(Stack<Bid> bids) {
		this.processedBids = bids;
	}

	public Stack<Bid> getProcessedBids() {
		return processedBids;
	}

	public void setProcessedBids(Stack<Bid> processedBids) {
		this.processedBids = processedBids;
	}

	public Queue<Bid> getUnprocessedBids() {
		return unprocessedBids;
	}

	public void setUnprocessedBids(Queue<Bid> unproccessedBids) {
		this.unprocessedBids = unproccessedBids;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}


}
