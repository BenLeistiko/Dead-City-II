package Menus;

import java.awt.geom.Rectangle2D.Double;

import interfaces.Drawable;
import processing.core.PApplet;

public abstract class Menu implements Drawable{

	private boolean isVisible;
	

	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

}
