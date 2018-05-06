package interfaces;

public interface Destructive {
	/**
	 * Deals damage to a Damageable object.
	 * Do NOT call d.takeDamage(this); it will generate an endless loop
	 * @param d - the Damageable object to deal damage to
	 * @pre do NOT call d.takeDamage(this); it will generate an endless loop
	 */
	public void dealDamage(Damageable d);
	
	public double getDamage();
}
