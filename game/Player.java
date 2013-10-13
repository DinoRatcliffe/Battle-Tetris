package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Timer;

/**
 * Player class that keeps track of the players current state as well as providing
 * methods for interacting with the currently active pieces on the players board
 * 
 * Future Development - change how the game is ended as it is currently the worst ...
 * 
 * @author Dino Ratcliffe
 *
 */
public class Player {
	
	//Infinite scroll switch for arcade demo mode
	public static final boolean INFINITE_SCROLL = false;
	
	public static final int SPEEDUP_DELAY = 50;	
	
	//obvious variables
	private String name;
	private boolean speedUpOnScore = false;
	
	//used to stop actions from being run on finished games.
	private boolean endOfGame = false;
	
	//values for controling the possibility of multiple pieces for players
	private int numActivePieces = 1;
	private int pieceOffSet = 5;
	
	//the current permenant delay between game ticks, used to control rate of fall.
	private int tickDelay;
	
	//list of current pieces. allows the player to have multiple pieces falling at once.
	private List<Tetromino> currentPieces = new ArrayList<Tetromino>();
	
	//this players board
	private Board board;
	
	//parent controller
	private GameController controller;
	
	//stat object to keep track of players score and mana level
	private PlayerStat stats;
	
	//timer object that controls the game tick for this player
    private Timer timer;
    
