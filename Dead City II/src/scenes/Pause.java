package scenes;

import java.awt.Color;

import gamePlay.Button;
import gamePlay.Main;

public class Pause extends Scene{

	private Button mainMenu;
	
	
	
	
	
	public Pause(Main m) {
		super(m);
		mainMenu = new Button(width/2, height/2, 400, 150,"RETURN TO MAIN MENU",new Color(255,0,0), new Color(0,255,0));

	}

	public void draw() {
			background(125);
			
			
			textAlign(CENTER,CENTER);

			textSize(100);
			text("PAUSED",(float)(getVisSpace().getX()+getVisSpace().getWidth()/2),(float)(getVisSpace().getY()+ getVisSpace().getHeight()/2));
			
			mainMenu.setCoordsAndDraw(this, width/2, height*2/3);
			
			
			//textSize(40);
			//text("Push '1' to return to Main Menu",(float)(getVisSpace().getX()+getVisSpace().getWidth()/2),(float)(getVisSpace().getY()+ getVisSpace().getHeight()*2/3));
		
	}











}
