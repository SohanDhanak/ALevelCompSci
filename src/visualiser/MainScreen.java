package visualiser;

import javax.swing.JFrame;

public class MainScreen {
	
	public void setUp() {
		GUI gui = new GUI();      //Creates an instance of the frame
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes on X
		gui.setSize(1024,640);   //sets the size
		gui.setLocationRelativeTo(null);  //centres the frame
		gui.setResizable(false);  //stops frame from changing size
		gui.setVisible(true);   //shows the frame
		
	}
}
