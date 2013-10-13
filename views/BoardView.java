package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JComponent;

import game.Board;
import game.Player;
import game.Tetromino;

/**
 * This is the view class that has the job of creating a visual representation of the tetris
 * board 
 * 
 * @author Dino Ratcliffe
 *
 */
public class BoardView extends JComponent{
	
	//size of each grid space
	private static int size = 20;
	
	int[][] visualMap, strengthMap;
	List<Tetromino> activeTetrominos;
	ViewController controller;
	
	/**
	 * Constructor
	 * 
	 * @param controller 		 ViewController that created this object
	 * @param board				 the board object to be painted
	 * @param activeTetrominos   the list of active tetrominos to be painted
	 */
	public BoardView(ViewController controller, Board board, List<Tetromino> activeTetrominos){
		this.controller = controller;
		this.visualMap = board.getVisualMap();
		this.activeTetrominos = activeTetrominos;
		this.strengthMap = board.getStrengthMap();
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		//makes mana orbs look much better
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		
		//go through and paint the the board
		for (int i = 0; i<visualMap.length; i++){
			for (int j = 0; j<visualMap[0].length; j++){
				if(visualMap[i][j] == Board.VISUAL_MANA_ORB){
					g2.setColor(controller.getBlankColor());
					g2.fillRect(j*size, i*size, size, size);
					
					g2.setColor(Color.MAGENTA);
					g2.fillOval(j*size, i*size, size, size);
				} else {
					Color color;
					
					if (visualMap[i][j] == Board.VISUAL_EMPTY_AREA){
						color = controller.getBlankColor();
					} else if (strengthMap[i][j] > 1){
						color = controller.getStrongColor();
					} else {
						color = controller.getColor(visualMap[i][j]);
					}
					
					g2.setColor(color);
					
					g2.fill3DRect(j*size, i*size, size, size, true);
				}
				
			}
		}
		
		//now paint the currently active pieces
		for (Tetromino activeTetromino : activeTetrominos){
			int[][] currentShape = activeTetromino.getVisual();
			int x = activeTetromino.getLeft();
			int y = activeTetromino.getTop();
			
			for (int i = 0; i<currentShape.length; i++){
				for (int j = 0; j<currentShape[0].length; j++){
					if(currentShape[i][j] == Board.VISUAL_MANA_ORB){
						g2.setColor(controller.getBlankColor());
						g2.fillRect((x+j)*size, (y+i)*size, size, size);
						
						g2.setColor(controller.getManaColor());
						g2.fillOval((x+j)*size, (y+i)*size, size, size);
					} else if(currentShape[i][j] != Board.VISUAL_EMPTY_AREA){
						g2.setColor(controller.getColor(currentShape[i][j]));
						g2.fill3DRect(
								(x+j)*size,
								(y+i)*size, 
								size, 
								size, 
								true);
					}
				}
			}
		}
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(visualMap[0].length*size, visualMap.length*size);
	}
	
}
