package Menus;

import java.awt.geom.Rectangle2D.Double;

import items.RangedWeapon;
import items.Weapon;
import processing.core.PApplet;
import sprites.Hero;

public class HUD extends Menu{

	private double health;
	private int ammo;
	private Weapon weapon;

	public HUD() {
		health = 0;
		ammo = 0;
	}

	public void draw(PApplet marker) {
		System.out.println("HUD");
	
		marker.pushMatrix();
		
		marker.fill(0);
	
		marker.rect(100, 100, 800, 800);
		marker.text("ammo: " + ammo + "Health: " + health, 500, 500);

		if(weapon instanceof RangedWeapon) {
			RangedWeapon rw = ((RangedWeapon) weapon);

		}

		marker.popMatrix();
		
		


	}

	public void update(Hero h) {
		health =	h.getHealth();


		weapon = h.getWeapon();

		if(weapon instanceof RangedWeapon) {
			RangedWeapon rw = ((RangedWeapon) weapon);
			ammo = ((RangedWeapon) weapon).getCurrentAmmo();
		}


	}







	@Override
	public Double getHitBox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean shouldRemove() {
		// TODO Auto-generated method stub
		return false;
	}
















}
