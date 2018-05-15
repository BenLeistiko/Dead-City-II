package gamePlay;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import interfaces.Clickable;
import interfaces.Drawable;
import processing.core.PApplet;
/**
 * This is a button that is in scenes.  It could open other scenes or do other suff.
 * @author bleistiko405
 *
 */
public class Button extends Rectangle2D.Double implements Drawable, Clickable {

	String name;
	boolean isPressed;
	int x,y;
	
	public Button(int x, int y, int h, int w, String name) {
		super(x,y,h,w);
		this.name = name;
		this.isPressed = false;
		x = (x+x+w)/2;
		y = (y+y+h)/2;
	}

	public boolean shouldRemove() {
		return false;
	}

	public boolean isPressed() {
		return isPressed;
	}

	public boolean mouseInside(PApplet marker) {
		if(this.contains(marker.mouseX, marker.mouseY)) {
			return true;
		}
		return false;
	}

	public void mousePressed(PApplet marker) {
		if(this.contains(marker.mouseX, marker.mouseY)) {
			isPressed = true;
		}
	}

	public void mouseReleased(PApplet marker) {
		isPressed = false;
	}

	public void draw(PApplet marker) {
		if(this.mouseInside(marker)) {
			marker.fill(new Color(128,128,128).getRGB(), 0.5f);
			marker.strokeWeight(3);
		}
		if(this.mouseInside(marker)) {
			marker.noFill();
			marker.strokeWeight(2);
		}
		marker.rect((float)super.x, (float)super.y, (float)super.getWidth(), (float)super.getHeight());
		marker.textAlign(marker.CENTER);
		marker.text(name, x, y);
	}

	@Override
	public Rectangle2D.Double getHitBox() {
		return this;
	}
}