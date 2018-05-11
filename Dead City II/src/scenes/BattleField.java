package scenes;

import java.util.ArrayList;

import gamePlay.Main;
import gamePlay.ResourceLoader;
import interfaces.Clickable;
import interfaces.Damageable;
import interfaces.Destructive;
import interfaces.Drawable;
import interfaces.Typeable;
import items.RangedWeapon;
import processing.core.PApplet;
import sprites.Barrier;
import sprites.Bullet;
import sprites.Creature;
import sprites.DamageableBarrier;
import sprites.Hero;
import sprites.Monster;
import sprites.Sprite;

public class BattleField extends Scene {
	
	ArrayList<Sprite> worldlyThings;
	ArrayList<Drawable> toDraw;//Everything to draw that is NOT a sprite
	ArrayList<Clickable> mouseInput;
	ArrayList<Typeable> keyInput;

	public BattleField(Main m) {
		super(m);	
	}



	public void setup() {		
		Main.resources.load(this);

		worldlyThings = new ArrayList<Sprite>();
		bullets = new ArrayList<Bullet>();
		scenes = new ArrayList<Scene>();

		//	scenes.add(new MainMenu());
		//	scenes.add(new BattleField());


		zombie = new Monster("Trooper",100,100,200,200, worldlyThings);
		worldlyThings.add(zombie);
		
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

		//joe.draw(this);

		for(Sprite s:worldlyThings) {
			s.draw(this);
		}

		for(Bullet b: bullets) {
			b.draw(this);
		}

		//		for(Scene s:scenes) {
		//			s.draw(this);
		//		}

		removeDeadSprites(worldlyThings);


	}


	public void mousePressed() {
		joe.mousePressed(this);
	}
	public void mouseReleased() {
		joe.mouseReleased(this);
	}

	public void keyPressed() {		
		joe.keyPressed(this);
		if(keyCode == 85)
			super.changePanelAndPause(this, "MainMenu");
	}
	public void keyReleased() {
		joe.keyReleased(this);
	} 

	public void removeDeadSprites(ArrayList<Sprite> thingys) {
		for(int i = 0;i<thingys.size();i++) {
			if(thingys.get(i) instanceof Damageable) {
				Damageable d = ((Damageable) thingys.get(i));
				if(!d.checkAlive()) {
					thingys.remove(i);
				}
			}
		}
	}


	//	public void keyPressed() {
	//	super.changePanelAndPause(this, "MainMenu",true);
	//	}

	public void keyPressed(PApplet marker) {
	}


	public void keyReleased(PApplet marker) {


	}

}
