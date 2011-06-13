package othello;

public class GameRunner {
	public static void main (String [] args) {
		
		Game game = new Game (5);
		GameGui gameGui = new GameGui (game);
		GameController gameController = new GameController (game, gameGui);		
	}
}
