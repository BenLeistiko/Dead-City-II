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

import interfaces.Drawable;

/**
 * 
 * @author Jose
 * @version 5/5/2018 5:30
 *
 */
public abstract class Sprite extends Rectangle2D.Double implements Drawable {
	// FIELDS
	private BufferedImage img;
	private boolean isVisible;

	private boolean textured;



	// CONSTRUCTOR
	/*
		 All coordinates are in assumed coordinates and represent data for the
		 image, not the window.
	 */
	public Sprite(String filename, int x, int y, int w, int h, boolean textured) {
		super(x,y,w,h);
		try {
			img = ImageIO.read(new File(filename));
		} catch(IOException ex) {
		}

		isVisible = true;

		this.textured = textured;
	}


	// NON-STATIC METHODS
	/*
	 * If the MovingImage should be visible, draws the MovingImage to the
	 * screen in the way specified by the resizable field.
	 */
	public abstract void draw(Graphics2D g, ImageObserver io);

	public boolean collides(Sprite s) {
		if(this.intersects(s)) {
			return true;
		} else {
			return false;	
		}

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





	//a movebyAmount method but makes sure it doesnt leave the limits
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




}
