package gamePlay;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import interfaces.Damageable;
import items.*;
import processing.core.PApplet;
import scenes.*;
import sprites.*;

public class DrawingSurface extends PApplet{

	public static ResourceLoader resources = new ResourceLoader();
	private Hero joe;
	ArrayList<Sprite> worldlyThings;
	Barrier b1, b2, b3;
	ArrayList<Bullet> bullets;
	ArrayList<Scene> scenes;

	public void setup() {
		resources.load(this);

		worldlyThings = new ArrayList<Sprite>();
		bullets = new ArrayList<Bullet>();
		scenes = new ArrayList<Scene>();

		scenes.add(new MainMenu());
		scenes.add(new BattleField());



		joe = new Hero("Trooper",100,100,200,200, new RangedWeapon(30, 500, 1000, 10, bullets), worldlyThings);
		joe.setState(Creature.State.WALKING);
		b1 = new Barrier(0, 800, 1000, 100);
		b2 = new Barrier(600,550,250,50);
		b3 = new DamageableBarrier(600, 650, 100,100,100,0.5);
		worldlyThings.add(joe);
		worldlyThings.add(b1);
		worldlyThings.add(b2);
		worldlyThings.add(b3);
	}

	public void draw() {
		background(255);
		b1.drawHitBox(this);
		b2.drawHitBox(this);
		//	joe.draw(this);
		joe.drawHitBox(this);
		//	b1.draw(this);
		//	b2.draw(this);
		//b3.draw(this);

		for(Sprite s:worldlyThings) {
			s.draw(this);
		}

		for(Bullet b: bullets) {
			b.draw(this);
		}
	
		for(Scene s:scenes) {
			s.draw(this);
		}

		removeDeadSprites(worldlyThings);
		

	}


	public void mousePressed() {
		joe.mousePressed(this);
	}
	public void mouseReleased() {
		joe.mouseReleased(this);
	}

	public void keyPressed() {
//		if(keyCode == KeyEvent.VK_ESCAPE) {
//			//m.changePanel("1");
//			//super.pause(this, true);
//			System.out.println("WOWOWOOW");
//		}
		
		
		joe.keyPressed(this);
	}
	public void keyReleased() {
		joe.keyReleased(this);
	} 

	public void removeDeadSprites(ArrayList<Sprite> thingys) {
		for(int i = 0;i<thingys.size();i++) {
			if(thingys.get(i) instanceof Damageable) {
				Damageable d = ((Damageable) thingys.get(i));
				if(!d.isAlive()) {
					thingys.remove(i);
				}
			}
		}
	}






}
