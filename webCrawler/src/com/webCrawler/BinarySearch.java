package com.webCrawler;

import java.util.ArrayList;

public class BinarySearch {
	
	public static <E extends Comparable<E>> int binarySearch(ArrayList<E> data, int left, int right, E x) {
		if (right >= left) {
			int mid = (left + right) / 2;
			if (data.get(mid).compareTo(x) == 0)
				return mid;
			
			if (data.get(mid).compareTo(x) > 0)
				return binarySearch(data, left, mid - 1, x);
			
			return binarySearch(data, mid + 1, right, x);
		}
		return -1;
	}


}
