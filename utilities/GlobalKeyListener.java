package utilities;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that allows you to add keylisteners that get fired no matter what component has focus
 * 
 * @author Dino Ratcliffe
 *
 */
public class GlobalKeyListener {
	
	private List<KeyListener> keyListeners = new ArrayList<KeyListener>();
	private boolean consume;
	
	/**
	 * Constructor
	 * 
	 * @param consumeEvents boolean to indicate if it should consume the key events
	 * 		                        or allow them to cascade to other components
	 */
	public GlobalKeyListener(boolean consumeEvents){
		this.consume = consumeEvents;
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  notifyListeners(e);
		    	  return consume;
		      }
		});
	}
	
	//lisener methods
	private void notifyListeners(KeyEvent e){
		
		switch (e.getID()){
			case KeyEvent.KEY_PRESSED:
				for(KeyListener listener : keyListeners){
					listener.keyPressed(e);
				}
				break;
			case KeyEvent.KEY_RELEASED:
				for(KeyListener listener : keyListeners){
					listener.keyReleased(e);
				}
				break;
			case KeyEvent.KEY_TYPED:
				for(KeyListener listener : keyListeners){
					listener.keyTyped(e);
				}
				break;
		}
		
	}
	
	public void addListener(KeyListener listener){
		keyListeners.add(listener);
	}
	
	public void removeKeyListener(KeyListener listener){
		keyListeners.remove(listener);
	}

}
