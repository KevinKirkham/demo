package rinosJavaSalesProject;

import java.util.ArrayList;
import java.util.List;

/**
 * Merge sort routine implemented with a List
 * @author waveo
 *
 */

public class ObjectALMergeSort {

	// calls recursive split method to begin merge sorting
	public static <E extends Comparable<E>> void mergeSort(List<E> data) {
		sortArray(data, 0, data.size() - 1); // sort entire array
	} // end method sort

	// splits array, sorts subarrays and merges subarrays into sorted array
	private static <E extends Comparable<E>> void sortArray(List<E> data, int low, int high) {

		// test base case; size of array equals 1
		if ((high - low) >= 1) { // if not base case
			int middle1 = (low + high) / 2; // calculate middle of array
			int middle2 = middle1 + 1; // calculate next element over

			// split array in half; sort each half (recursive calls)
			sortArray(data, low, middle1); // first half of array
			sortArray(data, middle2, high); // second half of array

			// merge two sorted arrays after split calls return
			merge(data, low, middle1, middle2, high);
		}
	}

	// merge two sorted subarrays into one sorted subarray
	private static <E extends Comparable<E>> void merge(List<E> data, int left, int middle1, int middle2, int right) {

		int leftIndex = left; // index into left subarray
		int rightIndex = middle2; // index into right subarray
		int combinedIndex = left; // index into temporary working array
		
		List<E> combined = new ArrayList<>(); // working list
		for (int i = 0; i < data.size(); i++) {
			combined.add(null);
		}

		// merge arrays until reaching end of either
		while (leftIndex <= middle1 && rightIndex <= right) {
			// place smaller of two current elements into result
			// and move to next space in arrays
			if (data.get(rightIndex).compareTo(data.get(leftIndex)) <= 0)
				combined.set(combinedIndex++, data.get(leftIndex++));
			else
				combined.set(combinedIndex++, data.get(rightIndex++));
		}

		// if left array is empty
		if (leftIndex == middle2)
			// copy in rest of right list
			while (rightIndex <= right)
				combined.set(combinedIndex++, data.get(rightIndex++));
		else // right array is empty
				// copy in rest of left list
			while (leftIndex <= middle1)
				combined.set(combinedIndex++, data.get(leftIndex++));

		// copy values back into original list
		for (int i = left; i <= right; i++)
			data.set(i, combined.get(i));

	} // end method merge


} // end class MergeSort

