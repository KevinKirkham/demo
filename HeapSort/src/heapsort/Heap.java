package heapsort;

/**
 * A data structure that represents a max heap.
 * @param <T> : The type of item stored in the heap. It must implement
 *           the Comparable<T> interface, so that the data structure
 *           knows how to compare two items.
 *           Note: String already implements Comparable.
 */
public class Heap<T extends Comparable<T>> {
	
	/**
	 * The node class for the heap data structure.
	 * @author Kevin Kirkham
	 *
	 */
	private class Node {
		
		/**
		 * The parent of this node.
		 */
		private Node parent;
		
		/**
		 * The left child of this node.
		 */
		private Node leftChild;
		
		/**
		 * The right child of this node.
		 */
		private Node rightChild;
		
		/**
		 * The sorted state of this node (only used in heapsort).
		 */
		private boolean sorted;
		
		/**
		 * The value that is held by this node (could be any object).
		 */
		private T value;
		
		public Node(T item) {
			this.parent = null;
			this.value = item;
			this.leftChild = null;
			this.rightChild = null;
			this.sorted = false;
		}
		
		public Node(Node parent, T value) {
			this.parent = parent;
			this.value = value;
			this.leftChild = null;
			this.rightChild = null;
			this.sorted = false;
		}
		
		public Node(Node parent, Node leftChild, Node rightChild, boolean sorted, T value) {
			this.parent = parent;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.sorted = sorted;
			this.value = value;
		}
	}
	
	/**
	 * The root node of the heap.
	 */
	private Node root;
	
	/**
	 * The last leaf of the heap
	 */
	private Node lastLeaf;
	
    /**
     * Construct a max heap that is initially empty.
     */
    public Heap() {
        this.root = null;
        this.lastLeaf = null;
    }
    
    /**
     * Overrides the toString() method from the Object class, returns information about this heap.
     * @return information about this heap.
     */
    public String toString() {
    	return "Root: " + this.root.value.toString() + "\n"
    		+ "Last Leaf: " + this.lastLeaf.value.toString() + "\n"
    		+ "Height: " + this.height() + "\n"
    		+ "Size: " + this.size() + "\n"
    		+ "Max: " + this.findMax();
    }

    /**
     * Returns the maximum item in the heap.
     * @return : The maximum item in the heap.
     */
    public T findMax() {
    	if (this.isEmpty())
    		return null;
    	if (this.root == this.lastLeaf)
    		return this.root.value;
    	else {
    		Node node = this.root;
    		T max = node.value;
        	while (true) {
        		Node next = levelOrderTraversal(node);
        		if (reverseLevelOrderTraversal(next) != node)
        			break;	// node is the last node of the heap
        		if (next.value.compareTo(max) > 0)
        			max = next.value;
        		node = next;
        	}
        	return max;
    	}
    }
    
    /**
     * Removes the maximum item from this heap.
     * Returns the value of the node which hold the maximum 
     * value found in the heap.
     * @return : The maximum item, which was removed.
     * @precondition : Heap is not empty.
     */
    public T extractMax() {
        assert (isEmpty()): "Heap is empty";
        
        if (this.isEmpty())
    		return null;
    	if (this.root == this.lastLeaf)
    		return this.root.value;
    	
		Node node = this.root;
		Node max = this.root;
		while (true) {
			Node next = levelOrderTraversal(node);
			if (reverseLevelOrderTraversal(next) != node)
				break; // node is the last node of the heap
			if (next.value.compareTo(max.value) > 0)
				max = next;
			node = next;
		}
        slide(max);
        return max.value;	// This doesn't return the max value... why??
    }
    
    /**
     * Moves all values present in the heap to the node that 
     * would be in an index position less than it if the heap 
     * were implemented from an array.
     */
    private void slide(Node target) {
    	Node node = this.lastLeaf;
    	T overwrite = null;
    	T save = null;
    	while (node != target) {
    		save = node.value;
    		Node next = reverseLevelOrderTraversal(node);
    		node.value = overwrite;
    		node = next;
    		overwrite = save;
    	}
    	node.value = overwrite;
    	severLastLeaf();
    }
    
