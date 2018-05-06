package items;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import interfaces.Damageable;
import interfaces.Destructive;

public class MeleeWeapon extends Weapon implements Destructive {

	private long timeLastUpdated;

	public MeleeWeapon(double damage, int attackRate, double range) {
		super(damage,attackRate,range);
		timeLastUpdated = System.currentTimeMillis();
	}

	public void dealDamage(Damageable d) {
		d.takeDamage(this.getDamage());
	}

	public void attack(Rectangle2D creatureRect, boolean isFacingRight, ArrayList<Damageable> damageables) {

		if(System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {
			Rectangle2D target;
			for(Damageable d: damageables) {
				target = d.getLocationRect();
				if(isFacingRight) {
					if(Point2D.distance(creatureRect.getX()+creatureRect.getWidth(), creatureRect.getY(),
							target.getX(), target.getY()) <  getRange()) {
						this.dealDamage(d);
					}
				}else {
					if(Point2D.distance(creatureRect.getX(), creatureRect.getY(),
							target.getX()+target.getWidth(), target.getY()) <  getRange()) {
						this.dealDamage(d);
					}
				}

			}
			timeLastUpdated = System.currentTimeMillis();
		}

	}




}
