package gamePlay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class ResourceLoader {

	private HashMap<String, ArrayList<PImage>> animations;
	private HashMap<String, PImage> images;

	public ResourceLoader() {
		
		
	}

	public void load(PApplet loader) {
		ArrayList<PImage> zombieWalking = new ArrayList<PImage>();
		
		zombieWalking.add(loader.loadImage("walking1"));
		zombieWalking.add(loader.loadImage("walking2"));
		animations.put("zombieWalking", zombieWalking);
		
		ArrayList<PImage> ZombieRunning = new ArrayList<PImage>();
		ZombieRunning.add(loader.loadImage("running1"));
		ZombieRunning.add(loader.loadImage("running1"));
		
		animations.put("ZombieRunning", ZombieRunning);
		
		
		
		ArrayList<PImage> list = new ArrayList<PImage>();
		
		list.add(loader.loadImage("box.wow"));

		animations.put("somethingElse", list);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		ArrayList<PImage> list = new ArrayList<PImage>();
		list.add(loader.loadImage("running"));
		list.add(loader.loadImage("running2"));
		
		animations.put("BruteRunning", list);
		
		
		
		
		Collection<ArrayList<PImage>> c = animations.values();
		for(ArrayList<PImage> list : c) {
			for(PImage i: list) {
				i.setPImage(loader.loadImage(i.getFilename()));
			}
		}
	}


	public PImage getAnimation(String key, int index) {
		return animations.get(key).get(index).getPImage();

	}
	
	public PImage getImage(String key) {
		return images.get(key).getPImage();

	}


}