    /**
     * Removes from the heap the first instance of the specified value moving 
     * in a level order traversal starting at the root. Returns null if the 
     * target wasn't found.
     * @param value the target to be removed
     * @return the value that was removed
     */
    public T remove(T value) {
    	Node node = this.root;
    	while (true) {
    		Node next = levelOrderTraversal(node);
    		if (reverseLevelOrderTraversal(next) != node)
    			break;	// node is the last node of the heap
    		if (node.value == value) {
    			Node target = node;
    			this.slide(target);
    			return target.value;
    		}
    		node = next;
    	}
    	return null;
    }
    
    /**
     * Used during the execution of the extractMax method, this
     * method finds the next node that needs to be evaluated.
     * @param node node being evaluated
     * @return the next node in the sequence
     */
    public Node reverseLevelOrderTraversal(Node node) {
    	int levels = 0;
    	if (!isLeftmost(node)) {
    		for (; node.parent != this.root && node.parent.leftChild == node; levels++)
    			node = node.parent;
    		node = node.parent.leftChild;
    		for (; levels > 0; levels--)
    			node = node.rightChild;
    		return node;
    	}
    	else {
    		for (; node.parent != null; levels++)
    			node = node.parent;
    		for (; levels > 1; levels--)
    			node = node.rightChild;
    		return node;
    	}
    }

    /**
     * Traverses the heap in the level order fashion. Returns the next node in 
     * @param node The node from which the traversal begins
     * @return the next node in the level order traversal
     */
    public Node levelOrderTraversal(Node node) {
    	if (node == this.root)
    		return this.root.leftChild;
    	int levels = 0;
    	if (!isRightmost(node)) {
    		for (; node.parent != this.root && node.parent.rightChild == node; levels++)
    			node = node.parent;
    		if (node.parent.rightChild != null) 
    			node = node.parent.rightChild;
    		else
    			return node;
    		for (; levels > 0; levels--)
    			if (node.leftChild != null) 
    				node = node.leftChild;
    			else 
    				return node;
    		return node;
    	}
    	else {
    		for (; node.parent != null; levels++)
    			node = node.parent;
    		for (; levels >= 0; levels--)
    			if (node.leftChild != null)
    				node = node.leftChild;
    			else 
    				return node;
    		return node;
    	}
    }

    /** 
     * Returns true if the node passed into it is a node along the leftmost branch of the binary tree.
     * @param node Node in question
     * @return true if the node is on the leftmost branch
     */
    public boolean isLeftmost(Node node) {
    	if (node == this.root)
    		return false;
    	if (node.parent.rightChild == node)
    		return false;
    	while (node.parent.parent != null && node.parent != node.parent.parent.rightChild)
    		node = node.parent;
    	if (node.parent.parent == null)
    		return true; // node is root.leftChild
    	else
    		return false;
    }
    
    /** 
     * Returns true if the node passed into it is a node along the rightmost branch of the binary tree.
     * @param node Node in question
     * @return true if the node is on the rightmost branch
     */
    private boolean isRightmost(Node node) {
    	if (node == this.root)
    		return false;
    	if (node.parent.leftChild == node)
    		return false;
    	while (node.parent.parent != null && node.parent != node.parent.parent.leftChild)
    		node = node.parent;
    	if (node.parent.parent == null)
    		return true;
    	else 
    		return false;
    }

