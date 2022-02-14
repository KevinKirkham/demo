package animation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * This class contains all data and behaviors pertaining to an animation that a shape can have. It 
 * is meant to be part of the composition of a Shape object.
 * @author Kevin Kirkham
 *
 */
public class Animation {
	
	/**
	 * The timer object that controls the speed at which the animation changes frames.
	 */
	private Timer timer;
	
	/**
	 * How long the timer should wait before advancing. This is used as a way to meter 
	 * for how long each frame of the animation is displayed. 
	 */
	public int delay;
	
	/**
	 * The URL object that links to the spritesheet.
	 */
	private URL url;
	
	/**
	 * The height and width of each sprite involved in the animation.
	 */
	private int height, width;
	
	/**
	 * Sprites used in the animation.
	 */
	private BufferedImage[] sprites;
	
	/**
	 * A counter indicating which element of the sprites array should be displayed 
	 * at any given point in time.
	 */
	private int spriteCounter;
	
	/**
	 * For creation of an Animation object from scratch, to be used when a shape is
	 * being created for the first time as opposed to being created out of necessity
	 * for a wrap companion shape.
	 * 
	 * @param delay Duration of time that each frame is displayed for.
	 * @param spriteCount How many frames of animation will be present in the animation.
	 * @param spriteSheet Path to the spritesheet for this animation.
	 * @param height Height of each sprite in this animation.
	 * @param width Width of each sprite in this animation.
	 */
	public Animation(int delay, int spriteCount, String spriteSheet, int height, int width) {
		this.delay = delay;
		this.sprites = new BufferedImage[spriteCount];
		this.url = this.getClass().getResource(spriteSheet);
		this.height = height;
		this.width = width;
		
		loadSprites();	// Load the sprites found at the URL into the sprites array
		setTimer();		// Establish and start the timer
	}
	
	/**
	 * This constructor is used when an animation needs to be created for a wrap companion object,
	 * in the case that the sprites array is already filled with the correct sprites and the delay 
	 * has previously been defined.
	 * @param delay Duration of time that each frame is displayed for. 
	 * @param sprites Sprites involved in the animation.
	 */
	public Animation(int delay, BufferedImage[] sprites) {
		this.delay = delay;
		this.sprites = sprites;
		
		setTimer();		// Start the timer 
	}
	
	/**
	 * Grabs the sprites out of the spritesheet and loads them into the sprite array.
	 */
	public void loadSprites() {
		try {
			BufferedImage spriteSheet = ImageIO.read(this.url);
			for (int i = 0, x = 0; i < this.sprites.length; i++, x += width)
				this.sprites[i] = spriteSheet.getSubimage(x, 0, width, height);
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	/**
	 * Establishes and begins the timer that determines the speed at which the animation will advance.
	 */
	void setTimer() {
		this.timer = new Timer(delay, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				incrementSpriteCounter();
			}
		});
		this.timer.start();
	}

	/**
	 * Increments the sprite counter in a circular way - in the event that the counter is pointing at
	 * the last sprite in the list, the counter is reset to 0.
	 */
	public void incrementSpriteCounter() {
		if (this.spriteCounter == this.sprites.length - 1)
			this.spriteCounter = 0;
		else spriteCounter++;
	}
	
	public int getDelay() {
		return this.delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public BufferedImage[] getSprites() {
		return sprites;
	}

	public void setSprites(BufferedImage[] sprites) {
		this.sprites = sprites;
	}

	public int getSpriteCounter() {
		return spriteCounter;
	}

	public void setSpriteCounter(int spriteCounter) {
		this.spriteCounter = spriteCounter;
	}
	
}
