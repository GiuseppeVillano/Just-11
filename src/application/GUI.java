package application;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JPanel{
	private static final long serialVersionUID = 1L;
	private int score=0;
	private JLabel scoreTxt;

	public GUI() {
		scoreTxt=new JLabel("score ="+score);
		this.add(scoreTxt);
	}
	
	public void updateScore(int value) {
		score+=value*100;
		scoreTxt.setText("score ="+score);
	}
}
