package scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import gamePlay.Button;
import gamePlay.Main;
import processing.core.PApplet;
import sprites.Barrier;
import sprites.Hero;
import sprites.Sprite;

/**
 * 
 * 
 *
 *@author Jose
 * 
 * @version 8/21/18 8:51
 */
public class Camp extends Scene {

	public Camp(Main m, Hero joe) {
		super(m, joe);
		add(new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/6, 400, 50, "Next Day", new Color(0,0,0), "*BattleField"));
		add(new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/3, 400, 50, "Upgrade Max Health by 20", new Color(0,0,0), "actionIncreaseHealth"));
		add(new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/2, 400, 50, "Upgrade Max Stamina by 5", new Color(0,0,0), "actionIncreaseStamina"));
		add(new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT*2/3, 400, 50, "Upgrade Damage by 8", new Color(0,0,0), "actionIncreaseDamage"));
	}

	public void draw() {
		background(255);
		super.draw();
		
		
		
		
		
	}	
	
	public void keyPressed() {
		super.keyPressed();
		if(keyCode == KeyEvent.VK_U) {
			super.changePanelAndPause("TitleScreen");
		}
	}	
}
