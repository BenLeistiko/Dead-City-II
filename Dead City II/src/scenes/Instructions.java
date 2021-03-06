package scenes;

import java.awt.Color;
import java.awt.event.KeyEvent;

import Menus.Button;
import gamePlay.Main;
import processing.core.PImage;
import sprites.Hero;

/**
 * 
 * The place where player's can learn about the game.
 * @author Ben
 * 
 * @version 8/21/18 8:51
 *
 */
public class Instructions extends Scene{
	PImage instr;
	public Instructions(Main m, Hero joe) {
		super(m, joe);
		add(new Button(ASSUMED_DRAWING_WIDTH/2, ASSUMED_DRAWING_HEIGHT-75, 400, 50, "Main Menu", new Color(0,0,0), "TitleScreen"));
		instr = Main.resources.getImage("Instr");
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
