package gamePlay;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import interfaces.Clickable;
import interfaces.Drawable;
import processing.core.PApplet;
import processing.core.PImage;
import scenes.BattleField;
import scenes.Scene;
import sprites.Hero;
import sprites.Sprite;
/**
 * This is a button that is in scenes.  It could open other scenes or do other stuff.
 * Reacts to user input and appears as a drawn image
 * 
 * @author bleistiko405
 * @version 5/17/18 5:31
 */
public class Button extends Rectangle2D.Double implements Drawable, Clickable {

	private String name;
	private Color textColor;
	private PImage left,center,right,leftP,centerP,rightP,centerCrop,centerCropP;
	private String target;
	private boolean isPressed = false;
	private boolean swapToTarget;
	private boolean makeNewScene;

	private long timeLastUpdated;

	public Button(double x, double y, double w, double h, String name,Color textColor, String target) {
		super(x-(w/2),y-(h/2),w,h);
		this.name = name;
		this.textColor = textColor;
		left = Main.resources.getImage("ButtonLeft");
		left = Main.resources.getTexture("ButtonLeft", new Point((int) (left.width*(h/left.height)),(int)h), new Point((int) (left.width*(h/left.height)),(int)h));
		right = Main.resources.getImage("ButtonRight");
		right = Main.resources.getTexture("ButtonRight", new Point((int) (right.width*(h/right.height)),(int)h), new Point((int) (right.width*(h/right.height)),(int)h));

		leftP = Main.resources.getImage("ButtonLeftPressed");
		leftP = Main.resources.getTexture("ButtonLeftPressed", new Point((int) (leftP.width*(h/leftP.height)),(int)h), new Point((int) (leftP.width*(h/leftP.height)),(int)h));
		rightP = Main.resources.getImage("ButtonRightPressed");
		rightP = Main.resources.getTexture("ButtonRightPressed", new Point((int) (rightP.width*(h/rightP.height)),(int)h), new Point((int) (rightP.width*(h/rightP.height)),(int)h));

		center = Main.resources.getImage("Button");
		center = Main.resources.getTexture("Button", new Point((int) (center.width*(h/center.height)),(int)h-4), new Point((int) (center.width*(h/center.height)),(int)h-4));

		centerP = Main.resources.getImage("ButtonPressed");
		centerP = Main.resources.getTexture("ButtonPressed", new Point((int) (centerP.width*(h/centerP.height)),(int)h-4), new Point((int) (centerP.width*(h/centerP.height)),(int)h-4));

		double centerWidth = getWidth() - left.width - right.width;

		int cropX = (int) (centerWidth-(Math.ceil(centerWidth/center.width)-1.0001)*center.width);

		centerCrop = Main.resources.getImage("Button");
		centerCrop = Main.resources.getTexture("Button", new Point((int) (centerCrop.width*(h/centerCrop.height)),(int)h-4), new Point(cropX,(int)h-4));

		int cropXP = (int) (centerWidth-(Math.ceil(centerWidth/centerP.width)-1.0001)*centerP.width);

		centerCropP = Main.resources.getImage("ButtonPressed");
		centerCropP = Main.resources.getTexture("ButtonPressed", new Point((int) (centerCropP.width*(h/centerCropP.height)),(int)h-4), new Point(cropXP,(int)h-4));

		this.target = target;
		swapToTarget = false;
		if(target.charAt(0) == '*') {
			this.target = target.substring(1);
			makeNewScene = true;
		}else
			makeNewScene = false;


		timeLastUpdated = System.currentTimeMillis();
	}


	public boolean shouldRemove() {
		return false;
	}

	private boolean mouseInside(Point point) {
		if(this.contains(point.x, point.y)) {
			return true;
		}
		return false;
	}

