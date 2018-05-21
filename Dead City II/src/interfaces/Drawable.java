package interfaces;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PApplet;
import scenes.Scene;
import sprites.Sprite;
/**
 * Anything that can be drawn.
 * @author Jose Amador
 *@version 5/5/2018 5:20
 */
public interface Drawable extends Removable{

	public abstract void draw(PApplet marker);
	public abstract void act(Scene s);
	public Rectangle2D.Double getHitBox();
	
}
