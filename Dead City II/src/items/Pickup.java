package items;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import gamePlay.Main;
import interfaces.Drawable;
import processing.core.PApplet;
import processing.core.PImage;
import scenes.Scene;
import sprites.*;
import sprites.Sprite;

public abstract class Pickup extends Rectangle2D.Double implements Drawable{

	PImage image;
	boolean pickedUp;


	public Pickup(double x, double y, double w, double h,String imageKey) {
		super(x,y,w,h);
		image = Main.resources.getImage(imageKey);
		pickedUp = false;

	}

	public void draw(PApplet marker) {
		marker.image(image,(float) x, (float)y);

	}


	public void act(Scene s) {
		if(s.getFocusedSprite().intersects(this)) {
			pickedUp = true;
			power((Creature)s.getFocusedSprite());
		}

	}
	
public abstract void power(Creature target);
	
	public Double getHitBox() {
		return this;
	}

	
	public boolean shouldRemove() {
		return pickedUp;
	}






















}
