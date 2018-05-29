package items;

import java.awt.geom.Rectangle2D.Double;

import interfaces.Drawable;
import processing.core.PApplet;
import scenes.Scene;

/**
 * To be implemented.  Heros would need this to survive a day.
 * @author Ben
 * @version 5/16/18
 */
public class Supplies implements Drawable {

	private boolean remove;

	public void draw(PApplet marker) {

	}

	public boolean shouldRemove() {
		return remove;
	}

	public Double getHitBox() {
		return null;
	}

	@Override 
	public void act(Scene s) {
		// TODO Auto-generated method stub
		
	}

}
