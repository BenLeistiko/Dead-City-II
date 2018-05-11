package sprites;

import java.util.ArrayList;

import processing.core.PApplet;
/**
 * CPU controlled monster that attacks heros.
 * @author bleistiko405
 *
 */
public class Monster extends Creature {

	public Monster(String key,double x, double y, double w, double h, ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h,worldlyThings, key);
	}

	public void draw(PApplet marker) {
		super.draw(marker);
	}

}
