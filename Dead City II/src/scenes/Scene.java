package scenes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.*;
import processing.core.PApplet;
import sprites.Monster;
import sprites.Sprite;



public abstract class Scene extends PApplet {
	//****We pretend the screen is this size, then later scale everything up/down to the correct size.  This way it doen't matter what size the windows are when they are created o resized.  It is 1/4 the old scale.
	public static final int ASSUMED_DRAWING_WIDTH = 1600;
	public static final int ASSUMED_DRAWING_HEIGHT = 900;
	
	private ArrayList<Drawable> toDraw;//Everything to draw that is NOT a sprite
	private ArrayList<Clickable> mouseInput;
	private ArrayList<Typeable> keyInput;
	private ArrayList<Sprite> worldlyThings;
	private ArrayList<Monster> zombies;

	private Main m;

	//****These are all in the assumed drawing coordinates before we translate*****
	private Rectangle2D.Double renderSpace;//Everything in here is drawn.  Otherwise it is not.
	private Rectangle2D.Double worldSpace;//This is the limits of the world.  Past this the camera will not follow the focusedSprite.
	private Rectangle2D.Double screenSpace;//This is the space that should be drawn onto ones screen.  Its width and height are the assumed width and hieghts.
	private Rectangle2D.Double characterSpace;//This is the Space that the focused Sprite is allowed to be in.  If he tries to move out of it the world will just be translated around him.

	double xRatio, yRatio;
	
	private Sprite focusedSprite;

	private boolean SideScroll;

	/**
	 * This creates a new scene.  If you don't want it to side scroll, just set the world space to the the assumed drawing width and height
	 * @param m - the main method
	 * @param focusedSprite - the sprite to follow with the camera.  If this scene doesn't scroll just put any Sprite that is on the window.
	 * @param worldSpace - the size of the level
	 * @param xPadding - the distance from each side on the x axis that the focused sprite can not reach
	 * @param yPadding - the distance from each side on the y axis that the focused sprite can not reach
	 * @param renderDistance - the distance off the screen that is drawn
	 */
	public Scene (Main m, Sprite focusedSprite, Rectangle2D.Double worldSpace, int xPadding, int yPadding, int renderDistance) {
		this.m=m;

		toDraw = new ArrayList<Drawable>();
		mouseInput = new ArrayList<Clickable>();
		keyInput = new ArrayList<Typeable>();
		worldlyThings = new ArrayList<Sprite>();
		zombies = new ArrayList<Monster>();

		
		this.setWorldSpace(worldSpace);
		this.screenSpace = new Rectangle2D.Double(0,0, ASSUMED_DRAWING_WIDTH, ASSUMED_DRAWING_HEIGHT);
		this.characterSpace = new Rectangle2D.Double(xPadding, yPadding, ASSUMED_DRAWING_WIDTH-2*xPadding, ASSUMED_DRAWING_HEIGHT-2*yPadding);
		renderSpace = new Rectangle2D.Double(-renderDistance,-renderDistance,ASSUMED_DRAWING_WIDTH+2*renderDistance,ASSUMED_DRAWING_HEIGHT+2*renderDistance);
		this.focusedSprite = focusedSprite;

		updateRatios();

		this.SideScroll = true;
	}


	/**
	 * This creates a new scene.  If you don't want it to side scroll, just set the world space to the the assumed drawing width and height
	 * If using this constructor, setup(Sprite) must be called.
	 * @param m - the main method
	 * @param worldSpace - the size of the level
	 * @param xPadding - the distance from each side on the x axis that the focused sprite can not reach
	 * @param yPadding - the distance from each side on the y axis that the focused sprite can not reach
	 * @param renderDistance - the distance off the screen that is drawn
	 */
	public Scene (Main m, Rectangle2D.Double worldSpace, int xPadding, int yPadding, int renderDistance) {
		this.m=m;

		focusedSprite = null;

		toDraw = new ArrayList<Drawable>();
		mouseInput = new ArrayList<Clickable>();
		keyInput = new ArrayList<Typeable>();
		worldlyThings = new ArrayList<Sprite>();
		zombies = new ArrayList<Monster>();

		this.setWorldSpace(worldSpace);
		this.screenSpace = new Rectangle2D.Double(0,0, ASSUMED_DRAWING_WIDTH, ASSUMED_DRAWING_HEIGHT);
		this.characterSpace = new Rectangle2D.Double(xPadding, yPadding, ASSUMED_DRAWING_WIDTH-2*xPadding, ASSUMED_DRAWING_HEIGHT-2*yPadding);
		renderSpace = new Rectangle2D.Double(-renderDistance,-renderDistance,ASSUMED_DRAWING_WIDTH+2*renderDistance,ASSUMED_DRAWING_HEIGHT+2*renderDistance);

		updateRatios();

		this.SideScroll = true;
	}

