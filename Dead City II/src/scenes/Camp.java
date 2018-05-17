package scenes;

import java.awt.event.KeyEvent;

import gamePlay.Main;
import processing.core.PApplet;

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
			super.changePanelAndPause(this, "TitleScreen");
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
