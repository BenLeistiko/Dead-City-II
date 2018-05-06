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
	
	
	public abstract void attack(double x, double y); 
	
	
	
	public void upgrade(int upgradeAmt) {
		damage+=upgradeAmt;
	}










}
