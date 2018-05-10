package scenes;

import java.util.ArrayList;

import gamePlay.Main;
import interfaces.*;
import processing.core.PApplet;


public abstract class Scene extends PApplet implements Drawable, Typeable, Clickable {

	private ArrayList<Drawable> drawables;;
	private ArrayList<Typeable> typeables;
	private ArrayList<Clickable> clickables;

	private Main m;


	public Scene (Main m) {
		this.m=m;
	}

	public void draw(PApplet marker) {
//		System.out.println("SCENE DRAW");
//		for(Drawable d:drawables) {
//			d.draw(marker);
//		}
	}


	public void pause(PApplet marker,boolean paused) {
		if (paused)
			marker.noLoop();
		else
			marker.loop();
	}

	public void runMe() {
		super.sketchPath();
		super.initSurface();
		super.surface.startThread();

		// runSketch();

		pause(this,true);
	}

	public void changePanelAndPause(PApplet marker, String name) {
		m.changePanel(name);
		pause(marker,true);
	}

	public void changePanelAndPause(PApplet marker, String name,boolean pause) {
		m.changePanel(name);
		pause(marker,pause);
	}

}
