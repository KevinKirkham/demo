package heapsort;

public class Item implements Comparable<Item> {

	private int value;
	
	public Item(int value) {
		this.value = value;
	}
	
	public String toString() {
		return Integer.toString(this.value);
	}
	
	public int compareTo(Item item) {
		if (this.value > item.getValue()) 
			return 1;
		else if (this.value < item.getValue())
			return -1;
		else 
			return 0;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
