package sprites;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;


public class Bullet extends Sprite implements Destructive {

	public Bullet(int x, int y, int w, int h) {
		super(x, y, w, h);
		// TODO Auto-generated constructor stub
	}

	double damage;
	
	@Override
	public void draw(PApplet marker) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dealDamage(Damageable d) {
		d.takeDamage(damage);
	}

}
