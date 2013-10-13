package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

/**
 * This class creates a visual representation of the amount of mana that a player has
 * 
 * @author Dino Ratcliffe
 *
 */
public class ManaBar extends JComponent{
	
	public static int size = 20;
	
	// height of bar and amount of mana
	private int h, mana;
	
	/**
	 * Constructor
	 * 
	 * @param h height of mana bar
	 */
	public ManaBar(int h){
		this.h = h;
	}
	
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.DARK_GRAY);
		g2.fillRect(0, 0, size, h*size);
		
		//display appropriate amount of mana
		for (int i = 0; i<mana; i++){
			g2.setColor(Color.DARK_GRAY);
			g2.fillRect(0, (h-i-1)*size, size, size);
			
			g2.setColor(Color.MAGENTA);
			g2.fillOval(0, (h-i-1)*size, size, size);
		}
	}
	
	//setters
	public void setMana(int mana){
		this.mana = mana;
	}
	
	public Dimension getPreferredSize(){
		return new Dimension(size, h*size);
	}
}
