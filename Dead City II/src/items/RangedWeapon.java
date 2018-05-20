package items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.Damageable;
import scenes.Scene;
import sprites.Bullet;
import sprites.Sprite;

/**
 * Something generates bullets.
 * @author bleistiko405
 *
 */
public class RangedWeapon extends Weapon {

	private final int MAX_AMMO;
	private int currentAmmo;
	private long timeLastUpdated;
	private Scene s;
	private boolean isReloading;
	private long reloadTime;




	public RangedWeapon(double damage, double attackRate, double bulletSpeed, int ammo,double reloadTime, Scene s) {
		super(damage,attackRate,bulletSpeed);
		MAX_AMMO = ammo;
		this.currentAmmo = ammo;
		this.reloadTime = (long) reloadTime;
		this.s=s;
	}



	public void perform(Rectangle2D creatureRect, int dir, ArrayList<Sprite> sprites) {
		 if(currentAmmo>0 && !isReloading && System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {

			Bullet bull;
			if(dir == 1) {
				bull = new Bullet(creatureRect.getX()+creatureRect.getWidth(),
						creatureRect.getY()+creatureRect.getHeight()/3,30,20, getDamage(),sprites,dir);
			}else {
				bull = new Bullet(creatureRect.getX(),
						creatureRect.getY()+creatureRect.getHeight()/3,30,20, getDamage(),sprites,dir);
			}
			bull.setvX(dir*getRange());

			s.add(bull);
			currentAmmo--;
			timeLastUpdated = System.currentTimeMillis();
		}



	}


	public void act() {
		if(isReloading && System.currentTimeMillis()-timeLastUpdated>reloadTime) {
			currentAmmo = MAX_AMMO;
			isReloading = false;
		}
	}

	public void reload() {
		isReloading = true;
		timeLastUpdated = System.currentTimeMillis();
		//currentAmmo = MAX_AMMO;
	}



	public int getCurrentAmmo() {
		return currentAmmo;
	}

	public int getMaxAmmo() {
		return MAX_AMMO;
	}



}

