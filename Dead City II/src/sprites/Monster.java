package sprites;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import gamePlay.Main;
import processing.core.PApplet;
/**
 * CPU controlled monster that attacks heros.
 * @author bleistiko405
 *
 */
public class Monster extends Creature {

	private	double damage;
	private long timeLastUpdated;
	private double attackRate;

	public Monster(String animationKey,double x, double y, double w, double h, ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h,worldlyThings, animationKey);
		damage = Main.resources.getStat(animationKey, Main.resources.DAMAGE);
		attackRate = Main.resources.getStat(animationKey, Main.resources.FIRERATE);
	}

	public void draw(PApplet marker) {
		super.draw(marker);
	}

	public void act(Hero h) {
		super.act();

		double distance = this.getCenter().distance(h.getCenter());
		if(distance <1000) {
			Main.isBattle = true;
			System.out.println("Monsters are here");
			double targetX = h.getCenterX();
			double targetY = h.getCenterY();

			if(super.getvX() == 0) {
				super.jump();
			}
			if(targetX >this.getCenterX()) { //hero is to the right

				this.setvX(Main.resources.getStat(getAnimationKey(), Main.resources.SPEED));
			}else {//hero is to the left
				this.setvX(-Main.resources.getStat(getAnimationKey(), Main.resources.SPEED));	
			}

			if(System.currentTimeMillis() - timeLastUpdated>  attackRate && this.intersects(h)) {
				h.takeDamage(damage);
				timeLastUpdated = System.currentTimeMillis();
			}




		}

	}



}
