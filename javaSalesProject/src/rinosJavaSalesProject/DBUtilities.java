package rinosJavaSalesProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Contains means to connect, read, and write to a MySQL database
 * 
 * @author waveo
 *
 */

public class DBUtilities {

	private static Connection con;
	private static Statement stmt;
	private static Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		createConnection();

		ArrayList<Item> testItems = new ArrayList<>();

		testItems.add(new Item(150, "item a", 10));
		testItems.add(new Item(150, "item b", 10));
		testItems.add(new Item(150, "item c", 10));

		storeItems(testItems);

		ArrayList<Item> newItems = readItems();

		System.out.println("Items coming back from the database:");
		for (int i = 0; i < newItems.size(); i++) {
			System.out.println(newItems.get(i).toString());
		}
	}

	/**
	 * Establishes a connection to the database by logging the user in with an account in the database.
	 * 
	 * @return a Connection object
	 */
	public static Connection createConnection() {

		System.out.println("What is the username for the database?");
		String user = scan.nextLine();
		System.out.println("What is the password?");
		String pass = scan.nextLine();
		System.out.println("What is the name of the database?");
		String name = scan.nextLine();

		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/" + name;

		System.out.println(driver);
		System.out.println(url);

		try { // load the driver
			Class.forName(driver).newInstance();
			con = DriverManager.getConnection(url, user, pass);
			System.out.println("Connection successful!");

		} catch (Exception e) { // problem loading driver, class not exist?
			e.printStackTrace();
		}
		System.out.println("con really is from : " + con.getClass().getName());
		return con;
	}

	/**
	 * Closes the connection to the database.
	 */
	public static void closeConnection() {
		if (con != null) {
			try {
				con.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks the connection to the database, if none exists, the
	 * user is prompted to log in with an user account that has been established with the database
	 */
	public static void checkConnect() {
		if (con == null) {
			con = createConnection();
		}
		if (stmt == null) {
			try {
				stmt = con.createStatement();
			} catch (SQLException e) {
				System.out.println("Cannot create the statement");
			}

		}
	}

	/**
	 * Stores the items that are present in the program in the database.
	 * 
	 * @param list of items the items that are to be stored in the database
	 */
	public static void storeItems(ArrayList<Item> items) {
		checkConnect();
		deleteTable("items");
		for (int i = 0; i < items.size(); i++) {
			addItem(items.get(i));
		}
	}

	/**
	 * Reads the items in from the database and puts them in the current running program.
	 * 
	 * @return list of items to be added to the driver
	 */
	public static ArrayList<Item> readItems() {
		checkConnect();
		ArrayList<Item> items = new ArrayList<Item>();
		String readData = "SELECT * from items";
		try {
			ResultSet rs = stmt.executeQuery(readData);
			System.out.println("result set really is from : " + rs.getClass().getName());

			while (rs.next()) {
				Double startingPrice = rs.getDouble(1);
				String name = rs.getString(2);
				Double priceSold = rs.getDouble(3);
				int increment = rs.getInt(4);
				int itemID = rs.getInt(5);
				int available = rs.getInt(5);
				int paidFor = rs.getInt(6);
				boolean availableBool;
				boolean paidForBool;

				if (available == 1)
					availableBool = true;
				else
					availableBool = false;
				if (paidFor == 1)
					paidForBool = true;
				else
					paidForBool = false;

				// Item(double startingPrice, String name, int increment, int itemID, boolean
				// paidFor, boolean available)
				Item item = new Item(startingPrice, name, increment, itemID, paidForBool, availableBool);
				items.add(item);

			}
		} catch (SQLException e) {
			System.out.println("It didn't work!!");
		}
		return items;
	}

	/**
	 * Individually adds one item to the database. Called many times by storeItems
	 * as multiple items get added to the database
	 * 
	 * @param item	item to be stored in the database
	 */
	public static void addItem(Item item) {
		checkConnect();
		double startingPrice = item.getStartingPrice();
		String name = item.getName();
		double priceSold = item.getPriceSold();
		int increment = item.getIncrement();
		int itemID = item.getItemID();
		int paidFor;
		int available;
		if (item.isAvailable())
			available = 1;
		else
			available = 0;
		if (item.isPaidFor())
			paidFor = 1;
		else
			paidFor = 0;

		// Example query
		// INSERT INTO `items` (`starting_price`, `name`, `price_sold`, `increment`,
		// `item_id`,
		// `available`, `paid_for`) VALUES ('130.00', 'Sony Playstation', 'NULL', '10',
		// '102', '1', '0')

		String query = "INSERT INTO `items` (`starting_price`, `name`, `price_sold`, `increment`, `item_id`, `available`, `paid_for`) VALUES "
				+ "(\'" + startingPrice + "\', " + "\'" + name + "\', " + "\'" + priceSold + "\', " + "\'" + increment
				+ "\', " + "\'" + itemID + "\', " + "\'" + paidFor + "\', " + "\'" + available + "\')";

		System.out.println(query);

		try {
			// drop the data from the puppies table so I can store a new version
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// catch that exception and do nothing
			System.out.println("Something went wrong!!");
		}
	}

	/**
	 * Adds a single customer to the database.
	 * 
	 * @param c customer to be added to the database
	 */
	public static void addCustomer(Customer c) {
		checkConnect();
		String username = c.getUsername();
		String password = c.getPassword();
		int userID = c.getUserID();
		double balance = c.getBalance();
		String query = "INSERT INTO `customers` (`cust_id`, `username`, `password`, `balance`) VALUES " + "(\'" + userID
				+ "\', " + "\'" + username + "\', " +  "\'" + password + "\', " +  "\'" + balance + "\')";

		System.out.println(query);

		try {
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// catch that exception and do nothing
			System.out.println("Something went wrong!!");
		}

	}

	/**
	 * Stores the customers in the program in the database.
	 */
	public static void storeCustomers() {
		ArrayList<Customer> customers = new ArrayList<>();
		for (int i = 0; i < Driver.accounts.size(); i++) {
			if (Driver.accounts.get(i) instanceof Customer) {
				customers.add((Customer) Driver.accounts.get(i));
			}
		}
		
		checkConnect();
		deleteTable("customers");
		for (int i = 0; i < customers.size(); i++) {
			addCustomer(customers.get(i));
		}
	}
	
	/**
	 * Reads customers in from the database.
	 * 
	 * @return	list of customers to be added to the driver
	 */
	public static ArrayList<Customer> readCustomers() {
		checkConnect();
		ArrayList<Customer> customers = new ArrayList<>();
		String readData = "SELECT * from customers";
		try {
			ResultSet rs = stmt.executeQuery(readData);
			System.out.println("result set really is from : " + rs.getClass().getName());

			while (rs.next()) {
				int cust_id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				double balance = rs.getDouble(4);

				//public Customer(String username, String password, int userID, double balance)
				Customer cust = new Customer(username, password, cust_id, balance);
				customers.add(cust);

			}
		} catch (SQLException e) {
			System.out.println("It didn't work!!");
		}
		return customers;
	}

	/**
	 * Adds a single auction to the database.
	 * 
	 * @param auction	auction to be added to the database
	 */
	public static void addAuction(Auction auction) {
		checkConnect();
		int auctionID = auction.getAuctionID();
		int itemID = auction.getItem().getItemID();
		int active = 0;
		double currentSalesPrice = auction.getCurrentSalesPrice();
		if (auction.isActive() == true)
			active = 1;
		String open = compileDateTime(auction.getStartDateTime());
		String close = compileDateTime(auction.getEndDateTime());

		// INSERT INTO `auctions` (`auction_id`, `item_id`, `active`,
		// 'current_sales_price', 'opening_time', 'closing_time')
		// VALUES ('12', '103', '1', '63.95', '2021-7-5-18-30-00', '2021-7-5-18-30-00')

		String query = "INSERT INTO `auctions` (`auction_id`, `item_id`, `active`, `current_sales_price`, `opening_time`, `closing_time`) VALUES "
				+ "(\'" + auctionID + "\', " + "\'" + itemID + "\', " + "\'" + active + "\', " + "\'"
				+ currentSalesPrice + "\', " + "\'" + open + "\', " + "\'" + close + "\')";

		System.out.println(query);

		try {
			stmt.executeUpdate(query);

		} catch (SQLException e) {
			// catch that exception and do nothing
			System.out.println("Something went wrong!!");
		}
	}


	/**
	 * Stores the auctions that are in the program in the database.
	 */
	public static void storeAuctions() {
		checkConnect();
		deleteTable("auctions");
		ArrayList<Auction> auctions = new ArrayList<>();
		for (int i = 0; i < Driver.ongoingAuctions.size(); i++) {
			auctions.add(Driver.ongoingAuctions.get(i));
		}

		for (int i = 0; i < Driver.completedAuctions.size(); i++) {
			auctions.add(Driver.completedAuctions.get(i));
		}

		for (int i = 0; i < Driver.futureAuctions.size(); i++) {
			auctions.add(Driver.futureAuctions.get(i));
		}

		for (int i = 0; i < auctions.size(); i++) {
			addAuction(auctions.get(i));
		}
	}

	/**
	 * Reads in auctions from the database.
	 * 
	 * @return	list of auctions to be added to the program
	 */
	public static ArrayList<Auction> readAuctions() {
		checkConnect();
		ArrayList<Auction> auctions = new ArrayList<>();
		String readData = "SELECT * from auctions";
		try {
			ResultSet rs = stmt.executeQuery(readData);
			System.out.println("result set really is from : " + rs.getClass().getName());

			while (rs.next()) {
				int auctionID = rs.getInt(1);
				int itemID = rs.getInt(2);
				int activeInt = rs.getInt(3);
				double currentSalesPrice = rs.getInt(4);
				String open = rs.getString(5);
				String close = rs.getString(6);
				LocalDateTime opening = createDateTime(open);
				LocalDateTime closing = createDateTime(close);

				boolean active;
				if (activeInt == 1)
					active = true;
				else
					active = false;

				Item item = Read.findItem(itemID, Driver.items);

				// Auction(Item item, int auctionID, double currentSalesPrice, LocalDateTime
				// startDateTime, LocalDateTime endDateTime)
				Auction auction = new Auction(item, auctionID, currentSalesPrice, opening, closing, active);
				auctions.add(auction);
			}
		} catch (SQLException e) {
			System.out.println("It didn't work!!");
		}
		return auctions;
	}

	/**
	 * Deletes each record in the specified table.
	 * 
	 * @param tableName	the name of the table that is to be deleted
	 */
	public static void deleteTable(String tableName) {
		checkConnect();
		String delete = "DELETE FROM " + tableName;
		try {
			stmt.executeUpdate(delete);
		} catch (SQLException e) {
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
	}

	/**
	 * Converts a LocalDateTime object into a String of a certain format so that it can be stored
	 * in the database as a VARCHAR. 
	 * @param ot	the LocalDateTime object to be converted to a String
	 * @return		String that represents the LocalDateTime parameter
	 */
	public static String compileDateTime(LocalDateTime ot) {
		String date = ot.getYear() + "-" + ot.getMonthValue() + "-" + ot.getDayOfMonth() + "-" + ot.getHour() + "-"
				+ ot.getMinute() + "-" + ot.getSecond();
		return date;
	}

	/**
	 * Creates a LocalDateTime object from a String that is passed into it.
	 * 
	 * @param data	String from which the LocalDateTime object is to be created
	 * @return		a LocalDateTime object
	 */
	public static LocalDateTime createDateTime(String data) {
		String[] info = data.split("-");
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

	/*
	 * public static void findPup() { checkConnect();
	 * System.out.println("\nLet's find a puppy!");
	 * System.out.println("What is the puppy's name"); String name =
	 * scan.nextLine();
	 * 
	 * String query = "Select * from puppies where name = '" + name + "'";
	 * System.out.println(query); try { ResultSet rs = stmt.executeQuery(query); if
	 * (rs != null) { rs.next(); boolean bPed, bHypo, bAvail; int ped =
	 * rs.getInt(4); int hypo = rs.getInt(6); int avail = rs.getInt(7); if (ped ==
	 * 1) bPed = true; else bPed = false; if (hypo == 1) bHypo = true; else bHypo =
	 * false; if (avail == 1) bAvail = true; else bAvail = false; Puppies c = new
	 * Puppies(rs.getString(1), rs.getString(2), rs.getString(3), bPed,
	 * rs.getDouble(5), bHypo, bAvail); System.out.println(c.toString()); } else {
	 * System.out.println("A puppy with that name does not exist"); } } catch
	 * (SQLException e) { System.out.println("An exception was thrown"); } }
	 */

}
