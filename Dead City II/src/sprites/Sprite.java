package sprites;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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

import interfaces.Drawable;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * Anything that takes collides with other sprites and is drawn.
 * @author Jose
 * @version 5/5/2018 5:30
 *
 */
public abstract class Sprite extends Rectangle2D.Double implements Drawable {
	// FIELDS
	
	
	
	public Sprite(double x, double y, double w, double h, boolean visiblity) {
		super(x,y,w,h);
	}

	public Sprite(double x, double y, double w, double h) {
		super(x,y,w,h);
	}
	
	public void drawHitBox(PApplet marker) {
		marker.noFill();
		marker.rect((float)getX(), (float)getY(), (float)getWidth(), (float)getHeight());
	}
	/**
	 * True if this sprite hits any other sprite in the array list that is passed in
	 * @param worldlyThings
	 * @return
	 */
	public boolean collides(ArrayList<Sprite> worldlyThings) {
		for(Sprite s:worldlyThings) {
			if(s.intersects(this) && s != this)
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
	
	public Rectangle2D.Double getHitBox() {
		return this;
	}
}