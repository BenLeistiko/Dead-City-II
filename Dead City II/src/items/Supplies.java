package items;

import interfaces.Drawable;
import processing.core.PApplet;

public class Supplies implements Drawable {

	private boolean remove;

	public void draw(PApplet marker) {

	}

	@Override
	public boolean shouldRemove() {
		return remove;
	}

}
