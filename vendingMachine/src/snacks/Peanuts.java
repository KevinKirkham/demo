package snacks;

/**
 * One of the more specialized snack objects.
 * @author Kevin Kirkham
 *
 */
public class Peanuts extends Snack {
	
	public static double price = 0.91;

	public Peanuts() {
		super("Peanuts", price);
	}
	
	public Peanuts(double price) {
		super("Peanuts", price);
	}
	
	public void setPrice(double price) {
		Peanuts.price = price;
	}

	
}