	/**
	 * This creates a new scene.  If you don't want it to side scroll, just set the world space to the the assumed drawing width and height
	 * If using this constructor, setup(Sprite) must be called.
	 * @param m - the main method
	 * @param worldSpace - the size of the level
	 * @param xPadding - the distance from each side on the x axis that the focused sprite can not reach
	 * @param yPadding - the distance from each side on the y axis that the focused sprite can not reach
	 * @param renderDistance - the distance off the screen that is drawn
	 */
	public Scene (Main m) {
		this.m=m;

		focusedSprite = null;

		toDraw = new ArrayList<Drawable>();
		mouseInput = new ArrayList<Clickable>();
		keyInput = new ArrayList<Typeable>();
		worldlyThings = new ArrayList<Sprite>();
		zombies = new ArrayList<Monster>();

		this.setWorldSpace(new Rectangle2D.Double(0,0, ASSUMED_DRAWING_WIDTH, ASSUMED_DRAWING_HEIGHT));
		this.screenSpace = worldSpace;
		this.characterSpace = worldSpace;
		renderSpace = worldSpace;

		updateRatios();

		this.SideScroll = false;
	}

	public void setup(Sprite focusedSprite) {
		this.focusedSprite = focusedSprite;
		addAtEnd(focusedSprite);
	}


	public void setup() {
		this.frameRate(40f);
	}

