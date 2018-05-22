package scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import Menus.HUD;
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

	private int xEdge = 200;
	private int yEdge = 400;

	private int groundThickness;
	private double groundHeight;

	private int mobsSpawn;

	private HUD display;

	private Hero joe;
	//private boolean paused;


	public BattleField(Main m, Hero hero) {
		super(m, hero, new Rectangle2D.Double(0,0,50000,2000), 200, 400, 100);	
		joe = hero;
		groundThickness = 1000;


	}


	public void setup() {
		super.setup();
		//Hero joe = new Hero("Trooper", 49000,100,100,100,new RangedWeapon(50,1000,20,10,this),super.getWorldlyThings(), this);
		mobsSpawn = 30;

		groundHeight = getWorldSpace().getY()+getWorldSpace().getHeight()-groundThickness;
		generateEdges();
		generateGround();
		generateHill(10);
		generatePlatforms(80,100);
		generateMobs(mobsSpawn);

		display = joe.getHUD();
		this.add(display);
	}

	public void draw() {
		background(255);
		image(Main.resources.getImage("BattleFieldBackground"),0, 0);
		
		System.out.println(width +", " + height);
		super.draw();
	
		if(!joe.isAlive()) {
			super.changePanelAndPause("Death");
			//	joe.act();
		//	joe.draw(this);
		}
		
		
		display.update(this,(Hero)super.getFocusedSprite());
 
		//for(Monster m: super.getMonsters()) {
			//m.act(this);
		//}
		
		
	//	System.out.println(getMonsters().size());
		if(super.getMonsters().size() == 0) {
			super.changePanelAndPause("Camp");
		}

	}



	public void keyPressed() {
		super.keyPressed();
		if(keyCode == KeyEvent.VK_U) {
			super.changePanelAndPause( "TitleScreen");
		}

		if(keyCode == KeyEvent.VK_P) {
			super.changePanelAndPause("Pause");
		}

		joe.keyPressed(this);

	}
	
	public void keyReleased() {
		super.keyReleased();
		
		joe.keyReleased(this);
	}



	public void generateEdges() {
		Barrier bottom= new Barrier(getWorldSpace().getX(), getWorldSpace().getHeight()-100, getWorldSpace().getWidth(), 100,Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		Barrier left = new Barrier(getWorldSpace().getX()-100, getWorldSpace().getY(), 100, getWorldSpace().getHeight(),Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		Barrier right = new Barrier(getWorldSpace().getWidth(), getWorldSpace().getY(), 100, getWorldSpace().getHeight(),Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		add(bottom,left,right);
	}

	public void generateGround() {
		for(int x = 0; x < getWorldSpace().getWidth(); x=x+100) {
			add(new DamageableBarrier(x,getWorldSpace().getHeight()-groundThickness,100,100,Main.resources.getImage("Grass").width,Main.resources.getImage("Grass").height,"Grass",5,0));
			for(int y = (int) (getWorldSpace().getHeight()-groundThickness+100); y < getWorldSpace().getHeight()-100;y=y+100) {
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
				
				double a1 = Math.toRadians(Math.random()*30+45);
				double a2 = Math.toRadians(Math.random()*30+45);
				double x = getWorldSpace().getX() + getWorldSpace().getWidth()*Math.random();
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
			
			for(int  i = (int) startX;i<=endX;i=i+100) {
				DamageableBarrier grass = new DamageableBarrier(i-50,floor-100,100,100,Main.resources.getImage("Grass").width,Main.resources.getImage("Grass").height,"Grass",5,0);
			
				if(!grass.collides(getWorldlyThings())) {
					add(grass);
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
			Barrier plat = new Barrier((getWorldSpace().getWidth()-getWorldSpace().getX())*Math.random(), (getWorldSpace().getHeight()-getWorldSpace().getY())*Math.random(), ySizes[(int)(Math.random()*10)], 100,Main.resources.getImage("Bricks").width,Main.resources.getImage("Bricks").height,"Bricks");
			Rectangle2D.Double box = new Rectangle2D.Double(plat.getX(), plat.getY(), plat.getWidth(), plat.getHeight());
			plat.setRect(plat.getX()-space, plat.getY()-space, plat.getWidth()+2*space, plat.getHeight()+2*space);
			while(plat.collides(getWorldlyThings())) {
				plat = new Barrier((getWorldSpace().getWidth()-getWorldSpace().getX())*Math.random(), (getWorldSpace().getHeight()-getWorldSpace().getY())*Math.random(), ySizes[(int)(Math.random()*10)], 100,Main.resources.getImage("Bricks").width,Main.resources.getImage("Bricks").height,"Bricks");
				box = new Rectangle2D.Double(plat.getX(), plat.getY(), plat.getWidth(), plat.getHeight());
				plat.setRect(plat.getX()-space, plat.getY()-space, plat.getWidth()+2*space, plat.getHeight()+2*space);
			}
			plat.setRect(box);
			add(plat);
		}
	}
 
	public void generateMobs(int amtToSpawn) {
		ArrayList<String> mobTypes = Main.resources.getBadMobNames();

		for(int i =0; i <amtToSpawn;i++) {
			int rand = (int)(Math.random()*3);
			Monster zomb = new Monster(mobTypes.get(rand),49000*Math.random(),100,100,100);

			add(zomb);
			super.getMonsters().add(zomb);
		}


	}



}