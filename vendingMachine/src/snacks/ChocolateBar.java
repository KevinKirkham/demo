package snacks;

/**
 * Holds the information and handles the creation of ChocolateBar objects.
 * @author Kevin Kirkham
 *
 */
public class ChocolateBar extends Snack {
	
	public static double price = 0.99;
	
	public ChocolateBar() {
		super("Chocolate bar", price);
	}
	
	public ChocolateBar(double price) {
		super("Chocolate bar", price);
	}

	public void setPrice(double price) {
		ChocolateBar.price = price;
	}
}
