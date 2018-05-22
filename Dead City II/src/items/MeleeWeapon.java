package items;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import interfaces.Damageable;
import interfaces.Destructive;
import scenes.Scene;
import sprites.Creature;
import sprites.Sprite;
/**
 * A short ranged melee weapon that deals damage. To be implemented.
 * @author Jose
 * @version 5/12/18
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


	public void perform(Creature hero, int dir, Scene scene) {
		ArrayList<Sprite> sprites = scene.getWorldlyThings();
		if(System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {
			Rectangle2D target;
			for(Sprite s: sprites) {
				if(s instanceof Damageable) {
					Damageable damageableSprite = ((Damageable) s);

					target = damageableSprite.getHitBox();
					if(dir == 1) {
						if(Point2D.distance(hero.getX()+hero.getWidth(), hero.getY(),
								target.getX(), target.getY()) <  getRange()) {
							damageableSprite.takeDamage(getDamage());
						}
					}else {
						if(Point2D.distance(hero.getX(), hero.getY(),
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
