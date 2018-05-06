package sprites;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import interfaces.Damageable;
import interfaces.Destructive;

public class DamageableBarrier extends Barrier implements Damageable {
	private double health;
	private double armor;
	
	
	public DamageableBarrier(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}	

	public void takeDamage(Destructive d) {
		d.dealDamage(this);
	}

	public void takeDamage(double damage) {
		health = health-(damage*armor);
	}


}
