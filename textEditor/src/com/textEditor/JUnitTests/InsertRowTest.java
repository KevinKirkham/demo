package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.textEditor.MutableCharacterMatrix;

class InsertRowTest {

	@Test
	void insertStart() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		char[] newRow = new char[] { 'x', 'y', 'z' };
		matrix.insertRow(0, newRow);

		char[][] confirm = new char[][] { { 'x', 'y', 'z' }, { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };

		// Printing the two matrices for a visual comparison
		System.out.println("Inserting {x, y, z} at the start of the matrix");
		for (int i = 0; i < confirm.length; i++)
			for (int j = 0; j < confirm[i].length; j++)
				System.out.println("confirm[" + i + "][" + j + "] - " + confirm[i][j] + "\tdata[" + i + "][" + j
						+ "] - " + matrix.getData()[i][j]);

		if (compare2DArrays(matrix.getData(), confirm))
			assert (true);
		else
			fail("Insert at start fail - the two matrices do not match");

	}

	@Test
	void insertEnd() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		char[] newRow = new char[] { 'x', 'y', 'z' };
		matrix.insertRow(3, newRow);

		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' }, { 'x', 'y', 'z' } };

		// Printing the two matrices for a visual comparison
		System.out.println("Inserting {x, y, z} at the end of the matrix");
		for (int i = 0; i < confirm.length; i++)
			for (int j = 0; j < confirm[i].length; j++)
				System.out.println("confirm[" + i + "][" + j + "] - " + confirm[i][j] + "\tdata[" + i + "][" + j
						+ "] - " + matrix.getData()[i][j]);

		if (compare2DArrays(matrix.getData(), confirm))
			assert (true);
		else
			fail("Insert at start fail - the two matrices do not match");

	}

	@Test
	void insertMiddle() {
		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		char[] newRow = new char[] { 'x', 'y', 'z' };
		matrix.insertRow(1, newRow);

		char[][] confirm = new char[][] { { 'a', 'b', 'c' }, { 'x', 'y', 'z' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };

		// Printing the two matrices for a visual comparison
		System.out.println("Inserting {x, y, z} in the middle of the matrix");
		for (int i = 0; i < confirm.length; i++)
			for (int j = 0; j < confirm[i].length; j++)
				System.out.println("confirm[" + i + "][" + j + "] - " + confirm[i][j] + "\tdata[" + i + "][" + j
						+ "] - " + matrix.getData()[i][j]);

		if (compare2DArrays(matrix.getData(), confirm))
			assert (true);
		else
			fail("Insert at start fail - the two matrices do not match");

	}

	// Loop through each element of each row ensuring that the two matrices are the
	// same
	boolean compare2DArrays(char[][] a, char[][] b) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[i].length; j++)
				if (a[i][j] != b[i][j])
					return false;
		return true;
	}

}
