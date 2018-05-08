package gamePlay;

import java.util.ArrayList;

import items.*;
import processing.core.PApplet;
import sprites.Barrier;
import sprites.Bullet;
import sprites.Creature;
import sprites.DamageableBarrier;
import sprites.Hero;
import sprites.Sprite;

public class DrawingSurface extends PApplet{

	public static ResourceLoader resources = new ResourceLoader();
	private Hero joe;
	ArrayList<Sprite> mobs;
	Barrier b1, b2, b3;
	ArrayList<Bullet> bullets;

	public void setup() {
		resources.load(this);

		ArrayList<Sprite> mobs = new ArrayList<Sprite>();
		bullets = new ArrayList<Bullet>();

		joe = new Hero("Trooper",100,100,200,200, new RangedWeapon(10, 500, 1000, 10, bullets), mobs);
		joe.setState(Creature.State.WALKING);
		b1 = new Barrier(0, 800, 1000, 100);
		b2 = new Barrier(600,550,250,50);
		b3 = new DamageableBarrier(600, 650, 100,100,100,0.5);
		mobs.add(joe);
		mobs.add(b1);
		mobs.add(b2);
		mobs.add(b3);
	}

	public void draw() {
		background(255);
		b1.drawHitBox(this);
		b2.drawHitBox(this);
		joe.draw(this);
		joe.drawHitBox(this);
		b1.draw(this);
		b2.draw(this);
		b3.draw(this);
		for(Bullet b: bullets) {
			b.draw(this);
		}




	}


	public void mousePressed() {
		joe.mousePressed(this);
	}
	public void mouseReleased() {
		joe.mouseReleased(this);
	}

	public void keyPressed() {
		joe.keyPressed(this);
	}
	public void keyReleased() {
		joe.keyReleased(this);
	} 








}
