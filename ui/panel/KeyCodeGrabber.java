package ui.panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * a class that passes a keycode back to an options panel
 * good for allowing a user to set there own controls in a game
 * 
 * @author Dino Ratcliffe
 *
 */
public class KeyCodeGrabber implements KeyListener {

	OptionsPanel opt;
	int keyCode;
	boolean active = false;
	int playerNum, button;
	
	/**
	 * Constructor
	 * 
	 * @param opt the Options panel to pass the keycode to
	 */
	public KeyCodeGrabber(OptionsPanel opt){
		this.opt = opt;
	}
	
	/**
	 * sets this listener as active on a specific button. meaning it
	 * will call setKeyCode in the optionsPanel for the next key press
	 * 
	 * @param playerNum the playerNum that is passed back in the setKeyCode() method
	 * @param button    the button number that is passed back in the setKeyCode() mehtod
	 */
	public void setActive(int playerNum, int button){
		this.playerNum = playerNum;
		this.button = button;
		active = true;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(active){
			opt.setKeyCode(playerNum, button, e.getKeyCode(), e.getKeyChar());
			active = false;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
