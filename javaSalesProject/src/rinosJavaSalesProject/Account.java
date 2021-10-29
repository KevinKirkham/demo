package rinosJavaSalesProject;

/**
 * Serves as the parent class for the Customer and Admin classes
 * @
 * @author waveo
 *
 */

public abstract class Account {

	protected String username;
	protected String password;
	protected String privileges;
	protected int userID;

	protected static int nextNum = 1;

	// All accounts are added to the arraylist upon creation now, which seriously
	// cuts down on code
	public Account() {
		userID = nextNum;
		nextNum++;
		Driver.accounts.add(this);

		//System.out.println("nextNum: " + nextNum); // debug line for testing purposes
	}

	// Constructor used when a user decides to create a new account
	public Account(String un, String pw, String pr) {
		username = un;
		password = pw;
		privileges = pr;
		userID = findNextNum();
		nextNum++;
		Driver.accounts.add(this);

		//System.out.println("nextNum:" + nextNum); // debug line for testing purposes
	}

	// Constructor used when (re)creating accounts imported from a text file
	public Account(String un, String pw, int id, String pr) {
		username = un;
		password = pw;
		privileges = pr;
		userID = id;
	}

	public String toString() {
		return "Username: " + username + "\nPassword: " + password + "\nPrivileges: " + privileges + "\nUserID: "
				+ userID;
	}
	

	public void logout(Account account) {
		Driver.currentUser.setUser(account);
	}
	
	public int findNextNum() {
		int highest = 0;
		for (int i = 0; i < Driver.accounts.size(); i++) {
			if (Driver.accounts.get(i).getUserID() > highest) {
				highest = Driver.accounts.get(i).getUserID();
			}
		}
		return highest + 1;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrivileges() {
		return privileges;
	}

	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
