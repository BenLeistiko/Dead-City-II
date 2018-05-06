package sprites;

import interfaces.Clickable;
import interfaces.Typeable;
import items.Weapon;
import processing.core.PApplet;

public class Hero extends Creature implements Clickable, Typeable {

	public Hero(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void keyPressed(PApplet marker) {
		if(marker.keyCode == 68) {
			if(calculateVelocity() <10) {
				
			}
			
		}
	}

	public void keyReleased(PApplet marker) {

		
	}

}
