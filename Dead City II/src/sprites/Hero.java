package sprites;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import Menus.HUD;
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

	private ArrayList<Integer> keysPressed;
	private ArrayList<Integer> mouseButtonsPressed;
	private Weapon weapon;
	private HUD display; 

	public Hero(String animationKey, double x, double y, double w, double h, Weapon weapon,ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h, worldlyThings, animationKey);
		this.weapon = weapon;
		keysPressed = new ArrayList<Integer>();
		mouseButtonsPressed = new ArrayList<Integer>();
		display = new HUD();

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
		display.update(this);
		display.draw(marker);

		double vX = 0;
		if(keysPressed.contains(KeyEvent.VK_W)) {//w
			super.jump();
		}
		if(keysPressed.contains(KeyEvent.VK_A)) {//a
			vX = vX - super.getSpeed();
		}
		if(keysPressed.contains(KeyEvent.VK_S)) {//s

		}
		if(keysPressed.contains(KeyEvent.VK_D)) {//d
			vX = vX + super.getSpeed();
		}
		if(keysPressed.contains(KeyEvent.VK_SHIFT)  && isOnSurface()) {//shift
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
					attack();
					weapon.perform(super.getHitBox(), super.getDirection(), super.getWorldlyThings());
				} else {
					Main.resources.getSound("emptyClick").play();
				}
			}else {
				attack();
				weapon.perform(super.getHitBox(), super.getDirection(), super.getWorldlyThings());
			}
		}
		super.draw(marker);
	}

	public Weapon getWeapon() {
		return weapon;

	}

}