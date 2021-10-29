package rinosJavaSalesProject;

import java.util.List;


/**
 * Insertion sort routine implemented with a List
 * @author waveo
 *
 */
public class ObjectALInsertionSort {

	public static <E extends Comparable<E>> List<E> insertionSort(List<E> data) {

		// loop over data.length - 1 elements
		for (int i = 1; i < data.size(); i++) {

			E insert = data.get(i); // value to insert
			int pointer = i; // location to place element

			// search for place to put current element
			while (pointer > 0 && insert.compareTo(data.get(pointer - 1)) > 0) {
				// shift element right one slot
				E temp = data.get(pointer - 1);
				data.set((pointer - 1), data.get(pointer));
				data.set(pointer, temp);
				pointer--;
			}
			E temp = data.get(pointer);
			temp = insert; // place inserted element
		}
		return data;
	}

} // end class InsertionSort
