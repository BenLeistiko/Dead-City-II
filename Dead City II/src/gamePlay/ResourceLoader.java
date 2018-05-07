package gamePlay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;

public class ResourceLoader {

	private HashMap<String, ArrayList<ImageWrapper>> animations;
	private HashMap<String, ImageWrapper> images;

	public ResourceLoader() {
		ArrayList<ImageWrapper> walking = new ArrayList<ImageWrapper>();
	
		walking.add(new ImageWrapper("walking1.jpg"));
		walking.add(new ImageWrapper("walking2.jpg"));
		animations.put("zombieWalking", walking);
		
		ArrayList<ImageWrapper> running = new ArrayList<ImageWrapper>();
		running.add(new ImageWrapper("running1.jpg"));
		running.add(new ImageWrapper("running2.jpg"));
		
		animations.put("running", running);
		
		
		
		ArrayList<ImageWrapper> list = new ArrayList<ImageWrapper>();
		
		list.add(new ImageWrapper("somethingElse1.jpg"));
		list.add(new ImageWrapper("somethingElse2.jpg"));
		animations.put("somethingElse", list);
		
		
		
		images.put("basic not animated thing", new ImageWrapper("this some box thing that dont move.png"));
		
		

	}

	public void load(PApplet loader) {
		Collection<ArrayList<ImageWrapper>> c = animations.values();
		for(ArrayList<ImageWrapper> list : c) {
			for(ImageWrapper i: list) {
				i.setPImage(loader.loadImage(i.getFilename()));
			}
		}
	}


	public ImageWrapper getAnimation(String key, int index) {
		return animations.get(key).get(index);

	}
	
	public ImageWrapper getImage(String key) {
		return images.get(key);

	}


}
