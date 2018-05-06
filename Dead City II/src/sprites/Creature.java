package sprites;

import java.util.ArrayList;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * 
 * @author Ben
 *
 */
public class Creature extends Sprite implements Damageable {
	public enum State {WALKING, RUNNING, ATTACKING};

	private double health;
	private double defense;
	private double agility;//a percent chance that the bullet is dodged

	private State state;

	private int animationPos;//Position in arrayList
	private int animationCounter;//How many frames you have been on the current image for

	private int framesPerWalking;//number of frames until you go to the next image (ie animationPos ++)
	private ArrayList<PImage> walking;//images in walking

	private int framesPerRunning;
	private ArrayList<PImage> running;

	private int framesPerAttacking;
	private ArrayList<PImage> attacking;

	private double vX;
	private double vY;
	private double maxV;

	public Creature(int x, int y, int w, int h) {
		super(x, y, w, h);
	}

	public void draw(PApplet marker) {
		if(state == State.WALKING) {
			if(animationPos > walking.size())
				animationPos = 0;
			marker.image(walking.get(animationPos), (float)getX(), (float)getX());

			if(animationCounter>framesPerWalking) {
				animationCounter = 0;
				animationPos++;
			}
		}

		if(state == State.RUNNING) {
			if(animationPos > running.size())
				animationPos = 0;
			marker.image(running.get(animationPos), (float)getX(), (float)getX());

			if(animationCounter>framesPerRunning) {
				animationCounter = 0;
				animationPos++;
			}
		}
		
		if(state == State.ATTACKING) {
			if(animationPos > attacking.size())
				animationPos = 0;
			marker.image(attacking.get(animationPos), (float)getX(), (float)getX());

			if(animationCounter>framesPerAttacking) {
				animationCounter = 0;
				animationPos++;
			}
		}
	}

	public void takeDamage(Destructive d) {
		d.dealDamage(this);
	}

	public void takeDamage(double damage) {
		if(Math.random()>agility) {
			health = health - damage* (1-defense);
		}
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
	
	/**
	 * Calculates this creatures current velocity from its x and y velocity
	 * @return
	 */
	public double calculateVelocity() {
		return Math.sqrt(vX*vX+vY*vY);
	}

}
