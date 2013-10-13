package ui.panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * this ActionLister sets up
 * 
 * @author Dino Ratcliffe
 *
 */
public class OptionsButtonHandler implements ActionListener {

	int playerNum, button;
	KeyCodeGrabber keyGrabber;
	
	/**
	 * constructor
	 * 
	 * @param playerNum  the player number associated with the button pressed
	 * @param button	 int representing the button that was pressed
	 * @param keyGrabber a keygrabber object for this action listener
	 */
	public OptionsButtonHandler(int playerNum, int button, KeyCodeGrabber keyGrabber){
		this.playerNum = playerNum;
		this.button = button;
		this.keyGrabber = keyGrabber;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		keyGrabber.setActive(playerNum, button);
	}

}
