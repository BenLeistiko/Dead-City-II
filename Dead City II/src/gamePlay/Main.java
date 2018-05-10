package gamePlay;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;
import scenes.BattleField;
import scenes.MainMenu;
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

	private BattleField panel1;  // These are PApplets - you use these to do regular processing stuff
	private MainMenu panel2;  // Even though we've named them "DrawingSurface", they are better thought of as "Drawer"s - like a Graphics object.

	private PSurfaceAWT surf;  // These are the "portals" through which the PApplets draw on the canvas
	private PSurfaceAWT surf2;

	private PSurfaceAWT.SmoothCanvas processingCanvas;  // These are swing components (think of it as the canvas that the PApplet draws on to)
	private PSurfaceAWT.SmoothCanvas processingCanvas2;   // They are what is literally in the window

	public Main() {
		resources = new ResourceLoader();
		
		window = new JFrame();

//		
		panel1 = new BattleField(this);//this and the next 4 lines must be repeated for each new scene along with the bottom method
		panel1.runMe();

		surf = (PSurfaceAWT) panel1.getSurface();
		processingCanvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();////
		// window = (JFrame)processingCanvas.getFrame();

		panel2 = new MainMenu(this);
		panel2.runMe();

		surf2 = (PSurfaceAWT) panel2.getSurface();
		processingCanvas2 = (PSurfaceAWT.SmoothCanvas) surf2.getNative();

		window.setSize(900, 900);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		cardPanel = new JPanel();
		CardLayout cl = new CardLayout();
		cardPanel.setLayout(cl);

		cardPanel.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent arg0) {
				Component x = (Component)arg0.getSource();
				fixProcessingPanelSizes(x);
			}

		});

		//	panel1 = new OptionPanel(this);    

		//cardPanel.add(panel1,"1");
		cardPanel.add(processingCanvas,"BattleField");
		cardPanel.add(processingCanvas2,"MainMenu");

		
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






	public static void main(String args[]) {
		Main m = new Main();
		/*
		resources = new ResourceLoader();
		
		DrawingSurface drawing = new DrawingSurface();
		PApplet.runSketch(new String[]{""}, drawing);
		PSurfaceAWT surf = (PSurfaceAWT) drawing.getSurface();
		PSurfaceAWT.SmoothCanvas canvas = (PSurfaceAWT.SmoothCanvas) surf.getNative();
		JFrame window = (JFrame)canvas.getFrame();

		window.setSize(900, 900);
		window.setMinimumSize(new Dimension(100,100));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(true);

		window.setName("Dead City II");
		window.setLocation(0,0);

		Image icon = (new ImageIcon("resources/Dead-City-II-Icon.jpg")).getImage();
		window.setIconImage(icon);

		window.setVisible(true);
		 */
	}



	public void changePanel(String name) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel,name);//cardpanel is a JPanel that gets added to the window JFrame
		if (name.equals("BattleField")) {
			processingCanvas.requestFocus();
			panel1.pause(panel1,false);
		} else if (name.equals("MainMenu")) {
			processingCanvas2.requestFocus();
			panel2.pause(panel2,false);
		}
	}

	public void fixProcessingPanelSizes(Component match) {
		surf.setSize(match.getWidth(),match.getHeight());
		surf2.setSize(match.getWidth(),match.getHeight());
	}



}
