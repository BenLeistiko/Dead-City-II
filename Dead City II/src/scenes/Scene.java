package scenes;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.*;
import processing.core.PApplet;


public abstract class Scene extends PApplet {

	private ArrayList<Drawable> toDraw;//Everything to draw that is NOT a sprite
	private ArrayList<Clickable> mouseInput;
	private ArrayList<Typeable> keyInput;

	private Main m;

	private Rectangle2D.Double renderSpace;

	public Scene (Main m) {
		this.m=m;
		toDraw = new ArrayList<Drawable>();
		mouseInput = new ArrayList<Clickable>();
		keyInput = new ArrayList<Typeable>();
		renderSpace = new Rectangle2D.Double(0,0,0,0);
	}

	public Rectangle2D.Double getVisSpace(){

		return new Rectangle2D.Double(0, 0, width, height);
	}

	public void setRenderSpace(Rectangle2D.Double r) {
		renderSpace = r;
	}

	public void updateRenderSpace(double xTrans, double yTrans) {
		renderSpace.setRect(renderSpace.getX()+xTrans, renderSpace.getY()+yTrans, renderSpace.getWidth(), renderSpace.getHeight());
	}

	public void draw(PApplet marker) {
		
		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i).shouldRemove()) {
				toDraw.remove(i);
			}else if(toDraw.get(i).getHitBox().intersects(renderSpace)) {
				toDraw.get(i).draw(this);
			}
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

//	public void draw(PApplet kik, boolean wow) {
//		if(wow) {
//		kik.fill(0);
//		kik.ellipse(kik.width/2, kik.height/2, 100000, 100);
//		System.out.println("wow");
//		}
//	}
	
	
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
	public void add(Object ... objects) {
		for(Object o:objects) {
			if(o instanceof Drawable)
				toDraw.add((Drawable)o);
			if(o instanceof Clickable)
				mouseInput.add((Clickable)o);
			if(o instanceof Typeable)
				keyInput.add((Typeable)o);
		}
	}


}
