package othello;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class GameController {
	Game gameModel;
	GameGui gameView;
	
	public GameController (Game gameModel, GameGui gameView) {
		this.gameModel = gameModel;
		this.gameView = gameView;		
		
		gameView.addClickListener(new CellClickListener());
		gameView.addResetListener (new ResetListener());
		gameView.addPassListener(new PassButtonListener());
		gameView.refresh ();
	}	
	
	class CellClickListener implements ActionListener {
		
		public void actionPerformed (ActionEvent event) {
			int buttonClicked = Integer.parseInt(event.getActionCommand());
			int row = buttonClicked / gameModel.getSize();
			int col = buttonClicked % gameModel.getSize();
		
			if (gameModel.isValidMove(row, col, true)) {
				gameView.refresh ();
			}
			
			if (gameModel.isGameOver()) {
				System.out.println("Black: " + gameModel.getBlackCount());
				System.out.println("White: " + gameModel.getWhiteCount());
				gameView.showWinner ();
				gameModel.reset ();
				gameView.refresh ();
			}			
		}
	}
	
	class ResetListener implements ActionListener {
		
		public void actionPerformed (ActionEvent event) {
			gameModel.reset ();
			gameView.refresh ();
		}
	}
	
	class PassButtonListener implements ActionListener {
		
		public void actionPerformed (ActionEvent event) {
			gameModel.setNextTurn();			
			gameView.refresh ();
		}
	}
} 