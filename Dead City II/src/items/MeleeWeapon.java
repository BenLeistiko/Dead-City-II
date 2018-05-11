package items;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import interfaces.Damageable;
import interfaces.Destructive;
import sprites.Sprite;
/**
 * A short ranged melee weapon that deals damage.
 * @author bleistiko405
 *
 */
public class MeleeWeapon extends Weapon implements Destructive {

	
	private long timeLastUpdated;

	public MeleeWeapon(double damage, int attackRate, double range) {
		super(damage,attackRate,range);
		timeLastUpdated = System.currentTimeMillis();
	}

	public void dealDamage(Damageable d) {
		d.takeDamage(this.getDamage());
	}


	public void perform(Rectangle2D creatureRect, int dir, ArrayList<Sprite> sprites) {

		if(System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {
			Rectangle2D target;
			for(Sprite s: sprites) {
				if(s instanceof Damageable) {
					Damageable damageableSprite = ((Damageable) s);

					target = damageableSprite.getHitBox();
					if(dir == 1) {
						if(Point2D.distance(creatureRect.getX()+creatureRect.getWidth(), creatureRect.getY(),
								target.getX(), target.getY()) <  getRange()) {
							damageableSprite.takeDamage(getDamage());
						}
					}else {
						if(Point2D.distance(creatureRect.getX(), creatureRect.getY(),
								target.getX()+target.getWidth(), target.getY()) <  getRange()) {
							damageableSprite.takeDamage(getDamage());
						}
					}

				}

			}
			timeLastUpdated = System.currentTimeMillis();
		}

	}

	
	



}
