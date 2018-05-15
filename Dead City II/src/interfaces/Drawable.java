package interfaces;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
/**
 * Anything that can be drawn.
 * @author Jose Amador
 *@version 5/5/2018 5:20
 */
public interface Drawable extends Removable{

	public abstract void draw(PApplet marker);
	public Rectangle2D.Double getHitBox();
	
}
