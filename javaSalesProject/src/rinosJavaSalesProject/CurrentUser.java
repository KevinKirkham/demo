package rinosJavaSalesProject;

/**
 * Represents the user that is currently logged in
 * @author waveo
 *
 */

public class CurrentUser {
	
	private Account user;
	
	public String toString() {
		return "The user that is currently logged in is: \n" + user.toString();
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
		SystemMessage.print("The current user has been set to: " + user.username);
		if (Driver.currentUser.getUser().userID == 1) {
			SystemMessage.print("No one is logged in");
		}
	}
	
}
