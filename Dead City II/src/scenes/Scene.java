package scenes;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import interfaces.*;
import processing.core.PApplet;
import sprites.Sprite;


public abstract class Scene extends PApplet {
	//****We pretend the screen is this size, then later scale everything up/down to the correct size.  This way it doen't matter what size the windows are when they are created o resized.  It is 1/4 the old scale.
	public static final int ASSUMED_DRAWING_WIDTH = 1600;
	public static final int ASSUMED_DRAWING_HEIGHT = 900;

	
	private ArrayList<Drawable> toDraw;//Everything to draw that is NOT a sprite
	private ArrayList<Clickable> mouseInput;
	private ArrayList<Typeable> keyInput;
	private ArrayList<Sprite> worldlyThings;

	private Main m;

	//****These are all in the assumed drawing coordinates before we translate*****
	private Rectangle2D.Double renderSpace;//Everything in here is drawn.  Otherwise it is not.
	private Rectangle2D.Double worldSpace;//This is the limits of the world.  Past this the camera will not follow the focusedSprite.
	private Rectangle2D.Double screenSpace;//This is the space that should be drawn onto ones screen.  Its width and height are the assumed width and hieghts.
	private Rectangle2D.Double characterSpace;//This is the Space that the focused Sprite is allowed to be in.  If he tries to move out of it the world will just be translated around him.
	
	private Sprite focusedSprite;
	
	private double xRatio; 
	private double yRatio;
	
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

		this.setWorldSpace(worldSpace);
		this.screenSpace = new Rectangle2D.Double(0,0, ASSUMED_DRAWING_WIDTH, ASSUMED_DRAWING_HEIGHT);
		this.characterSpace = new Rectangle2D.Double(xPadding, yPadding, ASSUMED_DRAWING_WIDTH-2*xPadding, ASSUMED_DRAWING_HEIGHT-2*yPadding);
		renderSpace = new Rectangle2D.Double(-renderDistance,-renderDistance,ASSUMED_DRAWING_WIDTH+2*renderDistance,ASSUMED_DRAWING_HEIGHT+2*renderDistance);
		
		updateRatios();
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

		this.setWorldSpace(worldSpace);
		this.screenSpace = new Rectangle2D.Double(0,0, ASSUMED_DRAWING_WIDTH, ASSUMED_DRAWING_HEIGHT);
		this.characterSpace = new Rectangle2D.Double(xPadding, yPadding, ASSUMED_DRAWING_WIDTH-2*xPadding, ASSUMED_DRAWING_HEIGHT-2*yPadding);
		renderSpace = new Rectangle2D.Double(-renderDistance,-renderDistance,ASSUMED_DRAWING_WIDTH+2*renderDistance,ASSUMED_DRAWING_HEIGHT+2*renderDistance);
		
		updateRatios();
	}
	
	public void setup(Sprite focusedSprite) {
		this.focusedSprite = focusedSprite;
		add(focusedSprite);
	}
	
	public void draw() {
		updateRatios();
		slideWorldToImage();
		this.pushMatrix();
		this.scale((float)xRatio, (float)yRatio);
		this.translate((float)(-this.screenSpace.getX()), (float) -this.screenSpace.getY());
		System.out.println(xRatio + ", " + yRatio);
		for(int i = 0; i < toDraw.size(); i++) {
			if(toDraw.get(i).shouldRemove()) {
				toDraw.remove(i);
			}else if(toDraw.get(i).getHitBox().intersects(renderSpace)) {
				toDraw.get(i).draw(this);
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
	}
	
	public void slideWorldToImage() {
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
		}
	}
	
	private void translateRect(Rectangle2D.Double r, double x, double y) {
		r.setRect(r.getX()+x, r.getY()+y, r.getWidth(), r.getHeight());
	}
	
	public void mousePressed() {
		int x = this.mouseX;
		int y = this.mouseY;

		for(Clickable c:mouseInput) {
			c.mousePressed(new Point(x,y), mouseButton);
		}
	}

	public void mouseReleased() {
		int x = this.mouseX;
		int y = this.mouseY;
		
		for(Clickable c:mouseInput) {
			c.mouseReleased(new Point(x,y), mouseButton);
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

	/**
	 * These are the ratios of actual/assumed
	 */
	public void updateRatios() {
		xRatio = (double)(width)/ASSUMED_DRAWING_WIDTH;
		yRatio = (double)(height)/ASSUMED_DRAWING_HEIGHT;
	}
	
	public Point actualToAssumed(Point actual, PApplet window) {
		return new Point((int)(actual.getX()/xRatio-screenSpace.getX()), (int) (actual.getY()/yRatio-screenSpace.getY()));
	}
	
	public Point AssumedToAcual(Point assumed, PApplet window) {
		return new Point((int)((assumed.getX() - screenSpace.getX())*xRatio), (int)((assumed.getY() - screenSpace.getY())*yRatio));
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
}