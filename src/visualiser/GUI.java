package visualiser;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import generators.*;

public class GUI extends JFrame{
	private static final long serialVersionUID = 1L;
	private JLabel label;
	private JButton button;
	private JComboBox<String> dropdown;
	public CubeLayout cLayout;
	private WholeCubeGen cube;

	public GUI() {
		super("Scrambler");    //Names the frame
		setLayout(new BorderLayout());  //sets the layout of the frame
		
		cLayout = new CubeLayout();   //generates a new cube net
		cube = new WholeCubeGen();  //sets the default cube
		label = new JLabel(cube.getScramble());  //sets the default scramble
		cLayout.setConfig(cube.getCubeArray());  //sets the default cube net
		
		button = new JButton();  //creates a button
		button.setText("Generate scramble");  //sets the text on the button
		
		String[] dropdowns = {"General","Cross Solved","F2L Solved","OLL Solved"};
		dropdown = new JComboBox<String>(dropdowns);  //creates a new dropdown box
		
		JPanel container = new JPanel();
				
		container.add(label);   //groups the scramble, button and dropdown box
		container.add(button);
		container.add(dropdown);
		
		add(container, BorderLayout.NORTH); //adds  the scramble, button and dropdown to the frame
		add(cLayout, BorderLayout.CENTER);  //adds the cube net to the frame
		
		button.addActionListener(new ActionListener() {      //changes the frame when the button is pressed
			public void actionPerformed(ActionEvent arg0) {
				changePosition();
			}
		});
		
		dropdown.addActionListener(new ActionListener() {  //changes the frame when the dropdown box is changed
			public void actionPerformed(ActionEvent arg0) {
				changePosition();
			}
		});
	}
	
	private void changePosition() {
		switch((String) dropdown.getSelectedItem()) {      //This checks the state of the dropdown box
		case "General":
			cube = new WholeCubeGen();      //Create a cube using general scrambler
			break;
		case "Cross Solved":
			cube = new CrossGen();    //Creates a cube using the cross generator
			break;
		case "F2L Solved":
			cube = new F2LGen();   //Creates a cube using the F2L generator
			break;
		case "OLL Solved":
			cube = new OLLGen();  //Creates a cube using the OLL generator
			break;
		}
		
		cLayout.setConfig(cube.getCubeArray());  //set the cube net
		label.setText(cube.getScramble());   //set the scrambler
		cLayout.repaint();     //change the state of the frame
	}
}
