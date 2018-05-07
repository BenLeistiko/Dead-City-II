package gamePlay;

import processing.core.PImage;

public class PImage {

	private String filename;
	private PImage image;
	
	public PImage(String filename) {
		this.filename = filename;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void setPImage(PImage image) {
		this.image = image;
	}
	
	public PImage getPImage() {
		return image;
	}
}
