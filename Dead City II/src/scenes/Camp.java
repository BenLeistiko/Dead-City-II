package scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import gamePlay.Button;
import gamePlay.Main;
import processing.core.PApplet;
import sprites.Barrier;
import sprites.Sprite;

public class Camp extends Scene {

	public Camp(Main m, Sprite hero) {
		super(m);
		add(new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT/2, 400, 50, "Next Day", new Color(0,0,0), this, "BattleField-NEW"));
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
