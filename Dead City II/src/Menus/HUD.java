package Menus;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import gamePlay.Main;
import interfaces.Drawable;
import items.RangedWeapon;
import items.Weapon;
import processing.core.PApplet;
import processing.core.PImage;
import scenes.BattleField;
import scenes.Scene;
import sprites.Barrier;
import sprites.Hero;
/**
 * Displays the characters current stats
 * @author Jose
 * @version 5/19/18
 */
public class HUD implements Drawable{

	//private Scene s;
	private Rectangle2D.Double visSpace;
	private double healthRatio;
	private double ammoRatio;
	private double staminaRatio;

	private double health,maxHealth;
	private double stamina,maxStamina;
	private int ammo,maxAmmo;
	private int mobsLeft;

	private Color red = new Color(255,0,0);

	private boolean isReloading;

	private static final float healthBarOffset = 4;
	private static final float staminaBarOffset = 24;
	private static final float bulletBarOffset = 44;
	public static final float xScaleFactor = 2;
	public static final float yScaleFactor = 2;

	private boolean isAlive;

	private PImage HUD, firstBulletBar, bulletBar, firstHealthBar,healthBar,firstStaminaBar,staminaBar,firstEmptyBar,emptyBar,
	bulletBarCap,healthBarCap,staminaBarCap,hero;

	private final int numBars;


	public HUD() {

		health = 0;
		maxHealth = 0;
		stamina = 0;
		maxStamina=0;
		ammo = 0;
		maxAmmo = 0;
		numBars=10;
		isReloading = false;
		isAlive = true;
		//	this.s=s;
		visSpace = new Rectangle2D.Double();

		HUD = Main.resources.getImage("HUD");
		firstBulletBar = Main.resources.getImage("1stBulletBar");
		bulletBar = Main.resources.getImage("BulletBar");
		firstHealthBar =Main.resources.getImage("1stHealthBar");
		healthBar=Main.resources.getImage("HealthBar");
		firstStaminaBar=Main.resources.getImage("1stStaminaBar");
		staminaBar=Main.resources.getImage("StaminaBar");
		firstEmptyBar=Main.resources.getImage("1stEmptyBar");
		emptyBar=Main.resources.getImage("EmptyBar");
		bulletBarCap=Main.resources.getImage("BulletBarCap");
		healthBarCap=Main.resources.getImage("HealthBarCap");
		staminaBarCap=Main.resources.getImage("StaminaBarCap");
		hero = Main.resources.getAnimation("TrooperWalking", 0);	


	}



