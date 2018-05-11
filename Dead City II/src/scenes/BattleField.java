package scenes;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import gamePlay.Main;
import gamePlay.ResourceLoader;
import interfaces.Clickable;
import interfaces.Damageable;
import interfaces.Destructive;
import interfaces.Drawable;
import interfaces.Typeable;
import items.RangedWeapon;
import processing.core.PApplet;
import sprites.Barrier;
import sprites.Bullet;
import sprites.Creature;
import sprites.DamageableBarrier;
import sprites.Hero;
import sprites.Monster;
import sprites.Sprite;

public class BattleField extends Scene {
	public static final int ASSUMED_DRAWING_WIDTH = 400; // These numbers are way too small
	public static final int ASSUMED_DRAWING_HEIGHT = 300; // We are only using them to zoom in on the scene

	private Rectangle2D.Double visibleSpace;
	private Rectangle2D.Double characterSpace;

	private ArrayList<Sprite> worldlyThings;

	private Sprite focusedSprite;

	public BattleField(Main m) {
		super(m);	
		worldlyThings = new ArrayList<Sprite>();

		visibleSpace = new Rectangle2D.Double(0,height-ASSUMED_DRAWING_HEIGHT,ASSUMED_DRAWING_WIDTH,ASSUMED_DRAWING_HEIGHT);
		characterSpace = new Rectangle2D.Double(visibleSpace.getX()+visibleSpace.getWidth()/5,visibleSpace.getY()+visibleSpace.getHeight()/5,
				visibleSpace.getWidth()*3/5,visibleSpace.getHeight()*3/5);

	}

	public void setup() {	
		Main.resources.load(this);

		Hero joe = new Hero("Trooper", 100,100,200,200,new RangedWeapon(50,1000,20,10,this),worldlyThings);
		focusedSprite = joe;
		Barrier b= new Barrier(0, 500, 2000, 50);
		
		
		
		


		this.add(joe);
		this.add(b);
	}

	public void draw() {
		background(255);

		slideWorldToImage(focusedSprite);
		translate((float)-visibleSpace.getX(),(float)-visibleSpace.getY());

		super.draw(this);

		removeDeadSprites();

	}

	public void removeDeadSprites() {
		for(int i = 0; i < worldlyThings.size(); i++) {
			if(worldlyThings.get(i).shouldRemove()) {
				worldlyThings.remove(i);
			}
		}
	}

	public void keyPressed() {
		super.keyPressed();
		if(keyCode == 85)
			super.changePanelAndPause(this, "MainMenu");
	}

	public void add(Object o) {
		super.add(o);
		if(o instanceof Sprite) 
			worldlyThings.add((Sprite) o);
	}

	public void slideWorldToImage(Sprite s) {
		Point2D.Double center = s.getCenter();
		if (!characterSpace.contains(center)) {
			double newX = visibleSpace.getX();
			double newY = visibleSpace.getY();

			if (center.getX() < characterSpace.getX()) {
				newX -= (characterSpace.getX() - center.getX());
			} else if (center.getX() > characterSpace.getX() + characterSpace.getWidth()) {
				newX += (center.getX() - (characterSpace.getX() + characterSpace.getWidth()));
			}

			if (center.getY() < characterSpace.getY()) {
				newY -= (characterSpace.getY() - center.getY());
			} else if (center.getY() > characterSpace.getY() + characterSpace.getHeight()) {
				newY += (center.getY() - characterSpace.getY() - characterSpace.getHeight());
			}
			newX = Math.max(newX,0);
			newY = Math.max(newY,0);
			newX = Math.min(newX,width-visibleSpace.getWidth());
			newY = Math.min(newY,height-visibleSpace.getHeight());
			
			visibleSpace.setRect(newX,newY,visibleSpace.getWidth(),visibleSpace.getHeight());
			characterSpace.setRect(visibleSpace.getX()+visibleSpace.getWidth()/5,visibleSpace.getY()+visibleSpace.getHeight()/5,visibleSpace.getWidth()*3/5,visibleSpace.getHeight()*3/5);
		}

	}


}