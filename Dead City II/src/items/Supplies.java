package items;

import java.awt.geom.Rectangle2D.Double;

import interfaces.Drawable;
import processing.core.PApplet;
import scenes.Scene;

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
