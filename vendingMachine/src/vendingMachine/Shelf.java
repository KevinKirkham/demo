package vendingMachine;

import snacks.ChocolateBar;
import snacks.Peanuts;
import snacks.Popcorn;
import snacks.Raisins;
import snacks.Soda;

/**
 * The shelf acts as a container in which to store racks of snack items. 
 * @author Kevin Kirkham
 *
 */
public class Shelf {
	
	Rack[] racks = new Rack[5];
	
	/**
	 * Constructs a shelf.
	 */
	public Shelf() {
		this.racks[0] = new Rack(2, new ChocolateBar());
		this.racks[1] = new Rack(10, new Peanuts());
		this.racks[2] = new Rack(10, new Popcorn());
		this.racks[3] = new Rack(10, new Raisins());
		this.racks[4] = new Rack(10, new Soda());
	}

	public Rack[] getRacks() {
		return racks;
	}

	public void setRacks(Rack[] racks) {
		this.racks = racks;
	}
	
	

}
