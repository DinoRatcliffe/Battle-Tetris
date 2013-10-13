package game;

/**
 * 
 * RowClearEvent is used when a row is cleared on a tetis board.
 * contains ints representing the number of mana and standard blocks
 * cleared from the row.
 * 
 * @author Dino Ratcliffe
 *
 */
public class RowClearEvent {
	
	//number of mana and standard blocks cleared from row.
	private int mana, blocks;
	
	/**
	 * Constructor
	 * 
	 * @param mana 		int number of mana orbs on cleared row
	 * @param blocks	int number of standard blocks cleared on row
	 */
	public RowClearEvent(int mana, int blocks){
		this.mana = mana;
		this.blocks = blocks;
	}
	
	//simple getters
	public int getBlockCount(){
		return blocks;
	}
	
	public int getManaCount(){
		return mana;
	}

}
