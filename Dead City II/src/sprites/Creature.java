package sprites;

import java.util.ArrayList;

import gamePlay.Main;
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
	private static final double SPRINT_SPEED = 2;//the number of times faster that sprint speed is

	public enum State {WALKING, RUNNING, ATTACKING, JUMPING};

	private double health;
	private double defense;
	private double agility;//a percent chance that the bullet is dodged
	private double stamina;
	private double speed;
	private double jumpPower;


	private State state;

	private int animationPos;//Position in arrayList
	private int animationCounter;//How many frames you have been on the current image for

	private int framesPerWalking;//number of frames until you go to the next image (ie animationPos ++)
	private ArrayList<PImage> walking;//images in walking

	private int framesPerRunning;
	private ArrayList<PImage> running;

	private int framesPerAttacking;
	private ArrayList<PImage> attacking;

	private int framesPerJumping;
	private ArrayList<PImage> jumping;

	private double vX;
	private double vY;

	ArrayList<Sprite> collision;

	public Creature(int x, int y, int w, int h, ArrayList<Sprite> collision) {
		super(x, y, w, h);
		this.collision =  collision;
	}

	public void draw(PApplet marker) {

		
		
		super.moveByAmount(vX*Main.frameTime, vY*Main.frameTime);

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

		if(state == State.JUMPING) {
			if(animationPos > jumping.size())
				animationPos = 0;
			marker.image(jumping.get(animationPos), (float)getX(), (float)getX());

			if(animationCounter>framesPerJumping) {
				animationCounter = 0;
				animationPos++;
			}
		}
	}

	/**
	 * sets the animation state of this creature
	 * @param state - the state to set this creature to
	 * @return true if the state changed, else false
	 */
	public boolean setState(State state) {
		if(this.state != state) {
			this.state = state;
			animationPos = 0;
			animationCounter = 0;
			return true;
		}
		
		return false;
	}

	public void takeDamage(Destructive d) {
		d.dealDamage(this);
	}

	public void takeDamage(double damage) {
		if(Math.random()>agility) {
			health = health - damage* (1-defense);
		}
	}
	
	public void jump() {
		if(setState(state.JUMPING)) {
			state = state.JUMPING;
			vY = jumpPower;
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
}