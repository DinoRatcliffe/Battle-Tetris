package sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
/**
 * Sound class that uses restricted java classes so best not to use this
 * in any serious projects. also only works with uncompressed wav files so
 * not a very portable soution
 * 
 * @author Dino Ratcliffe
 *
 */
public class Sound {
	
	AudioStream as;
	
	public Sound(String sound){
		InputStream is;
		try {
			is = new FileInputStream(sound);
			as = new AudioStream(is);         
			AudioPlayer.player.start(as);            
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start(){
		AudioPlayer.player.start(as);
	}
	public void stop(){
		AudioPlayer.player.stop(as);
	}

}
