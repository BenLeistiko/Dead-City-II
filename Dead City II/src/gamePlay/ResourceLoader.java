package gamePlay;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import processing.core.PApplet;
import processing.core.PImage;
import processing.sound.SoundFile;
import sprites.Creature;

public class ResourceLoader {

	private static String fileSeparator = System.getProperty("file.separator");

	private HashMap<String, ArrayList<PImage>> animations;
	private HashMap<String, PImage> images;
	private HashMap<String, SoundFile> sounds;


	public ResourceLoader() {
		animations = new HashMap<String, ArrayList<PImage>>();
		images = new HashMap<String, PImage>();
		sounds = new HashMap<String, SoundFile>();

	}

	public void load(PApplet loader) {
		//****Loading Creature Animations*****
		ArrayList<String> animationNames = new ArrayList<String>();
		ArrayList<String> states = new ArrayList<String>();

		states.add("Walking");
		states.add("Standing");
		states.add("Running");
		states.add("Attacking");
		states.add("Jumping");
		states.add("MovingAndAttacking");


		animationNames.add("Trooper");

		for(String name: animationNames) {
			for(String state:states) {
				ArrayList<PImage> list = new ArrayList<PImage>();
				int number = 1;
				while(true) { 
					PImage img = loader.loadImage(fileSeparator+"resources" +fileSeparator + name + fileSeparator + state + fileSeparator + number+ ".png");
					if(img == null)
						break; 
					//list.add(cropedImage(img));
					list.add(img);
					number++;
				}

				animations.put(name+state, list);
			}
		}


		//****Loading Normal Images****
		images.put("Bullet", loader.loadImage(fileSeparator+"resources"+fileSeparator+"Bullet.png"));
		images.put("Barrier", loader.loadImage(fileSeparator+"resources"+fileSeparator+"Barrier.jpg"));

		//****Loading Sounds****
	//	sounds.put("Shoot", new SoundFile(loader,fileSeparator+"resources"+fileSeparator+"Shoot.mp3"));
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

	public PImage cropedImage(PImage pImg) {//still does not work
		int margin = 5;
		Image img = pImg.getImage();
		BufferedImage buffedImg = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffedImg.createGraphics();
		g.drawImage(img, 0, 0, null);
		g.dispose();

		int x1 = 0;
		int x2 = buffedImg.getWidth(null)-1;
		int y1 = 0;
		int y2 = buffedImg.getWidth(null)-1;

		while(true) {
			boolean b = false;
			for(int i = 0; i < buffedImg.getHeight(); i++) {
				Color c = new Color(buffedImg.getRGB(x1, i));
				//System.out.println("Loop 1: PIXLE found at (" + x1 + ", " + i + ") color: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
				if(c.getBlue() > margin || c.getRed() >margin || c.getGreen() > margin) {
					b = true;
					break;
				}
			}
			if(b)
				break;
			x1++;
		}
		while(true) {
			boolean b = false;
			for(int i = 0; i < buffedImg.getHeight(); i++) {
				Color c = new Color(buffedImg.getRGB(x2, i));
				//System.out.println("Loop 2: PIXLE found at (" + x2 + ", " + i + ") color: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
				if(c.getBlue() > margin || c.getRed() >margin || c.getGreen() > margin) {
					b = true;
					break;
				}
			}
			if(b)
				break;
			x2--;
		}
		while(true) {
			boolean b = false;
			for(int i = 0; i < buffedImg.getWidth(); i++) {
				Color c = new Color(buffedImg.getRGB(i, y1));
				//System.out.println("Loop 3: PIXLE found at (" + i + ", " + y1 + ") color: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
				if(c.getBlue() > margin || c.getRed() >margin || c.getGreen() > margin) {
					b = true;
					break;
				}
			}
			if(b)
				break;
			y1++;
		}
		while(true) {
			boolean b = false;
			for(int i = 0; i < buffedImg.getWidth(); i++) {
				Color c = new Color(buffedImg.getRGB(i, y2));
				//System.out.println("Loop 4: PIXLE found at (" + i + ", " + y2 + ") color: " + c.getRed() + ", " + c.getGreen() + ", " + c.getBlue());
				if(c.getBlue() > margin || c.getRed() >margin || c.getGreen() > margin) {
					b = true;
					break;
				}
			}
			if(b)
				break;
			y2--;
		}

		System.out.println(x1 + ", "+ y1 + "||" +  (x2-x1) + "," + (y2-y1) + "\n");
		return new PImage(buffedImg.getSubimage(x1, y1, x2-x1, y2-y1));
	}


}
