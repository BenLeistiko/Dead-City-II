package sprites;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * A barrier that can be destroyed.
 * @author bleistiko405
 *
 */
public class DamageableBarrier extends Barrier implements Damageable {
	private double health;
	private double armor;
	private boolean alive;

	public DamageableBarrier(double x, double y, double w, double h, double textureW, double textureH, PImage texture, double health, double armor) {
		super(x, y, w, h, textureW, textureH, texture);
		this.health = health;
		this.armor = armor;
		alive = true;
	}	

	public void draw(PApplet marker) {
		if(checkAlive()) {
			super.draw(marker);
		}
	}

	public void takeDamage(Destructive d) {
		health = health-(d.getDamage()*armor);
	}

	public void takeDamage(double damage) {
		health = health-(damage*armor);
	}

	public Rectangle2D getHitBox() {
		return this.getBounds2D();
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	/**
	 * tests the health and sets it to false if its health is <=0
	 */
	public boolean checkAlive() {	
		if(health <=0) {
			setAlive(false) ;
		}
		return alive;
	}



}
