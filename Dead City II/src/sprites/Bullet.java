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


	public Bullet(double x, double y, double w, double h, double damage, ArrayList<Sprite> sprites) {
		super(x, y, w, h);
		this.damage = damage;
		image = DrawingSurface.resources.getImage("Bullet");
		this.sprites = sprites;
		alive = true;
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
		System.out.println("detecting collision"
				+ "");
		for(Sprite s: sprites) {
			if(this.intersects(s.getHitBox())) {
				if(s instanceof Damageable) {
					Damageable damageableSprite = ((Damageable) s);
					damageableSprite.takeDamage(getDamage());
				}

				return true;
			}
		}

		return false;

	}

	/**only draws bullets/detects collisions with bullets and other objects if the bullet is alive
	 * 
	 */
	public void draw(PApplet marker) {
		if(alive) {
			marker.image(image, (float)getX(),(float)getY(),(float)getWidth(),(float)getHeight());
			
			if(detectHit()) {
				setAlive(false);
			}
		}
		
		act();

		
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
