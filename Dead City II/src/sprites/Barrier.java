package sprites;

import gamePlay.DrawingSurface;
import gamePlay.Main;
import processing.core.PApplet;
import processing.core.PImage;
/**
 * An rectangle that can't take damage but can collide with sprites. 
 * @author bleistiko405
 *
 */
public class Barrier extends Sprite {

	private PImage image; 
	private boolean shouldBeRemoved;
	private double textureHeight,textureWidth;
	
	public Barrier(double x, double y, double w, double h, double textureH, double textureW, PImage texture) {
		super(x, y, w, h);
		image = texture;
		shouldBeRemoved = false;
		textureHeight = textureH;
		textureWidth = textureW;
	}

	@Override
	public void draw(PApplet marker) {
		for(int y = 0; y < super.getHeight()/textureHeight; y++) {
			for(int x = 0; x < super.getWidth()/textureWidth;x++) {
				marker.image(image, (float)(getX() + x*textureWidth), (float)(getY()+ y*textureHeight),(float)textureWidth,(float)textureHeight);
			}
		}
		
		
	}

	public boolean shouldRemove() {
		return shouldBeRemoved;
	}

	public void remove() {
		shouldBeRemoved = true;
	}
	
}
