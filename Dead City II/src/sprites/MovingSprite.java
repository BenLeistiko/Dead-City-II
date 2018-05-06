package sprites;

import java.util.ArrayList;

import gamePlay.Main;
import processing.core.PApplet;

public abstract class MovingSprite extends Sprite {

	private double vX;
	private double vY;
	
	public MovingSprite(double x, double y, double w, double h) {
		super(x, y, w, h);
	}
	public MovingSprite(double x, double y, double w, double h, boolean visible) {
		super(x, y, w, h,visible);
	}

	public void draw(PApplet marker) {
		ArrayList<Sprite> hits = super.collides();
		boolean isFalling = true;
		for(Sprite s:hits) {
			if(super.isBelow(s))
		}
		vY = vY + Main.GRAVITY*Main.frameTime;
		super.moveByAmount(vX*Main.frameTime, vY*Main.frameTime);
		
	}
	
	
	
	
	
	


}
