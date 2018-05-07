package gamePlay;

import java.util.ArrayList;

import items.*;
import processing.core.PApplet;
import sprites.Hero;
import sprites.Sprite;

public class DrawingSurface extends PApplet{

	public static ResourceLoader resources = new ResourceLoader();
	private Hero joe;
	ArrayList<Sprite> mobs;
	
	
	
	public void setup() {
	//	resources.load(this);
		ArrayList<Sprite> mobs = new ArrayList<Sprite>();
		joe = new Hero("Trooper",100,100,100,100, new MeleeWeapon(10,10,10), mobs);
		
		
	}
	
	public void draw() {
		
		joe.draw(this);
		
		for(Sprite s:mobs) {
			s.draw(this);
		}
		
		
		
		
		
	}
	
	
	public void mouseClicked() {
		joe.keyPressed(this);
	}
	
	
	public void mouseReleased() {
		joe.keyReleased(this);
	}
	
	
	
	
	
	
	
}