	public void draw() {
		m.manageMusic();
		//background(255,255,255);
		this.pushMatrix();
		updateRatios();
		//scale((float)xRatio, (float)yRatio);
		slideWorldToImage();
		
		
		this.translate((float)(-this.screenSpace.getX()), (float) -this.screenSpace.getY());
		Main.isBattle = false;
		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i).shouldRemove()) {  
				toDraw.remove(i);
			}else if(toDraw.get(i).getHitBox().intersects(renderSpace)) {
				toDraw.get(i).act();
				toDraw.get(i).draw(this);
			}else {
				toDraw.get(i).act();
			}
		}
		this.popMatrix();
		for(int i = 0; i < mouseInput.size(); i++) {
			if(mouseInput.get(i).shouldRemove())
				mouseInput.remove(i);
		}
		for(int i = 0; i < keyInput.size(); i++) {
			if(keyInput.get(i).shouldRemove())
				keyInput.remove(i);
		}
		for(int i = 0; i < worldlyThings.size();i++) {
			if(worldlyThings.get(i).shouldRemove())
				worldlyThings.remove(i);
		}
		
		for(int i = 0; i < zombies.size();i++) {
			if(zombies.get(i).shouldRemove())
				zombies.remove(i);
		}
	}

	public boolean slideWorldToImage() {
		if(SideScroll) {
			Point2D.Double center = focusedSprite.getCenter();
			//System.out.println(center + "    :    " + characterSpace);
			double xTrans = 0,yTrans = 0;

			if (!characterSpace.contains(center)) {
				//System.out.println("NOT CONTAINED\n\t" + characterSpace + " ___ " + center);
				if(center.x>characterSpace.getWidth()+characterSpace.getX() && characterSpace.getX()+characterSpace.getWidth()<getWorldSpace().getX()+getWorldSpace().getWidth()) {
					xTrans = (characterSpace.getWidth()+characterSpace.getX()) - center.x;
				}else if (center.x<characterSpace.getX() && getWorldSpace().getX() < characterSpace.getX()) {
					xTrans = characterSpace.getX() - center.x;
				}

				if(center.y>characterSpace.getHeight()+characterSpace.getY() && characterSpace.getY()+characterSpace.getHeight()<getWorldSpace().getY()+getWorldSpace().getHeight()) {
					yTrans = (characterSpace.getHeight()+characterSpace.getY()) - center.y;
				}else if (center.y<characterSpace.getY()&& getWorldSpace().getY() < characterSpace.getY()) {
					yTrans = characterSpace.getY() - center.y;
				}
				//System.out.println("\t" + xTrans + ", " + yTrans);
				this.translateRect(characterSpace, -xTrans, -yTrans);
				this.translateRect(screenSpace, -xTrans, -yTrans);
				this.translateRect(renderSpace, -xTrans, -yTrans);
				//System.out.println(characterSpace + "\n\t" + screenSpace + "\n\t" + worldSpace + "\n\t" + focusedSprite);
				return true;
			}
		}
		return false;
	}

	private void translateRect(Rectangle2D.Double r, double x, double y) {
		r.setRect(r.getX()+x, r.getY()+y, r.getWidth(), r.getHeight());
	}

	public void mousePressed() {
		int x = this.mouseX;
		int y = this.mouseY;

		for(Clickable c:mouseInput) {
			c.mousePressed(this.AssumedToAcual(new Point(x,y)), mouseButton);
		}
	}

	public void mouseReleased() {
		int x = this.mouseX;
		int y = this.mouseY;

		for(Clickable c:mouseInput) {
			c.mouseReleased(this.AssumedToAcual(new Point(x,y)), mouseButton);
		}
	}

	public Point actualToAssumed(Point actual) {
		return new Point((int)(actual.getX()-screenSpace.getX()*xRatio), (int) (actual.getY()-screenSpace.getY()*yRatio));
	}

	public Point AssumedToAcual(Point assumed) {
		return new Point((int)((assumed.getX()/xRatio + screenSpace.getX())), (int)((assumed.getY()/yRatio + screenSpace.getY())));
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

	public void changePanelAndPause(String name) {
		m.changePanel(name);
		pause(this,true);
	}

	public void changePanel(String name,boolean pause) {
		m.changePanel(name);
		pause(this,pause);
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
	public Object add(Object ... objects) {
		for(Object o:objects) {
			if(o instanceof Drawable)
				toDraw.add((Drawable)o);
			if(o instanceof Clickable)
				mouseInput.add((Clickable)o);
			if(o instanceof Typeable)
				keyInput.add((Typeable)o);
			if(o instanceof Sprite) 
				worldlyThings.add((Sprite) o);
		}
		return objects[0];
	}
	
	public Object addAtEnd(Object ... objects) {
		for(Object o:objects) {
			if(o instanceof Drawable)
				toDraw.add(toDraw.size(),(Drawable)o);
			if(o instanceof Clickable)
				mouseInput.add(mouseInput.size(),(Clickable)o);
			if(o instanceof Typeable)
				keyInput.add(keyInput.size(),(Typeable)o);
			if(o instanceof Sprite) 
				worldlyThings.add(worldlyThings.size(),(Sprite) o);
		}
		return objects[0];
	}

	/**
	 * These are the ratios of actual/assumed
	 */
	public boolean updateRatios() {
		double newXRatio = (double)(width)/ASSUMED_DRAWING_WIDTH;
		double newYRatio = (double)(height)/ASSUMED_DRAWING_HEIGHT;

		if(Math.abs(newXRatio - xRatio) > 0.0001 || Math.abs(newYRatio - yRatio) > 0.0001) {
			xRatio = newXRatio;
			yRatio = newYRatio;
			return true;
		}
		return false;
	}



	public ArrayList<Sprite> getWorldlyThings() {
		return worldlyThings;
	}

	public void setWorldlyThings(ArrayList<Sprite> worldlyThings) {
		this.worldlyThings = worldlyThings;
	}

	public Rectangle2D.Double getVisSpace() {
		return this.screenSpace;
	}

	public Rectangle2D.Double getWorldSpace() {
		return worldSpace;
	}

	public void setWorldSpace(Rectangle2D.Double worldSpace) {
		this.worldSpace = worldSpace;
	}


	public double getxRatio() {
		return xRatio;
	}


	public void setxRatio(double xRatio) {
		this.xRatio = xRatio;
	}


	public double getyRatio() {
		return yRatio;
	}


	public void setyRatio(double yRatio) {
		this.yRatio = yRatio;
	}


	public Rectangle2D.Double getScreenSpace() {
		return screenSpace;
	}


	public void setScreenSpace(Rectangle2D.Double screenSpace) {
		this.screenSpace = screenSpace;
	}
	
	public Sprite getFocusedSprite() {
		return focusedSprite;
	}
	
	public ArrayList<Monster> getMonsters(){
		return zombies;
	}
}