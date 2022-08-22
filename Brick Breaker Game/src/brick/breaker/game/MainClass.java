package brick.breaker.game;

import javax.swing.JFrame;

public class MainClass {
	public static void main(String[] args) {
		JFrame frame=new JFrame();
		Game game = new Game();
		
		// creating frame 
		frame.setBounds(10, 10, 700, 600);
		frame.setTitle("Brick Breaker");		
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(game);
		
        frame.setVisible(true);
	}
}