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

	private ArrayList<Button> buttons;
	private Button startGame,exitGame,camp;


	public TitleScreen(Main m) {
		super(m);

		this.startGame = new Button(width/2, height/2, 400, 150,"START",new Color(255,0,0), new Color(0,255,0));
		this.camp = new Button(width/2, height/2, 400, 150,"CAMP",new Color(255,0,0), new Color(0,255,0));
		this.exitGame = new Button(width/2, height/2, 400, 150,"EXIT",new Color(255,0,0), new Color(0,255,0));

		buttons = new ArrayList<Button>();
		
		buttons.add(startGame);
		buttons.add(camp);
		buttons.add(exitGame);
	}

	public void setup() {
		Main.resources.load(this);

	}


	public void draw() {
		background(255);

		//.6666-.5 = distance between buttons = height*1/6
		startGame.setCoordsAndDraw(this,width/2, height/3);
		
		camp.setCoordsAndDraw(this,width/2, height*1/2);
		
		exitGame.setCoordsAndDraw(this,width/2, height*2/3);
		


		act();

	
		//image(Main.resources.getAnimation("TrooperRunning",0),100,100);

		//	textAlign(CENTER,CENTER);
		//	rectMode(this.CENTER);


	}

	public void act() {
		if(startGame.isPressed()) {
			super.changePanelAndPause(this, "BattleField");
		}

		if(camp.isPressed()) {
			super.changePanelAndPause(this, "Camp");
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
		for(Button b:buttons) {
			b.mousePressed(this);
		}
		
	}

	public void mouseReleased() {
		for(Button b:buttons) {
			b.mouseReleased(this);
		}
		
	}




}
