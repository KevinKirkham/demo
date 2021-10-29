package vendingMachine;

import snacks.Snack;
import exceptions.EmptyRackException;
import exceptions.FullRackException;

/**
 * Acts as a container for snack objects, works like a queue with the top element always having a product as long as the rack is not empty.
 * @author Kevin Kirkham
 *
 */
public class Rack {
	
	private Snack[] contents;
	private Snack type;
	
	/**
	 * Constructs a Rack.
	 */
	public Rack() {
		this.contents = new Snack[10];
	}
	
	/**
	 * Constructs a rack object of specified size and type.
	 * @param size The quantity of items the rack is to hold.
	 * @param type The type of snack that is to be held by the rack.
	 */
	public Rack(int size, Snack type) {
		this.contents = new Snack[size];
		this.type = type;
	}
	
	/**
	 * Dispenses a product, removing it from the rack.
	 * @return The snack that was removed from the rack.
	 */
	public Snack dispenseProduct() {
		try {
			return pop();
		}
		catch (EmptyRackException ere) {
			System.out.println("The rack is empty!");
		}
		return null;
	}
	
	/**
	 * Loads snacks onto the rack.
	 * @param snack The snack to be loaded onto the rack.
	 */
	public void loadSnack(Snack snack) {
		try {
			push(snack);
		}
		catch (FullRackException fre) {
			System.out.println("The rack is full");
		}
	}
	
	/**
	 * Returns true if the rack has no products on it.
	 * @return True is the rack has no products on it, else false.
	 */
	public boolean isEmpty() {
		if (this.contents[0] == null) return true;
		return false;
	}
	
	/**
	 * Pushes an element onto the top of the rack.
	 * @param enqueueSnack Snack to be added to the rack.
	 * @throws FullRackException Thrown in the event that push is called on a full rack.
	 */
	private void push(Snack enqueueSnack) throws FullRackException {
		if (this.contents[this.contents.length - 1] == null) {
			shiftRight(this.contents);
			this.contents[0] = enqueueSnack;
		}
		else throw new FullRackException();
	}
	
	/**
	 * Returns the top of the stack - removes the top element in the array
	 * @return The top element of the array
	 * @throws EmptyRackException Thrown in the event a pop is attempted on an empty array.
	 */
	private Snack pop() throws EmptyRackException {
		if (this.contents[0] != null) {
			Snack snackItem = this.contents[0];
			shiftLeft(this.contents);
			return snackItem;
		}
		else throw new EmptyRackException();
	}
	
	/**
	 * Shifts every element in the array to the left by one element, setting the rightmost element to null.
	 * @param array The array of snacks on which to perform the shift.
	 */
	private void shiftLeft(Snack[] array) {
		for (int i = 0; i < array.length; i++) {
			if (i != array.length - 1) array[i] = array[i + 1];
			else array[i] = null;
		}
	}
	
	/**
	 * Shifts every element in the array to the right by one element, setting the leftmost element to null.
	 * @param array The array of snacks on which to perform the shift.
	 */
	private void shiftRight(Snack[] array) {
		for (int i = array.length - 1; i >= 0 ; i--) {
			if (i != 0)  array[i] = array[i - 1];
			else array[0] = null;
		}
	}
	
	/**
	 * Returns the number of objects present on the rack.
	 * @return The number of objects that are present on the rack.
	 */
	public int quantity() {
		int counter = 0;
		for (; counter < this.contents.length; counter++) {
			if (this.contents[counter] == null) break;
		}
		return counter;
	}
	
	/**
	 * Populates this rack with snack objects according to the type of snack it holds
	 * @throws FullRackException
	 */
	public void restock() throws FullRackException {
		for (int i = this.quantity(); i < this.contents.length; i++) {
			push(type);
		}
	}

	public Snack[] getContents() {
		return contents;
	}

	public void setContents(Snack[] contents) {
		this.contents = contents;
	}
	
	public Snack getType() {
		return this.type;
	}
	
	public void setType(Snack snack) {
		this.type = snack;
	}

	
}
