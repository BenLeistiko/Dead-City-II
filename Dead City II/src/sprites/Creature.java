package sprites;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.DrawingSurface;
import gamePlay.Main;
import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;
import processing.core.PImage;

/**	Represents a creature class, with the help of Shelby's Animation Demo for collision detection
 * 
 * @author Ben
 *
 */
public abstract class Creature extends MovingSprite implements Damageable {
	private static final double SPRINT_SPEED = 2;//the number of times faster that sprint speed is

	public enum State {STANDING, WALKING, RUNNING, ATTACKING, JUMPING};

	private double health;
	private double defense;
	private double agility;//a percent chance that the bullet is dodged
	private double stamina;
	private double speed;
	private double jumpPower;


	private State state;

	private int animationPos;//Position in arrayList
	private int animationCounter;//How many frames you have been on the current image for
	
	private int framesPerStanding;//number of frames until you go to the next image (ie animationPos ++)
	private ArrayList<PImage> standing;//images in walking
	
	private int framesPerWalking;//number of frames until you go to the next image (ie animationPos ++)
	private ArrayList<PImage> walking;//images in walking

	private int framesPerRunning;
	private ArrayList<PImage> running;

	private int framesPerAttacking;
	private ArrayList<PImage> attacking;

	private int framesPerJumping;
	private ArrayList<PImage> jumping;


	private boolean facingRight;
	private boolean onASurface;
	
	ArrayList<Sprite> worldlyThings;

	/**
	 * Creates a new creature.  It's state is currently standing when created.  It will stay 2 frames on each picture by defualt.
	 * @param x - sprite x location
	 * @param y - sprite y location
	 * @param w - sprite width
	 * @param h -sprite height
	 * @param worldlyThings - everything that this sprite can collide with
	 * @param animationKey - what pictures you want to load
	 */
	public Creature(int x, int y, int w, int h, ArrayList<Sprite> worldlyThings, String animationKey) {
		super(x, y, w, h);
		onASurface = false;
		facingRight = false;
		
		standing = DrawingSurface.resources.getAnimationList(animationKey+"Standing");
		walking = DrawingSurface.resources.getAnimationList(animationKey+"Walking");
		running = DrawingSurface.resources.getAnimationList(animationKey+"Running");
		attacking = DrawingSurface.resources.getAnimationList(animationKey+"Attacking");
		jumping = DrawingSurface.resources.getAnimationList(animationKey+"Jumping");
		
		animationPos = 0;
		animationCounter = 0;
		state = State.STANDING;
		
		framesPerStanding = 2;
		framesPerWalking = 2;
		framesPerRunning = 2;
		framesPerAttacking = 2;
		framesPerJumping = 2;
		
		this.worldlyThings =  worldlyThings;
	}

	
	
	private void act(ArrayList<Sprite> worldlyThings) {

		double xCoord = getX();
		double yCoord = getY();
		double width = getWidth();
		double height = getHeight();

		// ***********Y AXIS***********

		setvY(getvY() + Main.GRAVITY); // GRAVITY
		double yCoord2 = yCoord + getvY();

		Rectangle2D.Double strechY = new Rectangle2D.Double(xCoord,Math.min(yCoord,yCoord2),width,height+Math.abs(getvY()));

		onASurface = false;

		if (getvY() > 0) {
			Sprite standingSurface = null;
			for (Sprite s : worldlyThings) {
				if (s.intersects(strechY)) {
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
				if (s.intersects(strechY)) {
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

		// ***********X AXIS***********


		setvX(getvX() * Main.FRICTION);

		double xCoord2 = xCoord + getvX();

		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(getvX()),height);

		if (getvX() > 0) {
			Sprite rightSurface = null;
			for (Sprite s : worldlyThings) {
				if (s.intersects(strechX)) {
					rightSurface = s;
					setvX(0);
				}
			}
			if (rightSurface != null) {
				Rectangle r = rightSurface.getBounds();
				xCoord2 = r.getX()-width;
			}
		} else if (getvX() < 0) {
			Sprite leftSurface = null;
			for (Sprite s : worldlyThings) {
				if (s.intersects(strechX)) {
					leftSurface = s;
					setvX(0);
				}
			}
			if (leftSurface != null) {
				Rectangle r = leftSurface.getBounds();
				xCoord2 = r.getX()+r.getWidth();
			}
		}


		if (Math.abs(getvX()) < .2)
			setvX(0);

		moveToLocation(xCoord2,yCoord2);
	}

	public void draw(PApplet marker) {
		act(worldlyThings);
		
		if(state == State.STANDING) {
			if(animationPos > standing.size())
				animationPos = 0;
			marker.image(standing.get(animationPos), (float)getX(), (float)getX(), (float)getWidth(), (float)getHeight());
			
			if(animationCounter>framesPerStanding) {
				animationCounter = 0;
				animationPos++;
			}
		}else if(state == State.WALKING) {
			if(animationPos > walking.size())
				animationPos = 0;
			marker.image(walking.get(animationPos), (float)getX(), (float)getX());	
			
			if(animationCounter>framesPerWalking) {
				animationCounter = 0;
				animationPos++;
			}
		}else if(state == State.RUNNING) {
			if(animationPos > running.size())
				animationPos = 0;
			marker.image(running.get(animationPos), (float)getX(), (float)getX());

			if(animationCounter>framesPerRunning) {
				animationCounter = 0;
				animationPos++;
			}
		}else if(state == State.ATTACKING) {
			if(animationPos > attacking.size())
				animationPos = 0;
			marker.image(attacking.get(animationPos), (float)getX(), (float)getX());

			if(animationCounter>framesPerAttacking) {
				animationCounter = 0;
				animationPos++;
			}
		}else if(state == State.JUMPING) {
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
		if(Math.random()>agility) {
			health = health - d.getDamage()* (1-defense);
		}
	}

	public void takeDamage(double damage) {
		if(Math.random()>agility) {
			health = health - damage* (1-defense);
		}
	}

	public void jump() {
		if(setState(state.JUMPING) && onASurface) {
			state = state.JUMPING;
			setvY(jumpPower);
		}
	}

	


	public Rectangle2D getLocationRect() {

		return this.getBounds2D();
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void swapFacingRight() {
		this.facingRight = !facingRight;
	}


}