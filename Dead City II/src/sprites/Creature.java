package sprites;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

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

	public enum State {STANDING, WALKING, RUNNING, ATTACKING, JUMPING, MOVINGANDATTACKING};

	private double health;
	private double defense;
	private double agility;//a percent chance that the bullet is dodged
	private double stamina;
	private double speed;
	private double jumpPower;
	
	private double maxHealth;
	private double maxStamina;
	
	private double regenHealth;//in Health per second
	private double regenStamina;//in Stamina per second

	private int direction;//the direction that the creature is moving.  1=right, -1=left, 0=bugs!!

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

	private int framesPerMovingAndAttacking;
	private ArrayList<PImage> movingAndAttacking;

	private boolean isSprinting;
	private boolean onASurface;
	private boolean isAttacking;

	private ArrayList<Sprite> worldlyThings;

	/**
	 * Creates a new creature.  It's state is currently standing when created.  It will stay 2 frames on each picture by defualt.
	 * @param x - sprite x location
	 * @param y - sprite y location
	 * @param w - sprite width
	 * @param h -sprite height
	 * @param worldlyThings - everything that this sprite can collide with
	 * @param animationKey - what pictures you want to load
	 */
	public Creature(double x, double y, double w, double h, ArrayList<Sprite> worldlyThings, String animationKey) {
		super(x, y, w, h);
		onASurface = false;
		isSprinting = false;
		isAttacking = false;

		standing = Main.resources.getAnimationList(animationKey+"Standing");
		walking = Main.resources.getAnimationList(animationKey+"Walking");
		running = Main.resources.getAnimationList(animationKey+"Running");
		attacking = Main.resources.getAnimationList(animationKey+"Attacking");
		jumping = Main.resources.getAnimationList(animationKey+"Jumping");

		movingAndAttacking = Main.resources.getAnimationList(animationKey+"MovingAndAttacking");

		animationPos = 0;
		animationCounter = 0;
		state = State.STANDING;

		framesPerStanding = 4;
		framesPerWalking = 2;
		framesPerRunning = (int) Math.round((double)(framesPerWalking)/SPRINT_SPEED);
		framesPerAttacking = 4;
		framesPerJumping = 4;
		framesPerMovingAndAttacking = 2;

	//	health = 100;
		health = Main.resources.getStat(animationKey, Main.resources.HEALTH);
		//defense = .5;
		defense = Main.resources.getStat(animationKey, Main.resources.ARMOUR);
		
	//	agility = 0;
		agility = Main.resources.getStat(animationKey, Main.resources.AGILITY);
		stamina = Main.resources.getStat(animationKey, Main.resources.STAMINA);
		//speed = 10;
		speed = Main.resources.getStat(animationKey, Main.resources.SPEED);
		jumpPower = 25;
		
		maxHealth = health;
		maxStamina = stamina;
		
//		regenHealth = 1;
		regenHealth = Main.resources.getStat(animationKey, Main.resources.HEALTHREGEN);
	//	regenStamina = 1;
		regenStamina = Main.resources.getStat(animationKey, Main.resources.STAMINAREGEN);

		direction = 1;

		this.worldlyThings =  worldlyThings;
	}



	public void act() {
		
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

		// ***********X AXIS*********** 

		setvX(getvX() * Main.FRICTION);

		double xCoord2 = xCoord + getvX();

		Rectangle2D.Double strechX = new Rectangle2D.Double(Math.min(xCoord,xCoord2),yCoord2,width+Math.abs(getvX()),height);

		if (getvX() > 0) {
			Sprite rightSurface = null;
			for (Sprite s : worldlyThings) {
				if (s.intersects(strechX)&& s instanceof Barrier) {
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
				if (s.intersects(strechX)&& s instanceof Barrier) {
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
		
		
		if(isAttacking) {
			if(Math.abs(getvX()) < 0.000001 || !isOnSurface()) {
				state = State.ATTACKING;
			}else {
				state = State.MOVINGANDATTACKING;
			}
		}else if(Math.abs(getvX()) < 0.000001 && isOnSurface()) {
			state = State.STANDING;
		}else if(!isOnSurface()) {
			state = State.JUMPING;
		}else if(isSprinting) {
			state = State.RUNNING;		
		}else {
			state = State.WALKING;
		}
		
		if(health < maxHealth) {
			health = health + this.regenHealth*Main.frameTime;
		}else {
			health = maxHealth; 
		}
		
		if(stamina < maxStamina) {
			stamina = stamina + this.regenStamina*Main.frameTime;
		}else {
			stamina = maxStamina; 
		}
	}

	public void attack() {
		isAttacking = true;
	}

	public void setSprint(boolean isSprinting) {
		this.isSprinting = isSprinting;
	}

	public void draw(PApplet marker) {

		marker.pushMatrix();

		marker.scale((float)direction, 1f);
		if(state == State.STANDING) {
			if(animationPos >= standing.size())
				animationPos = 0;
			marker.image(standing.get(animationPos), (direction == 1)? (float)getX():direction*(float)getX()-(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());

			if(animationCounter>framesPerStanding) {
				animationCounter = 0;
				animationPos++;
			}
			animationCounter++;
		}else if(state == State.WALKING) {
			if(animationPos >= walking.size())
				animationPos = 0;
			marker.image(walking.get(animationPos), (direction == 1)? (float)getX():direction*(float)getX()-(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());	
			if(animationCounter>framesPerWalking) {
				animationCounter = 0;
				animationPos++;
			}
			animationCounter++;
		}else if(state == State.RUNNING) {
			if(animationPos >= running.size())
				animationPos = 0;
			marker.image(running.get(animationPos), (direction == 1)? (float)getX():direction*(float)getX()-(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());

			if(animationCounter>framesPerRunning) {
				animationCounter = 0;
				animationPos++;
			}
			animationCounter++;
		}else if(state == State.ATTACKING) {
			if(animationPos >= attacking.size()) {
				animationPos = 0;
				isAttacking = false;
			}
			marker.image(attacking.get(animationPos), (direction == 1)? (float)getX():direction*(float)getX()-(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());

			if(animationCounter>framesPerAttacking) {
				animationCounter = 0;
				animationPos++;
			}
			animationCounter++;
		}else if(state == State.MOVINGANDATTACKING) {
			if(animationPos >= movingAndAttacking.size()) {
				animationPos = 0;
				isAttacking = false;
			}
			marker.image(movingAndAttacking.get(animationPos), (direction == 1)? (float)getX():direction*(float)getX()-(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());

			if(animationCounter>framesPerMovingAndAttacking) {
				animationCounter = 0;
				animationPos++;
			}
			animationCounter++;
		}else if(state == State.JUMPING) {
			if(animationPos >= jumping.size())
				animationPos = 0;
			marker.image(jumping.get(animationPos), (direction == 1)? (float)getX():direction*(float)getX()-(float)getWidth(), (float)getY(), (float)getWidth(), (float)getHeight());

			if(animationCounter>framesPerJumping) {
				animationCounter = 0;
				animationPos++;
			}
			animationCounter++;
		}

		marker.popMatrix();
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
		if(state != State.JUMPING && isOnSurface()) {
			setvY(-jumpPower);
		}
	}

	public boolean isOnSurface() {
		return onASurface;
	}



	public double getStamina() {
		return stamina;
	}

	public void setStamina(double stamina) {
		this.stamina = stamina;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	public void setvX(double vX) {
		if(Math.abs(vX)<0.000001) {
		}else if(vX>0) {
			direction = 1;
		}else if(vX<0) {
			direction = -1;
		}
		super.setvX(vX);
	}

	public static double getSprintSpeed() {
		return SPRINT_SPEED;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public ArrayList<Sprite> getWorldlyThings() {
		return worldlyThings;
	}	

	public double getHealth() {
		return health;
	}

	public boolean shouldRemove(){
		return health<=0;
	}
	
	public double getMaxHealth() {
		return maxHealth;
	}
	
	public void setMaxHealth() {
		 health = maxHealth;
	}
	
	
}