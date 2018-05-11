package interfaces;

import processing.core.PApplet;
/**
 * Anything that can be clicked
 * @author bleistiko405
 *
 */
public interface Clickable extends Removable{

	public void mousePressed(PApplet marker);
	public void mouseReleased(PApplet marker);
}
