package items;

import interfaces.Slotable;
/**
 * 
 * @author Ben
 *
 */
public abstract class Weapon implements Slotable {

	private double damage;
	private int attackRate;
	private double range;
	
	public Weapon(double damage, int attackRate, double range) {
		this.damage = damage;
		this.attackRate = attackRate;
		this.range = range;
			
	}
	
	
	public void upgrade(double upgradeAmt) {
		damage+=upgradeAmt;
	}


	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}


	public int getAttackRate() {
		return attackRate;
	}


	public void setAttackRate(int attackRate) {
		this.attackRate = attackRate;
	}


	public double getRange() {
		return range;
	}


	public void setRange(double range) {
		this.range = range;
	}










}
