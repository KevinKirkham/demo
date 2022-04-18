package com.textEditor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

	private class ActionKey {
		
		boolean pressed = false;

		public void toggle() {
			this.pressed = !pressed;
		}
	}

	public CharacterCellMatrix matrix;
	private ActionKey control = new ActionKey();
	private ActionKey shift = new ActionKey();
	private ActionKey up = new ActionKey();
	private ActionKey down = new ActionKey();
	private ActionKey left = new ActionKey();
	private ActionKey right = new ActionKey();

	public InputHandler(CharacterCellMatrix matrix) {	
		this.matrix = matrix;
	}

	public void keyTyped(KeyEvent e) {
		//System.out.println("key typed : " + e.getKeyChar());
		if (e.getKeyChar() == '\b') {
			matrix.backspace();
			return;
		}
		if (e.getKeyChar() == '\n') {
			matrix.newLine();
			return;
		}
		if (e.getKeyChar() == '\t') {
			matrix.tab();
			return;
		}
		
		// This doesn't work for recognizing control C
		if (e.getKeyChar() == 'c' && this.control.pressed) {
			matrix.controlC();
			return;
		}
		if (!control.pressed) matrix.keyTyped(e.getKeyChar());
	}

	public void keyPressed(KeyEvent e) {
		//System.out.println("Key Pressed : " + e.getKeyCode());
		if (e.getKeyCode() == KeyEvent.VK_CONTROL && !this.control.pressed)
			this.control.toggle();
		if (e.getKeyCode() == KeyEvent.VK_SHIFT && !this.shift.pressed)
			this.shift.toggle();
		if (e.getKeyCode() == KeyEvent.VK_UP && !this.up.pressed)
			this.up.toggle();
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !this.down.pressed)
			this.down.toggle();
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !this.left.pressed)
			this.left.toggle();
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !this.right.pressed)
			this.right.toggle();
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_CONTROL && this.control.pressed)
			this.control.toggle();
		if (e.getKeyCode() == KeyEvent.VK_SHIFT && this.shift.pressed)
			this.shift.toggle();
		if (e.getKeyCode() == KeyEvent.VK_UP && this.up.pressed)
			this.up.toggle();
		if (e.getKeyCode() == KeyEvent.VK_DOWN && this.down.pressed)
			this.down.toggle();
		if (e.getKeyCode() == KeyEvent.VK_LEFT && this.left.pressed)
			this.left.toggle();
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && this.right.pressed)
			this.right.toggle();
	}

}