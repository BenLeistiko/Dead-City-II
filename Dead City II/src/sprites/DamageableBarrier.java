package sprites;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;

public class DamageableBarrier extends Barrier implements Damageable {
	private double health;
	private double armor;
	
	
	public DamageableBarrier(int x, int y, int w, int h, double health, double armor) {
		super(x, y, w, h);
		this.health = health;
		this.armor = armor;
	}	

	public void draw(PApplet marker) {
		super.draw(marker);
		System.out.println(health);
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


}
