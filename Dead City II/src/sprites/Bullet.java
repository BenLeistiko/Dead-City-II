package sprites;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.DrawingSurface;
import gamePlay.Main;
import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;
import processing.core.PImage;


public class Bullet extends MovingSprite implements Destructive {

	private double damage;
	private PImage image;
	private ArrayList<Sprite> sprites;
	private boolean alive;
	private final int dir;


	public Bullet(double x, double y, double w, double h, double damage, ArrayList<Sprite> sprites,int dir) {
		super(x, y, w, h);
		this.damage = damage;
		image = Main.resources.getImage("Bullet");
		this.sprites = sprites;
		alive = true;
		this.dir = dir;
	}

	public void act() {
		setvY(getvY() + Main.GRAVITY);

		moveByAmount(getvX(),getvY());
	}



	/**can only detect hits if alive because the method is called in draw(PApplet marker)
	 * 
	 * @param worldlyThings damageables in the world with which to check the collisions
	 * @return true if the bullet has hit something false otherwise
	 * @post if the bullet does hit something, the proper amount of damage is done to the damageable if the thing hit is damageable
	 */
	public boolean detectHit() {

		for(Sprite s: sprites) {
			if(!(s instanceof Hero)) {
				if(this.intersects(s.getHitBox())) {//if intersects with something
					if(s instanceof Damageable) {//if damageable do damage
						Damageable damageableSprite = ((Damageable) s);
						damageableSprite.takeDamage(getDamage());
						System.out.println("damaging?");
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
			//marker.image(image, (float)getX(),(float)getY(),(float)getWidth(),(float)getHeight());
			marker.popMatrix();
			
			if(detectHit()) {
				setAlive(false);
			}
		}
		act();
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



}
