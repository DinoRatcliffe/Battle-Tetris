package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utilities.MatrixUtilities;

/**
 *  Board class that stores an internal representation of a single tetris board.
 *  has methods for testing a tetrominos relationship to the board e.g is off the
 *  top of the board.
 * 
 * @author Dino Ratcliffe
 *
 */

public class Board {
	
	//int values that represent certain visual elements
	public static final int VISUAL_MANA_ORB = -1;
	public static final int VISUAL_EMPTY_AREA = 0;
	
	//Representations of the blocks on the board
	private int[][] strengthMap, visualMap;
	
	//listeners that have registered to be informed when a row is cleared
	private List<RowClearedListener> listeners = new ArrayList<RowClearedListener>();
	
	/**
	 * constructor
	 * 
	 * @param w int representing the width of the board to be built
	 * @param h int representing the height of the board to be built
	 */
	public Board(int w, int h){
		strengthMap = new int[h][w];
		visualMap = new int [h][w];
	}
	
	/**
	 * 
	 * tests to see if the tetromino passed in overlaps(collides) with another
	 * object on the board
	 * 
	 * @param tetromino the tetromino to test if it collides on the board
	 * @return a boolean to indicate whether the tetromino causes a collision
	 */
	public boolean collision(Tetromino tetromino){
		boolean isCollision = false;
		
		//going right
		if ((tetromino.getLeft() + tetromino.getWidth() > strengthMap[0].length)){
			return true;
		}
		
		//going left
		if (tetromino.getLeft() < 0){
			return true;
		}
		
		//reached bottom
		if (tetromino.getBottom() > strengthMap.length){
			return true;
		}
		
		//overlaps another block
		for (Point p : tetromino.getPoints()){
			if (p.y < 0) break;
			if(strengthMap[p.y][p.x] != 0) isCollision = true;
		}
		
		return isCollision;
	}

	/**
	 * places a tetromino on the board updating the internal representation
	 * of both the visual map and strength map
	 * 
	 * @param tetromino the tetromino to be placed on the board
	 */
	public void placePiece(Tetromino tetromino) {		
		for (Point p : tetromino.getPoints()){
			this.visualMap[p.y][p.x] = tetromino.getVisualAt(p.y, p.x);
			this.strengthMap[p.y][p.x] = tetromino.getStrengthAt(p.y, p.x);
		}
		
		this.clearRow();
	}
	
	/**
	 * searches through the board to see what rows need to be removed and then calls the removeRow 
	 * method in order to remove the row. then notifies RowEventLiseners with a RowEvent that contains
	 * the number of mana orbs and blocks removed from the board
	 * 
	 */
	private void clearRow(){
		for (int i = 0; i<strengthMap.length; i++){
			boolean complete = true;
			
			for (int j = 0; j<strengthMap[0].length; j++){
				if (strengthMap[i][j] == 0){
					complete = false;
				}
			}
			
			if (complete){
				boolean clear = true;
				int blockCount = 0;
				int mana = 0;
				
				for (int j = 0; j<strengthMap[0].length; j++){
					int newValue = --strengthMap[i][j];
					if (newValue > 0){
						clear = false;
					} else {
						if (strengthMap[i][j] == Tetromino.STRENGTH_MANA-1) mana++;
						blockCount++;
						visualMap[i][j] = this.VISUAL_EMPTY_AREA;
					}
				}
				
				if (clear) removeRow(i);
				if (mana>0 || blockCount>0) notifyListeners(mana, blockCount);
			}
			
		}
	}
	
	/**
	 * 
	 * removes a specified row from the board
	 * 
	 * @param row the index of the row to remove
	 */
	public void removeRow(int row){
		for (int i = row-1; i>=0; i--){
			for (int j = 0; j<strengthMap[0].length; j++){
				strengthMap[i+1][j] = strengthMap[i][j];
				visualMap[i+1][j] = visualMap[i][j];
			}
		}
		clearRow();
	}
	
	/**
	 * test to see if the tetromino is off the top of the board.
	 * 
	 * @param tetromino the tetromino to be tested
	 * @return boolean that is true if any part of the piece is off the board
	 */
	public boolean offTopBoard(Tetromino tetromino){		
		return tetromino.getTop() < 0;
	}
	
	//listener methods
	public void addListener(RowClearedListener listener){
		listeners.add(listener);
	}
	public void removeListener(RowClearedListener listener){
		listeners.remove(listener);
	}
	public void notifyListeners(int mana, int numBlocks){
		for(RowClearedListener listener : listeners){
			listener.rowCleared(new RowClearEvent(mana, numBlocks));
		}
	}
	
	//getters
	public int[][] getStrengthMap() {
		return strengthMap;
	}
	
	public int[][] getVisualMap(){
		return visualMap;
	}
}
