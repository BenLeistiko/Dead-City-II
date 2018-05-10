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
	
	public Barrier(double x, double y, double w, double h) {
		super(x, y, w, h);
		image = Main.resources.getImage("Barrier");
	}

	@Override
	public void draw(PApplet marker) {
		marker.image(image, (float)getX(), (float)getY(),(float)getWidth(),(float)getHeight());
	}

}
