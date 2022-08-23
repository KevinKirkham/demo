package com.webCrawler;

import java.util.ArrayList;

public class PathTree {
	
	/**
	 * The root Node of the structure
	 */
	Node root;
	
	/**
	 * The domain whose paths will occupy this tree
	 */
	String domain;
	
	private class Node implements Comparable<Node> {
		
		private Node parent;
		private ArrayList<Node> children;
		private String path;
		
		public Node(String path) {
			this.children = new ArrayList<Node>();
			this.path = path;
		}
		
		public String getPath() {
			return this.path;
		}
		
		public String getCompletePath() {
			String completePath = this.getPath();
			Node node = this.parent;
			
			while (node != null) {
				completePath = node.getPath() + completePath;
				node = node.parent;
			}
			
			return completePath;
		}

		/**
		 * As per the requirements that come with implementing the Comparable interface,
		 * this method will return -1 if this Node's path lexicographically precedes the 
		 * argument Node's path. 
		 */
		public int compareTo(Node node) {
			if (this.path.compareTo(node.getPath()) < 0)
				return -1;
			else if (this.path.equals(node.getPath()))
				return 0;
			else
				return 1;
		}
	}
	
	public PathTree(String domain) {
		if (domain.endsWith("/"))
			this.domain = domain.substring(0, domain.length() - 1);
		else
			this.domain = domain;
		
		this.root = new Node(this.domain);
		this.root.parent = null;
	}
	
	/**
	 * Inserts a supplied path into the tree. Returns true if the supplied path was inserted successfully, else false.
	 * @param path the path to be inserted into the tree
	 * @return true if the path was inserted successfully, else false
	 */
	public boolean insert(String path) {
		if (path.equals("/") || this.contains(path))
			return false;
		
		String[] directories = path.split("/");
		Node node = this.root;
		int foundAt = -1;
		int i = 1;
		
		// Discover how much of the path already exists in the tree
		while (!node.children.isEmpty() && 
				(foundAt = BinarySearch.binarySearch(node.children, 0, node.children.size() - 1, new Node("/" + directories[i]))) != -1) {
			node = node.children.get(foundAt);
			i++;
		}
		
		// In the event that we are inserting into node.children
 		if (!node.children.isEmpty()) {
			// Adding a node into node.children, have to put it in its sorted location
			Node child = new Node("/" + directories[i++]);
			for (int j = 0; j < node.children.size(); j++)
				if (node.children.get(j).compareTo(child) > 0) {
					node.children.add(j, child);
					child.parent = node;
					break;
				}
				else if (j == node.children.size() - 1) {
					node.children.add(node.children.size(), child);
					child.parent = node;
					break;
				}
			
			node = child;
		}
		
		// Add the remaining pieces of the path to the tree
		while (directories.length - i > 0) {
			Node child = new Node("/" + directories[i++]);
			child.parent = node;
			child.parent.children.add(child); // child is the only thing in node.children - no need to find sorted position!
			node = child;
		}
		
		return true;
	}
	
	/**
	 * Determines if a given path exists in the tree or not. Returns true if the 
	 * supplied path is present in the tree, else false.
	 * 
	 * @param newPath The path of inquiry
	 * @return true if the newPath is in the tree, else false
	 */
	public boolean contains(String newPath) {
		String[] directories = newPath.split("/");
		Node node = this.root;
		int foundAt = -1;
		int i = 1;
		
		while (directories.length - i > 0 && node.children.size() > 0 && 
				(foundAt = BinarySearch.binarySearch(node.children, 0, node.children.size() - 1, new Node("/" + directories[i++]))) != -1)
			node = node.children.get(foundAt);
		
		if (node.getCompletePath().equals(domain + newPath))
			return true;
		else
			return false;
	}
	
	/**
	 * Not in use yet, but this is intended to serve as a single method to traverse the tree among .contains() and .insert()
	 * because the code present in the two methods that moves around the tree are nearly identical.
	 * 
	 * Searches this tree for the most specific common node between a given a supplied path and the contents of the tree. 
	 * Returns that most common node.
	 * 
	 * Example:
	 * 		If the tree contains "/search/doctors/emergency/east-wing" and the supplied path is "/search/doctors/waiting-list"
	 * 		then this method will return "/search/doctors" because that is the most specific common directory between the path
	 * 		and the tree (the branch most similar to the supplied path)
	 * 
	 * @param path the path being searched for
	 * @return the closest path to the supplied path
	 */
	public Node findCommonNode(String path) {
		String[] directories = path.split("/");
		Node node = this.root;
		int foundAt = -1;
		int i = 1;
		
		while (directories.length - i > 0 && node.children.size() > 0 && 
				(foundAt = BinarySearch.binarySearch(node.children, 0, node.children.size() - 1, new Node("/" + directories[i++]))) != -1)
			node = node.children.get(foundAt);
		
		return node;
	}
	
	public void print() {
		this.printTree(this.root);
	}

	/**
	 * Recursive method which prints every node in the tree, starting with the root and ending with the leaves.
	 * @param node Node to be printed
	 */
	private void printTree(Node node) {
		System.out.println(node.getCompletePath());
		for (int i = 0; i < node.children.size(); i++)
			printTree(node.children.get(i));
	}
	
}
