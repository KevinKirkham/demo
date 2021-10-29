package rinosJavaSalesProject;

import java.util.Scanner;

/**
 * Contains methods that validate user input in various ways
 * @author waveo
 *
 */

public class InputMethods {
	public static boolean yesNoToBool(String question) {
		Scanner keyboard = new Scanner(System.in);

		boolean validInput = false;

		String input = null;
		while (!validInput) {

			try {
				System.out.println(question);
				input = keyboard.nextLine();
				if (!input.trim().equalsIgnoreCase("yes") && !input.trim().equalsIgnoreCase("no")) {
					throw new InvalidInputException();
				}
				validInput = true;
			} catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please enter either a yes or no");
			} catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please enter either a yes or no");
			}
		}
		if (input.trim().equalsIgnoreCase("yes")) {
			return true;
		} else {
			return false;
		}
	}

	public static double getPositiveDouble(String question) {
		double dub = -1;
		boolean done = false;
		while (!done) {
			try {
				System.out.println(question);
				Scanner scan = new Scanner(System.in);
				dub = scan.nextDouble();
				if (dub <= 0) {
					throw new InvalidInputException();
				}
				done = true;
			} catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Your bid must be greater than 0");
				done = !yesNoToBool("Would you like to try again?");
			} catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
				done = !yesNoToBool("Would you like to try again?");
			}
		}
		return dub;
	}

	public static int getIntFromMenu(int lower, int upper, String menu) {
		int choice = 0;
		boolean done = false;
		while (!done) {
			try {
				System.out.println(menu);
				Scanner scan = new Scanner(System.in);
				choice = scan.nextInt();
				if (choice < lower || choice > upper)
					throw new InvalidInputException();
				done = true;
			} catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please input a number between " + lower + " and " + upper);
			} catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
			}
		}
		return choice;
	}
	
	public static int validateInput(int upper, int lower) {
		Scanner scan = new Scanner(System.in);
		int input = 0;
		boolean valid = false;
		while (!valid) {
			try {
				System.out.print("Input: ");
				input = scan.nextInt();
				if (input <= upper && input >= lower) {
					valid = true;
				}
				else 
					System.out.println("That input is not valid. Please input something between " + upper + " and " + lower);
			}
			catch (Exception e) {
				System.out.println("Make sure your input is an integer");
			}
		}
		return input;
	}

	public static int getIntOrReturnNeg1(int lower, int upper, String menu) {
		int choice;
		boolean done = false;
		while (!done) {
			try {
				System.out.println(menu);
				Scanner scan = new Scanner(System.in);
				choice = scan.nextInt();
				if (choice < lower || choice > upper)
					throw new InvalidInputException();
				return choice;
			} catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please input a number between " + lower + " and " + upper);
				done = !yesNoToBool("Would you like to try again?");
			} catch (Exception e) {
				choice = -1;
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
				done = !yesNoToBool("Would you like to try again?");
			}
		}
		return -1;
	}
	
	public static int getIntOrStuckForever(int lower, int upper, String menu) {
		Scanner scan = new Scanner(System.in);
		int choice;
		while (true) {
			try {
				System.out.println(menu);
				choice = scan.nextInt();
				if (choice < lower || choice > upper)
					throw new InvalidInputException();
				return choice;
			} catch (InvalidInputException iie) {
				System.out.println("Invalid input");
				System.out.println("Please input a number between " + lower + " and " + upper);
			} catch (Exception e) {
				System.out.println("Invalid input");
				System.out.println("Please make sure your input does not contain letters or special characters.");
			}
		}
	}
	
}
