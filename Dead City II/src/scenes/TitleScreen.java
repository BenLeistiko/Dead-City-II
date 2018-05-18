package scenes;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import gamePlay.Button;
import gamePlay.Main;
//import gifAnimation.*;
import gamePlay.*;
import processing.core.PApplet;

public class TitleScreen extends Scene {
	
	public TitleScreen(Main m) {
		super(m);
	}

	public void setup() {
		
		Button startGame = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"START",new Color(0,0,0), this, "BattleField");
		Button camp = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, 2*Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"CAMP",new Color(0,0,0), this, "Camp");
		Button exitGame = new Button(Scene.ASSUMED_DRAWING_WIDTH/2, 3*Scene.ASSUMED_DRAWING_HEIGHT/4, 400, 50,"EXIT",new Color(0,0,0), this, "Exit");

		super.add(startGame,exitGame,camp);
	}


	public void draw() {
		background(255);
	
		super.draw();
		//image(Main.resources.getAnimation("TrooperRunning",0),100,100);

		//	textAlign(CENTER,CENTER);
		//	rectMode(this.CENTER);


	}
}