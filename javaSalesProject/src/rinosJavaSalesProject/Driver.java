package rinosJavaSalesProject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.swing.Timer;

public class Driver {

	private static Timer timer;
	private static boolean opening;
	static boolean running = false;
	static boolean invLoaded = false;
	static CurrentUser currentUser;
	static ArrayList<Account> accounts;
	static Admin rootUser;
	static ArrayList<Item> items; // Inventory of items to auction off
	static ArrayList<Auction> ongoingAuctions; // All active auctions
	static ArrayList<Auction> completedAuctions; // Auctions that have finished
	static ArrayList<Auction> futureAuctions;
	
	private static final LocalTime OPENTIME = LocalTime.of(9, 0);
	private static final LocalTime CLOSETIME = LocalTime.of(17, 0);
	
	public static void main(String[] args) {

		init();

		while (running) {
			if (currentUser.getUser().getUserID() == 1)
				MainMenuOptions.menuNoUser();
			else if (currentUser.getUser() instanceof Admin)
				MainMenuOptions.menuAdminLoggedIn();
			else
				MainMenuOptions.menuCustomerLoggedIn();
		}
	}

	public static boolean loginAttemptCheck(boolean menu) {
		if (currentUser.getUser().userID != 1) {
			menu = false;
		} else {
			SystemMessage.print("Something went wrong with the login attempt. No user is currently logged in.");
		}
		return menu;
	}
	// Go back to discord

	/*
	 * the purpose of this method is for setting the currentUser to the rootUser
	 * instance variable present in this class. The function of setting the
	 * currentUser equal to the rootUser has been given its own method for
	 * organization purposes. (When a user selects the log out option from the menu,
	 * we can call this method to set the currentUser back to rootUser)
	 */
	public static void logout() {
		currentUser.setUser(rootUser);
	}
	
	public static void printItems() {
		if (items.size() == 0)
			SystemMessage.print("Items ArrayList is empty");
		for (int i = 0; i < items.size(); i++) {
			System.out.println(items.get(i).toString());
		}
	}

	public static void loadInventory() {
		if (!invLoaded) {
			items.add(new Item(130, "Nintendo GameCube", 10));
			items.add(new Item(160, "Sony PlayStation", 10));
			items.add(new Item(150, "Nintendo GameBoy", 5));
			items.add(new Item(170, "Microsoft Xbox", 10));
			items.add(new Item(125, "Nintendo 64", 5));
			items.add(new Item(180, "Sony PlayStation 2", 5));
			items.add(new Item(90, "Sega Dreamcast", 10));
			items.add(new Item(190, "Sony PlayStation Portable", 10));
			items.add(new Item(230, "Microsoft Xbox 360", 10));
			items.add(new Item(80, "Atari 2600", 15));
			items.add(new Item(120, "Sega CD", 10));
			items.add(new Item(90, "Magnavox Odyssey", 15));
			items.add(new Item(250, "Nintendo GameCube", 10));
			items.add(new Item(180, "Nintendo Virtual Boy", 20));
			items.add(new Item(80, "Nintendo Entertainment System", 10));
			items.add(new Item(200, "Sony PlayStation 3", 15));
			items.add(new Item(130, "Sega GameGear", 10));
			items.add(new Item(250, "Microsoft Xbox One", 15));
			
			System.out.println("The data has been loaded into the ArrayList");
		}
		else {
			System.out.println("The data is already loaded");
		}
	}

	public static void loadAuctions() {
		for (int i = 0; i < 5; ++i) {
			LocalDateTime fakeStartTime = LocalDateTime.now();
			int seconds = fakeStartTime.getSecond();
			fakeStartTime = fakeStartTime.minusSeconds(seconds);
			LocalDateTime fakeEndTime = fakeStartTime.plusMinutes(i + 1);
			ongoingAuctions.add(new Auction(items.get(i), fakeStartTime, fakeEndTime));
		}
	}

	// All initial configurations to make the program run smoothly on startup
	// can happen in here, this cleans up the main method and keeps things organized
	public static void init() {
		running = true;
		accounts = new ArrayList<Account>();
		currentUser = new CurrentUser();
		rootUser = new Admin("rootUser", "password", "admin");
		new Admin("Admin", "password", "admin");
		currentUser.setUser(rootUser);
		items = new ArrayList<Item>();
		ongoingAuctions = new ArrayList<Auction>();
		completedAuctions = new ArrayList<Auction>();
		futureAuctions = new ArrayList<Auction>();
		loadInventory();
		invLoaded = true;
		timer();
		//currentUser.setUser(new Admin("Clay", "p", "admin"));
	}
	
	public static void timer() {
		timer = new Timer(1000, null);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkTime();
			}
		});
		
		timer.start();
		//loadAuctions();

		if (isOpen()) {
			opening = true;
		}
		else {
			opening = false;
		}
	}

	// This method is called everytime the actionlistener hears the timer
	private static void checkTime() {
		checkForEndingAuctions();
		checkForStartingAuctions();
		if (isOpen() && opening) {
			checkUnprocessedBids();
			opening = false;
		}
		if(!isOpen()) {
			opening = true;
		}
	}

	/*
	 * Puts stuff into the Driver.ongoingAuctions
	 */
	private static void checkForEndingAuctions() {
		for (int i = 0; i < ongoingAuctions.size(); ++i) {
			if (ongoingAuctions.get(i).getEndDateTime().isBefore(LocalDateTime.now())) {
				ongoingAuctions.get(i).endAuction();
				--i;
			}
		}
	}

	private static void checkForStartingAuctions() {
		for (int i = 0; i < futureAuctions.size(); ++i) {
			if (futureAuctions.get(i).getStartDateTime().isBefore(LocalDateTime.now())) {
				Auction a = futureAuctions.remove(i);
				a.setActive(true);
				ongoingAuctions.add(a);
				--i;
			}
		}
	}

	private static void checkUnprocessedBids() {
		if (isOpen()) {
			for (int i = 0; i < ongoingAuctions.size(); ++i) {
				Auction a = ongoingAuctions.get(i);
				if (a.getUnprocessedBids().size() > 0) {
					a.automateAuction();
					--i;
				}
			}
		}
	}

	public static boolean isOpen() {
		if (LocalTime.now().isAfter(OPENTIME) && LocalTime.now().isBefore(CLOSETIME)) {
			return true;
		}
		else {
			return false;
		}
	}

}
