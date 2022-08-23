package com.webCrawler;

import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		SVGScraper scraper = new SVGScraper("https://www.velocitycarebycarilion.com/");
		scraper.scrapeDomain();
	}

}
