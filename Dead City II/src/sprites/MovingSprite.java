package sprites;

import java.util.ArrayList;

import gamePlay.DrawingSurface;
import gamePlay.Main;
import processing.core.PApplet;
/**
 * Any sprite that can be moved.
 * @author bleistiko405
 *
 */
public abstract class MovingSprite extends Sprite {

	private double vX;
	private double vY;
	
	public MovingSprite(double x, double y, double w, double h) {
		super(x, y, w, h);
		vX = 0;
		vY = 0;
	}
	public MovingSprite(double x, double y, double w, double h, boolean visible) {
		super(x, y, w, h,visible);
	}

	public double getvX() {
		return vX;
	}

	public void setvX(double vX) {
		this.vX = vX;
	}

	public double getvY() {
		return vY;
	}

	public void setvY(double vY) {
		this.vY = vY;
	}


}
