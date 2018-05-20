package items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import interfaces.Damageable;
import interfaces.Slotable;
import sprites.Bullet;
import sprites.Creature;
import sprites.Sprite;
/**
 * A common superclass for anything that will do damage directly or indirectly.
 * @author Ben
 *
 */
public abstract class Weapon implements Slotable {

	private double damage;
	private double attackRate;
	private double range;
	

	public Weapon(double damage, double attackRate, double range) {
		this.damage = damage;
		this.attackRate = attackRate;
		this.range = range;
	
	}

	public abstract void perform(Creature c, int dir, ArrayList<Sprite> sprites); 


	public void upgrade(double upgradeAmt) {
		damage+=upgradeAmt;
	}


	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}


	public double getAttackRate() {
		return attackRate;
	}


	public void setAttackRate(long attackRate) {
		this.attackRate = attackRate;
	}


	public double getRange() {
		return range;
	}


	public void setRange(double range) {
		this.range = range;
	}


	public void act() {
		
	}






}
