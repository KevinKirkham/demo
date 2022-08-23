package com.webCrawler;

public class SVG {
	
	String src = "";		// filename of the SVG
	String path = "";	// page on which it is found
	String title = "";	// contents of the title tag
	String desc = "";	// contents of the desc tag
	String alt = "";		// alt text of the SVG
	String classAttr = "";	// class attribute of SVG
	String parentClass = "";
	
	public SVG(String src, String path, String title, String desc) {
		this.src = src;
		this.path = path;
		this.title = title;
		this.desc = desc;
	}
	
	public SVG(String src, String path, String alt) {
		this.src = src;
		this.path = path;
		this.alt = alt;
	}
	
	public SVG(String src, String path) {
		this.src = src;
		this.path = path;
	}
	
	public SVG(String src) {
		this.src = src;
	}
	
	public SVG() {
		
	}
	
	public String toString() {
		return "src: " + src + "\n" +
				"path: " + path + "\n" +
				"title: " + title + "\n" +
				"desc: " + desc + "\n" +
				"alt: " + alt + "\n" +
				"class: " + classAttr + "\n" +
				"parentClass: " + parentClass;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public String getClassAttr() {
		return classAttr;
	}

	public void setClassAttr(String classAttr) {
		this.classAttr = classAttr;
	}

	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}
	
}
