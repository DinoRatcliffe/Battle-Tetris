package controls;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import controls.powerups.RandomControls;

import game.GameController;
import utilities.GlobalKeyListener;

/**
 * This class sets up the controls for each player.it also includes defaults for the first
 * 4 players. if you want more than 4 player you will need to set up the extra keys by passing in
 * a custom int[][] of keycodes
 * 
 * keycodes[i][j] where i indicates a player and j indicates a specific key
 * 			   0 = rotate
 * 			   1 = move left
 * 			   2 = move right
 * 		  	   3 = speed up
 * 			   4 = spend 1 mana
 * 			   5 = spend 2 mana
 * 			   6 = spend 3 mana
 *  		   7 = spend 4 mana
 * 
 * @author Dino Ratcliffe
 *
 */
public class ControlController {
	public static final int[][] DEFAULT_CONTROLS = {
		{
			KeyEvent.VK_W,
			KeyEvent.VK_A,
			KeyEvent.VK_D,
			KeyEvent.VK_S,
			KeyEvent.VK_1,
			KeyEvent.VK_2,
			KeyEvent.VK_3,
			KeyEvent.VK_4
		},
		{
			KeyEvent.VK_I,
			KeyEvent.VK_J,
			KeyEvent.VK_L,
			KeyEvent.VK_K,
			KeyEvent.VK_7,
			KeyEvent.VK_8,
			KeyEvent.VK_9,
			KeyEvent.VK_0
		},
		{
			KeyEvent.VK_UP,
			KeyEvent.VK_LEFT,
			KeyEvent.VK_RIGHT,
			KeyEvent.VK_DOWN,
			KeyEvent.VK_DELETE,
			KeyEvent.VK_END,
			KeyEvent.VK_PAGE_DOWN,
			KeyEvent.VK_PAGE_UP
		},
		{
			KeyEvent.VK_NUMPAD5,
			KeyEvent.VK_NUMPAD1,
			KeyEvent.VK_NUMPAD3,
			KeyEvent.VK_NUMPAD2,
			KeyEvent.VK_NUMPAD7,
			KeyEvent.VK_NUMPAD8,
			KeyEvent.VK_NUMPAD9,
			KeyEvent.VK_ADD
		}
	};
	
	private GameController game;
	
	//int array containing the keycodes for each players controls
	private int[][] keyCodes;
	
	//array of player control objects
	private List<PlayerControl> controls = new ArrayList<PlayerControl>();
	
	/**
	 * Constructor
	 * 
	 * @param game gamecontroller that contains the player this class should control
	 * @param keyCodes custom keycodes to be used for each player in the format
	 * 			       
	 */
	public ControlController(GameController game, int[][] keyCodes){
		//init variables.
		this.game = game;
		this.keyCodes = keyCodes;
		
		//create global key listener
		GlobalKeyListener input = new GlobalKeyListener(true);
		
		//create player control objects
		PlayerControl con;
		for (int i = 0; i<game.getPlayerCount(); i++){
			con = new PlayerControl(game.getPlayer(i+1), keyCodes[i]);
			controls.add(con);
			input.addListener(con);
		}
		
		//register custom powerup with the game
		game.addPowerUp(4, new RandomControls(controls));
		
	}
	
	/**
	 * Constructor, with default controls
	 * 
	 * @param game gameController that contains the player this class should control
	 */
	public ControlController(GameController game){
		this(game, DEFAULT_CONTROLS);
	}

}
