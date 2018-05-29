package interfaces;

import processing.core.PApplet;

/**
 * Anything that takes in keyboard input.
 * @author bleistiko405
 *@version 5/8/18
 */
public interface Typeable extends Removable {
	public void keyPressed(PApplet marker);
	public void keyReleased(PApplet marker);
}
