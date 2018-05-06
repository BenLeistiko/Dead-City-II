package sprites;

import interfaces.Damageable;
import interfaces.Destructive;
import processing.core.PApplet;

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
	
	private int animationPos;

	public Creature(int x, int y, int w, int h) {
		super(x, y, w, h);
	}
	
	public void draw(PApplet marker) {
	
		
	}

	public void takeDamage(Destructive d) {
		d.dealDamage(this);
	}

	public void takeDamage(double damage) {
		if(Math.random()>agility) {
			health = health - damage* (1-defense);
		}
	}

}
