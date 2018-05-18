package scenes;

import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import gamePlay.Main;
import processing.core.PApplet;
import sprites.Barrier;

public class Camp extends Scene {

	public Camp(Main m) {
		super(m);
	}

	public void draw() {
		background(128);
		
		text("im a camp", width/2,height/2);
	}	
	
	public void keyPressed() {
		if(keyCode == KeyEvent.VK_U) {
			super.changePanelAndPause("TitleScreen");
		}
	}	
}
