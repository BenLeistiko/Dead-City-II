package scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import Menus.*;
import gamePlay.*;
import interfaces.*;
import items.*;
import processing.core.PApplet;
import sprites.*;

public class BattleField extends Scene {

	private int xEdge = 200;
	private int yEdge = 400;

	private int groundThickness;
	private double groundHeight;

	private int amtOfMobsSpawn;
	private int amtOfPowerUpsSpawn;

	private HUD display;

	private double mobHealthUpgrade;
	private double mobHealthUpgradeAmountPerDay;
	private int day;



	public BattleField(Main m, Hero hero) {
		super(m, hero, new Rectangle2D.Double(0,0,25000,2000), 200, 400, 100);	
		groundThickness = 1000;


	}


	public void setup() {
		super.setup();
		//Hero joe = new Hero("Trooper", 49000,100,100,100,new RangedWeapon(50,1000,20,10,this),super.getWorldlyThings(), this);
		amtOfMobsSpawn = 20;
		amtOfPowerUpsSpawn = 8;
		mobHealthUpgrade=0;
		mobHealthUpgradeAmountPerDay=15;
		day = 1;

		groundHeight = getWorldSpace().getY()+getWorldSpace().getHeight()-groundThickness;
		generateEdges();
		generateGround();
		generateHill(5);
		generatePlatforms(80,100);
		generateMobs(amtOfMobsSpawn);
		generatePowerUps(amtOfPowerUpsSpawn);



		display = super.getFocusedSprite().getHUD();
		this.add(display);
	}

	public void draw() {
		background(255);
		image(Main.resources.getImage("BattleFieldBackground"),0, 0);

		display.update(this,(Hero)super.getFocusedSprite());	
		//System.out.println(((Hero)(super.getFocusedSprite())).getStamina());


		super.draw();

		if(!super.getFocusedSprite().isAlive()) {//lose condition
			super.changePanelAndPause("Death");

		}

		if(super.getMonsters().size() == 0) {//win conditions
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

		super.getFocusedSprite().keyPressed(this);

	}

	public void keyReleased() {
		super.keyReleased();

		super.getFocusedSprite().keyReleased(this);
	}



	public void generateEdges() {
		Barrier bottom= new Barrier(getWorldSpace().getX(), getWorldSpace().getHeight()-100, getWorldSpace().getWidth(), 100,Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		Barrier left = new Barrier(getWorldSpace().getX()-100, getWorldSpace().getY()-800, 100, getWorldSpace().getHeight()+800,Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
		Barrier right = new Barrier(getWorldSpace().getWidth(), getWorldSpace().getY()-800, 100, getWorldSpace().getHeight()+800,Main.resources.getImage("Bedrock").width,Main.resources.getImage("Bedrock").height,"Bedrock");
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

	public void resetScene() {
		super.resetScene();
		amtOfMobsSpawn+=5;
		amtOfPowerUpsSpawn+=2;
		mobHealthUpgrade+=mobHealthUpgradeAmountPerDay;
		generateEdges();
		generateGround();
		generateHill(10);
		generatePlatforms(80,100);
		generateMobs(amtOfMobsSpawn);
		generatePowerUps(amtOfPowerUpsSpawn);
		super.getFocusedSprite().setRect(super.getWorldSpace().getWidth()/2, 0, super.getFocusedSprite().getWidth(), super.getFocusedSprite().getHeight());
		super.addAtEnd(super.getFocusedSprite());
		display = super.getFocusedSprite().getHUD();
		this.add(display);
		day++;
	}

	private boolean overlaps(double a1, double a2, double b1, double b2) {
		if((a1 < b2 && a1>b1)||(a2 < b2 && a2>b1) || (b1<a2 &&b1>a1) ||(b2<a2 &&b2>a1) ) {
			return true;
		}else if(a2>super.getWorldSpace().getX()+super.getWorldSpace().getWidth() || a1 < super.getWorldSpace().getX()) {
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
			Monster zomb = new Monster(mobTypes.get(rand),(getWorldSpace().getWidth()-2000)*Math.random()+1000,100,80,80);
			zomb.addHealth(mobHealthUpgrade);
			
			add(zomb);
			super.getMonsters().add(zomb);
		}
	}

	public void generatePowerUps(int amtToSpawn) {

		for(int i =0; i <amtToSpawn;i++) {
			int rand = (int)(Math.random()*2);
			double randomX = (getWorldSpace().getWidth()-2000)*Math.random()+1000;

			Pickup pick = null;
			//	System.out.println("rand= " + rand);
			if(rand ==0) {
				pick = new HealthPickup(randomX,50,75);
			}else {
				pick = new StaminaPickup(randomX,50,10);
			}

			this.add(pick);
		}


	}



}