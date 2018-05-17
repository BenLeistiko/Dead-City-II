package scenes;

import java.awt.Color;

import gamePlay.Button;
import gamePlay.Main;
//import gifAnimation.*;
import gamePlay.*;
import processing.core.PApplet;

public class TitleScreen extends Scene {

	private Button startGame,exitGame;

	public TitleScreen(Main m) {
		super(m);
		this.startGame = new Button(width/2, height/2, 400, 150,"START",new Color(255,0,0), new Color(0,255,0));
		this.exitGame = new Button(width/2, height/2, 400, 150,"EXIT",new Color(255,0,0), new Color(0,255,0));
	}

	public void setup() {
		Main.resources.load(this);
		
	}


	public void draw() {
		background(255);


		startGame.draw(this);
		startGame.setCoords(width/2, height/2);

		exitGame.draw(this);
		exitGame.setCoords(width/2, height*2/3);

		act();

		textSize(30);
		fill(0);

		//image(Main.resources.getAnimation("TrooperRunning",0),100,100);

		//	textAlign(CENTER,CENTER);
		//	rectMode(this.CENTER);


	}

	public void act() {
		if(startGame.isPressed()) {
			super.changePanelAndPause(this, "BattleField");
		}

		if(exitGame.isPressed()) {
			exit();
		}
	}

	public void keyPressed() {

	}

	public void keyReleased() {

	}

	public void mousePressed() {
		startGame.mousePressed(this);
		exitGame.mousePressed(this);
	}

	public void mouseReleased() {
		startGame.mouseReleased(this);
		exitGame.mouseReleased(this);
	}



}
