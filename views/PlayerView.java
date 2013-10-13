package views;

import java.awt.BorderLayout;
import javax.swing.JComponent;

import game.Player;
import game.PlayerStateListener;
import game.RowClearEvent;
import game.RowClearedListener;

/**
 * Class that combines a mana bar and board view in order to provide a complete view for a single
 * player
 * 
 * @author Dino Ratcliffe
 *
 */
public class PlayerView extends JComponent{
	
	ManaBar bar;
	Player player;
	
	/**
	 * Constructor
	 * 
	 * @param player 	 the player this view represents
	 * @param controller the parent ViewController
	 * @param leftAlign  if true the board view will be left aligned with the mana bar on the right
	 */
	public PlayerView(Player player, ViewController controller, boolean leftAlign){
		this.setLayout(new BorderLayout());
		this.player = player;
		
		//init board
		BoardView boardView = new BoardView(controller, player.getBoard(), player.getCurrentPieces());
		this.add(boardView, leftAlign?BorderLayout.WEST:BorderLayout.EAST);
		
		//init manabar
		bar = new ManaBar(player.getBoard().getStrengthMap().length);
		this.add(bar, leftAlign?BorderLayout.EAST:BorderLayout.WEST);
		
		//add a mana updater
		player.addStateChangeListener(new PlayerStateListener(){

			@Override
			public void playerStateUpdated(Player player) {
				// TODO Auto-generated method stub
				bar.setMana(player.getStat().getManaLevel());
			}
			
		});
	}

	//getters
	public Player getPlayer() {
		return player;
	}

}
