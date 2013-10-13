package game.powerups;

import game.Board;
import game.Player;

import java.util.List;
/**
 * Push Left is a powerUp that pushes all of the placed block on the
 * activators board to the left thus reducing gaps in there board.
 * 
 * @author Dino Ratcliffe
 *
 */
public class PushLeft implements PowerUp {

	@Override
	public void run(Player activator, List<Player> allPlayers) {
		// TODO Auto-generated method stub
		int [][] strengthMap = activator.getBoard().getStrengthMap();
		int [][] visualMap = activator.getBoard().getVisualMap();
		
		for (int i = 0; i<strengthMap.length; i++){
			
			for (int j = strengthMap[0].length; j>0; j--){
				if (strengthMap[i][j-1] == 0){
					
					for (int k = j; k<strengthMap[0].length; k++){
						strengthMap[i][k-1] = strengthMap[i][k];
						strengthMap[i][k] = 0;
						
						visualMap[i][k-1] = visualMap[i][k];
						visualMap[i][k] = Board.VISUAL_EMPTY_AREA;
					}
					
				}
			}
			
			
		}
	}

}
