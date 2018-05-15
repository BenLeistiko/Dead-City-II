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

	private Rectangle2D.Double characterSpace;//This is the rectangle that the character can not leave
	private Rectangle2D.Double worldSpace;// This is the world.  If the character leaves this area, the camera stops following him

	private ArrayList<Sprite> worldlyThings;

	private Sprite focusedSprite;

	private int xEdge = 200;
	private int yEdge = 400;

	public BattleField(Main m) {
		super(m);	
		worldlyThings = new ArrayList<Sprite>();

	}

	public void setup() {	
		Main.resources.load(this);

		Hero joe = new Hero("Trooper", 0,100,100,100,new RangedWeapon(50,1000,20,10,this),worldlyThings);
		focusedSprite = joe;
		Barrier b= new Barrier(0, 1875, 1000, 150,Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,Main.resources.getImage("Bedrock"
				+ ""));

		this.add(joe);
		this.add(b);

		characterSpace = new Rectangle2D.Double(xEdge, yEdge, width-2*xEdge, height - 2*yEdge);
		worldSpace = new Rectangle2D.Double(0, 0, 50000, 2000);
	}

	public void draw() {
		background(255);


		slideWorldToImage(focusedSprite);
		translate((float)-(characterSpace.getX() - xEdge),(float) -(characterSpace.getY() - yEdge));

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
		//System.out.println(center + "    :    " + characterSpace);
		double xTrans = 0,yTrans = 0;

		if (!characterSpace.contains(center)) {
			if(center.x>characterSpace.getWidth()+characterSpace.getX() && characterSpace.getX()+characterSpace.getWidth()<worldSpace.getX()+worldSpace.getWidth()) {
				xTrans = (characterSpace.getWidth()+characterSpace.getX()) - center.x;
			}else if (center.x<characterSpace.getX() && worldSpace.getX() < characterSpace.getX()) {
				xTrans = characterSpace.getX() - center.x;
			}

			if(center.y>characterSpace.getHeight()+characterSpace.getY() && characterSpace.getY()+characterSpace.getHeight()<worldSpace.getY()+worldSpace.getHeight()) {
				yTrans = (characterSpace.getHeight()+characterSpace.getY()) - center.y;
			}else if (center.y<characterSpace.getY()&& worldSpace.getY() < characterSpace.getY()) {
				yTrans = characterSpace.getY() - center.y;
			}
			characterSpace.setRect(characterSpace.getX()-xTrans, characterSpace.getY()-yTrans, characterSpace.getWidth(), characterSpace.getHeight());

		}

	}

}