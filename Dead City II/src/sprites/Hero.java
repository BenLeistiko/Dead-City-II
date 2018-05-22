package sprites;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import Menus.HUD;
import gamePlay.BetterSound;
import gamePlay.Main;
import interfaces.Clickable;
import interfaces.Typeable;
import items.RangedWeapon;
import items.Weapon;
import processing.core.PApplet;
import scenes.Scene;

/**
 * Any character that the player controls
 * @author bleistiko405
 *
 */
public class Hero extends Creature implements Clickable, Typeable {

	private ArrayList<Integer> keysPressed;
	private ArrayList<Integer> mouseButtonsPressed;
	private boolean hasClicked;
	private Weapon weapon;
	private boolean isAlive;

	private HUD display; 

	private double xp;
	private int level;
	private double initialXPCondition;


	public Hero(String animationKey, double x, double y, double w, double h, Weapon weapon) {
		super(x, y, w, h, animationKey);
		this.weapon = weapon;
		keysPressed = new ArrayList<Integer>();
		mouseButtonsPressed = new ArrayList<Integer>();
		isAlive = true;
		display = new HUD();
		hasClicked = false;
		level = 1;
		xp = 0;
		initialXPCondition =200;
	}



	public void keyPressed(PApplet marker) {
		if(!keysPressed.contains(marker.keyCode))
			keysPressed.add(marker.keyCode);
	}

	public void keyReleased(PApplet marker) {
		keysPressed.remove(new Integer(marker.keyCode));
	}

	public void mousePressed(Point clickPoint, int button) {
		if(!mouseButtonsPressed.contains(button))
			mouseButtonsPressed.add(button);			
	}

	public void mouseReleased(Point clickPoint, int button) {
		mouseButtonsPressed.remove(new Integer(button));
		hasClicked = false;
	}

	public void draw(PApplet marker) {
		System.out.println("Current XP: " + xp + " Current Level: "+ level);
		super.draw(marker);

	}

	public void act(Scene s) {
		super.act(s);
		if(super.getHealth()<=0) {
			isAlive=false;
		}
		//display.update(this);
		weapon.act();

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
			if(super.didRunOutOfStamina() ) {
				if(super.getStamina()>super.getMaxStamina()/4) {
					vX = vX*getSprintSpeed();
					super.setSprint(true);
					setRanOutOfStamina(false);
				}

			} else if(super.getStamina()>0) {
				vX = vX*getSprintSpeed();
				super.setSprint(true);
			}
		}else {
			super.setSprint(false);
		}

		if(keysPressed.contains(KeyEvent.VK_R)) {//r for reloading
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
					weapon.perform(this, super.getDirection(),s);
				} else {
					if(!hasClicked) {
						new BetterSound(Main.resources.getSound("EmptyClick"),true,false);
						hasClicked = true;
					}
				}
			}else {
				attack();
				weapon.perform(this, super.getDirection(),s);
			}
		}

	}
	public Weapon getWeapon() {
		return weapon;

	}
	public HUD getHUD() {
		return display;
	}

	public boolean isReloading() {
		if(weapon instanceof RangedWeapon) {
			RangedWeapon rw = ((RangedWeapon) weapon);
			return rw.isReloading();
		}else {
			return false;
		}
	}



	public boolean isAlive() {
		return isAlive;
	}



	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public boolean shouldRemove() {
		return false;
	}

	public void experience(double xp) {
		this.xp+=xp;
		if(this.xp > Math.pow(1.2, level)*initialXPCondition) {
			level++;
		}

	}


}