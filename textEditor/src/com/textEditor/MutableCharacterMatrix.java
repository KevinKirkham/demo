package com.textEditor;

/**
 * This is an abstraction of a mutable 2D array of characters for which any cell
 * can be altered or removed. All of the character data that is entered,
 * removed, or otherwise edited by the user is held here, and multiple instances
 * of these can exist should the need for multiple records of character matrices
 * arise.
 * 
 * The context of this class existing in this project is that this will hold the
 * data that the user enters into the program in an organized way. Another class
 * will be written to traverse the data held within this one so that the data
 * and the method of retrieving that data are separated.
 * 
 * @author Kevin Kirkham
 *
 */
public class MutableCharacterMatrix {

	private char[][] data;

	public MutableCharacterMatrix() {
		// Initialize the character matrix when you create this object
		this.data = new char[1][0];
	}

	public MutableCharacterMatrix(char[][] data) {
		this.data = data;
	}

	/**
	 * Inserts a character into a row of the matrix.
	 * 
	 * @param row   the original array
	 * @param c     the character to be inserted
	 * @param index the index that the inserted element is to occupy after insertion
	 * @return the original array with the character added to it
	 */
	public char[] insertCharacter(char[] row, char c, int index) {
//		System.out.println("row size: " + row.length);
//		System.out.println("inserting \'" + c + "\' at index " + index);
		char[] newRow = new char[row.length + 1];

		// Copy each element preceding the specified index
		int i = 0;

		for (; i < index; i++)
			newRow[i] = row[i];

		// Add in the index
		newRow[i] = c;

		// Copy the remaining elements into the new array
		for (; i < row.length; i++)
			newRow[i + 1] = row[i];

		return newRow;
	}

	/**
	 * Inserts a row into the matrix.
	 * 
	 * @param matrix the existing matrix
	 * @param newRow the row of characters to be added
	 * @param index  the index the new row will occupy once inserted
	 * @return the original matrix with the row added to it
	 */
	public char[][] insertRow(char[][] matrix, char[] newRow, int index) {

		char[][] newMatrix = new char[matrix.length + 1][];

		// Copy each element preceding the specified index
		int i = 0;
		for (; i < index; i++)
			newMatrix[i] = matrix[i];

		// Add in the index
		newMatrix[i] = newRow;

		// Copy the remaining elements into the new array
		for (; i < matrix.length; i++)
			newMatrix[i + 1] = matrix[i];

		return newMatrix;
	}

	/**
	 * Deletes a row of the matrix
	 * 
	 * @param matrix the matrix from which the row will be deleted
	 * @param index  the index of the row that is to be deleted
	 * @return the matrix with the specified row removed
	 */
	public char[][] deleteRow(char[][] matrix, int index) {
		if (matrix.length == 0)
			throw new IndexOutOfBoundsException("Matrix doesn't have any elements!");

		char[][] newMatrix = new char[matrix.length - 1][];

		// Copy each element preceding the specified index
		int i = 0; // tracking the original array
		for (; i < index; i++)
			newMatrix[i] = matrix[i];

		i++;

		// Copy the remaining elements into the new array
		for (; i < matrix.length; i++)
			newMatrix[i - 1] = matrix[i];

		return newMatrix;
	}

	/**
	 * Deletes a character from a single row of the matrix.
	 * 
	 * @param row   the row from which the character will be deleted
	 * @param index the index at which sits the character to be deleted
	 * @return row with the character removed from it
	 */
	public char[] deleteCharacter(char[] row, int index) {
		if (row.length == 0)
			throw new IndexOutOfBoundsException("Row " + index + " doesn't have any elements.");

		char[] newRow = new char[row.length - 1];

		// Copy each element preceding the specified index
		int i = 0; // tracking the original array
		for (; i < index; i++)
			newRow[i] = row[i];

		i++;

		// Copy the remaining elements into the new array
		for (; i < row.length; i++)
			newRow[i - 1] = row[i];

		return newRow;
	}

	/**
	 * Appends the contents of one row onto another row.
	 * 
	 * @param targetRow row to which the addition will be appended
	 * @param addition  row that will get appended onto the target row
	 * @return a row consisting of the contents of both the target row and the
	 *         addition
	 */
	public char[] appendRow(char[] targetRow, char[] addition) {
		char[] newRow = new char[targetRow.length + addition.length];

		for (int i = 0; i < targetRow.length; i++)
			newRow[i] = targetRow[i];

		for (int i = 0; i < addition.length; i++)
			newRow[targetRow.length + i] = addition[i];

		return newRow;
	}

	/**
	 * Returns the segment of the row between the two specified indices, inclusive
	 * on both indices. So you can pass in the same number for start and end and
	 * just the character at that index will be put into the segment.
	 * 
	 * @param row   the row from which the segment is to be taken
	 * @param start the starting index for the segment
	 * @param end   the ending index for the segment
	 * @return the segment of the row between the two indices
	 */
	public char[] rowSegment(int rowIndex, int start, int end) {
		char[] segment = new char[(end - start) + 1];

		for (int i = 0; i < segment.length; i++)
			segment[i] = this.data[rowIndex][i + start];

		return segment;
	}

	/**
	 * Truncates the specified row at the specified index.
	 * 
	 * @param rowIndex      the index of the row whose truncation is to be returned
	 * @param truncateIndex the index at which the truncation is to occur
	 * @return the specified row truncated at the specified truncation index
	 */
	public char[] truncateRow(int rowIndex, int truncateIndex) {
		char[] truncate = new char[truncateIndex];

		for (int i = 0; i < truncateIndex; i++)
			truncate[i] = this.data[rowIndex][i];

		return truncate;
	}

	/**
	 * Overwrites the data present in the specified column in the specified row with
	 * the specified data.
	 * 
	 * @param row    the row in which sits the target cell
	 * @param column the column in the row that is to be altered
	 * @param data   the value that is to be stored in the cell
	 */
	public void setCell(int row, int column, char data) {
		this.data[row][column] = data;
	}

	/**
	 * Returns the data stored at the specified row and column
	 * 
	 * @param row    the row in which the target cell sits
	 * @param column the column in which the target cell sits
	 * @return data held at the specified row and column
	 */
	public char getCharacter(int row, int column) {
		return this.data[row][column];
	}

	/**
	 * Returns the row located at the specified index
	 * 
	 * @param rowIndex index at which the requested row is located
	 * @return the requested row
	 */
	public char[] getRow(int rowIndex) {
		return this.data[rowIndex];
	}

	/**
	 * Sets the specified row equal to the supplied row.
	 * 
	 * @param rowIndex the row to be overwritten
	 * @param row      the data that is to overwrite the specified row
	 */
	public void setRow(int rowIndex, char[] row) {
		this.data[rowIndex] = row;
	}

	/**
	 * Returns the number of rows in the 2D array.
	 * 
	 * @return the number of rows in the 2D array.
	 */
	public int numRows() {
		return this.data.length;
	}

	/**
	 * Returns the length of a specified row.
	 * 
	 * @param row the index of the row of which the length is to be returned
	 * @return the length of the specified row
	 */
	public int rowSize(int row) {
		return this.data[row].length;
	}

	public boolean rowEmpty(char[] row) {
		if (row.length == 0)
			return true;
		return false;
	}

	public char[][] getData() {
		return this.data;
	}

	public void setData(char[][] data) {
		this.data = data;
	}

}