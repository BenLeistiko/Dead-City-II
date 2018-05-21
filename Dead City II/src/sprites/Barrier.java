package sprites;

import java.awt.Point;

import gamePlay.Main;
import processing.core.PApplet;
import processing.core.PImage;
import scenes.Scene;
/**
 * An rectangle that can't take damage but can collide with sprites. 
 * @author bleistiko405
 *
 */
public class Barrier extends Sprite {

	private PImage mainImg,cornerImg,bottomImg,sideImg; 
	private boolean shouldBeRemoved;
	private double textureHeight,textureWidth;
	private int cropX,cropY;
	
	static int Resizes = 0;
	public Barrier(double x, double y, double w, double h, double textureW, double textureH, String key) {
		super(x, y, w, h);

		shouldBeRemoved = false;

		textureHeight = textureH;
		textureWidth = textureW;
		
		cropX = (int) (super.getWidth()-(Math.ceil(super.getWidth()/textureWidth)-1.0001)*textureWidth);
		cropY = (int) (super.getHeight()-(Math.ceil(super.getHeight()/textureHeight)-1.001)*textureHeight);
		
		Point bounds = new Point((int)textureW, (int) textureH);
		mainImg = Main.resources.getTexture(key, bounds, new Point((int)textureW, (int)textureH));
		cornerImg = Main.resources.getTexture(key, bounds, new Point(cropX, cropY));
		bottomImg = Main.resources.getTexture(key, bounds, new Point((int)textureWidth, cropY));
		sideImg = Main.resources.getTexture(key, bounds, new Point(cropX, (int)textureHeight));

		
		/*mainImg = Main.resources.getImage(key);
		if(Math.abs(mainImg.width - textureWidth) > 0.0001 || Math.abs(mainImg.height - textureHeight) > 0.0001) {
			mainImg.resize((int)textureWidth, (int)textureHeight);
		}

		

		bottomImg = mainImg.get(0, 0, (int)textureWidth, cropY);
		sideImg = mainImg.get(0, 0, cropX, (int)textureHeight);
		cornerImg = mainImg.get(0,0,cropX,cropY);*/
	}

	public void draw(PApplet marker) {
		double yMax = Math.ceil(super.getHeight()/textureHeight)-1;
		double xMax = Math.ceil(super.getWidth()/textureWidth)-1;
		for(int y = 0; y <=yMax; y++) {
			for(int x = 0; x <=xMax;x++){
				if(y==yMax) {
					if(x == xMax) {
						marker.image(cornerImg, (float)(getX() + x*textureWidth), (float)(getY()+ y*textureHeight),(float)cropX,(float)cropY);
						//System.out.println(x + ", " + y + ": " + cropX + ", " + cropY);
					}else {
						marker.image(bottomImg, (float)(getX() + x*textureWidth), (float)(getY()+ y*textureHeight),(float)textureWidth,(float)cropY);
						//System.out.println(x + ", " + y + ": " + textureWidth + ", " + cropY);
					} 
				}else if(x==xMax) {
					marker.image(sideImg, (float)(getX() + x*textureWidth), (float)(getY()+ y*textureHeight),(float)cropX,(float)textureHeight);
					//System.out.println(x + ", " + y + ": " + cropX + ", " + textureHeight);
				}else {
					marker.image(mainImg, (float)(getX() + x*textureWidth), (float)(getY()+ y*textureHeight),(float)textureWidth,(float)textureHeight);
					//System.out.println(x + ", " + y + ": " + textureWidth + ", " + textureHeight);
				}
			}
		}

		
		/*marker.image(mainImg, (float)x, (float)y);
		marker.image(bottomImg, (float)x+200, (float)y);
		marker.image(sideImg, (float)x+400, (float)y);
		marker.image(cornerImg, (float)x+600, (float)y);*/

	}

	public boolean shouldRemove() {
		return shouldBeRemoved;
	}

	public void remove() {
		shouldBeRemoved = true;
	}

	@Override
	public void act(Scene s) {
		// TODO Auto-generated method stub
		
	}

}
