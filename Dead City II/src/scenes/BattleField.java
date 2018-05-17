package scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import gamePlay.Button;
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
	private Rectangle2D.Double visSpace;

	private ArrayList<Sprite> worldlyThings;

	private Sprite focusedSprite;

	private int xEdge = 200;
	private int yEdge = 400;

	private int groundThickness;
	private double groundHeight;

	private boolean paused;




	public BattleField(Main m) {
		super(m);	
		worldlyThings = new ArrayList<Sprite>();
		groundThickness = 1000;
		paused = false;

	}


	public void setup() {
		Main.resources.load(this);
		Hero joe = new Hero("Trooper", 49000,100,100,100,new RangedWeapon(50,1000,20,10,this),worldlyThings, this);
		focusedSprite = joe;

		//	this.add(joe);

		characterSpace = new Rectangle2D.Double(xEdge, yEdge, width-2*xEdge, height - 2*yEdge);
		worldSpace = new Rectangle2D.Double(0, 0, 50000, 2000); 
		groundHeight = worldSpace.getY()+worldSpace.getHeight()-groundThickness;
		generateEdges();
		generateGround();
		generateHill(10);
		generatePlatforms(80,100);
		super.setRenderSpace(new Rectangle2D.Double(characterSpace.getX()-500, characterSpace.getY()-500, characterSpace.getX()+characterSpace.getWidth()+1000, characterSpace.getY()+characterSpace.getHeight()+1000));

		this.add(joe);
	}

	public void draw() {
		if(paused) {			
			//fill(0,10);
			//rect((float)getVisSpace().getX(),(float)getVisSpace().getY(),(float)getVisSpace().getWidth(),(float)getVisSpace().getHeight(),100f);
			background(125);
			textAlign(CENTER,CENTER);
		
			textSize(100);
			text("PAUSED",(float)(getVisSpace().getX()+getVisSpace().getWidth()/2),(float)(getVisSpace().getY()+ getVisSpace().getHeight()/2));
			textSize(40);
			text("Push '1' to return to Main Menu",(float)(getVisSpace().getX()+getVisSpace().getWidth()/2),(float)(getVisSpace().getY()+ getVisSpace().getHeight()*2/3));

		}else {

			background(255);

			slideWorldToImage(focusedSprite);
			translate((float)-(characterSpace.getX() - xEdge),(float) -(characterSpace.getY() - yEdge));

			super.draw(this);
			//super.draw(this, paused);

			removeDeadSprites();
		}
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
		if(keyCode == KeyEvent.VK_U)
			super.changePanelAndPause(this, "TitleScreen");
		
		if(keyCode == KeyEvent.VK_P) {
			paused = !paused;//pseudo pause - only to know whether or not to show pause menu
			draw();
			super.pause(this, paused);// real pause
		}

		if(paused && keyCode == KeyEvent.VK_1) {
			paused = false;//undo pseudo pause
			super.changePanelAndPause(this, "TitleScreen");//real pause
		}


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
			for(int y = (int) (worldSpace.getHeight()-groundThickness); y < worldSpace.getHeight()-100;y=y+100) {
				add(new DamageableBarrier(x,y,100,100,Main.resources.getImage("Dirt").width,Main.resources.getImage("Dirt").height,"Dirt",10,0));
			}
		}
	}

	public void generateHill(int numOfHills) {
		double[] b1 = new double[numOfHills];
		double[] b2 = new double[numOfHills];
		int numGen = 0;
		for(int i = 0; i < numOfHills; i++) {
			boolean generate = false;
			while(generate == false) {
				//System.out.println("generating...");
				double a1 = Math.toRadians(Math.random()*30+45);
				double a2 = Math.toRadians(Math.random()*30+45);
				double x = worldSpace.getX() + worldSpace.getWidth()*Math.random();
				double y = (int)(this.groundHeight-300-Math.random()*800);
				double h = groundHeight - y;
				double x1 = x-h*Math.tan(a1);
				double x2 = x+h*Math.tan(a2);
				generate = true;
				for(int j = 0; j <numOfHills; j++) {
					if(overlaps(x1,x2,b1[j],b2[j])) {
						generate = false;
					}
				}
				if(generate) {
					generateHill(x,y,a1,a2,groundHeight);
					b1[numGen] = x1;
					b2[numGen] = x2;
					numGen++;
				}
			}
		}


	}


	private boolean overlaps(double a1, double a2, double b1, double b2) {
		if((a1 < b2 && a1>b1)||(a2 < b2 && a2>b1) || (b1<a2 &&b1>a1) ||(b2<a2 &&b2>a1) ) {
			return true;
		}return false;
	}

	private void generateHill(double x, double y, double a1, double a2, double floor) {
		if(floor>y) {
			double h = floor - y;
			double x1 = x-h*Math.tan(a1);
			double x2 = x+h*Math.tan(a2);
			double startX = x1+100*Math.tan(a1);
			double endX = x2-100*Math.tan(a2);
			//System.out.println("Varibles: " + h +", " + x1 + ", " + x2 + ", " + startX + ", " + endX);
			for(int  i = (int) startX;i<=endX;i=i+100) {
				DamageableBarrier dirt = new DamageableBarrier(i-50,floor-100,100,100,Main.resources.getImage("Dirt").width,Main.resources.getImage("Dirt").height,"Dirt",10,0);
				//System.out.println("added dirt" + (i-50) + ", " + (floor -100));
				if(!dirt.collides(worldlyThings)) {
					add(dirt);
				}
			}
			generateHill(x,y,a1,a2,floor-100);
		}
	}

	public void generatePlatforms(int number, int space) {
		int[] ySizes = new int[10];
		for(int i = 0; i < 10; i++) {
			ySizes[i] = 200+100*i;
		}
		for(int i = 0; i < number; i++) {
			Barrier plat = new Barrier((worldSpace.getWidth()-worldSpace.getX())*Math.random(), (worldSpace.getHeight()-worldSpace.getY())*Math.random(), ySizes[(int)(Math.random()*10)], 100,Main.resources.getImage("Bricks").width,Main.resources.getImage("Bricks").height,"Bricks");
			Rectangle2D.Double box = new Rectangle2D.Double(plat.getX(), plat.getY(), plat.getWidth(), plat.getHeight());
			plat.setRect(plat.getX()-space, plat.getY()-space, plat.getWidth()+2*space, plat.getHeight()+2*space);
			while(plat.collides(worldlyThings)) {
				plat = new Barrier((worldSpace.getWidth()-worldSpace.getX())*Math.random(), (worldSpace.getHeight()-worldSpace.getY())*Math.random(), ySizes[(int)(Math.random()*10)], 100,Main.resources.getImage("Bricks").width,Main.resources.getImage("Bricks").height,"Bricks");
				box = new Rectangle2D.Double(plat.getX(), plat.getY(), plat.getWidth(), plat.getHeight());
				plat.setRect(plat.getX()-space, plat.getY()-space, plat.getWidth()+2*space, plat.getHeight()+2*space);
			}
			plat.setRect(box);
			add(plat);
		}
	}

	public void generateMobs() {

	}


	public Rectangle2D.Double getVisSpace() {
		double width = characterSpace.getWidth();
		double height = characterSpace.getHeight();
		visSpace = new Rectangle2D.Double(characterSpace.getX()-xEdge, characterSpace.getY()-yEdge, width+2*xEdge, height+2*yEdge);

		return visSpace;
	}



}