package vendingMachine;

/**
 * The purpose of this program is to practice the steps of the software development life cycle 
 * by simulating a customer's use of a vending machine. The vending machine should have all of the basic functionalities of a
 * real-world vending machine including the ability to dispense a product to the user, accept the input of money from the user, 
 * and dispense the correct amount of change to the user in the event that he overpaid. There should also be an operator mode that 
 * should allow for the changing of prices of products in the vending machine as well as restocking the machine when it is low on
 * products and gathering the profits that the machine garnered through its interactions with customers.
 * @author Kevin Kirkham
 * @version 1
 */
public class Driver {
	
	public static VendingMachine vm = new VendingMachine();
	public static Operator op = new Operator("password");
	public static User user = new User();

	public static void main(String[] args) {		
		vm.init();
		Menu.mainMenu();
	}

}
