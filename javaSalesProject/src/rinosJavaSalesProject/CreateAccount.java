package rinosJavaSalesProject;

import java.util.Scanner;

/**
 * Contains methods for the creation and validation of Customer accounts
 * @author waveo
 *
 */
public abstract class CreateAccount {

	public static void createCustomerAccount() {
		String username = getUsername("customer");
		String password = null;
		if (username != null) {
			password = getPassword("customer");
		}
		if (password != null) {
			Driver.currentUser.setUser(new Customer(username, password, "customer"));
			SystemMessage.print("The new account has been created");
		}
	}

	public static void createAdminAccount() {
		String username = getUsername("admin");
		String password = null;
		if (username != null) {
			password = getPassword("admin");
		}
		if (password != null) {
			Driver.currentUser.setUser(new Admin(username, password, "admin"));
			SystemMessage.print("The new account has been created");
		}
	}

	public static String getUsername(String privileges) {
		String username = null;
		int resultOfValidation = 0;
		boolean done = false;
		while (!done) {
			System.out.println("What username do you want to have? (cannot contain '|', '}', or ',' characters)");
			try {
				Scanner scan = new Scanner(System.in);
				username = scan.nextLine();
				resultOfValidation = validateUsername(privileges, username);
				if (resultOfValidation > 0) {
					throw new InvalidInputException();
				}
				return username;
			} catch (InvalidInputException iie) {
				if (resultOfValidation == 1) {
					System.out.println("That username is already taken.");
				} else {
					System.out.println("That username contains an illegal character.");
				}
			} catch (Exception e) {
				System.out.println("That username is not valid.");
			}

			done = !InputMethods.yesNoToBool("Would you like to try another username? (yes or no)");

		}
		return null;
	}

	public static String getPassword(String privileges) {
		String password = null;
		int resultOfValidation = 0;
		boolean done = false;
		while (!done) {
			System.out.println("What password do you want to have? (cannot contain '|', '}', or ',' characters)");
			try {
				Scanner scan = new Scanner(System.in);
				password = scan.nextLine();
				resultOfValidation = validatePassword(password);
				if (resultOfValidation > 0) {
					throw new InvalidInputException();
				}
				return password;
			} catch (InvalidInputException iie) {
				if (resultOfValidation == 1) {
					System.out.println("That password contains an illegal character.");
				}
			} catch (Exception e) {
				System.out.println("That password is not valid.");
			}

			done = !InputMethods.yesNoToBool("Would you like to try another password? (yes or no)");
		}
		return null;
	}

	// returns 2 when it contains an illegal character
	// returns 1 when the username is already taken
	// returns 0 when the username is valid
	public static int validateUsername(String privileges, String username) {

		boolean containsPipe = false;
		boolean containsBracket = false;
		boolean containsComma = false;
		boolean alreadyTaken = false;

		for (int i = 0; i < username.length(); ++i) {
			if (username.charAt(i) == '|') {
				containsPipe = true;
			}
			if (username.charAt(i) == '}') {
				containsBracket = true;
			}
			if(username.charAt(i) == ',') {
				containsComma = true;
			}
		}

		for (Account acc : Driver.accounts) {
			if (acc.getPrivileges().equals(privileges)) {
				if (acc.getUsername().equals(username)) {
					alreadyTaken = true;
				}
			}
		}

		if (containsPipe || containsBracket || containsComma) {
			return 2;
		} else if (alreadyTaken) {
			return 1;
		} else {
			return 0;
		}

	}

	// returns a 1 if the password contains an illegal character
	// returns a 0 if the password is valid
	public static int validatePassword(String password) {

		boolean containsPipe = false;
		boolean containsBracket = false;
		boolean containsComma = false;

		for (int i = 0; i < password.length(); ++i) {
			if (password.charAt(i) == '|') {
				containsPipe = true;
			}
			if(password.charAt(i) == '}') {
				containsBracket = true;
			}
			if(password.charAt(i) == ',') {
				containsComma = true;
			}
		}

		if (containsPipe || containsBracket || containsComma) {
			return 1;
		} else {
			return 0;
		}
	}

}
