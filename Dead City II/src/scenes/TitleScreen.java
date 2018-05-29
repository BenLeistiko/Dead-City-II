package scenes;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import Menus.Button;
//import gifAnimation.*;
import gamePlay.*;
import processing.core.PApplet;
import sprites.Hero;

/**
 * The main screen that players see when the game starts.  it gives them multiple options via buttons.
 * 
 * @author Jose
 * 
 * @version 8/21/18 8:51
 *
 */
public class TitleScreen extends Scene {

	public TitleScreen(Main m, Hero joe) {
		super(m, joe);
	}

	public void setup() {

		Button startGame = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"START",new Color(0,0,0), "BattleField");
		//Button camp = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, 2*Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"CAMP",new Color(0,0,0), "Camp");
		Button instructions = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, 2*Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"INSTRUCTIONS",new Color(0,0,0), "Instructions");
		Button exitGame = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, 3*Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"EXIT",new Color(0,0,0), "Exit");

		super.add(startGame,instructions,exitGame);
	}


	public void draw() {
		background(255);
		image(Main.resources.getImage("TitleScreenBackground"),0,0);
		super.draw();
		


	}
}