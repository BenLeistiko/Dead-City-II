package items;

import interfaces.Slotable;
/**
 * 
 * @author Ben
 *
 */
public abstract class Weapon implements Slotable {

	private double damage;
	
	public Weapon(double damage) {
		this.damage = damage;
			
	}
	
	
	
	public void upgrade(int upgradeAmt) {
		damage+=upgradeAmt;
	}


	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}










}
