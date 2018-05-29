package scenes;

import java.awt.Color;

import Menus.Button;
import gamePlay.BetterSound;
import gamePlay.Main;
import sprites.Hero;




/**represents the death screen for when your hero dies
 * 
 * @author Jose
 * 
 * @version 8/21/18 8:52
 *
 */
public class Death extends Scene {

	BetterSound death;
	private boolean hasStarted;
	
	public Death(Main m, Hero joe) {
		super(m, joe);
		death = new BetterSound(Main.resources.getSound("Death"),false, true);
		hasStarted = false;
	}

	public void setup() {
		Button exit = new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/2, 400, 50,"EXIT",new Color(0,0,0), "exit");
		add(exit);
	}

	public void draw() {
		background(100);
		//image(Main.resources.getImage("DeathBackground"),0,0);
		super.draw();
		
		pushMatrix();
		
		scale((float) super.getxRatio(), (float) super.getyRatio());
		this.translate((float)(-getScreenSpace().getX()), (float) -getScreenSpace().getY());
		textAlign(CENTER,CENTER);
		textSize(100);
		fill(255,0,0);
		text("YOU DIED",(float)(ASSUMED_DRAWING_WIDTH/2),(float)(ASSUMED_DRAWING_HEIGHT/2-ASSUMED_DRAWING_HEIGHT/4));
		popMatrix();
		if(!super.getFocusedSprite().isAlive() && !hasStarted) {
			death.start();
			Main.playMusic = false;
			hasStarted = true;
		}
	}
	
	
	
	
	
}
