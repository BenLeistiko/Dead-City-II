package interfaces;

import java.awt.Point;

import processing.core.PApplet;
/**
 * Anything that can be clicked
 * @author bleistiko405
 *
 */
public interface Clickable extends Removable{

	public void mousePressed(Point clickPoint, int button);
	public void mouseReleased(Point clickPoint, int button);
}
