package items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.BetterSound;
import gamePlay.Main;
import interfaces.Damageable;
import scenes.Scene;
import sprites.Bullet;
import sprites.Creature;
import sprites.Hero;
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



	public void perform(Creature hero, int dir, ArrayList<Sprite> sprites) {
		if(currentAmmo>0 && !isReloading && System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {

			Bullet bull;
			if(dir == 1) {
				bull = new Bullet(hero.getX()+hero.getWidth(),
						hero.getY()+hero.getHeight()/3,30,20, getDamage(),sprites,dir);
			}else {
				bull = new Bullet(hero.getX(),
						hero.getY()+hero.getHeight()/3,30,20, getDamage(),sprites,dir);
			}
			bull.setvX(hero.getvX()+dir*getRange());
			bull.setvY(hero.getvY());

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
		if(currentAmmo != MAX_AMMO && !isReloading) {
			isReloading = true;
			timeLastUpdated = System.currentTimeMillis();
			new BetterSound(Main.resources.getSound("Reload"),true,false);
		}
		//currentAmmo = MAX_AMMO;
	}



	public int getCurrentAmmo() {
		return currentAmmo;
	}

	public int getMaxAmmo() {
		return MAX_AMMO;
	}

	public boolean isReloading() {
		return isReloading;
	}



}

