package com.textEditor;

public class CharacterCell {
	
	private int x, y;
	private char value;
	private int[] sprite;

	public CharacterCell(int x, int y, char value) {
		this.x = x;
		this.y = y;
		this.value = value;
		if (this.value == ' ') 
			setBlackSprite();
		else
			this.sprite = FontTable.getSprite(value);
	}
	
	public CharacterCell(int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = null;
	}
	
	public void render(Screen screen) {
		int spritePointer = 0;
		for (int yTrav = y; yTrav < y + Font.FONT_HEIGHT; yTrav++) 
			for (int xTrav = x; xTrav < x + Font.FONT_WIDTH; xTrav++)
				screen.pixels[(yTrav * screen.getWidth()) + xTrav] = FontTable.getSprite(value)[spritePointer++];
	}
	
	public void setBlackSprite() {
		this.sprite = new int[Font.FONT_HEIGHT * Font.FONT_WIDTH];
		for (int y = 0; y < Font.FONT_HEIGHT; y++)
			for (int x = 0; x < Font.FONT_WIDTH; x++)
				this.sprite[(y * Font.FONT_WIDTH) + x] = 0;
	}
	
}
