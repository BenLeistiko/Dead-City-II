package gamePlay;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import scenes.BattleField;
import scenes.MainMenu;
import scenes.Scene;
/**
 * 
 * @author Jose
 * @version 5/5/2018 5:30
 *
 */
public class Main {
	public static final int frameRate = 60;
	public static final double frameTime = 1/60;
	public static final double GRAVITY = 1;//PIXLES per second^2
	public static final double FRICTION = .5;
	
	
	public static ResourceLoader resources;
	
	private JFrame window;

	private JPanel cardPanel;

	//private OptionPanel panel1;

	//private BattleField panel1;  // These are PApplets - you use these to do regular processing stuff
	//private MainMenu panel2;  // Even though we've named them "DrawingSurface", they are better thought of as "Drawer"s - like a Graphics object.

	private HashMap<String,Scene> panels;
	
	//private PSurfaceAWT surf;  // These are the "portals" through which the PApplets draw on the canvas
	//private PSurfaceAWT surf2;

	private HashMap<String, PSurfaceAWT> surfaces;
	
	//private PSurfaceAWT.SmoothCanvas processingCanvas;  // These are swing components (think of it as the canvas that the PApplet draws on to)
	//private PSurfaceAWT.SmoothCanvas processingCanvas2;   // They are what is literally in the window
	
	private HashMap<String, PSurfaceAWT.SmoothCanvas> processingCanvases;
	
	public Main() {
		resources = new ResourceLoader();
		
		window = new JFrame();

		window.setSize(900, 900);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		cardPanel = new JPanel();
		CardLayout cl = new CardLayout();
		cardPanel.setLayout(cl);

		panels = new HashMap<String,Scene>();
		surfaces = new HashMap<String, PSurfaceAWT>();
		processingCanvases = new HashMap<String, PSurfaceAWT.SmoothCanvas>();
		
		cardPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent arg0) {
				Component x = (Component)arg0.getSource();
				fixProcessingPanelSizes(x);
			}

		});
		
		this.addScene(new BattleField(this), "BattleField");
		this.addScene(new MainMenu(this), "MainMenu");
		
		window.setLayout(new BorderLayout());

		window.add(cardPanel);

		
		window.setVisible(true);

		//	window.setBounds(0, 0, 800, 600);

		window.setName("Dead City II");
		window.setLocation(0,0);

		Image icon = (new ImageIcon("resources/Dead-City-II-Icon.jpg")).getImage();
		window.setIconImage(icon);

		changePanel("BattleField");
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
}