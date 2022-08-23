package com.tests;

import java.util.ArrayList;

import org.junit.Test;

import com.webCrawler.BinarySearch;
import com.webCrawler.SVGScraper;

class BinarySearchTest {

	@Test
	void binarySearchTest() {
		
		SVGScraper scraper = new SVGScraper("asdf");
		
		ArrayList<String> test = new ArrayList<String>();
		
		test.add("Baseball");
		test.add("Cookies");
		test.add("Hotdog");
		test.add("Jimmy");
		test.add("Mirabel");
		test.add("Roger");
		test.add("Tolfdir");
		test.add("Wyoming");
		
		
//		scraper.insertionSort(test);
		int result = BinarySearch.binarySearch(test, 0, test.size()-1, "Mirabel");
		System.out.println(result);
	}
	
}
