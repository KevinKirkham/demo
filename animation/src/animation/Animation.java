package animation;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * This class contains all data and behaviors pertaining to an animation that a shape can have. It 
 * is meant to be part of the composition of a Shape object.
 * @author Kevin Kirkham
 *
 */
public class Animation {
	
	/**
	 * Acts as a record of the last time an advancement in the animation happened.
	 */
	private long timer;
	
	/**
	 * The number of milliseconds to wait before advancing displaying the next sprite in the animation.
	 */
	private int delay;
	
	/**
	 * The URL object that links to the spritesheet.
	 */
	private URL url;
	
	/**
	 * The height and width of each sprite involved in the animation.
	 */
	private int height, width;
	
	/**
	 * Array of sprites used in the animation.
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
		this.timer = System.currentTimeMillis();
		
		loadSprites();	// Load the sprites found at the URL into the sprites array
	}
	
	/**
	 * This constructor is used when an animation needs to be created for a wrap companion object,
	 * in the case that the sprites array is already filled with the correct sprites and the delay 
	 * has previously been defined.
	 * @param delay Duration of time that each frame is displayed for. 
	 * @param sprites Sprites involved in the animation.
	 */
	public Animation(int delay, BufferedImage[] sprites, long timer) {
		this.delay = delay;
		this.sprites = sprites;
		this.timer = timer;
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
	 * If the delay for the Shape has passed then we can advance the sprite counter by one to
	 * display the next sprite. Each Shape can be configured to have it's own delay - some animations
	 * may look nicer at different speeds.
	 * 
	 * The variable timer indicates the time at which a frame update last occurred. The timer is set to
	 * now after the sprite counter is incremented so that even if the interval between now and the 
	 * last time a frame advancement occurred is several times larger than the delay, only one frame
	 * advancement happens rather than as many frame advancements can fit in that large time interval.
	 * Such a situation could occur when the last update value stays stagnant but current time keeps
	 * advancing, like when the user pauses the program.
	 */
	public void advance() {
		long now = System.currentTimeMillis();
		if (now - this.timer > this.delay) {
			incrementSpriteCounter();
			this.timer = now;
		}
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

	public long getTimer() {
		return timer;
	}

	public void setTimer(long spriteTimer) {
		this.timer = spriteTimer;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}
	
}
