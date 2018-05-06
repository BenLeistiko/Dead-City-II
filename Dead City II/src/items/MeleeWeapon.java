package items;

import interfaces.Damageable;
import interfaces.Destructive;

public class MeleeWeapon extends Weapon implements Destructive {


	private double range;

	public MeleeWeapon(double damage) {
		super(damage);
	}

	public void dealDamage(Damageable d) {
		d.takeDamage(this.getDamage());
	}

	public void attack(double x, double y) {

	}

}
