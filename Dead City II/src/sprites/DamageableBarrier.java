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

	public DamageableBarrier(double x, double y, double w, double h, double textureW, double textureH, String key, double health, double armor) {
		super(x, y, w, h, textureW, textureH, key);
		this.health = health;
		this.armor = armor;
	}	

	public void draw(PApplet marker) {
			super.draw(marker);
	}

	public double takeDamage(Destructive d) {
		double dmg =(d.getDamage()-d.getDamage()*armor);
		health -= dmg;
		checkAlive();
		return dmg;
	}

	public double takeDamage(double damage) {
		double dmg =damage-damage*armor;
		health -= dmg;
		checkAlive();
		return dmg;
	}


	/**
	 * tests the health and sets it to false if its health is <=0
	 */
	public void checkAlive() {	
		if(health <=0) {
			remove();
		}
	}
}
