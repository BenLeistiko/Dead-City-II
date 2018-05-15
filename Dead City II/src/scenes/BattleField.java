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

		Hero joe = new Hero("Trooper", 49000,100,100,100,new RangedWeapon(50,1000,20,10,this),worldlyThings);
		focusedSprite = joe;

		this.add(joe);

		characterSpace = new Rectangle2D.Double(xEdge, yEdge, width-2*xEdge, height - 2*yEdge);
		worldSpace = new Rectangle2D.Double(0, 0, 50000, 1500); 
		generateEdges();
		generateGround();
		for(int i =0; i < 10;i++) {
			generateHill((int)(worldSpace.getWidth()*Math.random()),(int)(worldSpace.getHeight()-1001) , 1);
		}
		generatePlatforms(100);
		super.setRenderSpace(new Rectangle2D.Double(characterSpace.getX()-500, characterSpace.getY()-500, characterSpace.getX()+characterSpace.getWidth()+1000, characterSpace.getY()+characterSpace.getHeight()+1000));
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

	public void add(Object ... objects) {
		super.add(objects);
		for(Object o:objects) {
			if(o instanceof Sprite) 
				worldlyThings.add((Sprite) o);
		}

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
			super.updateRenderSpace(-xTrans, -yTrans);
		}
	}

	public void generateEdges() {
		Barrier bottom= new Barrier(worldSpace.getX(), worldSpace.getHeight()-100, worldSpace.getWidth(), 100,Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		Barrier left = new Barrier(worldSpace.getX()-100, worldSpace.getY(), 100, worldSpace.getHeight(),Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		Barrier right = new Barrier(worldSpace.getWidth(), worldSpace.getY(), 100, worldSpace.getHeight(),Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		add(bottom,left,right);
	}

	public void generateGround() {
		for(int x = 0; x < worldSpace.getWidth(); x=x+100) {
			for(int y = (int) (worldSpace.getHeight()-1000); y < worldSpace.getHeight()-100;y=y+100) {
				add(new DamageableBarrier(x,y,100,100,Main.resources.getImage("Dirt").width,Main.resources.getImage("Dirt").height,"Dirt",10,0));
			}
		}
	}

	public void generateHill(int x, int y,int depth) {
		Barrier dirt = new DamageableBarrier(x,y,100,100,Main.resources.getImage("Dirt").width,Main.resources.getImage("Dirt").height,"Dirt",10,0);
		if(dirt.collides(worldlyThings)) {
			return;
		}
		double bottom = dirt.getY()+dirt.getHeight();
		boolean isOnTop = false;
		for(Sprite s:worldlyThings) {
			if(s.getY() -bottom < 3) {
				isOnTop = true;
			}
		}
		if(!isOnTop)
			return;
		else if(Math.random()*200<depth) {
			return;
		}
		else {
			add(dirt);
			generateHill(x-100,y,depth+1);
			generateHill(x+100,y,depth+1);
			generateHill(x,y+100,depth+1);
			System.out.println("added hill");
		}
	}

	public void generatePlatforms(int number) {
		int[] ySizes = new int[10];
		for(int i = 0; i < 10; i++) {
			ySizes[i] = 200+100*i;
		}
		for(int i = 0; i < number; i++) {
			Barrier plat = new Barrier((worldSpace.getWidth()-worldSpace.getX())*Math.random(), (worldSpace.getHeight()-worldSpace.getY())*Math.random(), ySizes[(int)(Math.random()*10)], 100,Main.resources.getImage("Bricks").width,Main.resources.getImage("Bricks").height,"Bricks");
			while(plat.collides(worldlyThings)) {
				plat = new Barrier((worldSpace.getWidth()-worldSpace.getX())*Math.random(), (worldSpace.getHeight()-worldSpace.getY())*Math.random(), ySizes[(int)(Math.random()*10)], 100,Main.resources.getImage("Bricks").width,Main.resources.getImage("Bricks").height,"Bricks");
			}
			add(plat);
		}
	}

}