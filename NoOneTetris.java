import javax.swing.JFrame;


import ui.UIController;

/**
 * This is the main class for starting my tetris game as
 * a native application.
 * 
 * @author DinoRatcliffe
 *
 */
public class NoOneTetris{
	
	private static final String TITLE = "Dino Ratcliffe 1101399";
	private static final int MAX_PLAYERS = 4;
	
	public static void main(String[] args){
		JFrame frame = new JFrame(TITLE);
		
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIController controller = new UIController(frame, MAX_PLAYERS);
		
		//experimental stuff & easter egg
		boolean sound = false, christmas = false;
		for (String arg : args){
			if (arg.equals("this_could_crash")) {
				controller.setSoundActive(true);
			} else if (arg.equals("its_christmas")){
				controller.setIsChristmas(true);
			}
		}
	}

}