	public void act(Scene scene) {
		if(!target.contains("action")) {
			if(swapToTarget){
				if(makeNewScene) {
					scene.newBattleField();
				}
				scene.changePanelAndPause(target);
				this.swapToTarget = false;
			}
		}else if(isPressed && System.currentTimeMillis()-timeLastUpdated > 400) {
			Hero joe = (Hero)scene.getFocusedSprite();
			int upgradeTokens = joe.getUpgradeTokens();
			if(upgradeTokens>0) {
				System.out.println("safretrdsfgsdfg");
				joe.decrementUpgradeTokens();
				if(target.equals("actionIncreaseHealth")) {
					joe.increaseMaxHealth(20);
					
				}else if(target.equals("actionIncreaseStamina")) {
					joe.increaseMaxStamina(5);
				}else if(target.equals("actionIncreaseDamage")) {
					joe.increaseDamage(8);
				}
			}
			timeLastUpdated = System.currentTimeMillis();
		}
	}

	public void draw(PApplet marker) {
		marker.pushMatrix();

		if(!isPressed) {
			marker.image(left, (float)(x), (float) y);
			int j = 0;
			for(int i = (int) (x+left.width); i<x+this.width-right.width-center.width; i = i + center.width) {
				marker.image(center, i, (float) y+2);
				j++;
			}
			float cropX = (float) (x+leftP.width+center.width*j);
			marker.image(centerCrop, cropX, (float)y+2);
			marker.image(right, cropX+centerCrop.width, (float) y);
		}else {
			marker.image(leftP, (float)x, (float) y);
			int j = 0;
			for(int i = (int) (x+leftP.width); i<x+this.width-rightP.width-centerP.width; i = i + centerP.width) {
				marker.image(centerP, i, (float) y+2);
				j++;
			}
			float cropX = (float) (x+leftP.width+centerP.width*j);
			marker.image(centerCropP, cropX, (float)y+2);
			marker.image(rightP, cropX+centerCropP.width, (float) y);
		}
		marker.textAlign(marker.CENTER, marker.CENTER);

		marker.fill(textColor.getRGB());
		marker.textSize(20);
		marker.text(name, (float)(x+getWidth()/2), (float)(y+getHeight()/2));

		marker.popMatrix();
	}

	/*private void reSizeImages() {
		//private PImage left,center,right,leftP,centerP,rightP,centerCrop,centerCropP;
		if(Scene.reSized) {
			double xRatio = Scene.xRatio_OLD/Scene.xRatio;
			double yRatio = Scene.yRatio_OLD/Scene.yRatio;
			System.out.println(Scene.xRatio_OLD + ", " + Scene.xRatio);
			resizeImage(left,xRatio*left.width,yRatio*left.height);
			resizeImage(center,xRatio*center.width,yRatio*center.height);
			resizeImage(right,xRatio*right.width,yRatio*right.height);
			resizeImage(leftP,xRatio*leftP.width,yRatio*leftP.height);
			resizeImage(centerP,xRatio*centerP.width,yRatio*centerP.height);
			resizeImage(rightP,xRatio*rightP.width,yRatio*rightP.height);
			resizeImage(centerCrop,xRatio*centerCrop.width,yRatio*centerCrop.height);
			resizeImage(centerCropP,xRatio*centerCropP.width,yRatio*centerCropP.height);

		}
	}

	private void resizeImage(PImage p,double xRatio,double yRatio) {
		System.out.println(p.width + ", " + p.height + " : " + xRatio + ", " + yRatio);
		p.resize((int)(p.width*xRatio), (int)(p.height*yRatio));
	}*/

	public void setX(double x) {
		this.x = x-super.getWidth();
	}

	public void setY(double y) {
		this.y = y-super.getHeight();
	}

	public Rectangle2D.Double getHitBox() {
		return this;
	}

	public void mousePressed(Point clickPoint, int button) {
		if(mouseInside(clickPoint)) {
			isPressed = true;
		}
	}

	public void mouseReleased(Point clickPoint, int button) {
		if(mouseInside(clickPoint)) {
			if(target.equalsIgnoreCase("exit")) {
				System.exit(0);
			}
			this.swapToTarget = true;
		}
		isPressed = false;
	}
}