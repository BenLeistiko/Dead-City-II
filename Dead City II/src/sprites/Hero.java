package sprites;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.Clickable;
import interfaces.Typeable;
import items.RangedWeapon;
import items.Weapon;
import processing.core.PApplet;

/**
 * Any character that the player controls
 * @author bleistiko405
 *
 */
public class Hero extends Creature implements Clickable, Typeable {

	ArrayList<Integer> keysPressed;
	ArrayList<Integer> mouseButtonsPressed;
	Weapon weapon;

	public Hero(String animationKey, double x, double y, double w, double h, Weapon weapon,ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h, worldlyThings, animationKey);
		this.weapon = weapon;
		keysPressed = new ArrayList<Integer>();
		mouseButtonsPressed = new ArrayList<Integer>();

	}

	public void keyPressed(PApplet marker) {
		if(!keysPressed.contains(marker.keyCode))
			keysPressed.add(marker.keyCode);
	}

	public void keyReleased(PApplet marker) {
		keysPressed.remove(new Integer(marker.keyCode));
	}

	public void mousePressed(PApplet marker) {
		if(!mouseButtonsPressed.contains(marker.mouseButton))
			mouseButtonsPressed.add(marker.mouseButton);	
	}

	public void mouseReleased(PApplet marker) {
		mouseButtonsPressed.remove(new Integer(marker.mouseButton));
	}

	public void draw(PApplet marker) {

		double vX = 0;
		if(keysPressed.contains(87)) {//w
			super.jump();
		}
		if(keysPressed.contains(65)) {//a
			vX = vX - super.getSpeed();
		}
		if(keysPressed.contains(83)) {//s

		}
		if(keysPressed.contains(68)) {//d
			vX = vX + super.getSpeed();
		}
		if(keysPressed.contains(16)  && isOnSurface()) {//shift
			vX = vX*getSprintSpeed();
			super.setSprint(true);
		}else {
			super.setSprint(false);
		}
		if(keysPressed.contains(82)) {//r for reloading
			if(weapon instanceof RangedWeapon) {
				((RangedWeapon) weapon).reload();
			}
		}
		super.setvX(vX);

		if(mouseButtonsPressed.contains(37)) {//shooting/attacking
			if(weapon instanceof RangedWeapon) {
				RangedWeapon rw = ((RangedWeapon) weapon);
				if(rw.getCurrentAmmo()>0) {
					Main.resources.getSound("Shoot").play();
					attack();
					weapon.perform(super.getHitBox(), super.getDirection(), super.getWorldlyThings());
				}
			}else {
				attack();
				weapon.perform(super.getHitBox(), super.getDirection(), super.getWorldlyThings());
			}
		}
		super.draw(marker);
	}


}