package com.textEditor;

import java.util.HashMap;

public class FontTable {

	public static HashMap<Character, int[]> map = new HashMap<Character, int[]>();

	public FontTable(HashMap<Character, int[]> map ) {
		this.map = map;
	}
	
	public FontTable() {
		FontTable.upperCase();
		FontTable.lowerCase();
		FontTable.numbers();
		FontTable.special();
	}
	
	public static int[] getSprite(char c) {
		try {
			return map.get(new Character(c));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return new int[0];
	}

	public static void upperCase() {
		int fontPointer = 0;
		for (int i = 65; i <= 90; i++)
			map.put((char) i, Font.uppercase[fontPointer++]);
	}

	public static void lowerCase() {
		int fontPointer = 0;
		for (int i = 97; i <= 122; i++)
			map.put((char) i, Font.lowercase[fontPointer++]);
	}

	public static void numbers() {
		System.out.println("Numbers");
		int fontPointer = 0;
		for (int i = 48; i < 58; i++)
			map.put((char) i, Font.numbers[fontPointer++]);
		
		map.put((char) 92, Font.special[fontPointer++]);
		map.put((char) 94, Font.special[fontPointer++]);
	}
	
	public static void special() {
		int fontPointer = 0;
		
		for (int i = 33; i < 47; i++) {
			map.put((char) i, Font.special[fontPointer++]);
			System.out.println("Putting " + (char) i);
		}
		
		for (int i = 58; i < 63; i++)
			map.put((char) i, Font.special[fontPointer++]);
		
		for (int i = 123; i < 125; i++)
			map.put((char) i, Font.special[fontPointer++]);
		
		map.put((char) 91, Font.special[fontPointer++]);
		map.put((char) 93, Font.special[fontPointer++]);
		
		// Space
		int[] black = new int[Font.FONT_HEIGHT * Font.FONT_WIDTH];
		for (int y = 0; y < Font.FONT_HEIGHT; y++)
			for (int x = 0; x < Font.FONT_WIDTH; x++)
				black[(y * Font.FONT_WIDTH) + x] = 0;
		map.put((char) 32, black);
	}
	
	public static void render(Screen screen, char value) {
		int sy = 100;
		int sx = 156;
		int sc = 0;
		for (int y = sy; y < sy + Font.FONT_HEIGHT; y++) 
			for (int x = sx; x < sx + Font.FONT_WIDTH; x++)
				screen.pixels[(y * screen.getWidth()) + x] = FontTable.getSprite(value)[sc++];
	}

}
