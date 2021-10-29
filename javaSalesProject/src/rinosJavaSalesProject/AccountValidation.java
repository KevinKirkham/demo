package rinosJavaSalesProject;

import java.util.Scanner;

/**
 * Contains methods that are used to validate input from the user
 * @author waveo
 *
 */

//Call this login/logout??
public class AccountValidation {
	
	
	//This method will take care of all things related to logging a user in
		public void login(String privileges) {
			
			boolean validLoginAttempt = false;
			while (!validLoginAttempt) {
				
				Scanner scan = new Scanner(System.in);
				
				System.out.print("Enter username: ");
				String username = scan.nextLine();
				
				boolean validUsername = validateUsername(username, privileges);

				//Only if the username returns as something we have on file, we continue
				if (validUsername) {
					
					boolean validPassword = false;
					while (validPassword == false) {
						
						//Have the user enter a password and get a response back 
						//regarding the success of the input attempt
						System.out.print("Enter password: ");
						String password = scan.nextLine();
						
						validPassword = validatePassword(username, password);
						
						if (validPassword) {
							
							Account accountToBeLoggedIn = null;
							for (int i = 0; i < Driver.accounts.size(); i++) {
								if (Driver.accounts.get(i).username.equals(username)) {	
									accountToBeLoggedIn = Driver.accounts.get(i);
								}
							}
							//Driver.currentUser.setUser(Account accountThatJustAuthenticated);
							//I will leave the logistics of how to pass in the right account up to you guys
							Driver.currentUser.setUser(accountToBeLoggedIn);
							SystemMessage.print("Successful login attempt");
							validLoginAttempt = validPassword = true;
						}
						else {
							validPassword = validLoginAttempt = !InputMethods.yesNoToBool("Would you like to try another password? (yes or no)");
							// Anything we want to do if the password as entered doesn't 
							// the one associated with the username the user just entered
						}
					}
				}
				
				else {
					validLoginAttempt = !InputMethods.yesNoToBool("Would you like to try another username? (yes or no)");
					//Anything that we do relating to a failure in finding the username as entered
				}
			}

		}
		
		
		//This method will handle all things related to fetching usernames from
		//the collection of usernames that we have (which right now is the is the
		//arraylist but it may change later (as we implement the database) which is all the more
		//reason to keep this function of the program as isolated as we can) and 
		//making sure what the user inputted is a username we have in our collection.
		//Returns true if the username the user entered is something we have on file,
		//returns false if it is not.
		public boolean validateUsername(String username, String privileges) {
			for (Account acc : Driver.accounts) {
				if (acc.getPrivileges().equalsIgnoreCase(privileges) && acc.username.equals(username)) {
					return true;
				}
			}
		
			System.out.println("This is an incorrect username");	

			return false;
			
			//Driver.accounts is all we need to access all of the accounts 
			//It is the central repository for all accounts
		}
		
		//Since the password to each account is an instance variable we don't have to 
		//pass that data into this method to verify it - it is stored in the instance of the
		//Account class itself. So we can just test to see if what the user inputs (and user input 
		//should happen inside of this method) is equal to this.password or, alternatively,
		//test if what the user entered is equal to what the getPassword() method returns
		public static boolean validatePassword(String username, String password) {
			Scanner scan = new Scanner(System.in);
			Account accountToBeLoggedIn = null;
			for (int i = 0; i < Driver.accounts.size(); i++) {
				if (Driver.accounts.get(i).username.equals(username)) {	
					accountToBeLoggedIn = Driver.accounts.get(i);
				}
			}
			
			if (accountToBeLoggedIn.getPassword().equals(password)) {
				return true;
			}
			else {
				System.out.println("This is an incorrect password");
				return false;
			}
			
		}
	
}
