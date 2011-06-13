package othello;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class GameGui {
	
	JFrame mainFrame;
	JPanel boardPanel;
	JButton [] cellButton;
	Game gameModel;
	JLabel whoseTurnLabel;
	JPanel mainPanel;
	JButton resetButton;
	JButton passButton;
	JPanel optionsPanel;
	Color backgroundColor;
	
	GameGui (Game gameModel) {
			
		int size = gameModel.getSize();
		this.gameModel = gameModel;
		mainFrame = new JFrame ();
		mainFrame.setTitle("Othello");

		boardPanel = new JPanel ();
		boardPanel.setLayout(new GridLayout(size, size));
		
		whoseTurnLabel = new JLabel ();
		resetButton = new JButton ("Reset");		
		passButton = new JButton ("Pass");
		optionsPanel = new JPanel ();
		optionsPanel.setLayout(new FlowLayout());
		optionsPanel.add(whoseTurnLabel);
		optionsPanel.add(resetButton);
		optionsPanel.add(passButton);
		
		setTurn ();
		int noOfCells = size * size;
		cellButton = new JButton [noOfCells];
		for (int index = 0; index < noOfCells; index ++) {
			cellButton[index] = new JButton ();
			cellButton[index].setActionCommand(""+index);
			backgroundColor = new Color (100, 150, 0);
			cellButton[index].setBackground(backgroundColor);
			cellButton[index].setBorder(BorderFactory.createLineBorder(Color.BLACK));
			boardPanel.add(cellButton[index]);
		}	
		
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
	 	splitPane.setTopComponent(boardPanel);
	 	splitPane.setBottomComponent(optionsPanel);
	 	splitPane.setDividerLocation(227);
	
		mainPanel = new JPanel ();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(splitPane);
		mainFrame.add(mainPanel);
		mainFrame.setSize(300, 300);
		mainFrame.setResizable(false);
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	void setTurn () {
		if (gameModel.getTurn() == Constants.BLACK) {
			whoseTurnLabel.setText("Turn: Black");
		}
		else {
			whoseTurnLabel.setText("Turn: White");
		}
	}
	
	public void refresh () {
		int size = gameModel.getSize();
		Cell [][] cellMatrix = gameModel.getCellMatrix();
		for (int indexRow = 0; indexRow < size; indexRow ++) {
			for (int indexCol = 0; indexCol < size; indexCol ++) {
				int cellNo = indexRow * size + indexCol;
				changeState (cellNo, cellMatrix[indexRow][indexCol].getState());
			}
		}
		setTurn();
	}
	
	public void changeState (int buttonNo, int state) {

		Color color;	
		if (state == Constants.BLACK){
			color = Color.BLACK;
			cellButton[buttonNo].setIcon(new BoxIcon(color));
		}
		else if (state == Constants.WHITE) {
			color = Color.WHITE;
			cellButton[buttonNo].setIcon(new BoxIcon(color));
		}		
		else {
			cellButton[buttonNo].setIcon(null);
		}
	}
	
	public void addClickListener (ActionListener clickListener) {
		int size = gameModel.getSize();
		int noOfCells = size * size;
		for (int index = 0; index < noOfCells; index ++) {
			cellButton[index].addActionListener(clickListener);
		}
	}	
	
	void addResetListener (ActionListener actionListener) {
		resetButton.addActionListener(actionListener);
	}
	
	void addPassListener (ActionListener actionListener) {
		passButton.addActionListener(actionListener);
	}
	
	void showWinner () {
		String winner = "Black: " + gameModel.getBlackCount() + "\n";
		winner = winner.concat("White: " + gameModel.getWhiteCount() + "\n");
		if (gameModel.getBlackCount() > gameModel.getWhiteCount()) {
			winner = winner.concat("Black wins!");
		}
		else {
			winner = winner.concat("White wins!");
		}
		JOptionPane.showMessageDialog(null, winner);
		
	}
	
	class BoxIcon implements Icon {
		private Color color;

		BoxIcon(Color color) {
			this.color = color;
		}

		public int getIconWidth() {
			return 15;
		}

		public int getIconHeight() {
		    return 15;
		}

		public void paintIcon(Component c, Graphics g, int x, int y) {
		    g.setColor(color);
		    g.fillOval(x, y, getIconWidth(), getIconHeight());
		}
	}
}
