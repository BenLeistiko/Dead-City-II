package items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.Damageable;
import sprites.Bullet;
import sprites.Sprite;

public class RangedWeapon extends Weapon {

	private final int MAX_AMMO;
	private int currentAmmo;
	private long timeLastUpdated;
	private ArrayList<Bullet> bullets;
	private final double BULLET_SPEED = 50;


	public RangedWeapon(double damage, int attackRate, double range, int ammo, ArrayList<Bullet> bullets) {
		super(damage,attackRate,range);
		MAX_AMMO = ammo;
		this.currentAmmo = ammo;
		this.bullets = bullets;
	}



	public void perform(Rectangle2D creatureRect, int dir, ArrayList<Sprite> sprites) {

		if(currentAmmo>0 && System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {
			Bullet bull;
			if(dir == 1) {
				bull = new Bullet(creatureRect.getX()+creatureRect.getWidth(),
						creatureRect.getY()+creatureRect.getHeight()/3,30,20, getDamage(),sprites,dir);
			}else {
				bull = new Bullet(creatureRect.getX(),
						creatureRect.getY()+creatureRect.getHeight()/3,30,20, getDamage(),sprites,dir);
			}
			bull.setvX(dir*BULLET_SPEED);
			bullets.add(bull);			
			currentAmmo--;
			timeLastUpdated = System.currentTimeMillis();
		}
		ridDeadBullets();


	}

	public void ridDeadBullets() {
		for(int i = 0; i < bullets.size();i++) {
			if(!bullets.get(i).isAlive()) {
				bullets.remove(i);
			}
		}
	}



	public void reload() {
		currentAmmo = MAX_AMMO;
	}

	public ArrayList<Bullet> getBulletArray(){
		return bullets;
	}


	public int getCurrentAmmo() {
		return currentAmmo;
	}

}