	public void draw(PApplet marker) {
		//System.out.println("HUD DRAWING");
		
		marker.pushMatrix();
		//	marker.scale(xScaleFactor, yScaleFactor);
		marker.image(HUD, (float)visSpace.getX(), (float)visSpace.getY());
		
	//	System.out.println("\t\tVISSPACE FROM HUD DRAW: " + visSpace.getX());
		
		//empty bars
		for(int i = 0;i <numBars;i++) {	
			if(i==0) {
				marker.image(firstEmptyBar, (float)(visSpace.getX()+1*(HUD.width-.5)), (float)(visSpace.getY()+yScaleFactor*healthBarOffset));
				marker.image(firstEmptyBar, (float)(visSpace.getX()+1*(HUD.width-.5)), (float)(visSpace.getY()+yScaleFactor*staminaBarOffset));
				marker.image(firstEmptyBar, (float)(visSpace.getX()+1*(HUD.width-.5)), (float)(visSpace.getY()+yScaleFactor*bulletBarOffset));
			}else {

				marker.image(emptyBar, (float) (visSpace.getX()+1*(HUD.width+firstEmptyBar.width*i-.5)), (float) (visSpace.getY()+yScaleFactor*healthBarOffset));
				marker.image(emptyBar, (float) (visSpace.getX()+1*(HUD.width+firstEmptyBar.width*i-.5)), (float) (visSpace.getY()+yScaleFactor*staminaBarOffset));
				marker.image(emptyBar, (float) (visSpace.getX()+1*(HUD.width+firstEmptyBar.width*i-.5)), (float) (visSpace.getY()+yScaleFactor*bulletBarOffset));
			}
		}

		//health bars
		for(int i = 0;i <healthRatio*numBars;i++) {	
			if(i==0) {
				marker.image(firstHealthBar, (float)(1*(visSpace.getX()+HUD.width-.5)), (float) (visSpace.getY()+(yScaleFactor*(healthBarOffset+2))));
			}else {
				marker.image(healthBar, (float) (1*(visSpace.getX()+HUD.width+firstHealthBar.width*i-.5)), (float) (visSpace.getY()+(yScaleFactor*(healthBarOffset+2))));
			}
		}
		marker.image(healthBarCap, ((float)(1*(visSpace.getX()+HUD.width+firstHealthBar.width*numBars-.5))), (float)(visSpace.getY()+(yScaleFactor*healthBarOffset-4)));


		//stamina bars

		for(int i = 0;i <staminaRatio*numBars;i++) {	
			if(i==0) {
				marker.image(firstStaminaBar, (float) (1*(visSpace.getX()+HUD.width-.5)), (float) (visSpace.getY()+(yScaleFactor*(staminaBarOffset+2))));
			}else {
				marker.image(staminaBar, (float) (1*(visSpace.getX()+HUD.width+firstStaminaBar.width*i-.5)), (float) (visSpace.getY()+(yScaleFactor*(staminaBarOffset+2))));
			}
		}

		marker.image(staminaBarCap,(float) (1*((visSpace.getX()+HUD.width+firstStaminaBar.width*numBars-.5))), (float) (visSpace.getY()+(yScaleFactor*staminaBarOffset-4f)));

		//bullet bars
		if(!isReloading) {
			for(int i = 0;i <ammoRatio*numBars;i++) {	
				if(i==0) {
					marker.image(firstBulletBar, (float) (1*(visSpace.getX()+HUD.width-.5)), (float) (visSpace.getY()+(yScaleFactor*(bulletBarOffset+2))));
				}else {
					marker.image(bulletBar, (float) (1*(visSpace.getX()+HUD.width+firstBulletBar.width*i-.5)), (float) (visSpace.getY()+(yScaleFactor*(bulletBarOffset+2))));
				}
			}
		}else {
			marker.fill(255);
			marker.textAlign(marker.LEFT, marker.TOP);
			marker.textSize(20);
			marker.text("RELOADING...", (float) (1*(visSpace.getX()+HUD.width+20)), (float) (visSpace.getY()+(yScaleFactor*(bulletBarOffset+2))));
		}


		marker.image(bulletBarCap,(float) (1*(visSpace.getX()+HUD.width+firstBulletBar.width*numBars-.5)), (float) (visSpace.getY()+(yScaleFactor*bulletBarOffset-4)));

		//hero image
		marker.image(hero, (float)visSpace.getX()+xScaleFactor*13, (float) visSpace.getY()+yScaleFactor*12,xScaleFactor*37,yScaleFactor*39);

		//xp bar




		if(!isAlive) {

			marker.fill(red.getRGB());
			marker.textAlign(marker.CENTER, marker.CENTER);
			marker.textSize(40);
			marker.text("YOU DIED", (float) ((visSpace.getX()/2)), (float) (visSpace.getY()/2));
		}

	
		marker.fill(255);
		marker.textSize(30);
		marker.text("Undead Left: " + this.mobsLeft, (float)visSpace.getX()+1300, (float) (visSpace.getY()+40));
		marker.popMatrix();
	}


	//characterSpace = new Rectangle2D.Double(xEdge, yEdge, width-2*xEdge, height - 2*yEdge);
	

	public Rectangle2D.Double getHitBox() {
		return visSpace;
	}

	public boolean shouldRemove() {
		return false;
	}

	public void act(Scene s) {
		
		Hero h = s.getFocusedSprite();
		
		visSpace = s.getVisSpace();
	
		mobsLeft = s.getMonsters().size();

		health = h.getHealth();
		maxHealth = h.getMaxHealth();

		stamina=h.getStamina();
		maxStamina=h.getMaxStamina();



		if(h.getWeapon() instanceof RangedWeapon) {
			RangedWeapon rw = ((RangedWeapon) h.getWeapon());
			ammo = rw.getCurrentAmmo();
			maxAmmo =rw.getMaxAmmo(); 
		}else {
			ammo = -1;
			maxAmmo = -1;
		}

		healthRatio = health/maxHealth;
		staminaRatio = stamina/maxStamina;
		ammoRatio = ammo/(double)maxAmmo;

		this.hero = h.getStanding().get(0);

		isReloading = h.isReloading();

		isAlive = h.isAlive();


	}

}