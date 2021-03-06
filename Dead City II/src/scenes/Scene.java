package scenes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.*;
import processing.core.PApplet;
import sprites.Hero;
import sprites.Monster;
import sprites.Sprite;


/**
 * A common superclass for everything PApplet that stores all of the objects in the screen.  It also handles side scrolling and keeps track of the main hero.
 * @author Ben
 * @version 5/21/18
 */
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
	
	private Hero focusedSprite;

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
	public Scene (Main m, Hero focusedSprite, Rectangle2D.Double worldSpace, int xPadding, int yPadding, int renderDistance) {
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
		addAtEnd(this.focusedSprite);

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
	public Scene (Main m, Hero joe) {
		this.m=m;

		focusedSprite = joe;

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

	public void setup(Hero focusedSprite) {
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
				toDraw.get(i).act(this);
				toDraw.get(i).draw(this);
			}else {
				toDraw.get(i).act(this);
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
		
			double xTrans = 0,yTrans = 0;

			if (!characterSpace.contains(center)) {
		
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
		
				this.translateRect(characterSpace, -xTrans, -yTrans);
				this.translateRect(screenSpace, -xTrans, -yTrans);
				this.translateRect(renderSpace, -xTrans, -yTrans);
		
				return true;
			}
		}
		return false;
	}

	public ArrayList<Sprite> getWorldyThings(){
		return this.worldlyThings;
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

	public void clearAllLists() {
		toDraw = new ArrayList<Drawable>();
		mouseInput = new ArrayList<Clickable>();
		keyInput = new ArrayList<Typeable>();
		worldlyThings = new ArrayList<Sprite>();
		zombies = new ArrayList<Monster>();
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
	public Object addBeforeEnd(Object ... objects) {
		for(Object o:objects) {
			if(o instanceof Drawable)
				toDraw.add(Math.max(0, toDraw.size()-1),(Drawable)o);
			if(o instanceof Clickable)
				mouseInput.add(Math.max(0, mouseInput.size()-1),(Clickable)o);
			if(o instanceof Typeable)
				keyInput.add(Math.max(0, keyInput.size()-1),(Typeable)o);
			if(o instanceof Sprite) 
				worldlyThings.add(Math.max(0, worldlyThings.size()-1),(Sprite) o);
		}
		return objects[0];
	}
	
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

	public void newBattleField() {
		m.getScene("BattleField").resetScene();;
	}

	public void resetScene() {
		clearAllLists();
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
	
	public Hero getFocusedSprite() {
		return focusedSprite;
	}
	
	public ArrayList<Monster> getMonsters(){
		return zombies;
	}
}