package sprites;

import java.util.ArrayList;
/**
 * CPU controlled monster that attacks heros.
 * @author bleistiko405
 *
 */
public class Monster extends Creature {

	public Monster(String key,int x, int y, int w, int h, ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h,worldlyThings, key);
	}

}
