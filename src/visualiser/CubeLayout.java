package visualiser;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class CubeLayout extends Canvas{

	private static final long serialVersionUID = 1L;
	private static final Color[] colours = {
		new Color (0xFFFFFF), new Color(0xe61e25),new Color (0x60bb46), //White,red,green
		new Color (0xefea3d), new Color(0xf89924),new Color (0x4c54a4)  //Yellow,orange,blue
	};
	private static final int[] xCoord = {
			460,490,520,460,490,520,460,490,520, //U
			560,590,620,560,590,620,560,590,620, //R
			460,490,520,460,490,520,460,490,520, //F
			460,490,520,460,490,520,460,490,520, //D
			360,390,420,360,390,420,360,390,420, //L
			660,690,720,660,690,720,660,690,720, //B
	};
	private static final int[] yCoord = {
			100,100,100,130,130,130,160,160,160, //U
			200,200,200,230,230,230,260,260,260, //R
			200,200,200,230,230,230,260,260,260, //F
			300,300,300,330,330,330,360,360,360, //D
			200,200,200,230,230,230,260,260,260, //L
			200,200,200,230,230,230,260,260,260, //B
	};
	private char[] config;  //The cube array
	
	public void paint(Graphics g) {
		
		for(int i = 0; i < 54; i++) {
			switch(config[i]) {
			case 'U':
				g.setColor(colours[0]);  //White for Up
				break;
			case 'R':
				g.setColor(colours[1]);  //Red for Right
				break;
			case 'F':
				g.setColor(colours[2]);  //Green for Front
				break;
			case 'D':
				g.setColor(colours[3]);  //Yellow for Down
				break;
			case 'L':
				g.setColor(colours[4]);  //Orange for Left
				break;
			case 'B':
				g.setColor(colours[5]);  //Blue for Back
				break;
			}
			
			g.fillRect(xCoord[i], yCoord[i], 30, 30); //Rectangle with the right colour
			g.setColor(Color.BLACK);
			g.drawRect(xCoord[i], yCoord[i], 30, 30); //Outline of the same rectangle
		}
	}
	
	public void setConfig(char[] newConfig) {
		this.config = newConfig;
	}
}
