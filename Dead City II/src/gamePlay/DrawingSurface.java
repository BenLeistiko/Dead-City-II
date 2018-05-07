package gamePlay;

import java.util.ArrayList;


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
		joe = new Hero("Joe",100,100,100,100, null);
		mobs.add(joe);
		
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
