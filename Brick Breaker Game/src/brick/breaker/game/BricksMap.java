package brick.breaker.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class BricksMap {

	public int map[][];
	public int brickHeight;
	public int brickWidth;
	
	public BricksMap(int row, int column){
		map = new int[row][column];
		
		for(int i=0;i<row;i++) {
			for(int j=0;j<column;j++) {
				map[i][j]=1;
			}
		}
		brickHeight = 150/row;
		brickWidth = 540/column;
	}
	
	public static void main(String[] args) {

	}

	public void draw(Graphics2D g) {
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map[0].length;j++) {
				 if(map[i][j]>0) {
					 g.setColor(Color.getHSBColor(16, 77, 250));
					 g.fillRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
					 
					 // adding border to bricks
					 g.setColor(Color.getHSBColor(50, 100, 80));
					 g.setStroke(new BasicStroke(3));
					 g.drawRect(j*brickWidth+80, i*brickHeight+50, brickWidth, brickHeight);
				 }
			}
		}
		
	}

}
