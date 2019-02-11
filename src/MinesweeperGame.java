

public class MinesweeperGame {
	public static void main(String[] s) {
		MinesweeperGame m = new MinesweeperGame(10, 10, 10);
	}
	
	MinesweeperGame(int width, int height, int mines){ 
		Window w = new Window(new Board(width, height, mines));
	}
}
