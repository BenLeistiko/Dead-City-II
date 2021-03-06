package sprites;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;

import gamePlay.BetterSound;
import gamePlay.Main;
import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;
import processing.core.PImage;
import scenes.Scene;

/**
 * Something that a gun generates.  Deals damage to damageables.
 * @author ben
 * @version 5/21/18
 *
 */
public class Bullet extends MovingSprite implements Destructive {

	private double damage;
	private PImage image;
	private boolean alive;
	private final int dir;



	public Bullet(double x, double y, double w, double h, double damage, int dir) {
		super(x, y, w, h);
		this.damage = damage;
		image = Main.resources.getImage("Bullet");
		alive = true;
		this.dir = dir;
		BetterSound b = new BetterSound(Main.resources.getSound("Shoot"), true,false);

	}

	public void act(Scene scene) {
		if(detectHit(scene.getWorldlyThings(),(Hero)scene.getFocusedSprite())) {
			setAlive(false);
		}
		setvY(getvY() + Main.GRAVITY);
		moveByAmount(getvX(),getvY());
	}



	/**can only detect hits if alive because the method is called in draw(PApplet marker)
	 * 
	 * @param worldlyThings damageables in the world with which to check the collisions
	 * @param h Hero to give XP to
	 * @return true if the bullet has hit something false otherwise
	 * @post if the bullet does hit something, the proper amount of damage is done to the damageable if the thing hit is damageable
	 */
	private boolean detectHit(ArrayList<Sprite> worldlyThings, Hero h) {

		for(Sprite s: worldlyThings) {
			if(s!=this && !(s instanceof Hero)) {
				if(this.intersects(s.getHitBox())) {//if intersects with something
					if(s instanceof Damageable) {//if damageable do damage
						Damageable damageableSprite = ((Damageable) s);
						double dmg = damageableSprite.takeDamage(getDamage()); 
						h.experience(dmg);
					}

					return true;//still means it hit something
				}
			}
		}
		return false;

	}

	/**only draws bullets/detects collisions with bullets and other objects if the bullet is alive
	 * 
	 */
	public void draw(PApplet marker) {
		if(alive) {
			marker.pushMatrix();
			marker.scale(-(float)getDir(), 1f);		
			marker.image(image, (getDir() == 1)? -(float)getX():-getDir()*(float)getX()+(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());
			marker.popMatrix();
		}
	}

	//public void setDir(int dir) {
	//	this.dir = dir;
	//}

	public int getDir() {
		return dir;
	}

	public void dealDamage(Damageable d) {
		d.takeDamage(this.getDamage());
	}


	public double getDamage() {
		return damage;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public boolean isAlive() {
		return alive;
	}

	public boolean shouldRemove() {
		return !alive;
	}




}
