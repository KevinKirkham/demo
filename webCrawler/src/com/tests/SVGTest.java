package com.tests;

import java.util.ArrayList;

import org.junit.Test;

import com.webCrawler.SVG;
import com.webCrawler.SVGScraper;

class SVGTest {

	@Test
	public static void SVGTest() {
		
		String html = "<html><body><svg class=\"phoneicon\" alt=\"This is an image of the phone icon\" xmlns=\"http://www.w3.org/2000/svg\" width=\"16.856\" height=\"14.974\" viewBox=\"0 0 16.856 14.974\"><title>Phone Icon</title><desc>This icon depicts a telephone</desc><defs><style>.a{fill:#05295b;}</style></defs><path class=\"a\" d=\"M15.264,10.587,12.7,9.184a.858.858,0,0,0-.923.2l-1.634,1.772A11.621,11.621,0,0,1,4.3,5.976l2-1.451a.65.65,0,0,0,.227-.819L4.946.431A.825.825,0,0,0,4.04.024L.613.726A.725.725,0,0,0,0,1.41c0,7.5,6.851,13.57,15.29,13.57a.777.777,0,0,0,.771-.544l.791-3.042c.084-.333-1.236-.671-1.588-.807Z\" transform=\"translate(0 -0.006)\"></path></svg></body></html>";
		
		SVGScraper scraper = new SVGScraper("https://www.velocitycarebycarilion.com/");
		String path = "/path/path/path";
		ArrayList<SVG> svgs = scraper.getInlineSVGs(html, path);
		ArrayList<String> srcs = new ArrayList<String>();
		String source = "/themes/custom/vc8/dist/img/logo.svg";
		srcs.add(source);
		
//		for (int i = 0; i < svgs.size(); i++) {
//			System.out.println(svgs.get(i));
//		}
		
//		svgs.addAll(scraper.requestSRCs(srcs, path));
		
		System.out.println(svgs.size() + " SVGs found on " + path);
		for (SVG svg : svgs)
			System.out.println(svg.toString() + "\n");
		
	}
	
}