    /**
     * Inserts the given item into the Heap.
     * By the end of this method's execution, the 
     * max heap property will still be true because we call 
     * maxheapify() after including the new value as part of the 
     * heap.
     * @param item : The item to insert into the heap.
     */
    public void insert(T item) {
    	
    	// You are inserting the first element into the heap
    	if (this.root == null) {
    		this.root = new Node(item);
    		this.lastLeaf = this.root;
    		return;
    	}
    	
    	// There is only one node in the heap, so you are inserting the second node into the heap
    	else if (this.root == this.lastLeaf) {
    		this.root.leftChild = new Node(item);
    		this.root.leftChild.parent = this.root;
    		this.lastLeaf = this.root.leftChild;
    		return;
    	}
    	
    	// You have 2 elements in the heap, and you are inserting the third element into the heap
    	else if (this.root.rightChild == null) {
    		this.root.rightChild = new Node(item);
    		this.root.rightChild.parent = this.root;
    		this.lastLeaf = this.root.rightChild;
    		return;
    	}
    	
    	Node node = levelOrderTraversal(this.lastLeaf);
    	
    	if (node.parent.rightChild == null) {
        	node.parent.rightChild = new Node(item);
        	node.parent.rightChild.parent = node.parent;
    		this.lastLeaf = node.parent.rightChild;
    	}
    	else if (node.leftChild == null) {
        	node.leftChild = new Node(item);
        	node.leftChild.parent = node;
        	this.lastLeaf = node.leftChild;
        }
        
    }

    /**
     * Returns whether or not the heap is empty.
     * Achieves this by calling the size() method. 
     * If size() returns 0, then the heap is empty and we return true.
     * Else false.
     * @return : true if heap is empty and false otherwise.
     */
    public boolean isEmpty() {
        if (this.root == null)
        	return true;
        else
        	return false;
    }

    /**
     * Returns the number of items stored in this heap.
     * @return : the number of items stored in this heap.
     */
    public int size() {
    	if (this.root == null)
    		return 0;
    	if (this.root == this.lastLeaf)
    		return 1;
    	if (this.root.rightChild == null)
    		return 2;
    	else {
    		int size = 3;
    		Node node = this.root.rightChild;
    		
    		while (true) {
    			Node next = levelOrderTraversal(node);
        		if (reverseLevelOrderTraversal(next) != node)
        			break;	// node is the last node in the heap
        		size++;
        		node = next;
    		}
    		return size;
    	}
    }

    /**
     * Returns the number of levels in this heap.
     * @return : The number of levels in this heap.
     */
    public int height() {
        int height = 0;
    	Node node = this.lastLeaf;
    	for (;node.parent != null; height++) 
        	node = node.parent;
    	// node is now the root
        return height;
    }
    
    /**
     * Severs the last leaf from the heap.
     * @return true if the leaf was removed successfully
     * @precondition : Heap is not empty
     */
    private boolean severLastLeaf() {
    	
    	assert (this.size() > 0): "The heap must not be empty to attempt to sever the last leaf.";
    	
    	if (this.root == this.lastLeaf) {
    		this.root = null;
    		this.lastLeaf = null;
    		return true;
    	}
    	else if (this.lastLeaf.parent.leftChild == this.lastLeaf) {
    		Node copy = this.lastLeaf;
    		this.lastLeaf = reverseLevelOrderTraversal(this.lastLeaf);
    		copy.parent.leftChild = null;
    		return true;
    	}
    	else {
    		this.lastLeaf = this.lastLeaf.parent.leftChild;
    		this.lastLeaf.parent.rightChild = null;
    		return true;
    	}
    }
    
    /**
     * Prints the status of this heap.
     */
    public void print() {
    	if (this.size() == 0)
    		System.out.println("The heap is empty.");
    	else if (this.size() == 1)
    		System.out.println(this.root.value.toString());
    	else if (this.size() == 2) {
    		System.out.println(this.root.value.toString() + " " + this.root.leftChild.value.toString());
    	}
    	else {
    		Node node = this.root;
        	while (true) {
        		System.out.print(node.value.toString() + " ");
        		Node next = levelOrderTraversal(node);
        		if (reverseLevelOrderTraversal(next) != node)
        			break;	// You've reached the end of the heap
        		node = next;
        	}
        	System.out.println();
    	}
    }
    
