package sprites;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import gamePlay.Main;
import processing.core.PApplet;
import scenes.Scene;
/**
 * CPU controlled monster that attacks heros.
 * @author jose
 * @version 5/21/18
 *
 */
public class Monster extends Creature {

	private	double damage;
	private long timeLastUpdated;
	private double attackRate;

	public Monster(String animationKey,double x, double y, double w, double h) {
		super(x, y, w, h, animationKey);
		damage = Main.resources.getStat(animationKey, Main.resources.DAMAGE);
		attackRate = Main.resources.getStat(animationKey, Main.resources.FIRERATE);
	}

	public void draw(PApplet marker) {
		super.draw(marker);
		marker.noFill();
		marker.stroke(255,255,255);
		marker.strokeWeight(2);
		marker.rect((float)x, (float)y-15, (float)width, 10);
		float dist = (float) (super.getHealth()/super.getMaxHealth()*width);
		marker.fill(255, 0, 0);
		marker.strokeWeight(0);
		marker.rect((float)x+1, (float)y-14, (float)dist-1, 9);
	}

	public void act(Scene s) {
		Hero h = (Hero) s.getFocusedSprite();
		super.act(s);

		double distance = this.getCenter().distance(h.getCenter());
		if(distance <1600) {
			Main.isBattle = true;
			//System.out.println("Monsters are here");
			//System.out.print(Main.isBattle);
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