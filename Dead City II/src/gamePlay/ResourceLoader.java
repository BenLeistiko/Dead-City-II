package gamePlay;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;
import sprites.Creature;

public class ResourceLoader {

	private HashMap<String, ArrayList<PImage>> animations;
	private HashMap<String, PImage> images;

	public ResourceLoader() {
		animations = new HashMap<String, ArrayList<PImage>>();
		images = new HashMap<String, PImage>();

	}

	public void load(PApplet loader) {
		ArrayList<String> animationNames = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();
		
		states.add("Attacking");

		animationNames.add("Zombie");
		animationNames.add("Hero");

		for(String name: animationNames) {
			ArrayList<PImage> list = new ArrayList<PImage>();
			for(String state:states) {
				int number = 1;
				while(true) {
					PImage img = loader.loadImage(name + state + number);
					if(img == null)
						break;
					list.add(img);
					number++;
				}
			}

			animations.put(name, list);

		}

	}


	public PImage getAnimation(String key, int index) {
		return animations.get(key).get(index);

	}

	public PImage getImage(String key) {
		return images.get(key);

	}


}
