package gamePlay;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import items.RangedWeapon;
import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import scenes.*;
import sprites.Hero;
/**
 * This class holds the main method and all of the scene that could be played during the game.  It also has structure to swap the scene that is currently being played.
 * @author Jose
 * @version 5/5/2018 5:30
 *
 */
public class Main {
	public static final int frameRate = 40;
	public static final double frameTime = 1/40.0;
	public static final double GRAVITY = 1;//PIXLES per second^2
	public static final double FRICTION = 1;
	public static boolean isBattle;
	public static boolean playMusic;

	
	private double battleVolume = 0;
	private double chillVolume = 1;

	BetterSound battle;
	BetterSound chill;

	public static ResourceLoader resources;

	private JFrame window;

	private JPanel cardPanel;

	private HashMap<String,Scene> panels;// These are PApplets - you use these to do regular processing stuff
	private HashMap<String, PSurfaceAWT> surfaces; // These are the "portals" through which the PApplets draw on the canvas
	private HashMap<String, PSurfaceAWT.SmoothCanvas> processingCanvases; // These are swing components (think of it as the canvas that the PApplet draws on to), They are what is literally in the window

	public Main() {
		resources = new ResourceLoader();
		resources.load();

		isBattle = false;

		chill = new BetterSound(Main.resources.getSound("Chill"), true, true);
		battle = new BetterSound(Main.resources.getSound("Battle"), true, true);
		playMusic = true;
		
		battle.setVolume(battleVolume);
		chill.setVolume(chillVolume);
		
		
		window = new JFrame();

		window.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		window.setMinimumSize(new Dimension(Scene.ASSUMED_DRAWING_WIDTH,Scene.ASSUMED_DRAWING_HEIGHT));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		cardPanel = new JPanel();
		CardLayout cl = new CardLayout();
		cardPanel.setLayout(cl);

		panels = new HashMap<String,Scene>();
		surfaces = new HashMap<String, PSurfaceAWT>();
		processingCanvases = new HashMap<String, PSurfaceAWT.SmoothCanvas>();

		cardPanel.addComponentListener(new ComponentAdapter() {//This rests the sizes of the processing windows when they have a change in size

			public void componentResized(ComponentEvent arg0) {
				Component x = (Component)arg0.getSource();
				fixProcessingPanelSizes(x);
			}
		});
		Hero joe = new Hero(Main.resources.TROOPER, 12500,100,90,90,
				new RangedWeapon(Main.resources.getStat(Main.resources.TROOPER, Main.resources.DAMAGE),Main.resources.getStat(Main.resources.TROOPER, Main.resources.FIRERATE),
						Main.resources.getStat(Main.resources.TROOPER, Main.resources.PROJECTILESPEED),(int)Main.resources.getStat(Main.resources.TROOPER, Main.resources.AMMO),Main.resources.getStat(Main.resources.TROOPER, Main.resources.RELOADTIME)));
		this.addScene(new TitleScreen(this, joe), "TitleScreen"); 
		this.addScene(new BattleField(this, joe), "BattleField");
		this.addScene(new Camp(this,joe), "Camp");
		this.addScene(new Pause(this,joe), "Pause");
		this.addScene(new Death(this,joe), "Death");

		changePanel("TitleScreen");


		window.setLayout(new BorderLayout());

		window.add(cardPanel);

		window.setVisible(true);

		window.setName("Dead City II");

		window.setSize(Scene.ASSUMED_DRAWING_WIDTH, Scene.ASSUMED_DRAWING_HEIGHT);
		window.setResizable(false);

		Image icon = (new ImageIcon("resources/Dead-City-II-Icon.jpg")).getImage();
		window.setIconImage(icon);


	}

	/**
	 * adds and sets up a scene
	 * s - the scene to add
	 * @pre the arraylist must be initialized
	 */
	public void addScene(Scene s, String name) {
		panels.put(name, s);
		s.runMe();
		surfaces.put(name, (PSurfaceAWT) s.getSurface());
		processingCanvases.put(name, (PSurfaceAWT.SmoothCanvas) surfaces.get(name).getNative());
		cardPanel.add(processingCanvases.get(name), name);
	}

	public Scene getScene(String name) {
		return panels.get(name);
	}

	public static void main(String args[]) {
		Main m = new Main();
	}

	public void changePanel(String name) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel,name);//cardpanel is a JPanel that gets added to the window JFrame
		processingCanvases.get(name).requestFocus();
		Scene s = panels.get(name);
		s.pause(s,false);
	}

	public void fixProcessingPanelSizes(Component match) {
		Collection<PSurfaceAWT> c = surfaces.values();
		for(PSurfaceAWT surf:c) {
			surf.setSize(match.getWidth(),match.getHeight());
		}
	}

	public void manageMusic() {

		if(isBattle) {
			battleVolume = Math.min(battleVolume + 0.005, 1);
			chillVolume = Math.max(chillVolume - 0.005, 0);
		}else {
			battleVolume = Math.max(battleVolume - 0.005, 0);
			chillVolume = Math.min(chillVolume + 0.005, 1);
		}
		if(playMusic) {
			battle.setVolume(battleVolume);
			chill.setVolume(chillVolume);
		}else {
			battle.setVolume(0);
			chill.setVolume(0);
		}


	}
}