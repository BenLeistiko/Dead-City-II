package items;

import processing.core.PApplet;
import scenes.Scene;
import sprites.Creature;
import sprites.Hero;
import sprites.Sprite;

public class HealthPickup extends Pickup{


	private double healthBoost;

	public HealthPickup(double x, double y, double width, double height, double boostAmount) {
		super(x,y,width,height,"Health");
		this.healthBoost=boostAmount;
	}


	public void power(Creature target) {
		target.addHealth(healthBoost);
	}







}
