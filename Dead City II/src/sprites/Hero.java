package sprites;

import java.util.ArrayList;

import interfaces.Clickable;
import interfaces.Typeable;
import items.Weapon;
import processing.core.PApplet;

public class Hero extends Creature implements Clickable, Typeable {

	ArrayList<Integer> keysPressed;

	public Hero(int x, int y, int w, int h/*, ArrayList<Sprite> collision*/) {
		super(x, y, w, h);
	}

	public void keyPressed(PApplet marker) {
		keysPressed.add(marker.keyCode);		
	}

	public void keyReleased(PApplet marker) {
		keysPressed.remove(marker.keyCode);
	}

	public void draw(PApplet marker) {
	
		if(keysPressed.contains(87)) {//w

		}
		if(keysPressed.contains(65)) {//a

		}
		if(keysPressed.contains(83)) {//s

		}
		if(keysPressed.contains(68)) {//d

		}

		super.draw(marker);
	}



}
