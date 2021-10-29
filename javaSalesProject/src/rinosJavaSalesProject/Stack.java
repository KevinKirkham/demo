package rinosJavaSalesProject;

import java.util.ArrayList;

/**
 * Linear data structure in which the most recently-entered node is the first one to leave
 * @author waveo
 *
 * @param <E>
 */

public class Stack<E> {
	
	private class Node {
		
		private E item;
		private Node next;
		private Node prev;
		
		Node(E item, Node next, Node prev) {
			this.item = item;
			this.next = next;
			this.prev = prev;
		}
		
		Node(E item) {
			this(item, null, null);
		}
	}
	
	private Node first;
	private Node last;
	
	public Stack() {
		first = null;
		last = null;
	}
	
	
	public boolean isEmpty() {
		if (first == null) {
			return true;
		}
		return false;
	}
	
	public int size() {
		int count = 0;
		Node p = first;
		while (p != null) {
			count++;
			p = p.next;
		}
		return count;
	}
	
	
	public void push(E elementToAdd) {
		if (isEmpty() == true) {
			first = new Node(elementToAdd);
			last = first;
		}
		else {
			last.next = new Node(elementToAdd, null, last);
			last = last.next;
		}
	}
	
	
	/*
	
	public void add(int index, Item itemToAdd) {
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		if (index == 0) {
			Node temp = new Node(itemToAdd);
			temp.next = first;
			first.prev = temp;
			first = temp;
		}
		else {
			Node pred = first;
			for (int k = 1; k <= index - 1; k++) {
				pred = pred.next;
			}
			Node succ = pred.next;
			Node nodeToAdd = new Node(itemToAdd, succ, pred);
			pred.next = nodeToAdd;
			if (succ == null)
				last = nodeToAdd;
			else
				succ.prev = nodeToAdd;
		}
	}
	
	*/
	
	
	public E pop() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		
		Node p = last;
		E e = p.item;	// The grabbing the item that we are to return
		if (p.prev != null) {	// If it is not the only item in the list
			last = p.prev;	// Set last to the second to last item
			last.next = null;
		}
		
		// How to handle the popping of an element when there is only one element in the stack?
		else {
			last = first = null;
		}


		remove(p.item);
		return e;
	}
	
	public E peek() {
		if (isEmpty()) {
			throw new IndexOutOfBoundsException();
		}
		return last.item;
	}
	
	
	
	// The remove methods will get replaced with the pop method
	/*
	public Item remove(int index) {
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}

		Node target = first;
		for (int k = 1; k <= index; k++)
			target = target.next;

		Item element = target.item;
		Node pred = target.prev;
		Node succ = target.next;

		if (pred == null)
			first = succ;
		else
			pred.next = succ;

		if (succ == null)
			last = pred;
		else
			succ.prev = pred;

		return element;
	}
	
	
	*/
	
	
	private boolean remove(E itemToBeRemoved) {
		if (isEmpty() == true) {
			return false;
		}

		Node target = first;
		while (target != null && !itemToBeRemoved.equals(target.item))
			target = target.next;

		if (target == null)
			return false;

		Node pred = target.prev; 
		Node succ = target.next; 

		if (pred == null)
			first = succ;
		else
			pred.next = succ;

		if (succ == null)
			last = pred;
		else
			succ.prev = pred;

		return true;
	}
	
	
	
	/**
	 * 
	 * When supplied a given item, it will return the index at 
	 * which that item is located in the linked list. Returns -1
	 * if that item can't be found in the list
	 * @param element
	 * @return
	 */
	
	public int indexOf(E element) {
		int count = 0;
		Node p = first;
		for (count = 0; p.item != element; count++) {
			p = p.next;
		}
		if (p.item == element) return count;
		else return -1;
	}
	
	
	private E get(int index) {
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException(String.valueOf(index));
		}
		
		Node p = first;
		for (int i = 0; i <= index; i++) {
			if (i == index) {
				return p.item;
			}
			p = p.next;
		}
		return null;
	}
	
	public void clear() {
		for (int i = 0; i < size(); i++) {
			remove(this.get(i));
		}
	}
	
	public Stack<E> clone() {
		Stack<E> copy = new Stack<>();
		if (!isEmpty()) {
			Node copyFirst = new Node(first.item, null, null);
			copy.first = copyFirst;
			copy.last = copy.first;
			
			int counter = 1;
			while (counter < this.size()) {
				copy.push(this.get(counter));
				counter++;
			}
		}
		return copy;
	}
	
	
	public ArrayList<E> toArrayList() {
		ArrayList<E> list = new ArrayList<>();
		for (int i = 0; i < this.size(); i++) {
			list.add(get(i));
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		Stack test = new Stack();
		Item testItem = new Item(100, "1", 15);
		Item testItem2 = new Item(100, "2", 15);
		Item testItem3 = new Item(100, "3", 15);
		Item testItem4 = new Item(100, "4", 15);
		
		test.push(testItem);
		test.push(testItem2);
		test.push(testItem3);
		test.push(testItem4);
		
		Stack clone = test.clone();
		
		clone.pop();
		clone.pop();
		clone.pop();
	}

}
