
public class Board {
	int[][] boardState;
	int width, height, mines;
	
	Board(int width, int height, int mines){
		this.width = width;
		this.height = height;
		this.mines = mines;
		boardState = new int[height][width];
		initializeBoard(mines);
	}	
	
	private void initializeBoard(int mines) {
		for (int r = 0; r < height; r++) {
			for (int c = 0; c < width; c++) {
				boardState[r][c] = 0;
			}
		}
		
		int minesPlaced = 0;
		while(minesPlaced <= mines) {
			int randRow = (int) (Math.random() * height);
			int randCol = (int) (Math.random() * width);
			if(boardState[randRow][randCol] != 1) {
				boardState[randRow][randCol] = -1;
				minesPlaced++;
			}
		}
		
		for (int r = 0; r < height; r++) {
			System.out.println("");
			for (int c = 0; c < width; c++) {
				if (boardState[r][c] == 0)
					boardState[r][c] = checkAdjacent(c, r);
			}
		}
		
		//debug
		for (int r = 0; r < height; r++) {
			System.out.println("");
			for (int c = 0; c < width; c++) {
				System.out.print(boardState[r][c] + ", ");
			}
		}
		//*****
		
	}
	
	private int checkAdjacent(int x, int y) {
		int counter = 0;
		
		if (y + 1 < height && boardState[y + 1][x] == -1) {
			counter++;
		}
		if (y - 1 >= 0 && boardState[y - 1][x] == -1) {
			counter++;
		}
		if (x + 1 < width && boardState[y][x + 1] == -1) {
			counter++;
		}
		if (x - 1 >= 0 && boardState[y][x - 1] == -1) {
			counter++;
		}
		if (y + 1 < height && x + 1 < width && boardState[y + 1][x + 1] == -1) {
			counter++;
		}
		if (y + 1 < height && x - 1 >= 0 && boardState[y + 1][x - 1] == -1) {
			counter++;
		}
		if (y - 1 >= 0 && x + 1 < width && boardState[y - 1][x + 1] == -1) {
			counter++;
		}
		if (y - 1 >= 0 && x - 1 >= 0 && boardState[y - 1][x - 1] == -1) {
			counter++;
		}
		
		return counter++;
	}
}
