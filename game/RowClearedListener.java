package game;
/**
 * RowClearedListener is an interface for a listener that is notified when a
 * row is cleared on a tetris board.
 * 
 * @author Dino Ratcliffe
 *
 */
public interface RowClearedListener {
	
	/**
	 * a method that performs an action when a row is cleared
	 * 
	 * @param e RowClearEvent that is passed in when the listener is fired
	 */
	public void rowCleared(RowClearEvent e);
}
