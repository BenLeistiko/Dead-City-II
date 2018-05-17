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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
	private ArrayList<String> characters;
	
	
	private HashMap<String,EasySound2> sounds;
//	private final EasySound2 shootSound = new EasySound2("resources" +fileSeparator +"shoot.wav");
 
	//	private HashMap<String, SoundFile> sounds;


	public ResourceLoader() {
		animations = new HashMap<String, ArrayList<PImage>>();
		images = new HashMap<String, PImage>();
		textures = new HashMap<String, HashMap<Point, HashMap<Point, PImage>>>();
		sounds = new HashMap<String, EasySound2>();
		statistics = new HashMap<String, HashMap<String,Double>>();
		
	
		
	}

	public void load(PApplet loader) {
		//****Loading Stats of everything*****
		ArrayList<String> stats = new ArrayList<String>();
		
		stats = FileIO.readFile("resources" + fileSeparator + "Stats.txt");
		
		statistics = parseStats(stats);
		
	
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


		for(String name: animationNames) {
			for(String state:states) {
				ArrayList<PImage> list = new ArrayList<PImage>();
				int number = 1;
				while(true) { 
					File f = new File("resources" +fileSeparator + name + fileSeparator + state + fileSeparator + number+ ".png");
					if(!f.exists()) {
						break;
					}
					PImage img = loader.loadImage(f.getPath());
					//System.out.print(++numberOfImages);
					list.add(img);
					number++;
				}

				animations.put(name+state, list);
			}
		}


		//****Loading Normal Images****
		images.put("Bullet", loader.loadImage(fileSeparator+"resources"+fileSeparator+"Bullet.png"));
		images.put("Bricks", loader.loadImage(fileSeparator+"resources"+fileSeparator+"Bricks.jpg"));
		images.put("Bedrock", loader.loadImage(fileSeparator+"resources"+fileSeparator+"Bedrock.png"));
		images.put("Dirt", loader.loadImage(fileSeparator+"resources"+fileSeparator+"Dirt.png"));

		//****Loading Sounds****
		sounds.put("Shoot", new EasySound2("resources" +fileSeparator +"Sounds"+fileSeparator + "shoot.wav"));
		sounds.put("emptyClick", new EasySound2("resources" +fileSeparator +"Sounds"+fileSeparator + "emptyClick.wav"));
	
	
	}
	
	public HashMap<String, HashMap<String,Double>> parseStats(ArrayList<String> stats) {
		
		
		for(String s: stats) {
			//String line = );
			
			if(statistics.get("smth") == null) {
				statistics.put("smth", new HashMap<String, Double>());
			}
			
		//	statistics.get("trooper").put("healt", 40);
		//	statistics.put("trooper", "helth",6);
			
			
			
		}
		
		
		return null;
	}

	/**
	 * This will return a cropped image.  bounds of (0, 0) are for raw scaled images
	 * 
	 * @param name - the name of the key for the image
	 * @param bounds - the bounds of the texture you want to get
	 * @return the cropped image
	 */
	public PImage getTexture(String name, Point bounds, Point cropBounds) {
		if(textures.get(name) == null) {
			textures.put(name, new HashMap<Point, HashMap<Point, PImage>>());
		}
		if (textures.get(name).get(bounds) == null) {
			textures.get(name).put(bounds, new HashMap<Point, PImage>());				
		}
		if(textures.get(name).get(bounds).get(cropBounds) == null) {
			PImage img = this.getImage(name); 
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
	
	public EasySound2 getSound(String key) {
		return sounds.get(key);
	}
}
