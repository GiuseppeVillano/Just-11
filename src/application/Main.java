package application;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame f=new JFrame("Just11");
		f.setLayout(new BorderLayout());
		f.setSize(600,650);
		GUI gui=new GUI();
		GamePanel p=new GamePanel(gui);
		Loop loop=new Loop(p);
		loop.start();
		f.add(gui,BorderLayout.NORTH);
		f.add(p,BorderLayout.CENTER);
		p.setFocusable(true);
		f.setMinimumSize(new Dimension(600,600));
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
