package game.powerups;

import game.Player;

import java.util.List;

/**
 * interface for powerUps to be used on Players of a tetris game
 * 
 * @author Dino Ratcliffe
 *
 */
public interface PowerUp {
	/**
	 * a method that performs some code that changes how the game is played or viewed
	 * 
	 * @param activator the player that activated this powerUp
	 * @param allPlayers a list of all the other players. this does not include the activator.
	 */
	public void run(Player activator, List<Player> allPlayers);
}
