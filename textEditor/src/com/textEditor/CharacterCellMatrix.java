package com.textEditor;

import java.util.ArrayList;

public class CharacterCellMatrix {

	public ArrayList<ArrayList<CharacterCell>> cells = new ArrayList<ArrayList<CharacterCell>>();
	public int activeRow = -1;
	public int activeColumn = 0;
	
	public int firstVisibleColumn = 0;
	
	public final int TOP_MARGIN_SIZE = 1;
	public final int SPACES_PER_TAB = 4;
	public final int VISIBLE_CHARS_PER_LINE = 39;

	public CharacterCellMatrix(ArrayList<ArrayList<CharacterCell>> cells) {
		this.cells = cells;
		this.activeRow = -1;
		this.activeColumn = 0;
	}

	public CharacterCellMatrix() {
		this.addRow();
	}

	public void render(Screen screen) {
		for (int i = 0; i < cells.size(); i++) {
			if (cells.get(i).size() == 0)
				continue;
			for (int j = firstVisibleColumn; j < cells.get(i).size(); j++)
				cells.get(i).get(j).render(screen);
		}
	}
	
//	public void render(Screen screen) {
//		for (int i = 0; i < cells.size(); i++)
//			for (int j = 0; j < cells.get(i).size(); j++)
//				cells.get(i).get(j).render(screen);
//	}


	public void addRow() {
		this.cells.add(new ArrayList<CharacterCell>());
		activeRow++;
		activeColumn = 0;
		
	}

	public void addColumn(int row, char c) {
		this.cells.get(row).add(new CharacterCell(getActiveX(), getActiveY(), c));
		activeColumn++;
		if (activeColumn > VISIBLE_CHARS_PER_LINE)
			scroll(-Font.FONT_WIDTH);
		System.out.println("Active Row: " + activeRow + " Active Column: " + activeColumn);
	}

	public void addColumn(int row) {
		this.cells.get(row).add(null);
		activeColumn++;
	}

	public void insertCell(int row, int column, char c) {
		this.addColumn(row);
	}

	public CharacterCell getActiveCell() {
		return this.cells.get(this.activeRow).get(this.activeColumn);
	}

	public void keyTyped(char c) {
		this.addColumn(activeRow, c);
	}

	
	// This method is jacked up
	public void backspace() {
		// Old code, new stuff seems to be working but this is here just in case 
//		if (this.cells.get(activeRow).size() != 0)
//			this.cells.get(activeRow).remove(activeColumn - 1);
		
		if (activeRow == 0 && activeColumn == 0) return;
		
		if (this.cells.get(activeRow).size() == 0) {
			System.out.println("empty line");
			this.cells.remove(cells.get(activeRow));
		}
		else if (this.cells.get(activeRow).size() > VISIBLE_CHARS_PER_LINE) {
			this.cells.get(activeRow).remove(activeColumn - 1);
			scroll(Font.FONT_WIDTH);
		}
		else
			this.cells.get(activeRow).remove(activeColumn - 1);
		
		// Handle reassignment of activeRow and activeColumn
		if (activeColumn == 0) {
			if (activeRow > 0) {
				this.activeColumn = this.cells.get(--activeRow).size();
			}
		}
		else
			activeColumn--;
		System.out.println("Active Row: " + activeRow + " Active Column: " + activeColumn);
	}
	
	public void newLine() {
		addRow();
	}
	
	public void tab() {
		for (int i = 0; i < SPACES_PER_TAB - (activeColumn % 4); i++)
			keyTyped(' ');
	}
	
	public void scroll(int distance) {
		if (distance > 0)
			firstVisibleColumn--;
		else
			firstVisibleColumn++;
		for (int i = 0; i < this.cells.size(); i++)
			for (int j = 0; j < this.cells.get(i).size(); j++)
				this.cells.get(i).get(j).shift(distance);
		
		System.out.println("First Visible Column: " + firstVisibleColumn);
	}
	
	public void controlC() {
		System.out.println("Ctrl + C!");
	}

	public void up() {
		if (activeRow != 0)
			activeRow--;
	}

	public void down() {
		if (activeRow != this.cells.size() - 1)
			activeRow++;
	}

	public void left() {
		if (activeColumn != 0)
			activeColumn--;
	}

	public void right() {
		if (activeColumn != this.cells.get(activeRow).size())
			activeColumn++;
	}
	
	public void skipWord() {
		
	}

	public void setActiveCell(int activeRow, int activeColumn) {
		this.activeRow = activeRow;
		this.activeColumn = activeColumn;
	}

	public int getActiveRow() {
		return activeRow;
	}

	public void setActiveRow(int activeRow) {
		this.activeRow = activeRow;
	}

	public int getActiveColumn() {
		return activeColumn;
	}

	public void setActiveColumn(int activeColumn) {
		this.activeColumn = activeColumn;
	}

	/**
	 * In the event that text is inputted that causes the length of a given row of text to be larger
	 * than the amount of characters the screen can display in a single line, the active X should be 
	 * calculated to be at the end of the line, or at the position of the last displayable character
	 * of that line. 
	 * 
	 * Moving the cursor to right when it is already sitting at the last column on the line shouldn't
	 * change the active X. However, moving it to the left when it is sitting to the far right should
	 * change the active X. 
	 * 
	 * @return
	 */
	public int getActiveX() {
		
		//return ((Font.FONT_WIDTH) * activeColumn) + 2;
		
//		int position = ((Font.FONT_WIDTH) * activeColumn) + 2;
//		if ((activeColumn / VISIBLE_CHARS_PER_LINE) > 1)
//			return ((Font.FONT_WIDTH) * VISIBLE_CHARS_PER_LINE) + 2;
		return (activeColumn - firstVisibleColumn) * Font.FONT_WIDTH + 2; 
	}

	public int getActiveY() {
		return (Font.FONT_HEIGHT + 2) * activeRow;
	}

}
