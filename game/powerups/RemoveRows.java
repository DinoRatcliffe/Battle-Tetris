package game.powerups;

import game.Player;

import java.util.List;

/**
 * Remove rows simply removes a set number of rows from the bottom of the activators
 * board. no points or mana will be collected when they are removed.
 * 
 * @author Dino Ratcliffe
 *
 */
public class RemoveRows implements PowerUp{

	int rows;
	
	/**
	 * Constructor
	 * 
	 * @param rows int number of rows to be removed when activated
	 */
	public RemoveRows(int rows){
		this.rows = rows;
	}

	@Override
	public void run(Player activator, List<Player> allPlayers) {
		// TODO Auto-generated method stub
		for (int i = 0; i<rows; i++){
			activator.getBoard().removeRow(activator.getBoard().getStrengthMap().length-1);
		}
	}
	
	
	
}
