package scenes;

import java.util.ArrayList;

import gamePlay.Main;
import interfaces.*;
import processing.core.PApplet;


public abstract class Scene extends PApplet {

	private ArrayList<Drawable> toDraw;//Everything to draw that is NOT a sprite
	private ArrayList<Clickable> mouseInput;
	private ArrayList<Typeable> keyInput;

	private Main m;
	
	public Scene (Main m) {
		this.m=m;
		
		toDraw = new ArrayList<Drawable>();
		mouseInput = new ArrayList<Clickable>();
		keyInput = new ArrayList<Typeable>();
	}

	public void draw(PApplet marker) {

		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i).shouldRemove()) {
				toDraw.remove(i);
			}else
				toDraw.get(i).draw(this);
		}
		for(int i = 0; i < mouseInput.size(); i++) {
			if(mouseInput.get(i).shouldRemove())
				mouseInput.remove(i);
		}
		for(int i = 0; i < keyInput.size(); i++) {
			if(keyInput.get(i).shouldRemove())
				keyInput.remove(i);
		}
	}

	public void mousePressed() {
		for(Clickable c:mouseInput) {
			c.mousePressed(this);
		}
	}
	
	public void mouseReleased() {
		for(Clickable c:mouseInput) {
			c.mouseReleased(this);
		}
	}

	public void keyPressed() {		
		for(Typeable t :keyInput) {
			t.keyPressed(this);
		}
	}
	
	public void keyReleased() {
		for(Typeable t :keyInput) {
			t.keyReleased(this);
		}
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
		pause(this,true);
	}

	public void changePanelAndPause(PApplet marker, String name) {
		m.changePanel(name);
		pause(marker,true);
	}

	public void changePanel(PApplet marker, String name,boolean pause) {
		m.changePanel(name);
		pause(marker,pause);
	}

	public void addDrawable(Drawable d) {
		toDraw.add(d);
	}
	
	public void addClickable(Clickable c) {
		mouseInput.add(c);
	}
	
	public void addTypeable(Typeable t) {
		keyInput.add(t);
	}
	
	/**
	 * Only use if you have to, this is much slower
	 * @param o - the object to 
	 */
	public void add(Object o) {
		if(o instanceof Drawable)
			toDraw.add((Drawable)o);
		if(o instanceof Clickable)
			mouseInput.add((Clickable)o);
		if(o instanceof Typeable)
			keyInput.add((Typeable)o);
	}
}
