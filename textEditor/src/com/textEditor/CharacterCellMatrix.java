package com.textEditor;

import java.util.ArrayList;

public class CharacterCellMatrix {

	public ArrayList<ArrayList<CharacterCell>> cells = new ArrayList<ArrayList<CharacterCell>>();
	public int activeRow = -1;
	public int activeColumn = 0;

	public CharacterCellMatrix(ArrayList<ArrayList<CharacterCell>> cells) {
		this.cells = cells;
		this.activeRow = -1;
		this.activeColumn = -1;
	}

	public CharacterCellMatrix() {
		this.addRow();
	}

	public void render(Screen screen) {
		for (int i = 0; i < cells.size(); i++)
			for (int j = 0; j < cells.get(i).size(); j++)
				cells.get(i).get(j).render(screen);
	}

	public void addRow() {
		this.cells.add(new ArrayList<CharacterCell>());
		activeRow++;
	}

	public void addColumn(int row, char c) {
		this.cells.get(row).add(new CharacterCell(getActiveX(), getActiveY(), c));
		activeColumn++;
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

	public void backspace() {
		if (this.cells.get(activeRow).size() != 0)
			this.cells.get(activeRow).remove(activeColumn - 1);
		if (activeColumn == 0) {
			if (activeRow != 0) {
				this.activeRow--;
				this.activeColumn = this.cells.get(activeRow).size();
			}
		}
		else
			activeColumn--;
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

	public int getActiveX() {
		return ((Font.FONT_WIDTH) * activeColumn) + 2;
	}

	public int getActiveY() {
		return (Font.FONT_HEIGHT + 2) * activeRow;
	}

}
