package JUnitTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.textEditor.MutableCharacterMatrix;

class InsertCharacterTest {

	@Test
	void insertEnd() {

		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		int row = 1;
		int index = 3;

		matrix.insertCharacter(row, index, 'x');

		char[] confirm = new char[] { 'd', 'e', 'f', 'x' };

		// Prints the two arrays
		System.out.println("Inserting at end:");
		for (int i = 0; i < confirm.length; i++)
			System.out.println("confirm[" + i + "] - " + confirm[i] + "\tmatrix[" + i + "] - " + matrix.getRow(row)[i]);

		if (compareArrays(matrix.getRow(row), confirm))
			assert (true);
		else
			fail("Inserting at end failure - the two arrays do not match");
	}
	
	@Test
	void insertMiddle() {

		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		int row = 1;
		int index = 1;

		matrix.insertCharacter(row, index, 'x');

		char[] confirm = new char[] { 'd', 'x', 'e', 'f'};

		// Prints the two arrays
		System.out.println("Inserting in middle:");
		for (int i = 0; i < confirm.length; i++)
			System.out.println("confirm[" + i + "] - " + confirm[i] + "\tmatrix[" + i + "] - " + matrix.getRow(row)[i]);

		if (compareArrays(matrix.getRow(row), confirm))
			assert (true);
		else
			fail("Inserting at end failure - the two arrays do not match");
	}
	
	@Test
	void insertStart() {

		char[][] test = new char[][] { { 'a', 'b', 'c' }, { 'd', 'e', 'f' }, { 'g', 'h', 'i' } };
		MutableCharacterMatrix matrix = new MutableCharacterMatrix(test);

		int row = 1;
		int index = 0;

		matrix.insertCharacter(row, index, 'x');

		char[] confirm = new char[] {'x', 'd', 'e', 'f'};

		// Prints the two arrays
		System.out.println("Inserting at start:");
		for (int i = 0; i < confirm.length; i++)
			System.out.println("confirm[" + i + "] - " + confirm[i] + "\tmatrix[" + i + "] - " + matrix.getRow(row)[i]);

		if (compareArrays(matrix.getRow(row), confirm))
			assert (true);
		else
			fail("Inserting at end failure - the two arrays do not match");
	}

	boolean compareArrays(char[] a, char[] b) {
		for (int i = 0; i < a.length; i++)
			if (a[i] != b[i])
				return false;
		return true;
	}

}
