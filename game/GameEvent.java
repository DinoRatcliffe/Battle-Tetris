package game;

/**
 * Event that is triggered by the game controller to inform listeners of
 * changes to the state of the game.
 * 
 * @author DinoRatcliffe
 *
 */
public class GameEvent {
	public static final int ENDGAME = 1;
	public static final int STATEUPDATE = 2;

	private int type;
	
	public GameEvent(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
	}
}