    /**
     * Sorts the heap using the maxHeapify method.
     */
    public void buildMaxHeap() {
    	if (isEmpty() || this.root == this.lastLeaf)
    		return;
    	
    	Node node = firstParent();
    	
    	while (node != this.root) {
    		System.out.println("Calling maxHeapify() on " + node.value.toString());
    		maxHeapify(node);
    		this.print();
    		node = reverseLevelOrderTraversal(node);
    	}
    	maxHeapify(this.root);
    }
    
    /**
     * Returns the first node that is a parent. Begins searching from the last node in 
     * the heap.
     * @return the first node that is a parent.
     */
    private Node firstParent() {
    	Node node = this.lastLeaf;
    	while (true) {
    		node = reverseLevelOrderTraversal(node);
    		if (node.leftChild != null)
    			return node;
    	}
    }
    
    /**
     * Accepts two arguments, the first of which will be a the parent of the other. 
     * The parent gets swapped with the child such that the child has all of the same links 
     * that the parent had before the swap took place, and the parent has all of the links
     * that the child had before the swap took place. This is performed using a copy of the 
     * parent node.
     * @param parent The parent node
     * @param child The parent node
     */
    private void swap(Node parent, Node child) {
    	Node copyParent = new Node(parent.parent, parent.leftChild, parent.rightChild, parent.sorted, parent.value);
    	if (child == parent.leftChild)
    		swapLeftChild(parent, child, copyParent);
    	else if (child == parent.rightChild)
    		swapRightChild(parent, child, copyParent);
    	else
    		swapSeparate(parent, child, copyParent);
    	
    	// Make sure the root and lastLeaf are still what they should be
    	if (parent == this.root) this.root = child;
    	if (child == this.lastLeaf) this.lastLeaf = parent;
    }
    
    /**
     * Swaps the position of node1 with the position of node2 in the heap.
     * Properly establishes the links between them as they should be.
     * @param node1 The upper node 
     * @param node2 The lower node
     * @param copyNode1	Initial node1 before any changes to its links were made
     */
    private void swapLeftChild(Node node1, Node node2, Node copyNode1) {
    	
    	// Set node1's fields to node2's
    	node1.rightChild = node2.rightChild;
		node1.leftChild = node2.leftChild;
		node1.parent = node2;
		
		// Set node2's fields
		node2.rightChild = copyNode1.rightChild;
		node2.leftChild = node1;
		node2.parent = copyNode1.parent;
		
		//Set either the left or right child of the initial parent node to the child
		setChild(node1, node2);
		
		// Set the parent field of child's rightChild to child
		if (node2.rightChild != null) node2.rightChild.parent = node2;
		
		// Set the parent field of the parent's two children to parent
		if (node1.leftChild != null) node1.leftChild.parent = node1;
		
		// Set node1's rightChild's parent to node1
		if (node1.rightChild != null) node1.rightChild.parent = node1;
    }

    /**
     * Swaps the position of node1 with the position of node2 in the heap.
     * Properly establishes the links between them as they should be.
     * @param node1 The upper node 
     * @param node2 The lower node
     * @param copyNode1	Initial node1 before any changes to its links were made
     */
    private void swapRightChild(Node node1, Node node2, Node copyNode1) {
    	
    	// Set parent nodes
    	node1.rightChild = node2.rightChild;
		node1.leftChild = node2.leftChild;
		node1.parent = node2;
	    
		// Set child nodes
		node2.rightChild = node1;
		node2.leftChild = copyNode1.leftChild;
		node2.parent = copyNode1.parent;
		
		setChild(node1, node2);

		// Make sure the right child of the parent node to the child 
		if (node2.leftChild != null) node2.leftChild.parent = node2;	//child.leftChild will never be null (at this point in the code) in a binary tree!
		
		// Set the parent field of the parent's two children to the parent
		if (node1.rightChild != null) node1.rightChild.parent = node1;
		
		// Set node1's leftChild's parent to node1
		if (node1.leftChild != null) node1.leftChild.parent = node1;
    }

