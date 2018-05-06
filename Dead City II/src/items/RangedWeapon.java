package items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import sprites.Bullet;

public class RangedWeapon extends Weapon {

	private final int MAX_AMMO;
	private int ammo;
	private long timeLastUpdated;
	

	public RangedWeapon(double damage, int attackRate, double range, int ammo) {
		super(damage,attackRate,range);
		MAX_AMMO = ammo;
		this.ammo = ammo;



	}

	public void shoot(Rectangle2D creatureRect, boolean isFacingRight,ArrayList<Bullet> battlefieldBullets) {
		
		if(System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {
			Bullet b = new Bullet(creatureRect.getX()+creatureRect.getWidth(),creatureRect.getY()+creatureRect.getHeight()/2,20,20, getDamage());
			battlefieldBullets.add(b);
			
			
			
			
			ammo--;
			timeLastUpdated = System.currentTimeMillis();
		}


	}


	public void reload() {
		ammo = MAX_AMMO;
	}





}

