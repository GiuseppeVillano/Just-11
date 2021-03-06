package application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import it.unical.mat.embasp.base.Handler;
import it.unical.mat.embasp.base.InputProgram;
import it.unical.mat.embasp.base.Output;
import it.unical.mat.embasp.languages.IllegalAnnotationException;
import it.unical.mat.embasp.languages.ObjectNotValidException;
import it.unical.mat.embasp.languages.asp.ASPInputProgram;
import it.unical.mat.embasp.languages.asp.ASPMapper;
import it.unical.mat.embasp.languages.asp.AnswerSet;
import it.unical.mat.embasp.languages.asp.AnswerSets;
import it.unical.mat.embasp.platforms.desktop.DesktopHandler;
import it.unical.mat.embasp.specializations.dlv2.desktop.DLV2DesktopService;

public class GamePanel extends JPanel implements MouseListener{

	private static final long serialVersionUID = 15527400473185110L;
	private Cell[][] grid;
	private int cellSize,size;
	private int maxCur;
	private int score;
	private GUI gui;
	
	public GamePanel(GUI gui) {
		this.gui=gui;
		size=5;
		grid=new Cell[size][size];
		cellSize=118;
		maxCur=3;
		Random r=new Random();
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Cell(i,j,r.nextInt(maxCur)+1);
			}
		}
		addMouseListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		for(int i=0;i<grid.length;i++)
			for(int j=0;j<grid[i].length;j++) {
				g.setColor(grid[i][j].getColor());
				g.fillRect(i*cellSize,j*cellSize,cellSize,cellSize);
				g.setColor(Color.BLACK);
				g.setFont(new Font(Font.SERIF,Font.BOLD,cellSize));
				g.drawString(String.valueOf(grid[i][j].getType()), i*cellSize, j*cellSize+cellSize);
			}
	}

	public void updateGame(int x,int y) {
		if(!hasNeighbour(x,y,grid[x][y].getType()))
			return;
		grid[x][y].nextType();
		if(grid[x][y].getType()>maxCur) 
			maxCur++;
		
		score=1;
		updateNeighbourhood(grid[x][y].getType()-1,x,y);
		gui.updateScore(score);
		
		updateFall();
		updateNumbers();
		if(gameLost())
			System.out.println("You Lost!");
		if(gameWon())
			System.out.println("You Won!");
		repaint();
//		System.out.println("%%%%%%%%%%");
//		for(int i=0;i<size;i++) {
//			for(int j=0;j<size;j++) {
//				if(grid[i][j].getType()>=0)
//					System.out.print(" ");
//				System.out.print(grid[i][j].getType());
//			}
//			System.out.println();
//		}
//		System.out.println("%%%%%%%%%%");
	}
	
	private boolean gameLost() {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				if(hasNeighbour(i,j,grid[i][j].getType()))
					return false;
		JOptionPane.showMessageDialog(null, "Hai perso!");
		return true;
	}
	private boolean gameWon() {
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++)
				if(grid[i][j].getType()==11) {
					JOptionPane.showMessageDialog(null, "Hai vinto!");
					return true;
				}
		return false;
	}
	
	private boolean hasNeighbour(int x,int y,int type) {
		if(x!=0&&grid[x-1][y].getType()==type)
			return true;
		if(x!=4&&grid[x+1][y].getType()==type)
			return true;
		if(y!=0&&grid[x][y-1].getType()==type)
			return true;
		if(y!=4&&grid[x][y+1].getType()==type) 
			return true;
		return false;
	}
	
	private void updateNeighbourhood(int type,int x,int y) {
		if(x<0|x>=size|y<0|y>=size) {
			System.out.println("error");
			return;
		}
		if(x!=0&&grid[x-1][y].getType()==type) {
			grid[x-1][y].changeType(-1);
			score+=1;
			updateNeighbourhood(type,x-1,y);
		}
		if(x!=4&&grid[x+1][y].getType()==type) {
			grid[x+1][y].changeType(-1);
			score+=1;
			updateNeighbourhood(type,x+1,y);
		}
		if(y!=0&&grid[x][y-1].getType()==type) {
			grid[x][y-1].changeType(-1);
			score+=1;
			updateNeighbourhood(type,x,y-1);
		}
		if(y!=4&&grid[x][y+1].getType()==type) {
			grid[x][y+1].changeType(-1);
			score+=1;
			updateNeighbourhood(type,x,y+1);
		}
		return;
	}
	
	private void updateNumbers() {
		Random r=new Random();
		for(int i=0;i<size;i++)
			for(int j=0;j<size;j++) 
				if(grid[i][j].getType()==-1) {
					if(r.nextInt(100)<=85) 
						grid[i][j].changeType(r.nextInt(3)+1);
					else 
						grid[i][j].changeType(r.nextInt(maxCur-2)+2);
				}
	}
	
	private void updateFall() {
		for(int i=4;i>=0;i--)
			for(int j=4;j>=0;j--) {
				if(grid[i][j].getType()==-1)
					fall(i,j);
			}
	}
	
	private void fall(int x,int y) {
		int k=0;
		while(k!=5) {
			for(int i=y;i>=1;i--) {
				grid[x][i].changeType(grid[x][i-1].getType());
			}
			grid[x][0].changeType(-1);
			if(grid[x][y].getType()!=-1)
				return;
			k++;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int newX=e.getX()/cellSize;
		int newY=e.getY()/cellSize;
		updateGame(newX,newY);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	public int getGridSize() {
		return size;
	}
	public Cell[][] getGrid() {
		return grid;
	}
	
		

}
