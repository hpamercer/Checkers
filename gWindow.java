import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class gWindow extends JFrame {
	
	board Board;
	public static stones selectedStone = null;
	public static stones justCaptured = null;
	public static boolean whiteTurn = true;
	public static char referenceFile = 'a';
	public static int referenceRank = 1;
	public boolean moveMade;
	public static int winStatus = 0;
	
	gWindow() {
	
		Board = new board();
	
		this.add(Board);
		
		this.setSize(1024,1024);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
		
		
		
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (selectedStone != null) {
					selectedStone.drawX = e.getX() - 64;
					selectedStone.drawY = e.getY() - 64;
					Board.repaint();
				}
				
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				
				
			}
		});
		
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
					selectedStone = getStone(e.getX(),e.getY());
					if ((whiteTurn && !selectedStone.white) || (!whiteTurn && selectedStone.white) || winStatus != 0) {
						selectedStone = null;
					} 
				
			}
			
			@Override
			public void mouseReleased (MouseEvent e) {
			
				if (selectedStone != null && winStatus == 0) {
					int newRank = (int)Math.ceil((1056 - e.getY())/128) + 1;
					int filler = (int)Math.floor(e.getX()/128);
					char newFile = indexToFile(filler);
				
					boolean capturesAvailable = false;
					for (stones c : stones.getSurvivors()) { // we begin the turn by checking if there are any captures available.
						if ((whiteTurn && c.white && !c.getLegalCaptures().isEmpty()) || (!whiteTurn && !c.white && !c.getLegalCaptures().isEmpty())) {		
							capturesAvailable = true;
							break;
						}
					}
					
					ArrayList<int[]> legalSquares = selectedStone.getLegalCaptures();
					if (justCaptured != null && justCaptured != selectedStone) {
						legalSquares.clear();
					}
					//then we decide what the legal moves for the selectedStone are. Start by setting it to the available captures for that stone, since this will be legal regardless of available captures. 
					if (!capturesAvailable) {
						for (int[] move : selectedStone.getLegalMoves()) {
							System.out.println(move[0] + "," + move[1]);
							legalSquares.add(move); // if there are no captures available, then we add the available legal moves for that checker.
						}
					}
					
					if (legalSquares.isEmpty()) {
						moveMade = false;
					} else {
						for (int[] destination : legalSquares) {
							moveMade = false;
							if (Character.compare(newFile, indexToFile(destination[0])) == 0 && newRank == destination[1]) { // if we drop the checker on a square where it can legally move, we must make that move!
								char oldFile = selectedStone.file;
								int oldRank = selectedStone.rank;
								selectedStone.move(newFile,newRank); //having stored the old position (for comparison to see if it captured anything), we can move the checker to the new position.
								if (oldRank - newRank > 1 || oldRank - newRank < -1) {
									for (int i = 0; i < stones.getSurvivors().size(); i++) {
										if (Character.compare(oldFile, newFile) == 2*Character.compare(oldFile, stones.getSurvivors().get(i).file) && oldRank - newRank == 2*(oldRank - stones.getSurvivors().get(i).rank)) { //this code checks if the checker has moved more than 1 diagonal space (i.e has made a capture), and if it has, then it will remove the checker that got jumped.
											stones.jump(stones.getSurvivors().get(i));
											Board.repaint();
											if (!selectedStone.getLegalCaptures().isEmpty()) {
												moveMade = true;
												justCaptured = selectedStone;
												selectedStone = null;
											} else {
												moveMade = true;
												justCaptured = null;
												selectedStone = null;
												whiteTurn = !whiteTurn;
											}
										}
									}
								} else {
									Board.repaint();
									whiteTurn = !whiteTurn;
									selectedStone = null;
									moveMade = true;
									justCaptured = null;
									break;
								}
							}
						}
					}
					
					if (!moveMade) {
							selectedStone.drawX = (Character.getNumericValue(selectedStone.file)-10)*128;
							selectedStone.drawY = 1024 - (selectedStone.rank*128);
							Board.repaint();
							selectedStone = null;
					} else {
						for (stones c : stones.getSurvivors()) {
							if ((c.white && c.rank == 8) || (!c.white && c.rank == 1)) {
								c.king = true;
								Board.repaint();
							}
						}
						winStatus = gameOver(whiteTurn);
						if (winStatus == 1) {
							System.out.println("White Wins!");
						} else if (winStatus == -1) {
							System.out.println("Black Wins!");
						}
					}
				}
			}
			
			@Override
			public void mouseEntered (MouseEvent e) {
				
				
				
			}
			
			@Override
			public void mouseExited (MouseEvent e) {
				
			
			}
		});
		
		
			
		
	
	}
	
	public static stones getStone(int x, int y) {
		
			int xAxis = (int)Math.floor(x/128);
			char file = indexToFile(xAxis);
			int yAxis = (int)Math.ceil((1056 - y)/128) + 1;
			
			for (stones c : stones.getSurvivors()) {
				if (c.rank == yAxis && Character.compare(c.file, file) == 0) {
					return c;
				}
			}
			return null;
	}
	
	public static char indexToFile(int xAxis) {
		
		char file = 'a';
		switch (xAxis) {
						
			case 0:
				file = 'a';
				break;
			case 1:
				file = 'b';
				break;
			case 2:
				file = 'c';
				break;
			case 3:
				file = 'd';
				break;
			case 4:
				file = 'e';
				break;
			case 5:
				file = 'f';
				break;
			case 6:
				file = 'g';
				break;
			case 7:
				file = 'h';
				break;
			default:
				file = 'i';
				break;
		}
		return file;
	}
	
	public static int gameOver(boolean W) { // this is a function that is to run at the end of each turn; it returns 0 if nobody has won, 1 if white won, and -1 if black won. The boolean input value should be true if it is white's turn.
		int whoWon = 0;
		if (!W) {
			for (stones c : stones.getSurvivors()) {
				if (!c.white) {
					whoWon = 0;
				} else {
					whoWon = 1;
					continue;
				}
				
				if (!c.white && c.getLegalMoves().isEmpty() && c.getLegalCaptures().isEmpty()) {
					whoWon = 1;
					continue;
				} else {
					whoWon = 0;
					break;
				}
			}
		} else {
			for (stones c : stones.getSurvivors()) {
				if (c.white) {
					whoWon = 0;
				} else {
					whoWon = -1;
					continue;
				}
				
				if (c.white && c.getLegalMoves().isEmpty() && c.getLegalCaptures().isEmpty()) {
					whoWon = -1;
					continue;
				} else {
					whoWon = 0;
					break;
				}
			}
		}
		return whoWon;
	}

			
					
	
	public static void main(String[] args) {
		
		gWindow gameWindow = new gWindow();
	
	}
}