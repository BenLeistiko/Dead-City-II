package sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.TexturePaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import gamePlay.DrawingSurface;
import interfaces.Drawable;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Jose
 * @version 5/5/2018 5:30
 *
 */
public abstract class Sprite extends Rectangle2D.Double implements Drawable {
	// FIELDS
	private boolean isVisible;
	private String imageKey;
//	private ArrayList<Sprite> sprites;
	
	public Sprite(String imageKey,double x, double y, double w, double h, boolean visiblity, ArrayList<Sprite> sprites) {
		super(x,y,w,h);
		//this.sprites = sprites;
		this.imageKey = imageKey;
		isVisible = visiblity;
		
	}
	public Sprite(double x, double y, double w, double h, boolean visiblity) {
		super(x,y,w,h);

		isVisible = visiblity;
	}

	public Sprite(double x, double y, double w, double h) {
		super(x,y,w,h);

		isVisible = true;
	}
	
	public void drawHitBox(PApplet marker) {
		marker.noFill();
		marker.rect((float)getX(), (float)getY(), (float)getWidth(), (float)getHeight());
	}
	
	public boolean collides(Sprite s) {
		if(this.intersects(s) && this != s) {
			return true;
		} else {
			return false;	
		}

	}

	public ArrayList<Sprite> collides() {
		/*
		ArrayList<Sprite> hits = new ArrayList<Sprite>();
		for(Sprite s: sprites) {
			if(this.collides(s)) {
				hits.add(s);
			}
		}
		return hits;
		*/
		return null;
	}
	
	public ArrayList<Sprite> collides(ArrayList<Sprite> sprites) {
		ArrayList<Sprite> hits = new ArrayList<Sprite>();

		for(Sprite s: sprites) {
			if(this.collides(s)) {
				hits.add(s);
			}
		}

		return hits;
	}
	
	public boolean isBelow(Sprite other) {
		if(this.getY()+this.getHeight() < other.getY()) {
			return true;
		}
		return false;
	}

	public void moveByAmount(double x, double y) {
		this.x+=x;
		this.y+=y;
	}
	
	public void moveInLimits(Rectangle2D.Double limits, double x, double y) {
		double newX = this.x + x;
		double newY = this.y + y;
		if (limits.contains(new Rectangle2D.Double(newX,newY,width,height))) {
			this.x = newX;
			this.y = newY;
		}
	}

	public void moveToLocation(double x, double y) {
		super.x = x;
		super.y = y;
	}

	public Point2D.Double getCenter() {
		return new Point2D.Double(getX()+getWidth()/2,getY()+getHeight()/2);
	}

	/*
	 * Switches the MovingImage visibility to the opposite of what it is
	 * currently.
	 */
	public void swapVisibility() {
		isVisible = !isVisible;
	}

	/*
	 * Returns the visibility of the MovingImage.
	 */
	public boolean isVisible() {
		return isVisible;
	}
	
	public Rectangle2D getHitBox() {
		return this.getBounds2D();
	}
	 
}