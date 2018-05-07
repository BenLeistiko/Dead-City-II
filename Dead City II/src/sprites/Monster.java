package sprites;

import java.util.ArrayList;

public class Monster extends Creature {

	public Monster(String key,int x, int y, int w, int h, ArrayList<Sprite> worldlyThings) {
		super(x, y, w, h,worldlyThings, key);
	}

}
