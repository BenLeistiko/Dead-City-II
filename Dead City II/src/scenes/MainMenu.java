package scenes;

import processing.core.PApplet;

public class MainMenu extends Scene {


	public void draw(PApplet marker) {
		marker.pushMatrix();
		marker.textSize(30);
		marker.fill(0);
		
		marker.textAlign(marker.CENTER,marker.CENTER);
		
		marker.text("Dead City II", marker.width/2, marker.height/2);
		marker.rect(marker.width/2, marker.height/2, 2, 2);
	
		
		marker.popMatrix();
	}




	public void keyPressed(PApplet marker) {


	}


	public void keyReleased(PApplet marker) {


	}



}
