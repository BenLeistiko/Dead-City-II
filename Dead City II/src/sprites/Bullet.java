package sprites;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;


public class Bullet extends Sprite implements Destructive {

	private double damage;
	private vX
	
	
	
	
	public Bullet(double x, double y, double w, double h, double damage) {
		super(x, y, w, h);
		this.damage = damage;
	}

	
	

	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealDamage(Damageable d) {
		d.takeDamage(damage);
	}

	@Override
	public double getDamage() {
		return damage;
	}

}
