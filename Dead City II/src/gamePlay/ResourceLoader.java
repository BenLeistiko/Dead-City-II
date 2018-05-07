package gamePlay;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.Creature;

public class ResourceLoader {

	private static String fileSeparator = System.getProperty("file.separator");
	
	private HashMap<String, ArrayList<PImage>> animations;
	private HashMap<String, PImage> images;

	public ResourceLoader() {
		animations = new HashMap<String, ArrayList<PImage>>();
		images = new HashMap<String, PImage>();

	}

	public void load(PApplet loader) {
		
		//****Loading Creature Animations*****
		ArrayList<String> animationNames = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();
		
		states.add("Attacking");
		states.add("Attacking");
		states.add("Attacking");
		states.add("Attacking");

		
		animationNames.add("Zombie");
		animationNames.add("Hero");

		for(String name: animationNames) {
			for(String state:states) {
				ArrayList<PImage> list = new ArrayList<PImage>();
				int number = 1;
				while(true) {
					PImage img = loader.loadImage(fileSeparator+"resources" +fileSeparator + name + fileSeparator + state + fileSeparator + number+ ".png");
					if(img == null)
						break; 
					list.add(img);
					number++;
				}
				animations.put(name+state, list);
			}
		}

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


}
