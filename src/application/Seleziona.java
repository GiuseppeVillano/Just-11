package application;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("seleziona")
public class Seleziona {

	@Param(0)
	private int x;
	@Param(1)
	private int y;
	@Param(2)
	private int type;
	
	public Seleziona() {}
	
	public Seleziona(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Seleziona [x=" + x + ", y=" + y + ", type=" + type + "]";
	}
	
	
}
