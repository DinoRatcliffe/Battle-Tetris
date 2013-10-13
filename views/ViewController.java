package views;

import static java.awt.Color.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import views.powerups.HideBoard;

import game.GameController;
import game.GameEvent;
import game.GameEventListener;
import game.Player;

public class ViewController {
	//number of variations of tetromino visuals
	public static final int NUMBER_VARIATIONS = 5;

	public static final Random random = new Random();
	
	//colors for specific parts
	private static Color blankArea = black;
	private static Color strongArea = gray;
	private static Color manaOrb = magenta;
	
	//lsit of possible tetromino colors
	static Color[] colors =
        {green, blue, red,
                yellow, pink, cyan};
	
	//needs different name to access from listeners
	private JFrame frame2;
	private GameController game2;
	
	
	private List<PlayerView> playerViews = new ArrayList<PlayerView>();
	
	/**
	 * Constructor
	 * 
	 * @param game	the game that this view needs to represent
	 * @param frame the frame that this view should use to display the game
	 */
	public ViewController(GameController game, JFrame frame){
			this.game2 = game;
			
			//pause game when frame looses focus
			frame.requestFocus();
			frame.addFocusListener(new FocusListener(){

				@Override
				public void focusGained(FocusEvent e) {
					for (Player p: game2.getPlayers()){
						p.resetSpeed();
					}
					game2.resume();
				}

				@Override
				public void focusLost(FocusEvent e) {
					game2.pause();
				}
				
			});
		
			// setup grid layout
			int cols = 1;
			if (game.getPlayerCount()>4){
				cols = 3;
			} else if (game.getPlayerCount()>2){
				cols = 2;
			}
		
			frame.setLayout(new GridLayout(0, cols, 10, 10));
			
			
			//add player views along with upcoming to the frame
			for (int i = 1; i<=game.getPlayerCount(); i+=2){
				PlayerView playerView = new PlayerView(game.getPlayer(i), this, true);
				playerViews.add(playerView);		
				
				JPanel pair = new JPanel();
				pair.setLayout(new BorderLayout());
				pair.add(playerView, BorderLayout.WEST);
				pair.add(new UpcomingView(this, game.getUpcoming()), BorderLayout.CENTER);
				if(i+1 <= game.getPlayerCount()){
					playerView = new PlayerView(game.getPlayer(i+1), this, false);
					playerViews.add(playerView);
					pair.add(playerView, BorderLayout.EAST);
				}
				frame.add(pair);
			}
			
			//prepare frame
			frame.pack();
			frame.repaint();
			
			
			//setup listener to repaint when needed
			this.frame2 = frame;
			game.addStateListener(new GameEventListener(){

				@Override
				public boolean handleEvent(GameEvent e) {
					frame2.repaint();
					return false;
				}
				
			});
			
			//register a custom powerup
			game.addPowerUp(2, new HideBoard(playerViews));
	}
	
	//getters
	public Color getColor(int i){
		return colors[i];
	}
	
	public Color getBlankColor(){
		return blankArea;
	}
	
	public Color getManaColor(){
		return manaOrb;
	}
	
	public static int getRandomVisual(){
		return random.nextInt(NUMBER_VARIATIONS)+1;
	}

	public Color getStrongColor() {
		return strongArea;
	}

}
