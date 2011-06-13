package othello;

/* Class to represent each cell on the board
 */

public class Cell {
	private int state;
	
	public Cell () {
		state = Constants.EMPTY; 
	}

	public int getState () {
		return state;
	}

	public void setState (int state) {
		if (state == Constants.BLACK || state == Constants.WHITE || state == Constants.EMPTY) {
			this.state = state;
		}	
	}
}
