package sprites;

import interfaces.Clickable;
import interfaces.Typeable;
import processing.core.PApplet;

public class Hero extends Creature implements Clickable, Typeable {

	public Hero(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	@Override
	public void keyPressed(PApplet marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(PApplet marker) {
		// TODO Auto-generated method stub
		
	}

}
