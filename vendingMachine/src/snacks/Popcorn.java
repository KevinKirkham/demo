package snacks;

/**
 * One of the more specialized snack objects.
 * @author Kevin Kirkham
 *
 */
public class Popcorn extends Snack {

	public static double price = 1.45;
	
	public Popcorn() {
		super("Popcorn", price);
	}
	
	public Popcorn(double price) {
		super("Popcorn", price);
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

}
