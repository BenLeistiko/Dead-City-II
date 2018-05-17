package Menus;

import java.awt.Color;
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
	private Rectangle2D.Double visSpace;
	private double health;
	private int ammo;
	private Weapon weapon;



	public HUD(Scene s) {
		super(true);
		health = 0;
		ammo = 0;
		this.s=s;
		visSpace = new Rectangle2D.Double();
	}




	public void draw(PApplet marker) {
		if(super.isVisible()) {

			marker.pushMatrix();
			Color c = new Color(255,0,0);
			marker.fill(0);
			marker.strokeWeight(5);
			marker.stroke(c.getRGB());;

		//	marker.rect((float)visSpace.getX(), (float)visSpace.getY(), (float)visSpace.getWidth(), (float)visSpace.getHeight());
			marker.textAlign(marker.LEFT, marker.TOP);
			marker.textSize(20);
			marker.text("Ammo: " + ammo + " Health: " + health, (float)visSpace.getX(), (float)visSpace.getY());


			marker.popMatrix();

			if(weapon instanceof RangedWeapon) {
				RangedWeapon rw = ((RangedWeapon) weapon);

			}

		}
	}

	//characterSpace = new Rectangle2D.Double(xEdge, yEdge, width-2*xEdge, height - 2*yEdge);
	public void update(Hero h) {
		visSpace = s.getVisSpace();

		health = h.getHealth();
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