    /**
     * Swaps the position of node1 with the position of node2 in the heap.
     * Properly establishes the links between them as they should be.
     * @param node1 The upper node 
     * @param node2 The lower node
     * @param copyNode1	Initial node1 before any changes to its links were made
     */
    private void swapSeparate(Node node1, Node node2, Node copyNode1) {
    	
    	// Set the parent's links
    	node1.leftChild = node2.leftChild;
    	node1.rightChild = node2.rightChild;
    	node1.parent = node2.parent;
    	
    	// Set the child's links
    	node2.leftChild = copyNode1.leftChild;
    	node2.rightChild = copyNode1.rightChild;
    	node2.parent = copyNode1.parent;
    	
    	// set either the leftChild or rightChild of the initial node1 to node2
    	setChild(node1, node2);
    	
    	// Set node2's rightChild's parent to node2
    	if (node2.rightChild != null) node2.rightChild.parent = node2;
    	
    	// Set node2's leftChild's parent to node2
    	if (node2.leftChild != null) node2.leftChild.parent = node2;
    	
    	// Set either left or right child of the initial node2 to node1
    	setChild(node2, node1);
    	
    	// Set the parent of node1's leftChild to node1
    	if (node1.leftChild != null) node1.leftChild.parent = node1;
    	
    	// Set the parent of node1's rightChild to node1
    	if (node1.rightChild != null) node1.rightChild.parent = node1;
    }
    
    private void setChild(Node a, Node b) {
		// Set what is now either the child's parent's leftChild or rightChild field
		// This depends on what side of the node the initial parent node was (leftChild or rightChild)
		if (b.parent != null)
			if (a == b.parent.rightChild) {
				b.parent.rightChild = b;
			} 
			else if (a == b.parent.leftChild) {
				b.parent.leftChild = b;
			}
    }
    
    /**
     * An algorithm that compares a node's value with its two children to 
     * decide which of the three nodes is the largest. If one of the children
     * is larger than the node, a swap happens such that the larger of the three
     * values resides on top. The method is then called recursively on the node
     * that contained the greatest value prior to the swap, thus sorting every subsequent 
     * node-left child-right child cluster until the end of the heap is reached.
     * @param node The node to be evaluated by the maxHeapify algorithm
     */
    private void maxHeapify(Node node) {
    	Node left = null;
    	Node right = null;
    	Node greatest = node;
    	if (node.leftChild != null)
    		left = node.leftChild;
    	if (node.rightChild != null)
    		right = node.rightChild;
    	
    	if (left != null && left.value.compareTo(node.value) > 0)
    		greatest = left;
    	
    	if (right != null && right.value.compareTo(greatest.value) > 0) 
    		greatest = right;
    	
    	if (greatest != node && !greatest.sorted) {
    		swap(node, greatest);
    		maxHeapify(node);
    	}
    }
    
    /**
     * Sorts this heap using the sorting algorithm heapsort.
     */
    public void heapSort() {
    	if (this.isEmpty() || this.size() < 2)
    		return;
    	this.buildMaxHeap();
    	Node node1 = this.root;
    	Node node2 = this.lastLeaf;
    	while (node2 != this.root) {
    		swap(this.root, node2);
    		this.print();
    		node1.sorted = true;
    		maxHeapify(this.root);
    		node2 = reverseLevelOrderTraversal(node1);
    		node1 = this.root;
    	}
    	if (this.root.value.compareTo(this.root.leftChild.value) > 0)
    		swap(this.root, this.root.leftChild);
    	setUnsorted();
    }
    
    /**
     * Sets the value the boolean variable sorted to false for all nodes in the heap .
     */
    private void setUnsorted() {
    	Node node = this.root;
    	while (true) {
    		node.sorted = false;
    		Node next = levelOrderTraversal(node);
    		if (reverseLevelOrderTraversal(next) != node)
    			break;	// You've reached the end of the heap
    		node = next;
    	}
    }
}
