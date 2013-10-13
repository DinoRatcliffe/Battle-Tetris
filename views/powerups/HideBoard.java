package views.powerups;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import utilities.MatrixUtilities;
import views.PlayerView;
import views.ViewController;

import game.Board;
import game.Player;
import game.Tetromino;
import game.powerups.PowerUp;

/**
 * this power up makes all the pieces that are placed on the players board at time of activation invisible.
 * this power up is not applied to the player that activates it.
 * 
 * @author Dino Ratcliffe
 *
 */
public class HideBoard implements PowerUp{
	
	//amount of time its active
	public static final int ACTIVE_TIME = 10000;
	
	private List<PlayerView> views;
	
	/**
	 * Constructor
	 * 
	 * @param views a list of all of the player views associated with the players that could appear in this powerup
	 */
	public HideBoard(List<PlayerView> views){
		this.views = views;
	}

	@Override
	public void run(Player activator, List<Player> allPlayers) {
		Timer timer = new Timer();
		
		//make all non
		for (PlayerView view : views){
			if (view.getPlayer() != activator){
				int[][] visual = view.getPlayer().getBoard().getVisualMap();
				
				MatrixUtilities.fillMatrix(visual, Board.VISUAL_EMPTY_AREA);
				
				timer.schedule(new HideBoardTask(visual, view.getPlayer().getBoard().getStrengthMap()), ACTIVE_TIME);
				
			}
		}
	}

}
/**
 * 
 * TimerTask that undoes the effects of the HideBoard power up
 * 
 * @author Dino Ratcliffe
 *
 */
class HideBoardTask extends TimerTask {
	
	int[][] active, strengthMap;
	int color;
	
	/**
	 * Constructor, due to limitation all blocks that come back must be 
	 * given a new color. apart from mana orbs which remain intact.
	 * 
	 * @param active		the currently active visual array
	 * @param strengthMap	the currently actvie strength array
	 */
	public HideBoardTask(int[][] active, int[][] strengthMap){
		this.strengthMap = strengthMap;
		this.active = active;
	}

	@Override
	public void run() {
		for(int i = 0; i<active.length; i++){
			for(int j = 0; j<active[0].length; j++){
				
				if (strengthMap[i][j] != 0 && active[i][j] == Board.VISUAL_EMPTY_AREA){
					active[i][j] = strengthMap[i][j] == Tetromino.STRENGTH_MANA ? Board.VISUAL_MANA_ORB : ViewController.getRandomVisual();
				}
				
			}
		}
	}

}
