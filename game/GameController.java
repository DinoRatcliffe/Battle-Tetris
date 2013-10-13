package game;

import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import views.ViewController;

import game.powerups.ManaSteal;
import game.powerups.PowerUp;
import game.powerups.PushLeft;
import game.powerups.Reinforce;
import game.powerups.RemoveRows;
/**
 * game controller that deals with all the players in the game and also handles the handing out of new pieces
 * and activating powerUps 
 * 
 * @author Dino Ratcliffe
 *
 */
public class GameController {
	
	//constants
	public static final int DEFAULT_GAME_DELAY = 300;
	
	public static final int BOARD_WIDTH = 10, 
							BOARD_HEIGHT = 20,
							NUM_UPCOMING_TET = 3;
	
	// the standard tetrominoes used in this game. initialised to start just above the board as close to the
	// center as possible. Visual code is change before thease a used so any number can be given here.
	public Tetromino[] STD_TETROMINOS = {
														new Tetromino(new int[][] {{1, 1, 1, 1}}, BOARD_WIDTH/2, -1, 1),
														new Tetromino(new int[][] {{1,1,1}, {0,0,1}}, BOARD_WIDTH/2, -1, 1),
														new Tetromino(new int[][] {{1,1,1},{1,0,0}}, BOARD_WIDTH/2, -1, 1),
														new Tetromino(new int[][] {{1,1}, {1,1}}, BOARD_WIDTH/2, -1, 1),
														new Tetromino(new int[][] {{0,1,1},{1,1,0}}, BOARD_WIDTH/2, -1, 1),
														new Tetromino(new int[][] {{1,1,1},{0,1,0}}, BOARD_WIDTH/2, -1, 1),
														new Tetromino(new int[][] {{1,1,0}, {0,1,1}}, BOARD_WIDTH/2, -1, 1)
										};

	//map of powerups associated with there cost/level
    private Map<Integer, List<PowerUp>> powerUps = new HashMap<Integer, List<PowerUp>>();
	
    //probability of a new tetromino having a mana orb. not fully accurate as java.util.Random is used. 
    private double manaProduction = 0.3;
	private Random random = new Random();
	
	//list of upcoming pieces as well as lists of registered listeners and players.
	private List<Tetromino> futurePieces = new LinkedList<Tetromino>();
	private List<Player> players = new ArrayList<Player>();
	private List<GameEventListener> stateListeners = new ArrayList<GameEventListener>();

	/**
	 * 
	 * Constructor
	 * 
	 * @param numPlayers int number of players to play this game.
	 */
	public GameController(int numPlayers){
		
		//creates players and adds them to the list also adds state change listeners for listener updating
		for (int i = 0; i<numPlayers; i++){
			players.add(new Player(BOARD_WIDTH, BOARD_HEIGHT, this, "Player " + (i+1)));
			players.get(i).addStateChangeListener(new PlayerStateListener(){
				@Override
				public void playerStateUpdated(Player player) {
					notifyStateListeners();
				}
			});
		}
        
        if (numPlayers == 1){
        	manaProduction = -1;
        	players.get(0).setSpeedUp(true);
        } else {
        	//init game powerups
            this.addPowerUp(4, new Reinforce());
            this.addPowerUp(1, new RemoveRows(3));
            this.addPowerUp(3, new ManaSteal());
            this.addPowerUp(1, new PushLeft());
        }
        
        //init upcoming tetrominoes
  		for (int i = 0; i<NUM_UPCOMING_TET; i++){
  			Tetromino newTet = new Tetromino(STD_TETROMINOS[random.nextInt(STD_TETROMINOS.length)], ViewController.getRandomVisual());
  			futurePieces.add(newTet);
  		}
		
	}
	
	/**
	 * selects a random powerUp of the supplied level and then activates it.
	 * 
	 * @param activator	the player that is requesting the powerUp
	 * @param level		the level of powerUp that the player has requested
	 */
	public void activatePowerUp(Player activator, int level) {
		List<Player> newList = new ArrayList<Player>(players);
		newList.remove(activator);
        PowerUp power = powerUps.get(level).get(random.nextInt(powerUps.get(level).size()));
        power.run(activator, newList);
	}
	
	/**
	 * allows external classes to register there own powerUps that affect the part of the program 
	 * they control
	 * 
	 * @param cost	the cost to be associated with this powerup
	 * @param power a class that implements the powerUp interface
	 */
	public void addPowerUp(int cost, PowerUp power){
        List<PowerUp> list = powerUps.get(cost);
		if (list == null){
            list = new ArrayList<PowerUp>();
            powerUps.put(cost, list);
        }
        list.add(power);
	}
	
	/**
	 * starts all of the players associated with this game. 
	 * start with there delays set to the game default
	 */
	public void startGame(){
        for (Player p: players){
            p.changeDelay(DEFAULT_GAME_DELAY, true);
            p.start();
        }
	}

	/**
	 * called by a player to indicate that they have ended there game
	 * 
	 * @param player the play that has finished
	 */
	public void gameEnded(Player player){
		//this should be moved to outside this class but I have too many other assignments
		players.remove(player);
		if (players.size() < 1){
			JOptionPane.showMessageDialog(new JFrame(), player + " score: " + player.getStat().getScore());
		} else if (players.size() == 1){
			JOptionPane.showMessageDialog(new JFrame(), players.get(0) + " won !");
			players.get(0).pause();
			players.clear();
		}
	}
	
	/**
	 * gets the next piece from the queue of future pieces. also adds a new piece to the end of that queue
	 * 
	 * @return the next tetromino in the queue of future pieces
	 */
	public Tetromino getNextPiece(){
		Tetromino newPiece = new Tetromino(
											STD_TETROMINOS[random.nextInt(STD_TETROMINOS.length)], 
											ViewController.getRandomVisual()
										  );
		if (random.nextDouble() < manaProduction) newPiece.addManaOrb(); 
		
		futurePieces.add(newPiece);
		return futurePieces.remove(0);
	}
	
	/**
	 * pauses all of the player associated with this game
	 */
	public void pause(){
		for (Player player: players){
			player.pause();
		}
	}
	
	/**
	 * resumes all of the players associated with this game
	 */
	public void resume(){
		for (Player player: players){
			player.start();
		}
	}

	//getters
	public Player getPlayer(int playerNum) {
		return players.get(playerNum - 1);
	}
	
	public List<Player> getPlayers(){
		return players;
	}
	
	public int getPlayerCount() {
		return players.size();
	}
	
	public List<Tetromino> getUpcoming(){
		return futurePieces;
	}
	
	
	//listener methods
	public void addStateListener(GameEventListener listener){
		stateListeners.add(listener);
	}
	
	public void removeStateListener(GameEventListener listener){
		stateListeners.remove(listener);
	}
	
	public void notifyStateListeners(){
		GameEvent e = new GameEvent(GameEvent.STATEUPDATE);
		
		for (GameEventListener listener : stateListeners){
			listener.handleEvent(e);
		}
	}
}
