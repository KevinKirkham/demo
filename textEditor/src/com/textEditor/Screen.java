package com.textEditor;

public class Screen {
	
	private int maxVisibleRows, maxVisibleColumns;
	private int rowSpacer, colSpacer;
	public  int[] pixels;
	private int width;
	private int height;
	
	public Screen(int[] pixels, int width, int height, int rowSpacer, int colSpacer) {
		this.pixels = pixels;
		this.width = width; 
		this.height = height;
		this.rowSpacer = rowSpacer;
		this.colSpacer = colSpacer;
		this.maxVisibleColumns = width / Font.FONT_WIDTH;
		this.maxVisibleRows = height / Font.FONT_HEIGHT;
	}
	
	public void renderBackground() {
		for (int y = 0; y < this.height; y++) 
			for (int x = 0; x < this.width; x++)
				this.pixels[(y * this.width) + x] = 0;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}
	
	public int getColSpacer() {
		return this.colSpacer;
	}
	
	public int getRowSpacer() {
		return this.rowSpacer;
	}

	public int getMaxVisibleRows() {
		return maxVisibleRows;
	}

	public void setMaxVisibleRows(int maxVisibleRows) {
		this.maxVisibleRows = maxVisibleRows;
	}

	public int getMaxVisibleColumns() {
		return maxVisibleColumns;
	}

	public void setMaxVisibleColumns(int maxVisibleColumns) {
		this.maxVisibleColumns = maxVisibleColumns;
	}

	public void setRowSpacer(int rowSpacer) {
		this.rowSpacer = rowSpacer;
	}

	public void setColSpacer(int colSpacer) {
		this.colSpacer = colSpacer;
	}
	
}
