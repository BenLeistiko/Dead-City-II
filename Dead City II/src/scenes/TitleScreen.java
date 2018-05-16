package scenes;

import gamePlay.Button;
import gamePlay.Main;
import processing.core.PApplet;

public class TitleScreen extends Scene {
	private Button b;

	public TitleScreen(Main m) {
		super(m);
		this.b = new Button(width/2, height/2, 200, 200,"button");
	}


	public void draw() {
		background(255);
	//	System.out.println("x-coord: " + mouseX + " y-coord: " + mouseY);
		
		b.draw(this);
		b.setCoords(width/2, height/2);

		textSize(30);
		fill(0);



		textAlign(CENTER,CENTER);

	//	text("Dead City II", width/2, height/2);
		rectMode(this.CENTER);
		rect(width/2, height/2, 200, 2);
		rect(width/2, height/2, 2, 30);



	}

	public void keyPressed() {
		super.changePanelAndPause(this, "BattleField");
	}

	public void mousePressed() {
		b.mousePressed(this);
	}
	
	public void mouseReleased() {
		b.mouseReleased(this);
	}



}
