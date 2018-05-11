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

	public BattleField(Main m) {
		super(m);	
		worldlyThings = new ArrayList<Sprite>();
	}

	public void setup() {	
		Main.resources.load(this);
		Barrier b= new Barrier(0, 500, 200, 50);
		
		
		
		Hero joe = new Hero("Trooper", 100,100,200,200,new RangedWeapon(50,1000,20,10,this),worldlyThings);
		
		this.add(joe);
		
		this.add(b);
	}

	public void draw() {
		background(255);
		super.draw(this);
	}

	public void removeDeadSprites() {
		
	}
	
	public void keyPressed() {
		super.keyPressed();
		if(keyCode == 85)
			super.changePanelAndPause(this, "MainMenu");
	}
	
	public void add(Object o) {
		super.add(o);
		if(o instanceof Sprite) 
			worldlyThings.add((Sprite) o);
	}
}