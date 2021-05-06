import javax.swing.JFrame;

public class LeagueInvaders {
	GamePanel game_panel;
	JFrame frame;
	public static final int WIDTH = 500;
	public static final int HEIGHT = 800;
	LeagueInvaders(){
		frame = new JFrame();
		game_panel = new GamePanel();
	}
	public static void main(String[] args) {
		new LeagueInvaders().setup();
	}
	public void setup() {
		frame.add(game_panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
