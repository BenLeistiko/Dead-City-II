package interfaces;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * Anything that can take damage.
 * @author bleistiko405
 *
 */
public interface Damageable {
	
	/**
	 * Takes damage from a Destructive object
	 * @param d - the destructive that deals the damage
	 */
	public void takeDamage(Destructive d);
	/**
	 * Takes a specified amount of damage
	 * @param damage - the amount of damage to deal
	 */
	public void takeDamage(double damage);
	
	public Rectangle2D getHitBox();
	
	public boolean checkAlive();
	
}
