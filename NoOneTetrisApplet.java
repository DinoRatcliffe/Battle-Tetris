import javax.swing.JApplet;
import javax.swing.JFrame;

import ui.UIController;

/**
 * 
 * This is the main class for starting my tetris game as an applet
 * 
 * @author Dino Ratcliffe
 */
public class NoOneTetrisApplet extends JApplet{
	
	private static final String TITLE = "Dino Ratcliffe 1101399";
	private static final int MAX_PLAYERS = 4;


	public void init(){
		JFrame frame = new JFrame(TITLE);
		frame.pack();
		frame.setVisible(true);
		new UIController(frame, MAX_PLAYERS);
	}
	
}
