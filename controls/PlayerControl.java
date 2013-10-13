package controls;

import game.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class that controls a specific player in a game of tetris
 * gets passed an int[] of key codes that specify what keys control the player
 * 
 * @author Dino Ratcliffe
 *
 */
public class PlayerControl implements KeyListener{
	
	Player player;
	int[] controls;
	
	/**
	 * Constructor
	 * 
	 * @param player	player that this controler is going to control
	 * @param keyCodes	the keycodes for this player 
	 */
	public PlayerControl(Player player, int[] keyCodes){
		this.player = player;
		controls = keyCodes;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//had to use if's because user can change controls at runtime
		if (e.getKeyCode() == controls[0]){
			player.rotatePiece();
		} else if (e.getKeyCode() == controls[1]){
			player.moveLeft();
		} else if (e.getKeyCode() == controls[2]){
			player.moveRight();
		} else if (e.getKeyCode() == controls[3]){
			if (player.getSpeedUp()){
				player.goToBottom();
			} else {
				player.changeDelay(Player.SPEEDUP_DELAY, false);
			}
		} else if (e.getKeyCode() == controls[4]){
			player.spendMana(1);
		} else if (e.getKeyCode() == controls[5]){
			player.spendMana(2);
		} else if (e.getKeyCode() == controls[6]){
			player.spendMana(3);
		} else if (e.getKeyCode() == controls[7]){
			player.spendMana(4);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
        if (e.getKeyCode() == controls[3]){
            player.resetSpeed();
        }
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}
	
	//getters
	public Player getPlayer(){
		return player;
	}

	public int[] getControls(){
		return controls;
	}
}
