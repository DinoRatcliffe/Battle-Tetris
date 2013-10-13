package controls.powerups;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import controls.PlayerControl;

import game.Player;
import game.powerups.PowerUp;

/**
 * This powerUp randomises the rotate, left, right, and speed up keys of all the players
 * except the player that activated the powerup
 * 
 * @author Dino Ratcliffe
 *
 */
public class RandomControls implements PowerUp{
	
	//time in ms before the controls are put back to normal
	public static final int ACTIVE_TIME = 10000;
	
	Random rand = new Random();
	List<PlayerControl> controls;
	
	public RandomControls(List<PlayerControl> controls){
		this.controls = controls;
	}

	@Override
	public void run(Player activator, List<Player> allPlayers) {
		Timer timer = new Timer();
		
		for (PlayerControl playerControl : controls){
			if (activator == playerControl.getPlayer()) continue;
			
			//for if a player has the speed up key currently pressed
			playerControl.getPlayer().resetSpeed();
			
			//randomise the controls
			int[] control = playerControl.getControls();
			int[] normal = control.clone();
			
			for (int i = 0; i<4; i++){
				int some = control[i];
				int randomIn = rand.nextInt(4);
				control[i] = control[randomIn];
				control[randomIn] = some;
			}
			
			//set timer to put controls back to normal
			timer.schedule(new RandomControlTask(normal, playerControl), ACTIVE_TIME);
		}
		
	}

}

/**
 * this is sub class of TimerTask that puts the players controls back to normal.
 * 
 * @author Dino Ratcliffe
 *
 */
class RandomControlTask extends TimerTask {
	
	private int[] normal, active;
	private Player player;
	
	/**
	 * Constructor
	 * 
	 * @param normal what the normal controls for this player where.
	 * @param active a refrence to the current active controls for the player
	 */
	public RandomControlTask(int[] normal, PlayerControl control){
		this.normal = normal;
		this.active = control.getControls();
		this.player = control.getPlayer();
	}

	@Override
	public void run() {
		
		for (int i = 0; i<active.length; i++){
			active[i] = normal[i];
		}
		
		//for if a player has the speed up key currently pressed
		player.resetSpeed();
	}

}
