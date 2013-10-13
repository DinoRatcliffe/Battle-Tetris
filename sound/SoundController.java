package sound;


import game.GameController;
import game.GameEvent;
import game.GameEventListener;

/**
 * sound controller that uses non official java classes so best not to use this
 * in any serious projects.
 * 
 * @author Dino Ratcliffe
 *
 */
public class SoundController {
	
	private static String bg = "sounds/kanye.wav";
	
	private Sound bgMusic;
	
	public SoundController(GameController game){

		bgMusic = new Sound(bg);
		
		game.addStateListener(new GameEventListener(){

			@Override
			public boolean handleEvent(GameEvent e) {
				bgMusic.start();
				return false;
			}
			
		});
		
	}
	
	//setters
	public void setBgFile(String filename){
		bgMusic.stop();
		bgMusic = new Sound(filename);
	}

}
