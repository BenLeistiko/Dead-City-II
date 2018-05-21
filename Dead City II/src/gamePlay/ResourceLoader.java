package gamePlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import Menus.HUD;
import processing.core.PApplet;
import processing.core.PImage;
//import processing.sound.SoundFile;
import sprites.Creature;
import sprites.Sprite;

/**
 * This class loads all of our resources and allows different classes to easily access picture sounds etc.
 * @author bleistiko405
 *
 */
public class ResourceLoader {

	private static String fileSeparator = System.getProperty("file.separator");

	private HashMap<String, ArrayList<PImage>> animations;
	private HashMap<String, PImage> images;
	private HashMap<String, Sprite> templateSprites;
	private HashMap<String, HashMap<Point, HashMap<Point, PImage>>> textures;
	private HashMap<String,  HashMap<String,Double>> statistics;
	private HashMap<String, ArrayList<Point>> Offsets;
	private HashMap<String, Integer> framesPerAnimation;
	private HashMap<String, Double> ScaleFactor;
	private ArrayList<String> characters;
	private ArrayList<String> attributes;
	private ArrayList<String> badMobs;
	public static final String TROOPER = "Trooper";
	public static final String COMMANDER = "Commandor";
	public static final String THUG = "Thug";
	public static final String JUNGLEMAN = "JungleMan";
	public static final String ASSASSIN = "Assassin";
	public static final String BASICZOMBIE = "BasicZombie";
	public static final String SKIRTZOMBIE = "SkirtZombie";
	public static final String BONEZOMBIE = "BoneZombie";
	public static final String BOYZOMBIE = "BoyZombie";
	public static final String MUMMY = "Mummy";
	public static final String MOHAWKZOMBIE = "MohawkZombie";

	public static final String HEALTH = "health";
	public static final String HEALTHREGEN = "healthRegen";	
	public static final String ARMOUR = "armour";
	public static final String SPEED = "speed";
	public static final String STAMINA = "stamina";
	public static final String STAMINAREGEN = "staminaRegen";
	public static final String WEAPONTYPE = "weaponType";
	public static final String DAMAGE = "damage";
	public static final String FIRERATE = "firerate";
	public static final String AMMO = "ammo";
	public static final String PROJECTILETYPE = "projectileType";
	public static final String PROJECTILESPEED = "projectileSpeed";
	public static final String ACCURACY = "accuracy";
	public static final String AGILITY = "agility";
	public static final String JUMPPOWER = "jumpPower";
	public static final String RELOADTIME = "reloadTime";
	
	private HashMap<String,File> sounds;
	
	public ResourceLoader() {
		animations = new HashMap<String, ArrayList<PImage>>();
		images = new HashMap<String, PImage>();
		textures = new HashMap<String, HashMap<Point, HashMap<Point, PImage>>>();
		sounds = new HashMap<String, File>();

		statistics = new HashMap<String,  HashMap<String,Double>>();

		characters = new ArrayList<String>();
		characters.add(TROOPER);
		characters.add(COMMANDER);
		characters.add(JUNGLEMAN);
		characters.add(ASSASSIN);
		characters.add(BASICZOMBIE);
		characters.add(SKIRTZOMBIE);
		characters.add(BONEZOMBIE);
		characters.add(BOYZOMBIE);
		characters.add(MUMMY);
		characters.add(MOHAWKZOMBIE);


		
		attributes = new ArrayList<String>();
		attributes.add(HEALTH);
		attributes.add(HEALTHREGEN);
		attributes.add(ARMOUR);
		attributes.add(SPEED);
		attributes.add(STAMINA);
		attributes.add(STAMINAREGEN);
		attributes.add(WEAPONTYPE);
		attributes.add(DAMAGE);
		attributes.add(FIRERATE);
		attributes.add(AMMO);
		attributes.add(PROJECTILETYPE);
		attributes.add(PROJECTILESPEED);
		attributes.add(ACCURACY);
		attributes.add(AGILITY);
		attributes.add(JUMPPOWER);
		attributes.add(RELOADTIME);
	
		
		badMobs = new ArrayList<String>();
		badMobs.add(BASICZOMBIE);
		badMobs.add(BONEZOMBIE);
		badMobs.add(SKIRTZOMBIE);

	}