    //listeners registerd to recieve events when state changes
    private List<PlayerStateListener> stateListeners = new ArrayList<PlayerStateListener>();
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param w				int width of the players board
	 * @param h				int heigth of the players board
	 * @param controller	GameController the parent controller for this player
	 * @param name			String the name of this player
	 */
	public Player(int w, int h, GameController controller, String name){
		//init variables
		this.name = name;
		this.controller = controller;
		this.tickDelay = GameController.DEFAULT_GAME_DELAY;
		board = new Board(w, h);
		this.stats = new PlayerStat();
		currentPieces.add(controller.getNextPiece());

        timer = new Timer(tickDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                movePieceDown();
            }
        });
        
        //add listener to board to update stats when a row is cleared
  		board.addListener(new RowClearedListener(){

  			@Override
  			public void rowCleared(RowClearEvent e) {
  				stats.addMana(e.getManaCount());
  				stats.rowCleared(e.getBlockCount());
  				if(stats.getScore()%30 == 0){
  					changeDelay((int) (tickDelay - (tickDelay*0.1)), true);
  				}
  			}
  			
  		});
	}
	
	//control methods
	/**
	 * Rotates the players lowest currently active tetromino. if this rotate would cause a collision
	 * then nothing is done.
	 */
	public void rotatePiece(){
		if(endOfGame) return;
		if(!board.collision(currentPieces.get(0).getRotatedTet())){
			currentPieces.get(0).rotate();
			fireStateChangeListeners();
		}
	}
	
	/**
	 * drops the players lowest currently active tetrominio as far down as it can go instantly.
	 */
	public void goToBottom(){
		if(endOfGame) return;
		Tetromino pieceToMove = currentPieces.get(0);
		while(pieceToMove == currentPieces.get(0) && !endOfGame){
			movePieceDown();
		}
		this.fireStateChangeListeners();
	}
	
	/**
	 * moves the players lowest currently active tetrominio right one block. if this causes a collision
	 * then nothing is done
	 */
	public void moveRight(){
		if(endOfGame) return;
		if (!board.collision(currentPieces.get(0).getRightTet())){
			currentPieces.get(0).moveRight();
		}
		
		this.fireStateChangeListeners();
	}
	
	/**
	 * moves the players lowest currently active tetrominio left one block. if htis causes a collision
	 * then nothing is done
	 */
	public void moveLeft(){
		if(endOfGame) return;
		if(!board.collision(currentPieces.get(0).getLeftTet())){
			currentPieces.get(0).moveLeft();
		}
		
		this.fireStateChangeListeners();
	}
	
	/**
	 * moves the players currently active pieces down one block.
	 * if it cannot be moved down without a collision then it has reached its destination on
	 * the board and is placed there. however if the piece is off the top of the board then 
	 * the player has lost so the game is ended.
	 * 
	 * also refills the players active pieces list if pieces where placed on the board
	 */
	public void movePieceDown(){
		ListIterator<Tetromino> iterator = currentPieces.listIterator();
		Tetromino currentPiece;
		
		//test all active pieces for collision
		while(iterator.hasNext()){
			currentPiece = iterator.next();
			if(!board.collision(currentPiece.getFallTet())){
				currentPiece.moveDown();
			} else {
				if (board.offTopBoard(currentPiece)){
					endOfGame = true;
					stop();
					controller.gameEnded(this);
				} else {
					if (!INFINITE_SCROLL) board.placePiece(currentPiece);
					iterator.remove();
				}
			}
		}
		
		//refill active piece list
		while (currentPieces.size() < numActivePieces){
			Tetromino oldPiece = currentPieces.size() != 0 ? currentPieces.get(currentPieces.size()-1) : null;
			Tetromino newPiece = controller.getNextPiece();
			
			
			if (oldPiece != null && oldPiece.getTop() < 0) {
				newPiece.setY(oldPiece.getY());
				newPiece.moveUp(pieceOffSet);
			}
			
			currentPieces.add(newPiece);
		}
		
		fireStateChangeListeners();
	}
	
	/**
	 * 1 level = 1 mana
	 * 
	 * spends an amount of mana on a powerUp to be used in a game. activating the powerUp immediately.
	 * powerUp to be used is decided by this Players parent GameController
	 * 
	 * @param level int representing the level of powerUp the user has requested.
	 */
	public void spendMana(int level){
		if (level <= stats.getManaLevel()){
			stats.removeMana(level);
			controller.activatePowerUp(this, level);
		}
	}
	
	//tick methods
	/**
	 * Changes the delay between ticks of this player. if permenant is set to true then this
	 * is the new default speed for this player e.g calling resetSpeed() will set it to this 
	 * new value.
	 * 
	 * @param newDelay  int new delay to be applied to this player
	 * @param permenant boolean to indicate whether this delay should be permenant
	 */
	public void changeDelay(int newDelay, boolean permenant){
		if (!endOfGame){
			timer.setDelay(newDelay);
		    if (permenant) tickDelay = newDelay;
		}
	}
	    
	public void resetSpeed(){
	    if (!endOfGame) timer.setDelay(tickDelay);
	}
	
	private void stop() {
        timer.stop();
        stats.removeMana(stats.getManaLevel());
    }
	
	public void pause(){
		timer.stop();
	}
    
    public void start(){
    	if (!endOfGame) timer.start();
    }
	
	
	//simple listener methods
	public void addStateChangeListener(PlayerStateListener listener){
		stateListeners.add(listener);
	}
	
	public void removeStateChangeListener(PlayerStateListener listener){
		stateListeners.remove(listener);
	}
	
	private void fireStateChangeListeners(){
		for (PlayerStateListener i : stateListeners){
			i.playerStateUpdated(this);
		}
	}
	
	//overridden methods
	public String toString(){
		return name;
	}
	
	//getters
	public List<Tetromino> getCurrentPieces() {
		return currentPieces;
	}
	
	public boolean isFinished(){
		return endOfGame;
	}
	
	public Board getBoard(){
		return board;
	}
	
	public PlayerStat getStat(){
		return this.stats;
	}
    
    public int getTickDelay(){
    	return tickDelay;
    }
    
    public boolean getSpeedUp(){
    	return speedUpOnScore;
    }
    
    //setters
    public void setNumActivePieces(int numPieces){
    	this.numActivePieces = numPieces;
    }
    
    public void setPieceOffset(int pieceOffSet){
    	this.pieceOffSet = pieceOffSet;
    }
    
    public void setSpeedUp(boolean speedUp){
    	this.speedUpOnScore = speedUp;
    }
}
