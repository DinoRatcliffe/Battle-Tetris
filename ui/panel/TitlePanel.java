package ui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import ui.UIController;

/**
 * the title screen for this tetris game. providing navigation to the options page and also buttons to strat the game
 * with a desired amount of players
 * 
 * @author Dino Ratcliffe
 *
 */
public class TitlePanel extends JPanel{
	
	UIController controller;
	List<JButton> playerButtons = new ArrayList<JButton>();
	
	/**
	 * Constructor
	 * 
	 * @param parentController 	the UIController that created this panel
	 * @param maxPlayers		the max number of players that is allowed to be created
	 * @param width				width that this jpanel should be
	 * @param height			height that this jpanel should be
	 */
	public TitlePanel(UIController parentController, int maxPlayers, int width, int height){
		this.controller = parentController;
		this.setLayout(new BorderLayout());
		
		//set up button for starting the game with a desired amount of players
		JPanel playerSelect = new JPanel();
		playerSelect.setPreferredSize(new Dimension(width, 70));

		ActionListener playerHandler = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				startGame(playerButtons.indexOf(e.getSource())+1);
			}
		};
		
		//add player number selection buttons
		for(int i = 1; i<=maxPlayers; i++){
			JButton button = new JButton(i + " Player");
			button.addActionListener(playerHandler);
			playerSelect.add(button);
			playerButtons.add(button);
		}
	
		//setup Options button
		JButton options = new JButton("Options");
		
		options.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.showPanel(UIController.OPTIONS);
			}
		});
		
		this.add(playerSelect, BorderLayout.NORTH);
		this.add(options, BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(width, height));
	}

	/**
	 * tells the parent controller that the user has indicated that they would like to start the game
	 * 
	 * @param numPlayers the number of players that the user selected
	 */
	private void startGame(int numPlayers) {
		controller.startGame(numPlayers);
	}
	
}
