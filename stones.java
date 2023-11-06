import java.util.*;

public class stones {
	int rank;
	char file;
	int drawX;
	int drawY;
	boolean white;
	boolean king;
	
	
	public static ArrayList<stones> survivors = new ArrayList<stones>();
	
	public static ArrayList<stones> getSurvivors() {
		
		return survivors;
		
	}
	
	public stones(char x, int y, boolean isWhite)  {
	
	this.rank = y;
	this.file = x;
	this.drawX = (Character.getNumericValue(x)-10)*128;
	this.drawY = 1024 - (y*128);
	this.white = isWhite;
	this.king = false;
	survivors.add(this);
	
	}
	
	public void move(char file, int rank) {
		
		this.rank = rank;
		this.file = file;
		this.drawX = (Character.getNumericValue(file)-10)*128;
		this.drawY = 1024 - (rank*128);
	
	}
	
	public static void jump(stones stone) {
	
		survivors.remove(stone);
		
	}
	
	public ArrayList<int[]> getLegalMoves() { // a function that returns all the legal moves of any checker, white or black, king or stone. Returns an nx2 array of legal moves of the form [[file,rank],[file,rank],....] where each [file,rank] represents a square the checker can move to.
		
		int blockers = 0;
		ArrayList<int[]> legalMoves = new ArrayList<int[]>();
		if (this.white) {
			if (this.rank < 8) { //a white checker on the 8th rank isn't going to be moving upwards, so we can discount these off the bat.
				if (this.file != 'a') { //this part is for left moves, so checkers on the a file are excluded
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank + 1) && Character.compare(c.file, (char)(this.file - 1)) == 0) {
							blockers += 1;
							break;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 98; //we don't use the character file straight away, since legalMove is an array of integers. We will later use the indexToFile function to sort this out.
						legalMove[1] = this.rank + 1; //if the square is clear, the white checker can move diagonally left and up
						legalMoves.add(legalMove);
					}
				}
				blockers = 0;
				if (this.file != 'h') { //this part is for right moves, so checkers on the h file are excluded
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank + 1) && Character.compare(c.file, (char)(this.file + 1)) == 0) {
							blockers += 1;
							break;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 96; 
						legalMove[1] = this.rank + 1; //if the square is clear, then the white checker can move diagonally right and up
						legalMoves.add(legalMove); //the code within the "if the checker is white and not on the 8th rank" can be copied directly into the statement "if the black checker is a king and not on the 8th rank".
					}
				}
			}
			if (this.king && this.rank > 1) { //if the king checker is on the first rank, it won't get any extra moves on account of being a king.
				blockers = 0;
				if (this.file != 'a') { //this part is for left moves, so checkers on the a file are excluded
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank - 1) && Character.compare(c.file, (char)(this.file - 1)) == 0) {
							blockers += 1;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 98; 
						legalMove[1] = this.rank - 1; //if the square is clear, the white king checker can move diagonally left and down
						legalMoves.add(legalMove);
					}
				}
				blockers = 0;
				if (this.file != 'h') {  
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank - 1) && Character.compare(c.file, (char)(this.file + 1)) == 0) {
							blockers += 1;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 96;
						legalMove[1] = this.rank - 1; //given a clear square, the white king checker can move diagonally right and down.
						legalMoves.add(legalMove); //the code within the "if checker is a king && not on the 1st rank" can be copied directly into the "if checker is black and not on the 1st rank" statement below, and will be.
					}
					blockers = 0;
				}
			}
		} else {
			if (this.rank != 1) {
				blockers = 0;
				if (this.file != 'a') {
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank - 1) && Character.compare(c.file, (char)(this.file - 1)) == 0) {
							blockers += 1;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 98; 
						legalMove[1] = this.rank - 1;
						legalMoves.add(legalMove);
					}
				}
				blockers = 0;
				if (this.file != 'h') {  
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank - 1) && Character.compare(c.file, (char)(this.file + 1)) == 0) {
							blockers += 1;
						}
					}	
					if (blockers == 0) {						
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 96;
						legalMove[1] = this.rank - 1; 
						legalMoves.add(legalMove);
					}
					blockers = 0;
				}
			}
			if (this.king && this.rank != 8) {
				if (this.file != 'a') {
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank + 1) && Character.compare(c.file, (char)(this.file - 1)) == 0) {
							blockers += 1;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 98; 
						legalMove[1] = this.rank + 1;
						legalMoves.add(legalMove);
					}
				}
				blockers = 0;
				if (this.file != 'h') {
					for (stones c : stones.getSurvivors()) {
						if (c.rank ==(this.rank + 1) && Character.compare(c.file, (char)(this.file + 1)) == 0) {
							blockers += 1;
						}
					}	
					if (blockers == 0) {
						int[] legalMove = new int[2];
						legalMove[0] = this.file - 96; 
						legalMove[1] = this.rank + 1;
						legalMoves.add(legalMove);
					}
				}
			}
		}
		return legalMoves;
	}
				
	
	public ArrayList<int[]> getLegalCaptures() {
		
		int hanging = 0;
		int defenders = 0;
		ArrayList<int[]> legalCaptures = new ArrayList<int[]>();
		boolean canCapture = false;
		
		if (this.white) {
			if(this.rank < 7) { //no white checker will be able to capture anything (unless it is a king) if it is on the 7th or 8th rank.
				for (stones c : stones.getSurvivors()) {
					if (!c.white && this.rank + 1 == c.rank && Character.compare(this.file, c.file) == -1) {
						canCapture = true; // if we find a black checker 1 up and 1 right, we assume we can capture it.
						for (stones s : stones.getSurvivors()) {
							if ((s.rank == this.rank + 2 && Character.compare(this.file, s.file) == -2) || this.file - 97 == 6) {
								canCapture = false; // but if there is a checker 2 up and 2 right defending it, we cannot capture.
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[0] = this.file - 95;
							legalCapture[1] = this.rank + 2;
							legalCaptures.add(legalCapture);
							canCapture = false; // if the right capture is legal, we add the square the capturing checker would move to. I need to add code in the move function such that if the move is a capture, the other checker is killed.
						}
					}
					
					if (!c.white && this.rank + 1 == c.rank && Character.compare(this.file, c.file) == 1) {
						canCapture = true; // if we find a black checker 1 up and 1 left, we assume we can capture it.
						for (stones s : stones.getSurvivors()) {
							if (this.file - 97 == 1 || (s.rank == this.rank + 2 && Character.compare(this.file, s.file) == 2)) {
								canCapture = false; // but if there is a checker 2 up and 2 left defending it, or the capturing checker is on the b file, we cannot capture.
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[0] = this.file - 99;
							legalCapture[1] = this.rank + 2;
							legalCaptures.add(legalCapture);
							canCapture = false; // if the left capture is legal, we add the square the capturing checker would move to. I need to add code in the move function such that if the move is a capture, the other checker is killed.
						}
					}
				}
			}
			if (this.king && this.rank > 2) {
				for (stones c : stones.getSurvivors()) {
					if (!c.white && this.rank - 1 == c.rank && Character.compare(this.file, c.file) == -1) {
						canCapture = true;
						for (stones s : stones.getSurvivors()) {
							if ((this.rank - 2 == s.rank && Character.compare(this.file, s.file) == -2) || this.file - 97 == 6) {
								canCapture = false;
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[1] = this.rank - 2;
							legalCapture[0] = this.file - 95;
							legalCaptures.add(legalCapture);
							canCapture = false;
						}
					}	
					if (!c.white && this.rank - 1 == c.rank && Character.compare(this.file, c.file) == 1) {
						canCapture = true;
						for (stones s : stones.getSurvivors()) {
							if ((this.rank - 2 == s.rank && Character.compare(this.file, s.file) == 2) || this.file - 97 == 1) {
								canCapture = false;
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[1] = this.rank - 2;
							legalCapture[0] = this.file - 99;
							legalCaptures.add(legalCapture);
							canCapture = false;
						}
					}
				}
			}
		} else {
			if (this.rank > 1) {
				for (stones c : stones.getSurvivors()) {
					if (c.white && this.rank - 1 == c.rank && Character.compare(this.file, c.file) == -1) {
						canCapture = true;
						for (stones s : stones.getSurvivors()) {
							if ((this.rank - 2 == s.rank && Character.compare(this.file, s.file) == -2) || this.file - 97 == 6) {
								canCapture = false;
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[1] = this.rank - 2;
							legalCapture[0] = this.file - 95;
							legalCaptures.add(legalCapture);
							canCapture = false;
						}
					}	
					if (c.white && this.rank - 1 == c.rank && Character.compare(this.file, c.file) == 1) {
						canCapture = true;
						for (stones s : stones.getSurvivors()) {
							if ((this.rank - 2 == s.rank && Character.compare(this.file, s.file) == 2) || this.file - 97 == 1) {
								canCapture = false;
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[1] = this.rank - 2;
							legalCapture[0] = this.file - 99;
							legalCaptures.add(legalCapture);
							canCapture = false;
						}
					}
				}
			}
			if (this.king && this.rank < 7) {
				for (stones c : stones.getSurvivors()) {
					if (c.white && this.rank + 1 == c.rank && Character.compare(this.file, c.file) == -1) {
						canCapture = true; // if we find a black checker 1 up and 1 right, we assume we can capture it.
						for (stones s : stones.getSurvivors()) {
							if ((s.rank == this.rank + 2 && Character.compare(this.file, s.file) == -2) || this.file - 97 == 6) {
								canCapture = false; // but if there is a checker 2 up and 2 right defending it, we cannot capture.
							}
						}
					}
					if (canCapture) {
						int[] legalCapture = new int[2];
						legalCapture[0] = this.file - 95;
						legalCapture[1] = this.rank + 2;
						legalCaptures.add(legalCapture);
						canCapture = false; // if the right capture is legal, we add the square the capturing checker would move to. I need to add code in the move function such that if the move is a capture, the other checker is killed.
					}
					if (c.white && this.rank + 1 == c.rank && Character.compare(this.file, c.file) == 1) {
						canCapture = true; // if we find a black checker 1 up and 1 left, we assume we can capture it.
						for (stones s : stones.getSurvivors()) {
							if (this.file - 97 ==  6 || (s.rank == this.rank + 2 && Character.compare(this.file, s.file) == 2)) {
								canCapture = false; // but if there is a checker 2 up and 2 left defending it, or the capturing checker is on the b file, we cannot capture.
							}
						}
						if (canCapture) {
							int[] legalCapture = new int[2];
							legalCapture[0] = this.file - 99;
							legalCapture[1] = this.rank + 2;
							legalCaptures.add(legalCapture);
							canCapture = false; // if the left capture is legal, we add the square the capturing checker would move to. I need to add code in the move function such that if the move is a capture, the other checker is killed.
						}
					}
				}
			}
		}
		return legalCaptures;
	}
				
}