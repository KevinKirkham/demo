package snacks;

/**
 * One of the more specialized snack objects.
 * @author Kevin Kirkham
 *
 */
public class Raisins extends Snack {
	
	public static double price = 0.80;
	
	public Raisins() {
		super("Raisins", price);
		//Raisins.price = 0.99;
	}
	
	public Raisins(double price) {
		super("Raisins", price);
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
}
