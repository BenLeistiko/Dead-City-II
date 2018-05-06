package sprites;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;


public class Bullet extends Sprite implements Destructive {

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