	public void load() {
		
		//****Loading Stats of everything*****
		ArrayList<String> stats = new ArrayList<String>();

		stats = FileIO.readFile("resources" + fileSeparator + "Stats.txt");

		parseStats(stats);


		//****Loading Creature Animations*****
		ArrayList<String> animationNames = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();

		states.add("Walking");
		states.add("Standing");
		states.add("Running");
		states.add("Attacking");
		states.add("Jumping");
		states.add("MovingAndAttacking");


		animationNames.add("BasicZombie");
		animationNames.add("BoneZombie");
		animationNames.add("SkirtZombie");
		animationNames.add("Trooper");
		//parseAnimations();
		try {
			for(String name: animationNames) {
				for(String state:states) {
					ArrayList<PImage> list = new ArrayList<PImage>();
					int number = 1;
					while(true) { 
						File f = new File("resources" +fileSeparator + name + fileSeparator + state + fileSeparator + number+ ".png");
						if(!f.exists()) {
							break;
						}
						PImage img = new PImage(ImageIO.read(f));
						//System.out.print(++numberOfImages);
						list.add(img);
						number++;
					}

					animations.put(name+state, list);
				}
			}


			//*****Loading From Folders*****
			this.loadFromFolder("resources" + fileSeparator+"GUI",HUD.xScaleFactor,HUD.yScaleFactor);

			//****Loading Normal Images****

			images.put("Bullet", new PImage(ImageIO.read(new File(("resources"+fileSeparator+"Bullet.png")))));
			images.put("Bricks", new PImage(ImageIO.read(new File(("resources"+fileSeparator+"Bricks.jpg")))));
			images.put("Bedrock", new PImage(ImageIO.read(new File(("resources"+fileSeparator+"Bedrock.png")))));
			images.put("Dirt", new PImage(ImageIO.read(new File(("resources"+fileSeparator+"Dirt.png")))));
			images.put("Grass", new PImage(ImageIO.read(new File(("resources"+fileSeparator+"Grass.png")))));

			//****Loading Sounds****
			sounds.put("Shoot",new File("resources" +fileSeparator +"Sounds"+fileSeparator + "shoot.wav"));
			sounds.put("EmptyClick",new File("resources" +fileSeparator +"Sounds"+fileSeparator + "emptyClick.wav"));
			sounds.put("Reload",new File("resources" +fileSeparator +"Sounds"+fileSeparator + "reload.wav"));
			sounds.put("Chill",new File("resources" +fileSeparator +"Sounds"+fileSeparator + "ChillMusic_Small.wav"));


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This will return a cropped image.  bounds of (0, 0) are for raw scaled images
	 * 
	 * @param name - the name of the key for the image
	 * @param bounds - the bounds of the texture you want to get
	 * @return the cropped image
	 */
	public PImage getTexture(String name, Point bounds, Point cropBounds) {
		PImage img = this.getImage(name); 
		if(img != null && img.width == bounds.x && img.height == bounds.y && bounds.equals(cropBounds)) {
			return img;
		}
		if(textures.get(name) == null) {
			textures.put(name, new HashMap<Point, HashMap<Point, PImage>>());
		}
		if (textures.get(name).get(bounds) == null) {
			textures.get(name).put(bounds, new HashMap<Point, PImage>());				
		}
		if(textures.get(name).get(bounds).get(cropBounds) == null) {
		//	System.out.println("x: " + bounds.x + " y: " + bounds.y);
			img.resize(bounds.x, bounds.y);
			textures.get(name).get(bounds).put(cropBounds, img.get(0,0,cropBounds.x,cropBounds.y));
			//System.out.println(++numberOfImages);
		}
		return textures.get(name).get(bounds).get(cropBounds);
	}

	public ArrayList<PImage> getAnimationList(String key){
		return animations.get(key);

	}

	public PImage getAnimation(String key, int index) {
		return animations.get(key).get(index);
	}

	public int length(String key) {
		return animations.get(key).size();
	}

	public PImage getImage(String key) {
		return images.get(key); 
	}

	public File getSound(String key) {
		return sounds.get(key);
	}

	private void loadFromFolder(String folderPath,double xScale, double yScale) {
		File folder = new File(folderPath);
		final String[] files = folder.list();
		for(int i = 0; i < files.length; i++) {
			String filePath = files[i].substring(0, files[i].indexOf("."));
			try {
				PImage temp = new PImage(ImageIO.read(new File(folderPath+fileSeparator+files[i])));
				temp.resize((int)(temp.width*xScale), (int)(temp.height*yScale));
				images.put(filePath, temp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void parseAnimations() {
		ArrayList<String> Animations = FileIO.readFile("resources" + fileSeparator + "Animations.txt");
		String currentCreature = null;
		String currentAnimationState = null;
		ArrayList<PImage> animations = null;
		int width = 0;
		int height = 0;
		for(int i = 0 ; i < Animations.size(); i++) {
			String s = Animations.get(i);
			char one = s.charAt(0);
			if(one != '/') {
				if(one == '#') {
					currentCreature = s.substring(1,s.indexOf("="));
					s = s.substring(s.indexOf("="));
					width = Integer.parseInt(s.substring(s.indexOf(',')+1,s.lastIndexOf(',')));
					s=s.substring(s.indexOf(',')+1);
					height = Integer.parseInt(s.substring(0,s.indexOf(',')));
					s=s.substring(s.indexOf(',')+1);
					System.out.println(s);
					ScaleFactor.put(currentCreature, Double.parseDouble(s));
					animations = new ArrayList<PImage>();
				}else if(one == '*') {
					currentAnimationState = s.substring(s.indexOf("*")+1, s.indexOf("="));
					s = s.substring(s.indexOf("=")+1);
					this.framesPerAnimation.put(currentCreature+currentAnimationState, Integer.parseInt(s));
				}else {
					String num = s.substring(0,s.indexOf("="));
					int x = Integer.parseInt(s.substring(s.indexOf("=")+1,s.indexOf(",")));
					int y = Integer.parseInt(s.substring(s.lastIndexOf(",")));
					ArrayList<Point> off = Offsets.get(currentCreature+currentAnimationState);
					if(off == null) {
						Offsets.put(currentCreature+currentAnimationState, new ArrayList<Point>());
						off = Offsets.get(currentCreature+currentAnimationState);
					}
					off.add(new Point(x,y));
					try {
						animations.add(new PImage(ImageIO.read(new File("resources" +fileSeparator + currentCreature + fileSeparator + currentAnimationState + fileSeparator + num + ".png"))));
						System.out.println("added:" + "resources" +fileSeparator + currentCreature + fileSeparator + currentAnimationState + fileSeparator + num + ".png");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void parseStats(ArrayList<String> statsFile) {
		//statsFile is all the lines from the txt file

		for(int i = 0; i<statsFile.size();i++) {
			String line = statsFile.get(i);

			if(characters.contains(line)) {//if we are getting the stats of a character(zombies, heros

				HashMap<String,Double> atts = new HashMap<String,Double>();

				for(int j = 0;j<attributes.size();j++) { //fill the above hashmap with the attribute names and values
					String laterLine = statsFile.get(i+1+j);//start going through the next lines
					int equalIndex=laterLine.indexOf("=");	//find an equal sign

					if(equalIndex != -1) {
						double num = Double.parseDouble(laterLine.substring(equalIndex+1));//get out the num
						//System.out.println(attributes.get(j));
						atts.put(attributes.get(j), num);
					}
				}
				
				statistics.put(line, atts);//put the new atts hash with the corresponding chracter
				i+=attributes.size();//save some time

			}
		}	
	}
	
	public double getStat(String name, String attribute) {
		return statistics.get(name).get(attribute);
	}
	
	public ArrayList<String> getBadMobNames(){
		return badMobs;
	}

}
