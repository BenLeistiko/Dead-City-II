package sprites;

import interfaces.Damageable;
import interfaces.Destructive;

public class DamageableBarrier extends Barrier implements Damageable {

	double health;
	double armor;
	@Override
	public void takeDamage(Destructive d) {
		d.dealDamage(this);
	}

	@Override
	public void takeDamage(Double damage) {
		health = health-(damage*armor);
	}

}
