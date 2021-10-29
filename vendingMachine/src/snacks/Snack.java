package snacks;

/**
 * Acts as the parent class of all snack types.
 * @author Kevin Kirkham
 *
 */
public class Snack {
	
	protected String name;
	protected double price;
	
	public Snack() {
		
	}
	
	public Snack(String name, double price) {
		this.name = name;
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
