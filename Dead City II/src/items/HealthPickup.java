package items;

import gamePlay.Main;
import processing.core.PApplet;
import scenes.Scene;
import sprites.Creature;
import sprites.Hero;
import sprites.Sprite;
/**
 * A pickup that gives a hero health.
 * @author Jose
 * @version 5/21/18
 */
public class HealthPickup extends Pickup{


	private double healthBoost;

	public HealthPickup(double x, double y, double boostAmount) {
		super(x,y,Main.resources.getImage("Health").width,Main.resources.getImage("Health").height,"Health");
		this.healthBoost=boostAmount;
	}


	public void power(Hero target) {
		target.addHealth(healthBoost);
	}








}
