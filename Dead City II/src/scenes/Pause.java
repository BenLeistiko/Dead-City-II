package scenes;

import java.awt.Color;

import gamePlay.Button;
import gamePlay.Main;
import sprites.Hero;

/**
 * A quick scene that allows temporary stoppage of the battle in battlefeild
 * @author Jose
 * @version 5/20/18
 */
public class Pause extends Scene{	
	public Pause(Main m,Hero joe) {
		super(m, joe);
	}

	public void setup() {
		Button mainMenu = new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/2, 400, 50,"RETURN TO MAIN MENU",new Color(0,0,0), "TitleScreen");
		add(mainMenu);
	}

	public void draw() {
		background(100);
		super.draw();
		pushMatrix();
		scale((float) super.getxRatio(), (float) super.getyRatio());
		this.translate((float)(-getScreenSpace().getX()), (float) -getScreenSpace().getY());
		textAlign(CENTER,CENTER);
		textSize(100);
		text("PAUSED",(float)(ASSUMED_DRAWING_WIDTH/2),(float)(ASSUMED_DRAWING_HEIGHT/2-ASSUMED_DRAWING_HEIGHT/4));
		textSize(40);
		text("\n\n\n Push 'p' to return to the fight!",(float)(ASSUMED_DRAWING_WIDTH/2),(float)(ASSUMED_DRAWING_HEIGHT/2-ASSUMED_DRAWING_HEIGHT/4));
		//text("Push 'p' to play again",(float)(ASSUMED_DRAWING_WIDTH/2),(float)(ASSUMED_DRAWING_HEIGHT/2-ASSUMED_DRAWING_HEIGHT/4));
		popMatrix();
	}
}