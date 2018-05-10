package scenes;

import gamePlay.Main;
import processing.core.PApplet;

public class MainMenu extends Scene {
	//private Main m;

	public MainMenu(Main m) {
		super(m);
	}

	/*
	public void draw(PApplet marker) {
		System.out.println("MAIN MENU");
		marker.pushMatrix();
		marker.textSize(30);
		marker.fill(0);

		marker.textAlign(marker.CENTER,marker.CENTER);

		marker.text("Dead City II", marker.width/2, marker.height/2);
		marker.rect(marker.width/2, marker.height/2, 2, 2);


		marker.popMatrix();
	}
*/

	public void draw() {
	
		pushMatrix();
		textSize(30);
		fill(0);

		textAlign(CENTER,CENTER);

		text("Dead City II", width/2, height/2);
		rect(width/2, height/2, 2, 2);


		popMatrix();
	}

	public void keyPressed() {
		super.changePanelAndPause(this, "BattleField");
	}


	public void keyPressed(PApplet marker) {


	}

	public void keyReleased(PApplet marker) {


	}



}
