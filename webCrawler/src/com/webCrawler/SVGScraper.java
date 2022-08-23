package com.webCrawler;

import java.time.Duration;
import java.util.ArrayList;

import org.jsoup.Jsoup;										// jsoup-1.15.2.jar
import org.jsoup.nodes.Document;							// jsoup-1.15.2.jar
import org.jsoup.nodes.Element;								// jsoup-1.15.2.jar
import org.jsoup.select.Elements;							// jsoup-1.15.2.jar	
import org.openqa.selenium.By;								// selenium-api-4.3.0.jar
import org.openqa.selenium.WebDriver;						// selenium-api-4.3.0.jar
import org.openqa.selenium.WebElement;						// selenium-api-4.3.0.jar
import org.openqa.selenium.chrome.ChromeDriver;				// selenium-support-4.3.0.jar
import org.openqa.selenium.support.ui.ExpectedConditions;	// selenium-support-4.3.0.jar
import org.openqa.selenium.support.ui.WebDriverWait;		// selenium-support-4.3.0.jar

public class SVGScraper {

	/**
	 * Amount of time in seconds that the web driver should wait before continuing execution of the program
	 */
	private int timeout = 10;
	
	/**
	 * The web driver that is used to visit webpages
	 */
	private WebDriver driver;
	
	/**
	 * The domain of the website being crawled
	 */
	private String domain;
	
	/**
	 * The PathTree object in which all of the paths are stored and organized
	 */
	private PathTree tree;

	public SVGScraper(String domain) {
		this.driver = new ChromeDriver();

		if (domain.endsWith("/"))
			this.domain = domain.substring(0, domain.length() - 1);
		else
			this.domain = domain;

		this.tree = new PathTree(this.domain);
	}
	
	public SVGScraper() {

	}

	/**
	 * Loops through a list of URLs to be visited and scraped, only actually visiting one if the
	 * path tree hasn't seen it before. The first group of hrefs (website paths to visit) comes 
	 * from the domain's landing page. Following that, only unique paths (paths that don't already
	 * exist in the tree) get added to the tree. 
	 * 
	 * If a path successfully inserted into the path tree, we can be sure that it is unique. It
	 * therefore hasn't been visited yet, so we scrape that page.
	 */
	public void scrapeDomain() {

		ArrayList<String> hrefs = getHrefs();
		cleanHrefs(hrefs);
		for (int i = 0; i < hrefs.size(); i++) {

			if (hrefs.get(i).contains("/themes"))
				continue;
			
			// Add new (unique) hrefs to the list so they too can be crawled
			if (this.tree.insert(hrefs.get(i))) {
//				WebElement result = new WebDriverWait(driver, Duration.ofSeconds(timeout)).until(ExpectedConditions.visibilityOfElementLocated(By.id("main-content")));
				scrapePage(hrefs, hrefs.get(i));
			}
		}
	}

	/**
	 * Scrapes a webpage in a two-step approach - gather the href values from all of the 
	 * anchor tags present in the page's source code, then gather all of the referenced
	 * or otherwise defined SVG images on the page. New hrefs are cleaned and then added
	 * to the supplied list of hrefs to be later analyzed by the path tree, and information
	 * about the found SVG images is printed to the console. 
	 * 
	 * @param hrefs the queue of hrefs that are to be scanned for SVG images
	 * @param path the path of the webpage that is currently being evaluated
	 */
	public void scrapePage(ArrayList<String> hrefs, String path) {
		// Search page for hrefs
		String pageSource = getSource(path);	// Get page source, sets driver to new page
		ArrayList<String> newHrefs = getHrefs();
		cleanHrefs(newHrefs);
		
		// debug
//		System.out.println("\nClean Hrefs:");
//		for (int i = 0; i < newHrefs.size(); i++)
//			System.out.println(newHrefs.get(i));
			
		hrefs.addAll(newHrefs);
		
		// debug
//		System.out.println("\nhrefs found on page: ");
//		for (int i = 0; i < newHrefs.size(); i++)
//			System.out.println(newHrefs.get(i));

		// Scrape SVGs off of page
		ArrayList<SVG> svgs = getInlineSVGs(pageSource, path);		// SVG tags in the page source
		svgs.addAll(requestSRCs(getIMGSRCs(pageSource), path));		// SVG as the src attribute of img tags
		
		// debug
		System.out.println("\n" + svgs.size() + " SVG images found on " + path);
		for (int i = 0; i < svgs.size(); i++)
			System.out.println(svgs.get(i).toString() + "\n");
		
	}
	
