package application;

import java.awt.Color;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("cell")
public class Cell {
	
	@Param(0)
	private int x;
	@Param(1)
	private int y;
	@Param(2)
	private int type;
	
	private Color color;
	
	public Cell(int x, int y,int type) {
		this.x=x;
		this.y=y;
		this.type = type;
		updateColor();
	}
	
	public Cell(int type) {
		this.type=type;
		updateColor();
	}
	
	public Cell() {}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setType(int type) {
		this.type = type;
		updateColor();
	}
	
	public int getType() {
		return type;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	private void updateColor() {
		switch(type) {
		case 0:
			color=Color.RED;
			break;
		case 1:
			color=Color.ORANGE;
			break;
		case 2:
			color=Color.YELLOW;
			break;
		case 3:
			color=Color.GREEN;
			break;
		case 4:
			color=Color.CYAN;
			break;
		case 5:
			color=Color.BLUE;
			break;
		case 6:
			color=Color.MAGENTA;
			break;
		case 7:
			color=Color.WHITE;
			break;
		case 8:
			color=Color.LIGHT_GRAY;
			break;
		case 9:
			color=Color.GRAY;
			break;
		case 10:
			color=Color.DARK_GRAY;
			break;
		case 11:
			color=Color.BLACK;
			break;
		default:
			color=Color.WHITE;
			break;
		}
	}
	
	public void nextType() {
		type++;
		updateColor();
	}
	
}
