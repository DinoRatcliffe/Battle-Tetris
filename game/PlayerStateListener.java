package game;
/**
 * very simple listener used to recieve updates based on when a players state
 * changes
 * 
 * @author Dino Ratcliffe
 *
 */
public interface PlayerStateListener {
	public void playerStateUpdated(Player player);
}
