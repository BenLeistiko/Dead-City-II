package scenes;

import java.awt.Color;

import gamePlay.Button;
import gamePlay.Main;

public class Death extends Scene {

	
	
	public Death(Main m) {
		super(m);
	}

	public void setup() {
		Button exit = new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/2, 400, 50,"EXIT",new Color(0,0,0), this, "exit");
		add(exit);
	}

	public void draw() {
		background(100);
		super.draw();
		pushMatrix();
		
		scale((float) super.getxRatio(), (float) super.getyRatio());
		this.translate((float)(-getScreenSpace().getX()), (float) -getScreenSpace().getY());
		textAlign(CENTER,CENTER);
		textSize(100);
		fill(255,0,0);
		text("YOU DIED",(float)(ASSUMED_DRAWING_WIDTH/2),(float)(ASSUMED_DRAWING_HEIGHT/2-ASSUMED_DRAWING_HEIGHT/4));
		popMatrix();
	}
	
	
	
	
	
}
