package heapsort;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HeapSorter {
	
	public static Heap<Item> heap = new Heap<Item>();
	
    /**
     * Repeatedly reads from standard input,
     * line by line, until an empty line or end of input is found.
     * Takes all lines of input and sorts them,
     * using heap sort.
     */

   //Declare and implement your methods here to implement heapsort as explained in the project document

	public static void heapSorter() {
		Scanner scan = new Scanner(System.in);
	    boolean loop = true;
	    while (loop) {
			try {
				System.out.println("Enter the contents of your heap: ");
				int input = scan.nextInt();
				
				HeapSorter.heap.insert(new Item(input));
				System.out.print("Heap: "); 
				HeapSorter.heap.print();
				scan.nextLine();
				while (true) {
					System.out.println("More items into the heap? (y/n)");
					String more = scan.nextLine();
					if (more.equalsIgnoreCase("Y"))
						break;
					else if (more.equalsIgnoreCase("N")) {
						loop = false;
						break;
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("You have to input an integer");
			}
	    }
	    
    	HeapSorter.heap.buildMaxHeap();
    	System.out.print("Heap Status: ");
    	HeapSorter.heap.print();
    	
    	System.out.println("\nHeapsorting...");
    	HeapSorter.heap.heapSort();
    	System.out.print("Heap Status: ");
    	HeapSorter.heap.print();
	}
	
}
