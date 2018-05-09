package scenes;

import java.util.ArrayList;

import processing.core.PApplet;
import sprites.Bullet;
import sprites.Sprite;

public class BattleField extends Scene {
	
	ArrayList<Bullet> bullets;
	ArrayList<Sprite> worldlyThings;

	public void draw(PApplet marker) {
		marker.pushMatrix();
		marker.textSize(30);
		marker.fill(255,0,0);
		
		marker.textAlign(marker.CENTER,marker.CENTER);
		
		marker.text("BATTLEFIELD City II", marker.width/2, marker.height/2);
		marker.rect(marker.width/2, marker.height/2, 2, 2);
	
		
		marker.popMatrix();	
		
	}


	public void keyPressed(PApplet marker) {

		
	}

	
	public void keyReleased(PApplet marker) {
	
		
	}

}
