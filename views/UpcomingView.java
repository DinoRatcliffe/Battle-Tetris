package views;

import game.Board;
import game.Tetromino;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import javax.swing.JComponent;

/**
 * Provides a visual representation of the upcoming pieces in the game controller
 * 
 * @author Dino Ratcliffe
 *
 */
public class UpcomingView extends JComponent{

	//size of each block
	private static int size = 20;
	
	List<Tetromino> upcoming;
	ViewController controller;
	
	/**
	 * Constructor
	 * 
	 * @param viewController	the parent controller of this object
	 * @param upcoming			a list of upcoming tetrominos
	 */
	public UpcomingView(ViewController viewController, List<Tetromino> upcoming) {
		this.upcoming = upcoming;
		this.controller = viewController;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		
		int heightMover = 0;
		
		for (int tetIndex = 0; tetIndex<upcoming.size(); tetIndex++){
			
			g2.setColor(controller.getBlankColor());
			g2.fillRect(0, heightMover*size, size*6, size);
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
			
			heightMover++;
		
			
			int[][] visualMap = upcoming.get(tetIndex).getVisual();
			
			for (int i = 0; i<visualMap.length; i++){
				
				//draw black block to the left
				g2.setColor(controller.getBlankColor());
				g2.fillRect(0, (i+heightMover)*size, size, size);
				
				//draw the tetromino
				for(int j = 0; j<visualMap[0].length; j++){	
					if(visualMap[i][j] == Board.VISUAL_MANA_ORB){
						g2.setColor(controller.getBlankColor());
						g2.fillRect((j+1)*size, (i+heightMover)*size, size, size);
						
						g2.setColor(Color.MAGENTA);
						g2.fillOval((j+1)*size, (i+heightMover)*size, size, size);
					} else {
						Color color;
						
						if (visualMap[i][j] == Board.VISUAL_EMPTY_AREA){
							color = controller.getBlankColor();
						} else {
							color = controller.getColor(visualMap[i][j]);
						}
						
						g2.setColor(color);
						
						g2.fill3DRect((j+1)*size, (i+heightMover)*size, size, size, true);
					}
					
				}
				//fill in remaining gap with black
				for (int l = visualMap[0].length+1; l<6; l++){
					g2.setColor(controller.getBlankColor());
					g2.fillRect(l*size, (i+heightMover)*size, size, size);
				}
				
			}
			heightMover += visualMap.length;
		}
		
		//add bottom black bar
		g2.setColor(controller.getBlankColor());
		g2.fillRect(0, heightMover*size, size*6, size);
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(6*size, upcoming.size()*size);
	}

}
