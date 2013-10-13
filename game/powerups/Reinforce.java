package game.powerups;

import game.Player;

import java.util.List;

/**
 * Reinforce is a powerUp that makes all of the blocks currently placed on every players
 * board, apart from the activator, have a strength of two. Meaning to clear a block it 
 * has to be in a completed row twice.
 * 
 * @author Dino Ratcliffe
 *
 */
public class Reinforce implements PowerUp{

	@Override
	public void run(Player activator, List<Player> allPlayers) {
		for (Player player : allPlayers){
			int[][] strength = player.getBoard().getStrengthMap();
			
			for(int i = 0; i<strength.length; i++){
				for(int j = 0; j<strength[0].length; j++){
					if (strength[i][j] == 1) strength[i][j]++;
				}
			}
			
		}
		
	}

}
