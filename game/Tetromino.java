package game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import utilities.MatrixUtilities;
/**
 * Tetromino Class
 * 
 * This class holds the representation of a single Tetromino in a game of tetris. 
 * provides methods for rotating and moving the piece on the board.
 * 
 * @author Dino Ratcliffe
 *
 */
public class Tetromino{
	//indicates if this tetromino has a mana orb
	private boolean hasMana = false;
	
	//contant used to represent a mana orb in the tetromino
	public static final int STRENGTH_MANA = -1;
	
	//holds array that represents the shape of this tetromino using 1 to represent a block and 0 to represent blank space
	private int[][] shape;
	
	//the visual code given to this tetromino. usually given by an external class that handles views.
	private int visualCode;
	
	//the x and y coordinates of the tetromino on a board.
	private int x,y;
	
	//random used to select a random block to be mana if needed.
	private Random random = new Random();
	
	//Constructors
	/**
	 * 
	 * Constructor
	 * 
	 * @param shape	shape to be used to represent this tetromino
	 * @param x 			x coordinate on the board to use for this tetromino
	 * @param y				y coordinate on the board to use for this tetromino
	 * @param visualCode	int value to represent how this tetromino should be drawn
	 * @param addMana			boolean to indicate if this tetromino should contain a mana orb
	 */
	public Tetromino(int[][] shape, int x, int y, int visualCode, boolean addMana){
		this.shape = shape;
		this.visualCode = visualCode;
		this.x = x;
		this.y = y;
		
		if (addMana){
			randomMana();
		}
	}
	
	/**
	 * 
	 * Constructor. Calls the main constructor with the boolean mana set to false.
	 * 
	 * @param shape 		shape to be used to represent this tetromino
	 * @param x 			x coordinate on the board to use for this tetromino
	 * @param y				y coordinate on the board to use for this tetromino
	 * @param visualCode	int value to represent how this tetromino should be drawn
	 */
	public Tetromino(int[][] shape, int x, int y, int visualCode){
		this(shape, x, y, visualCode, false);
	}
	
	/**
	 * Copy Constructor, Vannila.
	 * 
	 * @param original Tetromino to be copied.
	 */
	public Tetromino(Tetromino original){
		this.shape = MatrixUtilities.deepCopy(original.getShape());
		this.x = original.x;
		this.y = original.y;
		this.visualCode = original.visualCode;
	}
	
	/**
	 * 
	 * Copy Contructor, with different visualCode
	 * 
	 * @param original Tetromino to be copied
	 * @param visualCode new int representation to be used as the visual code
	 */
	public Tetromino(Tetromino original, int visualCode){
		this(original);
		this.visualCode = visualCode;
	}
	
	//simple tetromino manipulation methods
	public void rotate(){
		this.shape = getShapeRotate();
	}
	
	public void moveDown(){
		y++;
	}

	public void moveRight() {
		x++;
	}

	public void moveLeft() {
		x--;
	}
	//move the piece up a number of rows
	public void moveUp(int i) {
		y -= i;
	}
	/**
	 * adds a mana orb to this tetromino in a random location. If this block already has a mana orb nothing is changed.
	 */
	public void addManaOrb(){
		if (!hasMana) randomMana();
	}
	
	/*
	 * Changes a random block in this Tetromino to a mana orb. 
	 * or more specifically an int value that represents a mana orb.
	 */
	private void randomMana(){
		//can be improved! an empty tetromino would get stuck in an infinate loop ... etc
		while (!hasMana){
			int i = random.nextInt(shape.length);
			int j = random.nextInt(shape[0].length);
			if (shape[i][j] != 0){
				shape[i][j] = STRENGTH_MANA;
				hasMana = true;
			}
		}
	}
	
	/**
	 * A getter for visual map that isn't really a getter because it generates the array in the method.
	 * all coordinates of the shape array that are not a mana orb or blank are given the visual code value.
	 * mana orbs are given the constant value in this class and black areas are given the value 0;
	 * 
	 * @return int[][]  that is a visual representation of this tetromino
	 */
	public int[][] getVisual(){
		int[][] visual = new int[shape.length][shape[0].length];
		for (int i = 0; i<shape.length; i++){
			for (int j = 0; j<shape[0].length; j++){
				if (shape[i][j] != 0){
					visual [i][j] = shape[i][j] != STRENGTH_MANA ? visualCode : -1; 
				}
			}
		}
		return visual;
	}
	
	//gets what the shape of this tetris would be if it was rotated
	private int[][] getShapeRotate(){
		return MatrixUtilities.rotate2dMatrix(shape);
	}
	
	//methods that return new tetromino objects that represent this tetromino if certain manipulations where made
	public Tetromino getRotatedTet(){
		return new Tetromino(getShapeRotate(), this.x, this.y, this.visualCode, false);
	}
	public Tetromino getLeftTet(){
		return new Tetromino(this.shape, this.x-1, this.y, this.visualCode, false);
	}
	public Tetromino getRightTet(){
		return new Tetromino(this.shape, this.x+1, this.y, this.visualCode, false);
	}
	
	public Tetromino getFallTet(){
		return new Tetromino(this.shape, this.x, this.y+1, this.visualCode, false);
	}
	
	//getters
	
	//methods that calculate the outer corner indexes for this tetromino
	public int getLeft(){
		return x-shape[0].length/2;
	}

	public int getTop(){
		return y-shape.length/2;
	}	

	public int getRight(){
		return getLeft() + getWidth();
	}
	
	public int getBottom(){
		return getTop() + getHeight();
	}
	
	/**
	 * 
	 * @return List of points with x and y indicating the position of each block.
	 */
	public List<Point> getPoints(){
		//this seems like a much nicer way of possibly storing the piece
		//in the first place.
		ArrayList<Point> points = new ArrayList<Point>();
		
		for (int i = 0; i<shape.length; i++){
			for(int j = 0; j<shape[0].length; j++){
				if (shape[i][j] > 0 || shape[i][j] == STRENGTH_MANA){
					points.add(
							new Point(getLeft()+j, getTop()+i)
							);
				}
			}
		}
		
		return points;
	}
	
	public int getStrengthAt(int y, int x){
		return shape[y-getTop()][x-getLeft()];
	}
	
	public int getVisualAt(int y, int x){
		return this.getVisual()[y-getTop()][x-getLeft()];
	}
	
	//traditional getters
	public int[][] getShape(){
		return shape;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean hasMana(){
		return hasMana;
	}
	
	public int getWidth(){
		return shape[0].length;
	}
	
	public int getHeight(){
		return shape.length;
	}
	
	//setters
	public void setVisualCode(int visualCode){
		this.visualCode = visualCode;
	}

	public void setY(int y) {
		// TODO Auto-generated method stub
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
}
