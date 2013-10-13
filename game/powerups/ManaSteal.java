package game.powerups;

import java.util.List;
import java.util.Random;

import game.Player;

/**
 * Mana steal is a power up that removes mana from one random player and gives it to
 * the player who activated the powerUp.
 * 
 * @author Dino Ratcliffe
 *
 */
public class ManaSteal implements PowerUp {

	@Override
	public void run(Player activator, List<Player> allPlayers) {
		Random random = new Random();
		
		Player unluckyPlayer = allPlayers.get(random.nextInt(allPlayers.size()));
		int mana = unluckyPlayer.getStat().getManaLevel();
		
		activator.getStat().addMana(mana);
		unluckyPlayer.getStat().removeMana(mana);
	}

}
