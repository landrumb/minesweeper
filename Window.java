import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Window extends JPanel implements MouseListener, ActionListener{
	Board board;
	JFrame frame, toggleFrame;
	JPanel togglePanel;
	JButton flagToggle, reset;
	final int cellSize = 40;
	boolean flagging = false;
	int[][] ground;
	
	Window(Board b){
		ground = new int[b.height][b.width];
		
		for (int r = 0; r < b.height; r++) {
			for (int c = 0; c < b.width; c++) {
				ground[r][c] = 0;
			}
		}
				
		toggleFrame = new JFrame();
		togglePanel = new JPanel();
		flagToggle = new JButton("Flag");
		flagToggle.addActionListener(this);
		togglePanel.add(flagToggle);
		toggleFrame.add(togglePanel);
		toggleFrame.setVisible(true);
		toggleFrame.pack();
		this.addMouseListener(this);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		frame.setSize(b.width * cellSize + 7, b.height * cellSize + 36);
	
		board = b;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		for(int r = 0; r <= board.height; r++) {
			g2d.drawLine(0, cellSize * r, this.getWidth(), cellSize * r);
		}
		
		for(int c = 0; c <= board.width; c++) {
			g2d.drawLine(cellSize * c, 0, cellSize * c , this.getHeight());
		}
		
		g2d.setColor(Color.DARK_GRAY);
		for (int r = 0; r < board.height; r++) {
			for (int c = 0; c < board.width; c++) {
				if (board.boardState[r][c] > 0)
					g2d.drawString(Integer.toString(board.boardState[r][c]), (cellSize * c) + (cellSize / 2), (cellSize * r) + (cellSize / 2));
				if (board.boardState[r][c] == -1) {
					g2d.drawString("B", (cellSize * c) + (cellSize / 2), (cellSize * r) + (cellSize / 2));
				}
				if(ground[r][c] == 0) 
					g2d.fillRect((cellSize * c) + 1, (cellSize * r) + 1 , cellSize - 1 , cellSize - 1);
				
				if(ground[r][c] == 2) {
					g2d.setColor(Color.RED);
					g2d.fillRect((cellSize * c) + 1, (cellSize * r) + 1 , cellSize - 1 , cellSize - 1);
					g2d.setColor(Color.DARK_GRAY);
				}
			}
		}
	}

	private void updateGround(int row, int col) {
		if (board.boardState[row][col] == -1) {
			for (int r = 0; r < board.height; r++) {
				for (int c = 0; c < board.width; c++) {
					ground[r][c] = 1;
				}
			}
		}else if (board.boardState[row][col] == 0){
			checkAdjacent(row, col);
		}else {
			ground[row][col] = 1;
		}
	}
	
	private void clearSurrounding(int row, int col) {
		if(ground[row][col] != 2) {	
			if (row + 1 < board.height) {
				ground[row + 1][col] = 1;
			}
			if (row - 1 >= 0) {
				ground[row - 1][col] = 1;
			}
			if (col + 1 < board.width) {
				ground[row][col + 1] = 1;
			}
			if (col - 1 >= 0) {
				ground[row][col - 1] = 1;
			}
			if (row + 1 < board.height && col + 1 < board.width) {
				ground[row + 1][col + 1] = 1;
			}
			if (row + 1 < board.height && col - 1 >= 0) {
				ground[row + 1][col - 1] = 1;
			}
			if (row - 1 >= 0 && col + 1 < board.width) {
				ground[row - 1][col + 1] = 1;
			}
			if (row - 1 >= 0 && col - 1 >= 0) {
				ground[row - 1][col - 1] = 1;
			}
		}
		
	}
	
	private void checkAdjacent(int row, int col) {
		ground[row][col] = 1;
		
		if (row + 1 < board.height && board.boardState[row + 1][col] == 0 && ground[row + 1][col] != 1) {
			checkAdjacent(row + 1, col);
			clearSurrounding(row + 1, col);
		}
		if (row - 1 >= 0 && board.boardState[row - 1][col] == 0 && ground[row - 1][col] != 1) {
			checkAdjacent(row - 1, col);
			clearSurrounding(row - 1, col);

		}
		if (col + 1 < board.width && board.boardState[row][col + 1] == 0 && ground[row][col + 1] != 1) {
			checkAdjacent(row, col + 1);
			clearSurrounding(row, col + 1);

		}
		if (col - 1 >= 0 && board.boardState[row][col - 1] == 0 && ground[row][col - 1] != 1) {
			checkAdjacent(row, col - 1);
			clearSurrounding(row, col - 1);

		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int row = e.getY() / cellSize;
		int col = e.getX() / cellSize;
		if (!flagging)
			updateGround(row, col);
		if (flagging && ground[row][col] == 0) {
			ground[row][col] = 2;
		}
		else if (flagging && ground[row][col] == 2) {
			ground[row][col] = 0;
		}
		repaint();
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(flagToggle.getText().equals("Flag")) {
			flagToggle.setText("Sweep");
			flagging = true;
		} else {
			flagToggle.setText("Flag");
			flagging = false;
		}
	}
	
}


