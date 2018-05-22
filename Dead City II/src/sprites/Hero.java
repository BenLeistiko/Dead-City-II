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
 * @author ben
 * @version 5/21/18
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
	private int upgradeTokens;

	private Point clickPoint;
	private boolean clicked;
	
	ArrayList<Barrier> spawnedBarries;
	private int maxBarriers;


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
		initialXPCondition =150;
		upgradeTokens = 0;
		clicked = false;
		spawnedBarries = new ArrayList<Barrier>();
		maxBarriers = 4;
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
		this.clickPoint = clickPoint;
		if(button == PApplet.RIGHT)
			this.clicked = true;
	}

	public void mouseReleased(Point clickPoint, int button) {
		mouseButtonsPressed.remove(new Integer(button));
		hasClicked = false;
		this.clickPoint = null;
	}

	public void draw(PApplet marker) {
	
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
		if(this.clickPoint != null && clicked) {
			Barrier dirt = new DamageableBarrier(clickPoint.getX()-50,clickPoint.getY()-50,100,100,100,100,"Force",10,0);
			if(!dirt.colliedsWithMovingSprite(s.getWorldlyThings()) && this.getCenter().distance(clickPoint) < 500) {
				s.add(dirt);
				if(this.spawnedBarries.size() >= this.maxBarriers) {
					spawnedBarries.get(0).remove();
					spawnedBarries.remove(0);
				}
				spawnedBarries.add(dirt);
			}
			this.clicked = false;
		}
		for(int i = 0; i < spawnedBarries.size(); i++) {
			if(spawnedBarries.get(i).shouldRemove()) {
				spawnedBarries.remove(i);
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

	/**adds xp to be gained and levels up the Hero if he has enough xp
	 * if he levels up his upgradeTokens increases by 1
	 * 
	 * @param xp xp that should be added
	 */
	public void experience(double xp) {
		this.xp+=xp;
		if(this.xp > Math.pow(1.2, level)*initialXPCondition) {
			level++;
			this.xp=0;
			upgradeTokens++;
		}
	}

	public int getLevel() {
		return level;
	}

	public double getXP() {
		return xp;
	}

	public double getTotalXPToNextLevel() {
		return Math.pow(1.2, level)*initialXPCondition;
	}
	
	public double getXPToNextLevel() {
		return Math.pow(1.2, level)*initialXPCondition-xp;
	}
	
	public int getUpgradeTokens() {
		return upgradeTokens;
	}
	
	public void setUpgradeTokens(int num) {
		upgradeTokens = num;
	}
	
	public void incrementUpgradeTokens() {
		upgradeTokens++;
	}
	public void decrementUpgradeTokens() {
		upgradeTokens--;
	}
	
	public void increaseDamage(double amount) {
		weapon.increaseDamage(amount);
	}
	
	public double getDamage() {
		return weapon.getDamage();
	}


}