	/**
	 * Parses through the HTML present on the landing page of the domain for anchor tags,
	 * and then compiles a list of the href attribute of each of those anchor tags.
	 * @return hrefs that were found on the landing page of the domain
	 */
	public ArrayList<String> getHrefs() {
		ArrayList<String> hrefs = new ArrayList<String>();
		try {
			Document source = Jsoup.parse(getSource(""));
			Elements anchorTags = source.select("a");

			for (Element anchor : anchorTags)
				hrefs.add(anchor.attr("href"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Debug
//		System.out.println("hrefs:");
//		for (int i = 0; i < hrefs.size(); i++) {
//			System.out.println(hrefs.get(i));
//		}

		return hrefs;
	}

	/**
	 * Cleans the raw href values taken from a page's source HTML such that the
	 * final product only contains paths to other pages within the domain. This
	 * method removes hrefs that include other domains and fragment URLs that link
	 * to a different part of the same page that the source HTML came from
	 * 
	 * @param hrefs hrefs to be cleaned
	 */
	public void cleanHrefs(ArrayList<String> hrefs) {
		for (int i = 0; i < hrefs.size();) {
			if (hrefs.get(i).contains(domain)) {
				String withDomain = hrefs.get(i);
				if (hrefs.get(i).contains("#")) {
					int fragment = hrefs.get(i).indexOf("#");
					hrefs.set(i++, withDomain.substring(domain.length(), fragment));
				} else
					hrefs.set(i++, withDomain.substring(domain.length()));
			} else if (!hrefs.get(i).startsWith("/") || hrefs.get(i).startsWith("/#"))
				hrefs.remove(i);
			else
				i++;
		}
	}

	/**
	 * Prints the paths found during the crawl to the console
	 */
	public void printPaths() {
		this.tree.print();
	}

	/**
	 * Prints the cleaned hrefs to the console. Mostly used for debugging
	 * @param hrefs ArrayList of the hrefs to print to the console
	 */
	public void printCleanedHrefs(ArrayList<String> hrefs) {
		System.out.println("\nCleaned hrefs:");
		for (int i = 0; i < hrefs.size(); i++) {
			System.out.println(hrefs.get(i));
		}
	}

	/**
	 * Returns the source HTML code for the page specified by the supplied path.
	 * 
	 * @param url destination webpage
	 * @return source HTML code of the open webpage
	 */
	public String getSource(String path) {
		driver.get(domain + path);
		return driver.getPageSource();
	}

	/**
	 * Accepts the page source and its associated path and parses that data in search of SVG tags, 
	 * returning a list created SVG objects from the data that was found.
	 * 
	 * @param pageSource HTML code present on a webpage
	 * @param path the page to the wepage
	 * @return SVGs that were created from the data on the webpage
	 */
	public ArrayList<SVG> getInlineSVGs(String pageSource, String path) {
		ArrayList<SVG> svgs = new ArrayList<SVG>();
		Document html = Jsoup.parse(pageSource);
		Elements svgTags = html.select("svg");

		for (Element svgTag : svgTags) {
			Elements titles = svgTag.select("title");
			Elements desc = svgTag.select("desc");
			String alt = svgTag.attr("alt");
			String classAttr = svgTag.attr("class");
			String parentClass = svgTag.parent().attr("class");
			
			SVG svg = new SVG("Tag defined inline", path);

			if (titles.first() != null)
				svg.setTitle(titles.first().text());
			if (desc.first() != null)
				svg.setDesc(desc.first().text());
				
			svg.setAlt(svgTag.attr("alt"));
			svg.setClassAttr(svgTag.attr("class"));
			svg.setParentClass(parentClass);

			svgs.add(svg);
		}

		return svgs;
	}

	/**
	 * Searches through supplied HTML code searching for img tags, and then returns a 
	 * list of the src attributes of those img tags. This was added because some of the SVG 
	 * images on Carilion Clinic's website were coded this way instead of with SVG tags.
	 * 
	 * @param htmlText HTML code to parse for img tags
	 * @return the src attributes of each of the img tags on the supplied HTML
	 */
	public ArrayList<SVG> getIMGSRCs(String htmlText) {
		Document html = Jsoup.parse(htmlText);
		Elements images = html.select("img");
		ArrayList<SVG> svgs = new ArrayList<SVG>(); 	// ArrayList of src attributes from img tags
		
		for (Element image : images) 						// for each img tag found in the HTML
			if (image.attr("src").contains(".svg")) {		// if its src attribute references an SVG
				SVG svg = new SVG(image.attr("src"));
				svg.setAlt(image.attr("alt"));
				svg.setParentClass(image.parent().attr("class"));
				svgs.add(svg);
			}

		return svgs;
	}

	/**
	 * Using a list of paths to SVG resources (referenced in the src attribute of img tags), 
	 * a request is made to each one and then the code that defines them is fetched and parsed
	 * for information about their title and desc tags.
	 * 
	 * @param srcs
	 * @param path
	 * @return
	 */
	public ArrayList<SVG> requestSRCs(ArrayList<SVG> svgs, String path) {
		for (SVG svg : svgs)
			try {
				Document doc = Jsoup.connect(domain + svg.getSrc()).ignoreContentType(true).get();
				Document svgSource = Jsoup.parse(doc.text());
				Elements title = doc.select("title");
				Elements desc = doc.select("desc");

				if (title.first() != null) 					// if the title tag is not non-existent
					svg.setTitle(title.first().text()); 	// give its contents to the SVG object
				if (desc.first() != null) 					// if the desc tag  is not non-existent
					svg.setDesc(desc.first().text()); 		// give its contents to the SVG object
				
				svg.setPath(path);
				svg.setClassAttr(svgSource.attr("class"));
			} catch (Exception e) {
				System.out.println("There was a problem requesting that src: ");
				e.printStackTrace();
			}

		return svgs;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
