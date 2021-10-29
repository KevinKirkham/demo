package snacks;

/**
 * One of the more specialized snack objects.
 * @author Kevin Kirkham
 *
 */
public class Soda extends Snack {

	public static double price = 1.25;
	
	public Soda() {
		super("Soda", price);
	}
	
	public Soda(double price) {
		super("Soda", price);
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
}
