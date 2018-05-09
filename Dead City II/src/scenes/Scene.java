package scenes;

import java.util.ArrayList;


import interfaces.*;
import processing.core.PApplet;


public abstract class Scene implements Drawable, Typeable, Clickable {

	private ArrayList<Drawable> drawables;;
	private ArrayList<Typeable> typeables;
	private ArrayList<Clickable> clickables;
	
	
	public void draw(PApplet marker) {
		for(Drawable d:drawables) {
			d.draw(marker);
		}
	}
	
	
}
