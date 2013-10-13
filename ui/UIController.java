package ui;

import game.GameController;
import game.GameEvent;
import game.GameEventListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controls.ControlController;

import sound.SoundController;
import ui.panel.OptionsPanel;
import ui.panel.TitlePanel;
import views.ViewController;

/**
 * This is a controller that is in charge of the main ui for setting up and starting a
 * game of tetris.
 * 
 * @author DinoRatcliffe
 *
 */
public class UIController {
	
	//I am not having sound on for my submission, as it could fail spectacularly, but if you find this piece of code
	//then uncomment it and run this program as a native java program not an applet and enjoy ... 
	private static boolean soundActive = false;
	private static boolean isChristmas = false;
	
	private static final int DEFAULT_WIDTH = 400,
			 				 DEFAULT_HEIGHT = 400;
	
	public static final int OPTIONS = 1;
	public static final int TITLE = 0;
	
	private JFrame frame;
	private List<JPanel> panels = new ArrayList<JPanel>();
	private int[][] playerKeyCodes;
	
	//different name needed for listeners
	private GameController game2;
	
	/**
	 * Constructor
	 * 
	 * @param frame			the frame to display in
	 * @param maxPlayers	the max number of players the user is allowed to selec	t
	 * @param width			the preferred width of the menu
	 * @param height		the preferred height of the menu
	 */
	public UIController(JFrame frame, int maxPlayers, int width, int height){
		this.frame = frame;
		panels.add(new TitlePanel(this, maxPlayers, width, height));
		
		playerKeyCodes = new int[maxPlayers][8];
		for (int i = 0; i<ControlController.DEFAULT_CONTROLS.length; i++){
			playerKeyCodes[i] = ControlController.DEFAULT_CONTROLS[i];
		}
		panels.add(new OptionsPanel(this, playerKeyCodes, TITLE, width));
		
		//TODO clean this up and add panels correctly
		frame.add(panels.get(0));
		frame.pack();
		frame.repaint();
	}
	
	/**
	 * Constructor, with default width and height
	 * 
	 * @param frame			the frame to display in
	 * @param maxPlayers	the max number of players the user is allowed to select
	 */
	public UIController(JFrame frame, int maxPlayers){
		this(frame, maxPlayers, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	/**
	 * removes all the panels that could have been added by this object
	 * from the frame
	 */
	public void removeAllPanels(){
		for (JPanel j : panels){
			frame.remove(j);
		}
	}
	
	/**
	 * show a specified panel in the frame
	 * 
	 * @param panel the index of the panel in panels
	 */
	public void showPanel(int panel){
		removeAllPanels();
		frame.add(panels.get(panel));
		frame.pack();
		frame.repaint();
	}

	/**
	 * links all appropriate classes and starts the game.
	 * 
	 * i feel like this should be in a different classl
	 * 
	 * @param numPlayers the number of players to have in the game
	 */
	public void startGame(int numPlayers) {
		
		removeAllPanels();
		GameController game = new GameController(numPlayers);
		ViewController view = new ViewController(game, frame);
		ControlController control = new ControlController(game, playerKeyCodes);
		
		if (soundActive) {
			SoundController sound = new SoundController(game);
			if(isChristmas) sound.setBgFile("sounds/christmas.wav");
		}
		
		//added for compliance with assignment
		game2 = game;
		if (numPlayers == 1){
			frame.addMouseListener(new MouseListener(){
	
				@Override
				public void mouseClicked(MouseEvent e) {
					switch (e.getButton()){
						case MouseEvent.BUTTON1:
							game2.getPlayer(1).moveLeft();
							break;
						case MouseEvent.BUTTON2:
							game2.getPlayer(1).rotatePiece();
							break;
						case MouseEvent.BUTTON3:
							game2.getPlayer(1).moveRight();
							break;
					}
				}
	
				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mousePressed(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	
				@Override
				public void mouseReleased(MouseEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
			});
		}
		game.startGame();
	}
	
	//setters
	public void setSoundActive(boolean active){
		soundActive = active;
	}
	
	public void setIsChristmas(boolean christmas){
		isChristmas = christmas;
	}
}
