package items;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.Drawable;
import processing.core.PApplet;
import processing.core.PImage;
import scenes.Scene;
import sprites.*;

public abstract class Pickup extends MovingSprite implements Drawable{

	PImage image;
	private boolean pickedUp;
	private	boolean onASurface;


	public Pickup(double x, double y, double w, double h,String imageKey) {
		super(x,y,w,h);
		image = Main.resources.getImage(imageKey);
		pickedUp = false;
		onASurface = false;

	}

	public void draw(PApplet marker) {
		marker.image(image,(float) x, (float)y);

	}


	public void act(Scene scene) {
		if(scene.getFocusedSprite().intersects(this)) {
			pickedUp = true;
			power((Hero)scene.getFocusedSprite());
		}else {
			 
			//***********Y AXIS***********
			ArrayList<Sprite> worldlyThings = scene.getWorldlyThings();
			double xCoord = getX();
			double yCoord = getY();
			double width = getWidth();
			double height = getHeight();


			setvY(getvY() + Main.GRAVITY); // GRAVITY
			double yCoord2 = yCoord + getvY();

			Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(getvY()));

			onASurface = false;

			if (getvY() > 0) {
				Sprite standingSurface = null;
				for (Sprite s : worldlyThings) {
					if (s.intersects(strechY) && s instanceof Barrier) {
						onASurface = true;
						standingSurface = s;
						setvY(0);
					}
				}
				if (standingSurface != null) {
					Rectangle r = standingSurface.getBounds();
					yCoord2 = r.getY()-height;
				}
			} else if (getvY() < 0) {
				Sprite headSurface = null;
				for (Sprite s : worldlyThings) {
					if (s.intersects(strechY)&& s instanceof Barrier) {
						headSurface = s;
						setvY(0);
					}
				}
				if (headSurface != null) {
					Rectangle r = headSurface.getBounds();
					yCoord2 = r.getY()+r.getHeight();
				}
			}

			if (Math.abs(getvY()) < .2)
				setvY(0);
			
			
			moveToLocation(x,yCoord2);//both axis this should be at the end

		}

	}

	public abstract void power(Hero target);

	public Double getHitBox() {
		return this;
	}


	public boolean shouldRemove() {
		return pickedUp;
	}






















}
