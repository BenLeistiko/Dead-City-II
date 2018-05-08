package items;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.Damageable;
import sprites.Bullet;
import sprites.Sprite;

public class RangedWeapon extends Weapon {

	private final int MAX_AMMO;
	private int ammo;
	private long timeLastUpdated;
	private ArrayList<Bullet> bullets;


	public RangedWeapon(double damage, int attackRate, double range, int ammo, ArrayList<Bullet> bullets) {
		super(damage,attackRate,range);
		MAX_AMMO = ammo;
		this.ammo = ammo;
		this.bullets = bullets;
	}



	public void perform(Rectangle2D creatureRect, int dir, ArrayList<Sprite> sprites) {
		
		if(System.currentTimeMillis()-timeLastUpdated>getAttackRate()) {
			Bullet bull = new Bullet(creatureRect.getX()+creatureRect.getWidth(),
					creatureRect.getY()+creatureRect.getHeight()/3,30,20, getDamage(),sprites);
			
			bull.setvX(dir*50.0);
			bullets.add(bull);			
			ammo--;
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
		ammo = MAX_AMMO;
	}

	public ArrayList<Bullet> getBulletArray(){
		return bullets;
	}




}

