package sprites;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import processing.core.PApplet;
/**
 * CPU controlled monster that attacks heros.
 * @author bleistiko405
 *
 */
public class Monster extends Creature {




	public Monster(String animationKey,double x, double y, double w, double h, ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h,worldlyThings, animationKey);
	}

	public void draw(PApplet marker) {
		super.draw(marker);
	}

	public void act(Sprite s) {
		super.act();

		double distance = this.getCenter().distance(s.getCenter());
		if(distance <5000) {
			double targetX = s.getCenterX();
			double targetY = s.getCenterY();

			if(targetX >this.getCenterX()) { //hero is to the right
				this.setvX(5);
			}else {//hero is to the left
				this.setvX(-5);	
			}


			if(super.getvX() == 0) {
				super.jump();
			}



		}

	}



}
