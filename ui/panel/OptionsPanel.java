package ui.panel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.UIController;
import utilities.GlobalKeyListener;

/**
 * panel that displays a set of buttons allowing the user to set up custom controls for the game.
 * 
 * NOTE - this is a very poor implementation of this functionality.
 * 
 * @author Dino Ratcliffe
 *
 */
public class OptionsPanel extends JPanel {
	
	//prefered height for each label button pair
	private static final int HEIGHT_PER_ROW = 20;
	
	int columns;
	int previousPanel;
	UIController controller;
	int[][] newKeyCodes;
	int[][] activeKeyCodes;
	JButton[][] keyButtons;
	KeyCodeGrabber keyGrabber;
	
	/**
	 * Constructor
	 * 
	 * @param parentController	UIController that created this object
	 * @param keyCodes			keyCodes to use to populate the labels on the buttons
	 * @param previousPanel		the int index of the previous panel
	 * @param width				the prefered width to set this panel to 
	 */
	public OptionsPanel(UIController parentController, int[][] keyCodes, int previousPanel, int width){
		//init variables
		this.setLayout(new GridLayout(0,2));
		this.previousPanel = previousPanel;
		this.controller = parentController;
		this.newKeyCodes = keyCodes.clone();
		this.activeKeyCodes = keyCodes;
		this.keyButtons = new JButton[keyCodes.length][8];
		this.setFocusable(true);
		
		//set up key event listening
		keyGrabber = new KeyCodeGrabber(this);
		this.addKeyListener(keyGrabber);
		new GlobalKeyListener(false).addListener(keyGrabber);
		
		
		//add items for each player 
		for (int i = 0; i<keyCodes.length; i++){
			addPlayerItems(i+1);
		}
		
		//back button setup
		JButton back = new JButton("Back");
		back.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				close();
			}			
		});
		this.add(back);
		
		this.setPreferredSize(new Dimension(width, keyCodes[0].length*keyCodes.length*HEIGHT_PER_ROW));
	}
	
	/**
	 * horrible implementation must be changed
	 * 
	 * @param playerNum the number associated with this player
	 */
	private void addPlayerItems(int playerNum){
		this.add(new JLabel("Player " + playerNum + " rotate:"));
		JButton rotate = new JButton("default");
		rotate.addActionListener(new OptionsButtonHandler(playerNum, 0, keyGrabber));
		keyButtons[playerNum-1][0] = rotate;
		this.add(rotate);
		
		this.add(new JLabel("player " + playerNum + " left:"));
		JButton left = new JButton("default");
		left.addActionListener(new OptionsButtonHandler(playerNum, 1, keyGrabber));
		keyButtons[playerNum-1][1] = left;
		this.add(left);
		
		this.add(new JLabel("player " + playerNum + " right:"));
		JButton right = new JButton("default");
		right.addActionListener(new OptionsButtonHandler(playerNum, 2, keyGrabber));
		keyButtons[playerNum-1][2] = right;
		this.add(right);
		
		this.add(new JLabel("Player " + playerNum + " drop:"));
		JButton drop = new JButton("default");
		drop.addActionListener(new OptionsButtonHandler(playerNum, 3, keyGrabber));
		keyButtons[playerNum-1][3] = drop;
		this.add(drop);
		
		this.add(new JLabel("player " + playerNum + " mana 1:"));
		JButton mana1 = new JButton("default");
		mana1.addActionListener(new OptionsButtonHandler(playerNum, 4, keyGrabber));
		keyButtons[playerNum-1][4] = mana1;
		this.add(mana1);
		
		this.add(new JLabel("player " + playerNum + " mana 2:"));
		JButton mana2 = new JButton("default");
		mana2.addActionListener(new OptionsButtonHandler(playerNum, 5, keyGrabber));
		keyButtons[playerNum-1][5] = mana2;
		this.add(mana2);
		
		this.add(new JLabel("player " + playerNum + " mana 3:"));
		JButton mana3 = new JButton("default");
		mana3.addActionListener(new OptionsButtonHandler(playerNum, 6, keyGrabber));
		keyButtons[playerNum-1][6] = mana3;
		this.add(mana3);
		
		this.add(new JLabel("player " + playerNum + " mana 4:"));
		JButton mana4 = new JButton("default");
		mana4.addActionListener(new OptionsButtonHandler(playerNum, 7, keyGrabber));
		keyButtons[playerNum-1][7] = mana4;
		this.add(mana4);
	
	}

	/**
	 * calls updateKeyCodes and then tells the controller to go to
	 * previous page
	 */
	public void close(){
		for(int i = 0; i<activeKeyCodes.length; i++){
			updateKeyCodes(i);
		}
		controller.showPanel(previousPanel);
	}
	
	/**
	 * updates the keycodes for a specific player
	 * 
	 * @param player the int value asociated with this player
	 */
	public void updateKeyCodes(int player){
		for (int i = 0; i < activeKeyCodes[player].length; i++){
			activeKeyCodes[player][i] = newKeyCodes[player][i];
		}
	}
	
	/**
	 * sets a new keycode for an action for a specific player
	 * 
	 * @param playerNum player that controls applied to
	 * @param button	specific control that needs to be updated
	 * @param keyCode	the new keycode to use for this control
	 * @param key		the character that is associated with this keycode
	 */
	public void setKeyCode(int playerNum, int button, int keyCode, char key) {
		newKeyCodes[playerNum-1][button] = keyCode;
		keyButtons[playerNum-1][button].setText("" + key);
	}

}
