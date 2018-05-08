package gamePlay;

import java.util.ArrayList;

import items.*;
import processing.core.PApplet;
import sprites.Barrier;
import sprites.Creature;
import sprites.Hero;
import sprites.Sprite;

public class DrawingSurface extends PApplet{

	public static ResourceLoader resources = new ResourceLoader();
	private Hero joe;
	ArrayList<Sprite> mobs;
	Barrier b1, b2;


	public void setup() {
		resources.load(this);

		ArrayList<Sprite> mobs = new ArrayList<Sprite>();

		joe = new Hero("Trooper",100,100,200,200, new MeleeWeapon(10,10,10), mobs);
		joe.setState(Creature.State.WALKING);
		b1 = new Barrier(0, 800, 1000, 100);
		b2 = new Barrier(600,550,250,50);

		mobs.add(b1);
		mobs.add(b2);

	}

	public void draw() {
		background(255);
		b1.drawHitBox(this);
		b2.drawHitBox(this);
		joe.draw(this);







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
