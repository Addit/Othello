package othello;

public class Game {
	
	final int NORTH = 1;
	final int SOUTH = 2;
	final int EAST = 3;
	final int WEST = 4;
	final int NORTHEAST = 5;
	final int NORTHWEST = 6;
	final int SOUTHEAST = 7;
	final int SOUTHWEST = 8;
	
	private int size;     // Size of the board
	private Cell [][] cellMatrix;

	private int turn;       // BLACK or WHITE
	private int blackCount;
	private int whiteCount;

	public int getBlackCount() {
		return blackCount;
	}

	public void setBlackCount(int blackCount) {
		this.blackCount = blackCount;
	}

	public int getWhiteCount() {
		return whiteCount;
	}

	public void setWhiteCount(int whiteCOunt) {
		this.whiteCount = whiteCOunt;
	}

	public Cell[][] getCellMatrix() {
		return cellMatrix;
	}

	public void setCellMatrix(Cell[][] cellMatrix) {
		this.cellMatrix = cellMatrix;
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Game (int sizeOfGame) {
		
		if (sizeOfGame > 3 && sizeOfGame  < 10) {
			size = sizeOfGame;
			cellMatrix = new Cell [size][size];
	
			for (int rowIndex = 0; rowIndex < size; rowIndex ++) {
				for (int columnIndex = 0; columnIndex < size; columnIndex ++) {
					cellMatrix [rowIndex][columnIndex] = new Cell ();
				}
			}
			
			initializeBoard ();	
		}

		else {}
	}
	
	public Game () {
		this (8);    // Default size: 8*8
	}
	
	void initializeBoard () {
		int center = size / 2 -1;
		
		for (int rowIndex = 0; rowIndex < size; rowIndex ++) {
			for (int columnIndex = 0; columnIndex < size; columnIndex ++) {
				cellMatrix [rowIndex][columnIndex].setState(Constants.EMPTY);
			}
		}
		cellMatrix[center][center].setState(Constants.BLACK);
		cellMatrix[center][center+1].setState(Constants.WHITE);
		cellMatrix[center+1][center+1].setState(Constants.BLACK);
		cellMatrix[center+1][center].setState(Constants.WHITE);
		
		setBlackCount(2);
		setWhiteCount(2);
		setTurn (Constants.BLACK);
	}

	void reset () {
		initializeBoard ();
		turn = Constants.BLACK;
	}
	
	/* Determine whether the cell is at border */
	boolean isCellAtBoundary (Point point, int direction) {
		switch (direction) {
		case NORTH:
			return isCellAtTopBoundary (point);
		case SOUTH:
			return isCellAtBottomBoundary (point);
		case EAST:
			return isCellAtRightBoundary (point);
		case WEST:
			return isCellAtLeftBoundary (point);
		case NORTHEAST:
			return isCellAtTopBoundary (point)|| isCellAtRightBoundary (point);
		case NORTHWEST:
			return isCellAtTopBoundary (point)|| isCellAtLeftBoundary (point);
		case SOUTHEAST:
			return isCellAtBottomBoundary (point)|| isCellAtRightBoundary (point);
		case SOUTHWEST:
			return isCellAtBottomBoundary (point)|| isCellAtLeftBoundary (point);
		default:
			return true;
		}
	}

	boolean isCellAtTopBoundary (Point point) {
		if (point.getRow () == 0) {
			return true;
		}
		return false;
	}
	
	boolean isCellAtBottomBoundary (Point point) {
		if (point.getRow () == size - 1) {
			return true;
		}
		return false;
	}
	
	boolean isCellAtLeftBoundary (Point point) {
		
		if (point.getColumn () == 0) {
			return true;
		}
		return false;
	}
	
	boolean isCellAtRightBoundary (Point point) {
		
		if (point.getColumn () == size - 1) {
			return true;
		}
		return false;
	}
		
	public boolean isValidMove (int row, int column, boolean toFlip) {

		Point position = new Point (row, column);
		boolean isValid = false;
		
		if (cellMatrix[row][column].getState() != Constants.EMPTY) {
			isValid = false;
			return false;
		}
		
		if (makeMove (position, toFlip, NORTH) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, SOUTH) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, EAST) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, WEST) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, NORTHEAST) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, NORTHWEST) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, SOUTHEAST) > 0) {
			isValid = true;
		}
		
		if (makeMove (position, toFlip, SOUTHWEST) > 0) {
			isValid = true;
		}
	
		if (isValid && toFlip) {
			setNextTurn ();
		}
		return isValid;
	}
	
	void setNextTurn () {
		turn = - turn;
	}
	
	int makeMove (Point position, boolean toFlip, int direction) {
		Point moveOneStep = getMove (direction);
		
		int countOfFlips = 0;
		int stateAtPosition = -2;
		Point currentPosition = new Point (position.getRow(), position.getColumn());
		while (!isCellAtBoundary(currentPosition, direction)) {
			currentPosition.add(moveOneStep);
			stateAtPosition = cellMatrix[currentPosition.getRow()][currentPosition.getColumn()].getState();
			if (stateAtPosition == Constants.EMPTY) {
				return 0;
			}
			
			else if (stateAtPosition == turn) {
				if (countOfFlips == 0) {
					return 0;
				}
				else {					
					if (toFlip) {
						for (int flip = 0; flip < countOfFlips; flip ++) {
							currentPosition.subtract(moveOneStep);
							cellMatrix[currentPosition.getRow()][currentPosition.getColumn()].setState(turn);
							modifyCount (true);
						}
						if (cellMatrix[position.getRow()][position.getColumn()].getState() != turn) {
							cellMatrix[position.getRow()][position.getColumn()].setState(turn);
							modifyCount (false);
						}						
					}
					return countOfFlips;
				}
			}			
			else {			
				countOfFlips ++;
			}
		}
		if (stateAtPosition == turn)
			return countOfFlips;
		else
			return 0;
	}
	
	void modifyCount (boolean decrement) {
		
		if (turn == Constants.BLACK) { 
			blackCount ++;
			if (decrement)
				whiteCount --;
		}
		
		else {
			whiteCount ++;
			if (decrement)
				blackCount --;
		}
	}
	
	/* To move one step forward in a direction*/
	Point getMove (int direction) {
		switch (direction) {
		case NORTH:
			return (new Point(-1, 0));
		case SOUTH:
			return (new Point(1, 0));
		case EAST:
			return (new Point(0, 1));
		case WEST:
			return (new Point(0, -1));
		case NORTHEAST:
			return (new Point(-1, 1));
		case NORTHWEST:
			return (new Point(-1, -1));
		case SOUTHEAST:
			return (new Point(1, 1));
		case SOUTHWEST:
			return (new Point(1, -1));
		default:
			return null;
		}
	}
	
	/* Check for empty cells, for each empty cell, 
	 * check validity for either black or white
	 */
	boolean isGameOver () {
		for (int rowIndex = 0; rowIndex < size; rowIndex ++) {
			for (int columnIndex = 0; columnIndex < size; columnIndex ++) {
				if (cellMatrix [rowIndex][columnIndex].getState() == Constants.EMPTY) {
					if (isValidMove (rowIndex, columnIndex, false)) {
						return false;
					}
				}
			}
		}
		return true;
	}	
	
	
	
	class Point {
		private int row;
		private int column;

		public Point (int row, int column) {
			this.row = row;
			this.column = column;
		}

		public int getRow () {
			return row;
		}

		public int getColumn () {
			return column;
		}
		
		public void add (Point toAdd) {
			row += toAdd.getRow();
			column += toAdd.getColumn();
		}
		
		public void subtract (Point toSubtract) {
			row -= toSubtract.getRow();
			column -= toSubtract.getColumn();
		}
	}
}
