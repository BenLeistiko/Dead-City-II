package items;

import gamePlay.Main;
import processing.core.PApplet;
import scenes.Scene;
import sprites.Creature;
import sprites.Hero;
import sprites.Sprite;

public class HealthPickup extends Pickup{


	private double healthBoost;

	public HealthPickup(double x, double y, double boostAmount) {
		super(x,y,Main.resources.getImage("Health").width,Main.resources.getImage("Health").height,"Health");
		this.healthBoost=boostAmount;
	}


	public void power(Creature target) {
		target.addHealth(healthBoost);
	}







}
