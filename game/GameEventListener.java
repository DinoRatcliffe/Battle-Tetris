package game;

/**
 * simple interface that allows external classes to register listeners with the 
 * game controller
 * 
 * @author DinoRatcliffe
 *
 */
public interface GameEventListener {
	/**
	 * a simple method for handling GameEvent's
	 * 
	 * @param e game event that stores information on the game state
	 * @return whether this listener consumes this event
	 */
	public boolean handleEvent(GameEvent e);
}
