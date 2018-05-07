package gamePlay;

import processing.core.PImage;

public class ImageWrapper {

	private String filename;
	private PImage image;
	
	public ImageWrapper(String filename) {
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
