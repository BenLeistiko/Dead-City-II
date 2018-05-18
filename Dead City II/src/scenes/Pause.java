package scenes;

import java.awt.Color;

import gamePlay.Button;
import gamePlay.Main;

public class Pause extends Scene{	
	public Pause(Main m) {
		super(m);
	}

	public void setup() {
		Button mainMenu = new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/2, 400, 50,"RETURN TO MAIN MENU",new Color(0,0,0), this, "TitleScreen");
		add(mainMenu);
	}

	public void draw() {
		super.draw();
		pushMatrix();
		scale((float) super.getxRatio(), (float) super.getyRatio());
		this.translate((float)(-getScreenSpace().getX()), (float) -getScreenSpace().getY());
		textAlign(CENTER,CENTER);
		textSize(100);
		text("PAUSED",(float)(ASSUMED_DRAWING_WIDTH/2),(float)(ASSUMED_DRAWING_HEIGHT/2-ASSUMED_DRAWING_HEIGHT/4));
		popMatrix();
	}
}