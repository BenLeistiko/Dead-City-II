package Menus;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import items.RangedWeapon;
import items.Weapon;
import processing.core.PApplet;
import scenes.BattleField;
import scenes.Scene;
import sprites.Hero;

public class HUD extends Menu{

	private Scene s;
	private double health;
	private int ammo;
	private Weapon weapon;


	public HUD(Scene s) {
		super(true);
		health = 0;
		ammo = 0;
		this.s=s;
	}


	//characterSpace = new Rectangle2D.Double(xEdge, yEdge, width-2*xEdge, height - 2*yEdge);

	public void draw(PApplet marker) {


		marker.pushMatrix();

		marker.fill(0);

		marker.rect(300, 300, 800, 800);
		marker.text("ammo: " + ammo + "Health: " + health, 500, 500);


		marker.popMatrix();
		
		if(weapon instanceof RangedWeapon) {
			RangedWeapon rw = ((RangedWeapon) weapon);

		}

		



	}

	public void update(Hero h) {
		if(s instanceof BattleField) {
			BattleField b = ((BattleField)s);
			
			
			
			
		}else {
			
			
		}
		
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
