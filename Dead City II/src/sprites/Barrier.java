package sprites;

import gamePlay.DrawingSurface;
import processing.core.PApplet;
import processing.core.PImage;

public class Barrier extends Sprite {

	private PImage image; 
	
	public Barrier(int x, int y, int w, int h) {
		super(x, y, w, h);
		image = DrawingSurface.resources.getImage("Barrier");
	}

	@Override
	public void draw(PApplet marker) {
		marker.image(image, (float)getX(), (float)getY(),(float)getWidth(),(float)getHeight());
		
	}

}
