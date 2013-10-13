package game;
/**
 * Very simple class that keeps track of the players current stats.
 * 
 * Future Development - possibly add some more interesting stuff such as average points scored
 * per row etc.. 
 * 
 * @author Dino Ratcliffe
 *
 */
public class PlayerStat {
	//obvious class variables
	public static final int SCORE_PER_BLOCK = 1;
	private int score;
	private int manaLevel;
	
	//simple addition and subtraction of mana
	public void addMana(int mana){
		manaLevel += mana;
	}
	
	public void removeMana(int mana){
		manaLevel -= mana;
	}
	
	/**
	 * adds to the current score the points accumelated from clearing
	 * this number of blocks.
	 * 
	 * @param numBlocks int nubmer of blocks cleared
	 */
	public void rowCleared(int numBlocks){
		score += SCORE_PER_BLOCK*numBlocks;
	}
	
	//getters
	public int getScore(){
		return score;
	}
	
	public int getManaLevel(){
		return manaLevel;
	}
	
}
