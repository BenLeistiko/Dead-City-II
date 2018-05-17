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

	private String name;
	private	boolean isPressed;
	private boolean mouseOver;
	private Color buttonColor,textColor;


	public Button(double x, double y, double w, double h, String name,Color buttonColor, Color textColor) {
		super(x-(w/2),y-(h/2),w,h);
		this.name = name;
		this.isPressed = false;
		this.mouseOver = false;
		this.buttonColor = buttonColor;
		this.textColor = textColor;



	}
public boolean mouseOver() {
	return mouseOver;
}
	
	public boolean shouldRemove() {
		return false;
	}

	public boolean isPressed() {
		return isPressed;
	}

	private boolean mouseInside(PApplet marker) {
		if(this.contains(marker.mouseX, marker.mouseY)) {
			return true;
			
		}
		return false;
	}

	public void mousePressed(PApplet marker) {
		if(mouseOver) {
			isPressed = true;

		}
	}

	public void mouseReleased(PApplet marker) {
		isPressed = false;
	}

	public void draw(PApplet marker) {
		update(marker);

		marker.pushMatrix();

		if(mouseOver) {
			
			marker.fill(buttonColor.getRGB(), 175f);
			marker.strokeWeight(5);
		} else {
			
			marker.fill(buttonColor.getRGB());		
			marker.strokeWeight(2);
		}

		marker.rectMode(marker.CENTER);
		marker.rect((float)(super.getX()+getWidth()/2), (float)(super.getY()+getHeight()/2), (float)super.getWidth(), (float)super.getHeight());

		
		marker.textAlign(marker.CENTER, marker.CENTER);
	
		marker.fill(textColor.getRGB());
		marker.textSize(20);
		marker.text(name, (float)(super.getX()+getWidth()/2), (float)(super.getY()+getHeight()/2));
		marker.popMatrix();
	}

	public void update(PApplet marker) {
		if(this.mouseInside(marker)){
			mouseOver = true;
		}else {
			mouseOver = false;
		}



	}

	public void setCoords(double x, double y) {
		this.x = x-super.getWidth()/2;
		this.y = y-super.getHeight()/2;
		//	this.x = x;
		//	this.y = y;
		//	this.setRect(x-super.getWidth(), y-super.getHeight(), this.getWidth(), this.getHeight());
		//this.x = x-super.getWidth();

	}

	public void setX(double x) {
		this.x = x-super.getWidth();
	}

	public void setY(double y) {
		this.y = y-super.getHeight();
	}




	public Rectangle2D.Double getHitBox() {
		return this;
	}
}